package io.github.groupease.db;

import com.google.inject.persist.Transactional;
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

public class ChannelJoinRequestDao
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EntityManager entityManager;

    /**
     * Injectable constructor.
     *
     * @param entityManager
     */
    @Inject
    public ChannelJoinRequestDao(@Nonnull EntityManager entityManager)
    {
        this.entityManager = requireNonNull(entityManager);
    }

    /**
     * Creates a new {@link ChannelJoinRequest} from the provided values and stores it in the database
     * @param channelId The ID of the channel the user is requesting to join
     * @param userId The ID of the user requesting to join the channel
     * @param comments Free text comments supplied by the user for the channel owners to read
     * @return The newly created join request including assigned unique ID
     */
    @Transactional
    public ChannelJoinRequest create(long channelId, long userId, String comments) {
        LOGGER.debug("ChannelJoinRequestDao.create(channel={}, user={})", channelId, userId);

        Query insertQuery = entityManager.createNativeQuery(
                "INSERT INTO ChannelJoinRequest (ChannelID, UserID, Comments, LastUpdate) VALUES (?, ?, ?, current_timestamp) RETURNING ID");
        insertQuery.setParameter(1, channelId);
        insertQuery.setParameter(2, userId);
        insertQuery.setParameter(3, comments);
        BigInteger newId = (BigInteger)insertQuery.getSingleResult();

        return entityManager.find(ChannelJoinRequest.class, newId.longValue());
    }

    /**
     * Gets the {@link ChannelJoinRequest} a user submitted to a channel, if any
     * @param channelId The ID of the channel the user wants to join
     * @param userId The ID of the user wanting to join the channel
     * @return The matching join request, or null if it was not found
     */
    public ChannelJoinRequest getForUser(long channelId, long userId)
    {
        LOGGER.debug("ChannelJoinRequestDao.getForUser(channel={}, user={})", channelId, userId);

        TypedQuery<ChannelJoinRequest> query = entityManager.createQuery(
                "SELECT cjr FROM ChannelJoinRequest cjr WHERE channelId=?1 and userId=?2",
                ChannelJoinRequest.class);
        query.setParameter(1, channelId);
        query.setParameter(2, userId);

        // getSingleResult() throws if there is no record, so this is a workaround
        List<ChannelJoinRequest> results = query.getResultList();
        if(results.isEmpty())
        {
            return null;
        }

        return results.get(0);
    }

    /**
     * Gets a specific {@link ChannelJoinRequest} by its unique ID
     * @param id The ID of the join request
     * @return The join request matching the ID or null if the ID doesn't exist
     */
    public ChannelJoinRequest getById(long id)
    {
        LOGGER.debug("ChannelJoinRequestDao.getForUser({})", id);

        return entityManager.find(ChannelJoinRequest.class, id);
    }

    /**
     * Gets a list of all {@link ChannelJoinRequest}s in a channel
     * @param channelId The ID of the channel to retrieve join requests for
     * @return The list of matching join requests. The list will be empty if none were found
     */
    @Nonnull
    public List<ChannelJoinRequest> listInChannel(long channelId)
    {
        LOGGER.debug("ChannelJoinRequestDao.listInChannel({})", channelId);

        TypedQuery<ChannelJoinRequest> query = entityManager.createQuery(
                "SELECT cjr FROM ChannelJoinRequest cjr WHERE channelId=?1",
                ChannelJoinRequest.class);
        query.setParameter(1, channelId);

        return query.getResultList();
    }

    /**
     * Gets all {@link ChannelJoinRequest}s in a channel for a single user
     * @param channelId The ID of the channel
     * @param currentUser The user that is trying to join the channel
     * @return The list of join requests. The list will be empty if no matching requests were found
     */
    @Nonnull
    public List<ChannelJoinRequest> listInChannel(long channelId, @Nonnull GroupeaseUser currentUser)
    {
        LOGGER.debug("ChannelJoinREquestDao.listInChannel(channel={}, user={}", channelId, currentUser.getId());

        TypedQuery<ChannelJoinRequest> query = entityManager.createQuery(
                "SELECT cjr FROM ChannelJoinRequest cjr WHERE channelId=?1 AND requestor.id = ?2",
                ChannelJoinRequest.class);
        query.setParameter(1, channelId);
        query.setParameter(2, currentUser.getId());

        return query.getResultList();
    }

    /**
     * Removes a {@link ChannelJoinRequest} from the database
     * @param request The join request to remove. It must have previously been retrieved, not manually constructed
     */
    @Transactional
    public void delete(@Nonnull ChannelJoinRequest request)
    {
        LOGGER.debug("ChannelJoinRequestDao.delete()");

        entityManager.remove(request);
    }
}
