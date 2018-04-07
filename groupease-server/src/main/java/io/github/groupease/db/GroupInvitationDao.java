package io.github.groupease.db;

import com.google.inject.persist.Transactional;
import io.github.groupease.model.GroupInvitation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.invoke.MethodHandles;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class GroupInvitationDao
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EntityManager entityManager;

    /**
     * Injectable constructor.
     *
     * @param entityManager
     */
    @Inject
    public GroupInvitationDao(@Nonnull EntityManager entityManager)
    {
        this.entityManager = requireNonNull(entityManager);
    }

    /**
     * Gets the list of {@link GroupInvitation}s sent to a user in a channel
     * @param userId The ID of the user receiving the invitations
     * @param channelId The ID of the channel containing the groups
     * @return The matching list of invitations. An empty list will be returned if there are no results
     */
    @Nonnull
    public List<GroupInvitation> list(long userId, long channelId)
    {
        LOGGER.debug("GroupInvitationDao.list(user={}, channel={})", userId, channelId);

        TypedQuery<GroupInvitation> query = entityManager.createQuery(
                "SELECT gi FROM GroupInvitation gi WHERE gi.group.channelId = :channelId AND gi.recipient.id = :userId ORDER BY lastUpdate DESC",
                GroupInvitation.class);
        query.setParameter("channelId", channelId);
        query.setParameter("userId", userId);

        return query.getResultList();
    }

    /**
     * Gets the {@link GroupInvitation} matching the unique ID provided
     * @param invitationId The unique ID of the group invitation
     * @return The group invitation or null if the ID wasn't found in the database
     */
    public GroupInvitation get(long invitationId)
    {
        LOGGER.debug("GroupInvitationDao.get({})", invitationId);

        TypedQuery<GroupInvitation> query = entityManager.createQuery(
                "SELECT gi FROM GroupInvitation gi WHERE gi.id = :invitationId",
                GroupInvitation.class);
        query.setParameter("invitationId", invitationId);

        List<GroupInvitation> results = query.getResultList();
        if(results.isEmpty())
        {
            return null;
        }

        return results.get(0);
    }

    /**
     * Gets the {@link GroupInvitation} with the unique ID if it was sent to a given user in a given channel
     * @param invitationId The unique ID of the invitation
     * @param userId The ID of the user that was supposed to receive the invitation
     * @param channelId The ID of the channel the group being invited to is in
     * @return The invitation if all criteria were met, otherwise null
     */
    public GroupInvitation get(long invitationId, long userId, long channelId)
    {
        LOGGER.debug("GroupInvitationDao.get({})", invitationId);

        TypedQuery<GroupInvitation> query = entityManager.createQuery(
                "SELECT gi FROM GroupInvitation gi WHERE gi.id = :invitationId AND gi.recipient.id = :userID AND gi.group.channelId = :channelId",
                GroupInvitation.class);
        query.setParameter("invitationId", invitationId);
        query.setParameter("userID", userId);
        query.setParameter("channelId", channelId);

        List<GroupInvitation> results = query.getResultList();
        if(results.isEmpty())
        {
            return null;
        }

        return results.get(0);
    }

    /**
     * Deletes a {@link GroupInvitation} from the database
     * @param invitation The invitation to purge - it must have been previously retrieved and not constructed
     */
    @Transactional
    public void delete(GroupInvitation invitation)
    {
        LOGGER.debug("GroupInvitationDao.delete({})", invitation.getId());

        entityManager.remove(invitation);
    }
}
