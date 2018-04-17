package io.github.groupease.channelmember;

import java.util.List;

import javax.annotation.Nonnull;

import io.github.groupease.model.Member;

/**
 * Business service for operations on {@link Member} instances.
 */
public interface ChannelMemberService {

    /**
     * Fetch all {@link Member} instances for the channel ID.
     *
     * @param channelId of the channel from which to fetch members.
     * @return the list of all {@link Member} instances in the channel.
     */
    @Nonnull
    List<Member> list(
            long channelId
    );

    /**
     * Fetch a {@link Member} instance by its ID.
     *
     * @param memberId the ID of the {@link Member} instance to fetch.
     * @return the matching {@link Member} instance.
     */
    @Nonnull
    Member getByMemberId(
            long memberId
    );

    /**
     * Fetch a {@link Member} instance for the provided channel and authenticated user.
     *
     * @param channelId of the channel from which to fetch members.
     * @return the matching {@link Member} instance.
     */
    @Nonnull
    Member getForCurrentUser(
            long channelId
    );

    /**
     * Fetch a {@link Member} instance for the provided channel and user.
     *
     * @param channelId of the channel from which to fetch members.
     * @param userId of the user to look up.
     * @return the matching {@link Member} instance.
     */
    @Nonnull
    Member getForUser(
            long channelId,
            long userId
    );

    /**
     * Save updates to an existing {@link Member} instance.
     *
     * @param toUpdate the {@link Member} instance to save.
     * @return the updated {@link Member} instance.
     */
    @Nonnull
    Member update(
            @Nonnull Member toUpdate
    );

    /**
     * Creates a member in the specified channel.
     *
     * @param channelId of the channel within which to create a member.
     * @param userId of the user from which to create a member.
     * @param owner true if the member should be a channel owner, otherwise false.
     * @return the new {@link Member} instance.
     */
    @Nonnull
    Member create(
            long channelId,
            long userId,
            boolean owner
    );

    /**
     * Delete an existing {@link Member} instance.
     *
     * @param memberId the ID of the {@link Member} instance to delete.
     */
    void delete(
            long memberId
    );

}
