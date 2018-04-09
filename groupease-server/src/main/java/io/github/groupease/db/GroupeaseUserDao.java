package io.github.groupease.db;

import com.google.inject.persist.Transactional;
import io.github.groupease.model.GroupeaseUser;
import io.github.groupease.user.GroupeaseUserDto;
import io.github.groupease.user.UserDao;
import io.github.groupease.user.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.invoke.MethodHandles;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class GroupeaseUserDao implements UserDao
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
     * Fetch all {@link GroupeaseUser} instances in the system.
     *
     * @return the list of all {@link GroupeaseUser} instances.
     */
    @Nonnull
    @Override
    public List<GroupeaseUser> list()
    {
        LOGGER.debug("GroupeaseUserDao.list()");

        TypedQuery<GroupeaseUser> query = entityManager.createQuery(
                "SELECT gu FROM GroupeaseUser gu ORDER BY gu.name ASC", GroupeaseUser.class);

        return query.getResultList();
    }

    /**
     * Retrieves the GroupeaseUser for the given identifier
     * @param userId The locally unique id for the GroupeaseUser. This is not the Auth0 id
     * @return The stored GroupeaseUser for the given ID
     */
    @Nonnull
    @Override
    public GroupeaseUser getById(long userId)
    {
        LOGGER.debug("GroupeaseUserDao.getById({})", userId);

        GroupeaseUser user = entityManager.find(GroupeaseUser.class, userId);
        if(user == null)
        {
            throw new UserNotFoundException();
        }

        return user;
    }

    /**
     * Save a {@link GroupeaseUser} instance.
     * This method may update an existing instance or create a new one.
     *
     * @param toSave the {@link GroupeaseUserDto} instance to save.
     * @return the saved {@link GroupeaseUserDto} instance.
     */
    @Nonnull
    @Override
    @Transactional
    public GroupeaseUser save(@Nonnull GroupeaseUserDto toSave)
    {
        LOGGER.debug("GroupeaseUserDao.save({})", toSave.getProviderUserId());

        // Look for an existing user with the same provider ID. If found, overwrite existing record with
        // the new profile data, otherwise create new user in the database
        GroupeaseUser existingUser = getByProviderId(toSave.getProviderUserId());
        if(existingUser == null)
        {
            LOGGER.debug("GroupeaseUserDao.save: no matching provider ID in database, creating new record");
            return create(toSave);
        }

        LOGGER.debug("GroupeaseUserDao.save: merging new profile data into existing record");
        return entityManager.merge(new GroupeaseUser(toSave, existingUser.getId()));
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

        // Provider ID is a unique column in the DB so if the list is not empty,
        // then it is guaranteed to be exactly one result
        return results.get(0);
    }
}
