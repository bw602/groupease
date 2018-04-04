package io.github.groupease.restendpoint;

import com.codahale.metrics.annotation.Timed;
import io.github.groupease.auth.CurrentUserId;
import io.github.groupease.db.DataAccess;
import io.github.groupease.exception.*;
import io.github.groupease.model.Channel;
import io.github.groupease.model.ChannelInvitation;
import io.github.groupease.model.Member;
import io.github.groupease.model.GroupeaseUser;
import io.github.groupease.user.retrieval.UserRetrievalService;
import io.github.groupease.util.ChannelInvitationCreateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Path("users/{userId}/channel-invitations")
@Produces(MediaType.APPLICATION_JSON)
public class ChannelInvitationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final DataAccess dataAccess;
    private final Provider<String> currentUserIdProvider;
    private GroupeaseUser loggedOnUser;

    @Inject
    public ChannelInvitationService(@Nonnull DataAccess dataAccessObject,
                                    @Nonnull @CurrentUserId Provider<String> currentUserIdProvider)
    {
        dataAccess = dataAccessObject;
        this.currentUserIdProvider = currentUserIdProvider;
    }

    /**
     * Returns all {@link ChannelInvitation}s for a user
     * @param userId The ID of the user to retrieve invitations for
     * @return The list of invitations for the user. The list will be empty if there are no invitations
     */
    @GET
    @Timed
    public List<ChannelInvitation> list(@PathParam("userId") long userId)
    {
        LOGGER.debug("ChannelInvitationService.list({})", userId);

        verifyLoggedInUser(userId);

        return dataAccess.channelInvitation().listByUserId(userId);
    }

    /**
     * Gets a specific {@link ChannelInvitation}
     * @param userId The ID of the user the invitation was sent to
     * @param invitationId The ID of the invitation
     * @return The specified invitation
     */
    @GET
    @Path("{invitationId}")
    @Timed
    public ChannelInvitation get(@PathParam("userId") long userId, @PathParam("invitationId") long invitationId)
    {
        LOGGER.debug("ChannelInvitationService.get(user={}, invitation={})", userId, invitationId);

        verifyLoggedInUser(userId);

        ChannelInvitation result = dataAccess.channelInvitation().getByInvitationId(invitationId);
        if(result == null || result.getRecipient().getId() != userId)
        {
            throw new ChannelInvitationNotFound();
        }

        return result;
    }

    /**
     * Creates (sends) a new {@link ChannelInvitation} inviting a user to join a channel. Only an owner of the channel
     * can send an invitation
     * @param userId The ID of the user being invited
     * @param wrapper A {@link ChannelInvitationCreateWrapper} that contains the posted JSON with the user and channel
     *                to create the invitation for
     * @return The newly created invitation.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public ChannelInvitation create(@PathParam("userId") long userId, @Nonnull ChannelInvitationCreateWrapper wrapper)
    {
        LOGGER.debug("ChannelInvitationService.create(userUrl={}, userWrapper={}, channel={})",
                userId, wrapper.recipient.id, wrapper.channel.id);

        if(wrapper.recipient.id != userId)
        {
            throw new UserMismatchException("The user in the path and the user in the JSON don't match");
        }

        // Only a channel owner can create an invitation
        if(!isChannelOwner(wrapper.channel.id))
        {
            throw new NotChannelOwnerException();
        }

        // Verify that the recipient exists
        GroupeaseUser recipientProfile = dataAccess.userProfile().getById(wrapper.recipient.id);
        if(recipientProfile == null)
        {
            throw new GroupeaseUserNotFoundException();
        }

        // Check that the recipient hasn't already received an invitation to this channel
        List<ChannelInvitation> invitations =
                dataAccess.channelInvitation().listByUserAndChannel(userId, wrapper.channel.id);
        if(!invitations.isEmpty())
        {
            // If there is already an invitation, don't create another one. Return the existing one as a reminder
            return invitations.get(0);
        }

        // Verify that the recipient isn't already a member
        if(recipientProfile.getMemberList().stream()
                .anyMatch(member -> member.getChannel().getId() == wrapper.channel.id))
        {
            throw new AlreadyMemberException("Cannot invite a user to a channel the user is already a member of");
        }

        return dataAccess.channelInvitation()
                .create(loggedOnUser.getId(), recipientProfile.getId(), wrapper.channel.id);
    }

    /**
     * Processes a user's acceptance of an invitation to join a channel. A new {@link Member} object will be created
     * and the {@link ChannelInvitation} will be deleted
     * @param userId The ID of the user that the invitation was sent to
     * @param invitationId The ID of the invitation being accepted
     */
    @POST
    @Path("{invitationId}/acceptance")
    @Timed
    public void accept(@PathParam("userId") long userId, @PathParam("invitationId") long invitationId)
    {
        LOGGER.debug("ChannelInvitationService.accept({})", invitationId);

        verifyLoggedInUser(userId);

        // Check that the invitation exists and verify the invitation is for this user
        ChannelInvitation invitation = dataAccess.channelInvitation().getByInvitationId(invitationId);
        if(invitation == null || invitation.getRecipient().getId() != userId)
        {
            throw new ChannelInvitationNotFound();
        }

        // Create the new channel member
        dataAccess.member().create(invitation.getRecipient(), invitation.getChannel());

        // Clean up the invitation
        dataAccess.channelInvitation().delete(invitation);
    }

    /**
     * Processes a user's rejection of an invitation to join a channel. The {@link ChannelInvitation} will be deleted
     * @param userId The ID of the user that the invitation was sent to
     * @param invitationId The ID of the invitation
     */
    @POST
    @Path("{invitationId}/rejection")
    @Timed
    public void reject(@PathParam("userId") long userId, @PathParam("invitationId") long invitationId)
    {
        LOGGER.debug("ChannelInvitationService.reject({})", invitationId);

        verifyLoggedInUser(userId);

        // Check that the invitation exists and verify the invitation is for this user
        ChannelInvitation invitation = dataAccess.channelInvitation().getByInvitationId(invitationId);
        if(invitation == null || invitation.getRecipient().getId() != userId)
        {
            throw new ChannelInvitationNotFound();
        }

        // Clean up the invitation
        dataAccess.channelInvitation().delete(invitation);
    }

    /**
     * Deletes a {@link ChannelInvitation}. Only an owner of the channel may delete an invitation
     * @param userId The ID of the user the invitation was sent to
     * @param invitationId The ID of the invitation
     */
    @DELETE
    @Path("{invitationId}")
    @Timed
    public void delete(@PathParam("userId") long userId, @PathParam("invitationId") long invitationId)
    {
        LOGGER.debug("ChannelInvitationService.delete(user={}, invitation={})", userId, invitationId);

        // Make sure this invitation actually exists
        ChannelInvitation invitation = dataAccess.channelInvitation().getByInvitationId(invitationId);
        if(invitation == null)
        {
            throw new ChannelInvitationNotFound();
        }

        // The calling user must be an owner of the channel that the invitation is for
        if(!isChannelOwner(invitation.getChannel()))
        {
            throw new NotChannelOwnerException();
        }

        dataAccess.channelInvitation().delete(invitation);
    }

    private void verifyLoggedInUser(@PathParam("userId") long userId)
    {
        if(loggedOnUser == null)
        {
            loggedOnUser = dataAccess.userProfile().getByProviderId(currentUserIdProvider.get());
            if(loggedOnUser == null)
            {
                LOGGER.debug("verifyLoggedInUser({}): No profile for logged on user in database", userId);
                throw new GroupeaseUserNotFoundException("Currently logged in user has no profile");
            }
        }
        if(loggedOnUser.getId() != userId)
        {
            throw new UserMismatchException("Logged in user does not match the provided user id");
        }
    }

    private boolean isChannelOwner(Channel channel)
    {
        return isChannelOwner(channel.getId());
    }

    private boolean isChannelOwner(long channelId)
    {
        if(loggedOnUser == null) {
            loggedOnUser = dataAccess.userProfile().getByProviderId(currentUserIdProvider.get());
        }

        return loggedOnUser.getMemberList().stream()
                    .anyMatch(member -> member.isOwner() && member.getChannel().getId() == channelId);
    }
}
