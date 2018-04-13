package io.github.groupease.db;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import static java.util.Objects.requireNonNull;

import com.google.inject.persist.Transactional;
import io.github.groupease.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class MemberDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EntityManager entityManager;

    /**
     * Injectable constructor.
     *
     * @param entityManager
     */
    @Inject
    public MemberDao(@Nonnull EntityManager entityManager)
    {
        this.entityManager = requireNonNull(entityManager);
    }

    /**
     * Creates a new {@link Member} in a channel
     * @param userProfile The {@link GroupeaseUser} of the user joining the channel
     * @param channel The channel the user is joining
     * @return The newly created member object
     */
    @Transactional
    public Member create(@Nonnull GroupeaseUser userProfile, @Nonnull Channel channel)
    {
        LOGGER.debug("MemberDao.create(user={}, channel={}) called", userProfile.getName(), channel.getName());

        Member newMember = new Member();
        newMember.setGroupeaseUser(userProfile);
        newMember.setChannel(channel);

        entityManager.persist(newMember);

        return newMember;
    }

    /**
     * Creates a new {@link Member} in a channel
     * @param userId The unique ID of the user in the database being added to the channel
     * @param channelId The unique ID of the channel in the database
     * @param isOwner Flag indicating whether the user should be an owner of the channel
     * @return The newly created member object
     */
    @Transactional
    public Member create(long userId, long channelId, boolean isOwner)
    {
        LOGGER.debug("MemberDao.create(userId={}, channelId={}, isOwner={}) called", userId, channelId, isOwner);

        Query insertQuery = entityManager.createNativeQuery(
                "INSERT INTO Member (ChannelID, UserID, IsOwner, LastUpdate) VALUES (:channelId, :userId, :isOwner, CURRENT_TIMESTAMP)");
        insertQuery.setParameter("channelId", channelId);
        insertQuery.setParameter("userId", userId);
        insertQuery.setParameter("isOwner", isOwner);
        insertQuery.executeUpdate();

        return getById(userId, channelId);
    }

    /**
     * Deletes a {@link Member} from the database which prevents a user from using the associated channel further
     * @param member The previously retrieved member object. Do not supply a manually constructed member
     */
    @Transactional
    public void delete(@Nonnull Member member)
    {
        LOGGER.debug("MemberDao.delete({}) called", member.getId());

        entityManager.remove(member);
    }

    /**
     * Retrieves a specific {@link Member} in a channel
     * @param userId The ID of the user
     * @param channelId The ID of the channel
     * @return The matching member or null if none could be found
     */
    public Member getById(long userId, long channelId)
    {
        LOGGER.debug("MemberDao.getById(member={}, channel={})", userId, channelId);

        TypedQuery<Member> query = entityManager.createQuery(
                "SELECT m FROM Member m WHERE m.userProfile.id=?1 AND m.channel.id=?2", Member.class);
        query.setParameter(1, userId);
        query.setParameter(2, channelId);

        List<Member> result = query.getResultList();
        if(result == null || result.isEmpty())
        {
            return null;
        }
        return result.get(0);
    }
}
