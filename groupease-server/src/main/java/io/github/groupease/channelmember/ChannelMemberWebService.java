package io.github.groupease.channelmember;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import io.github.groupease.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * REST-ful web service for channel {@link Member} instances.
 */
@Path("channels/{channelId}/members")
@Produces(MediaType.APPLICATION_JSON)
@Immutable
public class ChannelMemberWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ChannelMemberService channelMemberService;

    /**
     * Injectable constructor.
     *
     * @param channelMemberService logic layer for {@link Member} operations.
     */
    @Inject
    public ChannelMemberWebService(
            @Nonnull ChannelMemberService channelMemberService
    ) {
        this.channelMemberService = requireNonNull(channelMemberService);
    }

    /**
     * Fetch all {@link Member} instances for the channel ID.
     *
     * @param channelId of the channel from which to fetch members.
     * @return the list of all {@link Member} instances in the channel.
     */
    @GET
    @Timed
    @Nonnull
    public List<Member> list(
            @PathParam("channelId") Long channelId
    ) {
        LOGGER.debug("ChannelMemberWebService.list called with channelId: '{}'.", channelId);
        return this.channelMemberService.list(channelId);
    }

    /**
     * Fetch a {@link Member} instance by its ID.
     *
     * @param channelId of the channel from which to fetch members.
     * @param memberId the ID of the {@link Member} instance to fetch.
     * @return the matching {@link Member} instance.
     */
    @GET
    @Path("{memberId}")
    @Timed
    @Nonnull
    public Member getByMemberId(
            @PathParam("channelId") Long channelId,
            @PathParam("memberId") Long memberId
    ) {
        LOGGER.debug(
                "ChannelMemberWebService.getByMemberId called with channelId '{}' and memberId '{}'.",
                channelId,
                memberId
        );
        return this.channelMemberService.getByMemberId(memberId);
    }

    /**
     * Fetch a {@link Member} instance for the authenticated user.
     *
     * @param channelId of the channel from which to fetch members.
     * @return the matching {@link Member} instance.
     */
    @GET
    @Path("current")
    @Timed
    @Nonnull
    public Member getForCurrentUser(
            @PathParam("channelId") Long channelId
    ) {
        LOGGER.debug(
                "ChannelMemberWebService.getForCurrentUser called with channelId '{}'.",
                channelId
        );
        return this.channelMemberService.getForCurrentUser(channelId);
    }

    /**
     * Save updates to an existing {@link Member} instance.
     *
     * @param channelId the ID of the channel containing the member.
     * @param memberId the ID of the {@link Member} instance to save.
     * @param toUpdate the {@link Member} instance to save.
     * @return the updated {@link Member} instance.
     */
    @PUT
    @Path("{memberId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    @Nonnull
    public Member update(
            @PathParam("channelId") Long channelId,
            @PathParam("memberId") Long memberId,
            @Nonnull Member toUpdate
    ) {
        LOGGER.debug(
                "ChannelMemberWebService.update called with channelId '{}', memberId '{}', & toUpdate '{}'.",
                channelId,
                memberId,
                toUpdate
        );

        if (memberId.equals(toUpdate.getId())) {
            return channelMemberService.update(toUpdate);
        }

        throw new ChannelMemberIdMismatchException("Provided Channel Member ID does not match URL ID.");
    }

    /**
     * Delete an existing {@link Member} instance.
     *
     * @param memberId the ID of the {@link Member} instance to delete.
     */
    @DELETE
    @Path("{memberId}")
    @Timed
    public void delete(
            @PathParam("memberId") Long memberId
    ) {
        LOGGER.debug("ChannelMemberWebService.delete called with memberId `{}`.", memberId);
        channelMemberService.delete(memberId);
    }

}
