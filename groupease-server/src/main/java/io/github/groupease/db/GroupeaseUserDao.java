package io.github.groupease.db;

import io.github.groupease.model.GroupeaseUser;
import io.github.groupease.user.GroupeaseUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.invoke.MethodHandles;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class GroupeaseUserDao
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EntityManager entityManager;

    /**
     * Injectable constructor.
     *
     * @param entityManager
     */
    @Inject
    public GroupeaseUserDao(@Nonnull EntityManager entityManager)
    {
        this.entityManager = requireNonNull(entityManager);
    }

    /**
     * Stores a new GroupeaseUser in the database
     * @param newGroupeaseUser The GroupeaseUser to store. The ID field should be null. It will be assigned a unique value
     * @return Returns the provided GroupeaseUser with updated ID and timestamp
     */
    public GroupeaseUser create(@Nonnull GroupeaseUser newGroupeaseUser)
    {
        LOGGER.debug("GroupeaseUserDao.create()");

        entityManager.persist(newGroupeaseUser);

        return newGroupeaseUser;
    }

    /**
     * Stores a new {@link GroupeaseUser} in the database created from a profile supplied by the authentication service
     * @param authZeroProfile The user profile supplied by Auth0
     * @return The newly created and saved user object
     */
    public GroupeaseUser create(@Nonnull GroupeaseUserDto authZeroProfile)
    {
        LOGGER.debug("GroupeaseUserDao.create(auth0 userid={}", authZeroProfile.getProviderUserId());

        GroupeaseUser newGroupeaseUser = new GroupeaseUser(authZeroProfile);

        entityManager.persist(newGroupeaseUser);

        return newGroupeaseUser;
    }

    /**
     * Retrieves the GroupeaseUser for the given identifier
     * @param userId The locally unique id for the GroupeaseUser. This is not the Auth0 id
     * @return The stored GroupeaseUser for the given ID or null for no match
     */
    public GroupeaseUser getById(long userId)
    {
        LOGGER.debug("GroupeaseUserDao.getForUser({})", userId);

        return entityManager.find(GroupeaseUser.class, userId);
    }

    /**
     * Retrieves the GroupeaseUser for the given identifier
     * @param authId the string identifying the user provided by the Auth0 service
     * @return The stored GroupeaseUser for the given ID or null for no match
     */
    public GroupeaseUser getByProviderId(@Nonnull String authId)
    {
        LOGGER.debug("GroupeaseUserDao.getByProviderId({})", authId);

        TypedQuery<GroupeaseUser> query = entityManager.createQuery(
                "SELECT up FROM GroupeaseUser up WHERE providerUserId = ?1", GroupeaseUser.class);
        query.setParameter(1, authId);

        // getSingleResult() throws if there is no record, so this is a workaround
        List<GroupeaseUser> results = query.getResultList();
        if(results.isEmpty())
        {
            return null;
        }

        return results.get(0);
    }
}
