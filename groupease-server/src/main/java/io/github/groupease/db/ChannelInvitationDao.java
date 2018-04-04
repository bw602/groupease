package io.github.groupease.db;

import io.github.groupease.model.Channel;
import io.github.groupease.model.ChannelInvitation;
import io.github.groupease.model.ChannelJoinRequest;
import io.github.groupease.model.GroupeaseUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ChannelInvitationDao
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EntityManager entityManager;

    /**
     * Injectable constructor.
     *
     * @param entityManager
     */
    @Inject
    public ChannelInvitationDao(@Nonnull EntityManager entityManager)
    {
        this.entityManager = requireNonNull(entityManager);
    }

    /**
     * Retrieves all {@link ChannelInvitation}s sent to a particular user
     * @param userId The Groupease user ID
     * @return The list of invitations
     */
    public List<ChannelInvitation> listByUserId(long userId)
    {
        LOGGER.debug("ChannelInvitationDao.listByUserId({})", userId);

        TypedQuery<ChannelInvitation> query = entityManager.createQuery(
                "SELECT ci FROM ChannelInvitation ci WHERE ci.recipient.id = ?1 ORDER BY ci.lastUpdate",
                ChannelInvitation.class);
        query.setParameter(1, userId);

        return query.getResultList();
    }

    /**
     * Retrieves all {@link ChannelInvitation}s sent to a user for a channel
     * @param userId The Groupease user ID
     * @param channelId The channel ID
     * @return The list of invitations
     */
    public List<ChannelInvitation> listByUserAndChannel(long userId, long channelId)
    {
        LOGGER.debug("ChannelInvitationDao.getByUserAndChannel(user={}, channel={})", userId, channelId);

        TypedQuery<ChannelInvitation> query = entityManager.createQuery(
                "SELECT ci FROM ChannelInvitation ci WHERE ci.recipient.id = ?1 AND ci.channel.id = ?2",
                ChannelInvitation.class);
        query.setParameter(1, userId);
        query.setParameter(2, channelId);

        return query.getResultList();
    }

    /**
     * Retrieves a specific {@link ChannelInvitation}
     * @param invitationId The ID of the invitation
     * @return The invitation matching the ID or null if not found
     */
    public ChannelInvitation getByInvitationId(long invitationId)
    {
        LOGGER.debug("ChannelInvitationDao.getByInvitationId({})", invitationId);

        return entityManager.find(ChannelInvitation.class, invitationId);
    }

    /**
     * Creates a new {@link ChannelInvitation} in the database
     * @param senderId The ID of the user that sent the invitation
     * @param recipientId The ID of the invitation recipient
     * @param channelId The ID of the channel the recipient is being invited to
     * @return The newly created ChannelInvitation object
     */
    public ChannelInvitation create(long senderId, long recipientId, long channelId)
    {
        LOGGER.debug("ChannelInvitationDao.create()");

        Query insertQuery = entityManager.createNativeQuery(
                "INSERT INTO ChannelInvitation (SenderID, RecipientID, ChannelID, LastUpdate) VALUES (?, ?, ?, current_timestamp) RETURNING ID");
        insertQuery.setParameter(1, senderId);
        insertQuery.setParameter(2, recipientId);
        insertQuery.setParameter(3, channelId);
        BigInteger newId = (BigInteger)insertQuery.getSingleResult();

        return entityManager.find(ChannelInvitation.class, newId.longValue());
    }

    /**
     * Permanently removes the {@link ChannelInvitation} from the database
     * @param invitation The ChannelInvitation object to remove. It must have previously been retrieved (not constructed)
     */
    public void delete(ChannelInvitation invitation)
    {
        LOGGER.debug("ChannelInvitationDao.delete({})", invitation.getId());

        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        entityManager.remove(invitation);
        entityManager.flush();
        et.commit();
    }
}
