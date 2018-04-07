package io.github.groupease.restendpoint;

import com.codahale.metrics.annotation.Timed;
import io.github.groupease.auth.CurrentUserId;
import io.github.groupease.user.UserNotFoundException;
import io.github.groupease.db.ChannelJoinRequestDao;
import io.github.groupease.db.DataAccess;
import io.github.groupease.exception.*;
import io.github.groupease.model.ChannelJoinRequest;
import io.github.groupease.model.Member;
import io.github.groupease.model.GroupeaseUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.invoke.MethodHandles;
import java.util.List;
import io.github.groupease.util.CommentWrapper;

@Path("channels/{channelId}/join-requests")
@Produces(MediaType.APPLICATION_JSON)
public class ChannelJoinRequestService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final DataAccess dataAccess;
    private final Provider<String> currentUserIdProvider;

    @Inject
    public ChannelJoinRequestService(@Nonnull DataAccess dataAccessObject,
                                     @Nonnull @CurrentUserId Provider<String> currentUserIdProvider)
    {
        dataAccess = dataAccessObject;
        this.currentUserIdProvider = currentUserIdProvider;
    }

    /**
     * Gets a list of all {@link ChannelJoinRequest}s for the channel if the logged on user is a channel owner. If not,
     * only join requests created by the user are returned (Note: only one join request per user per channel is
     * permitted)
     * @param channelId The ID of the channel to get join requests for
     * @return The list of join requests in the channel, filtered according to the ownership rules. The list will be
     * empty if no requests match the criteria
     */
    @GET
    @Timed
    public List<ChannelJoinRequest> list(@PathParam("channelId") long channelId)
    {
        LOGGER.debug("ChannelJoinRequestService.list()");

        if(isChannelOwner(channelId))
        {
            // When the user is a channel owner, return all requests
            return dataAccess.channelJoinRequest().listInChannel(channelId);
        }
        else
        {
            // Return only a request from this user
            GroupeaseUser userProfile = dataAccess.userProfile().getByProviderId(currentUserIdProvider.get());
            return dataAccess.channelJoinRequest().listInChannel(channelId, userProfile);
        }
    }

    /**
     * Retrieves a specific {@link ChannelJoinRequest}. Join requests can only be retrieved by the recipient or an
     * owner of the channel
     * @param channelId The ID of the channel
     * @param requestId The ID of the join request
     * @return The requested join request
     */
    @GET
    @Path("{requestId}")
    @Timed
    public ChannelJoinRequest get(@PathParam("channelId") long channelId, @PathParam("requestId") long requestId)
    {
        LOGGER.debug("ChannelJoinRequestService.list()");

        // Retrieve the request by ID and make sure that the requested ID is from the same channel
        // Indicated in the URL
        ChannelJoinRequest request = dataAccess.channelJoinRequest().getById(requestId);
        if(request == null || request.getChannelId() != channelId)
        {
            throw new ChannelJoinRequestNotFound();
        }

        // Verify the logged in user has permission to see this request
        if(isChannelOwner(channelId))
        {
            // When the user is a channel owner, they can retrieve any request in the channel
            return request;
        }
        else
        {
            // Return only a request from this user
            GroupeaseUser userProfile = dataAccess.userProfile().getByProviderId(currentUserIdProvider.get());
            if(userProfile.getId().equals(request.getRequestor().getId()))
            {
                return request;
            }
        }

        // Failed authentication
        throw new NotSenderException("User must own channel or request to retrieve it");
    }

    /**
     * Creates (sends) a new {@link ChannelJoinRequest}. A user can only create one join request per channel and
     * cannot create a join request for a channel they are already a member of
     * @param channelId The ID of the channel the logged on user wishes to join
     * @param wrapper A {@link CommentWrapper} that contains free text comments explaining the request (transmitted as JSON)
     * @return The newly created join request.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public ChannelJoinRequest create(@PathParam("channelId") long channelId, @Nonnull CommentWrapper wrapper)
    {
        LOGGER.trace("ChannelJoinRequestService.create(channel={}, comments={})", channelId, wrapper.comments);

        GroupeaseUser profile = dataAccess.userProfile().getByProviderId(currentUserIdProvider.get());
        if(profile == null)
        {
            throw new UserNotFoundException();
        }

        // Check if an existing join request for this user and channel already exists
        ChannelJoinRequest existing = dataAccess.channelJoinRequest().getForUser(channelId, profile.getId());
        if(existing != null)
        {
            // The user already has a join request for this channel, so don't allow another to be created
            // Just return the one that already exists
            return existing;
        }

        // Check if the user is already a member of the channel
        if(profile.getMemberList().stream().anyMatch(match -> match.getChannel().getId() == channelId))
        {
            throw new AlreadyMemberException("User cannot request to join channel the user is already a member of");
        }

        // No existing join request in this channel for this user, so create one and return it
        return dataAccess.channelJoinRequest().create(channelId, profile.getId(), wrapper.comments);
    }

    /**
     * Accepts a {@link ChannelJoinRequest} and adds the sender as a new {@link Member} of the channel. Only channel
     * owners can accept join requests. The join request will be deleted
     * @param channelId The ID of the channel being requested to join
     * @param requestId The ID of the request
     */
    @POST
    @Path("{requestId}/acceptance")
    @Timed
    public void accept(@PathParam("channelId") long channelId,  @PathParam("requestId") long requestId)
    {
        LOGGER.debug("ChannelJoinRequestService.accept(channel={}, request={})", channelId, requestId);

        // Find the request and validate that the request is really for this channel
        ChannelJoinRequest request = dataAccess.channelJoinRequest().getById(requestId);
        if(request == null || request.getChannelId() != channelId)
        {
            throw new ChannelJoinRequestNotFound();
        }

        // User must be a channel owner to accept
        if(!isChannelOwner(channelId))
        {
            throw new NotChannelOwnerException();
        }

        // Current user is a channel owner so create a new member object
        dataAccess.beginTransaction();
        Member newMember = dataAccess.member().create(request.getRequestor(), request.getChannel());

        // Now that it's confirmed that the user is now a member of the channel, we can cleanup the request
        dataAccess.channelJoinRequest().delete(request);
        dataAccess.commitTransaction();
    }

    /**
     * Rejects a request to a join a channel. The request will be deleted. Only channel owners can reject requests
     * @param channelId The ID of the channel that request was to join
     * @param requestId The ID of the request
     */
    @POST
    @Path("{requestId}/rejection")
    @Timed
    public void reject(@PathParam("channelId") long channelId, @PathParam("requestId") long requestId)
    {
        LOGGER.debug("ChannelJoinRequestService.reject(channel={}, request={})", channelId, requestId);

        // Find the request and validate that the request is really for this channel
        ChannelJoinRequest request = dataAccess.channelJoinRequest().getById(requestId);
        if(request == null || request.getChannelId() != channelId)
        {
            throw new ChannelJoinRequestNotFound();
        }

        // User must be a channel owner to reject
        if(!isChannelOwner(channelId))
        {
            throw new NotChannelOwnerException();
        }

        // At least for now, reject is a delete (no status field etc. recording the change)
        // In the future, maybe send an email? (Would require recording opt-in to receive email
        // plus needing an SMTP gateway)
        dataAccess.beginTransaction();
        dataAccess.channelJoinRequest().delete(request);
        dataAccess.commitTransaction();
    }

    /**
     * Deletes (cancels) a pending {@link ChannelJoinRequest}. Only the sender of a request can delete it
     * @param channelId The ID of the channel the request was sent to
     * @param requestId The ID of the join request
     */
    @DELETE
    @Path("{requestId}")
    @Timed
    public void delete(@PathParam("channelId") long channelId, @PathParam("requestId") long requestId)
    {
        LOGGER.debug("ChannelJoinRequestService.delete(channel={} request={}", channelId, requestId);

        // Only the creator of the join request can delete it
        // Channel owners must accept or reject

        ChannelJoinRequest request = dataAccess.channelJoinRequest().getById(requestId);
        if(request != null)
        {
            GroupeaseUser currentUser = dataAccess.userProfile().getByProviderId(currentUserIdProvider.get());
            if(currentUser == null)
            {
                throw new UserNotFoundException("Currently logged in user has no profile");
            }
            if(request.getRequestor().getId().equals(currentUser.getId())) {
                throw new NotSenderException();
            }
            dataAccess.beginTransaction();
            dataAccess.channelJoinRequest().delete(request);
            dataAccess.commitTransaction();
            return;
        }

        LOGGER.debug("ChannelJoinRequestService.delete: Didn't find request to delete");
        throw new ChannelJoinRequestNotFound();
    }

    private boolean isChannelOwner(long channelId)
    {
        GroupeaseUser currentUser = dataAccess.userProfile().getByProviderId(currentUserIdProvider.get());
        if(currentUser == null)
        {
            throw new UserNotFoundException("Currently logged in user has no profile");
        }

        return  currentUser.getMemberList().stream()
                .anyMatch(member -> member.isOwner() && member.getChannel().getId() == channelId);
    }
}
