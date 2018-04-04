package io.github.groupease.user;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * TODO
 */
@Immutable
public class JpaUserDao implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EntityManager entityManager;

    /**
     * Injectable constructor.
     *
     * @param entityManager to talk to the database.
     */
    @Inject
    public JpaUserDao(
            @Nonnull EntityManager entityManager
    ) {
        this.entityManager = requireNonNull(entityManager);
    }

    @Nonnull
    @Override
    @Timed
    public List<GroupeaseUser> list() {
        LOGGER.debug("JpaUserDao.list() called.");

        TypedQuery<GroupeaseUserDto> query = entityManager.createQuery(
                "SELECT dto FROM GroupeaseUserDto dto ORDER BY dto.name ASC",
                GroupeaseUserDto.class
        );
        List<GroupeaseUserDto> groupeaseUserDtoList = query.getResultList();

        List<GroupeaseUser> groupeaseUsers = new ArrayList<>();

        for (GroupeaseUserDto groupeaseUserDto : groupeaseUserDtoList) {
            groupeaseUsers.add(
                    GroupeaseUser.Builder.from(groupeaseUserDto)
                            .build()
            );
        }

        return groupeaseUsers;
    }

    @Nonnull
    @Override
    @Timed
    public GroupeaseUser getById(
            long id
    ) {
        LOGGER.debug("JpaUserDao.getById({}) called.", id);

        GroupeaseUserDto groupeaseUserDto = entityManager.find(
                GroupeaseUserDto.class,
                id
        );

        if (groupeaseUserDto == null) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }

        return GroupeaseUser.Builder.from(groupeaseUserDto)
                .build();
    }

    @Nonnull
    @Override
    @Timed
    public GroupeaseUser save(
            @Nonnull GroupeaseUserDto toSave
    ) {
        LOGGER.debug("JpaUserDao.save({}) called.", toSave);

        /* Find any existing user by providerUserId. */
        TypedQuery<GroupeaseUserDto> query = entityManager.createQuery(
                "SELECT dto FROM GroupeaseUserDto dto WHERE dto.providerUserId = :providerUserId",
                GroupeaseUserDto.class
        );

        query.setParameter("providerUserId", toSave.getProviderUserId());

        List<GroupeaseUserDto> matches = query.getResultList();

        if (matches.size() > 1) {
            throw new IllegalStateException("Multiple users with same providerUserId!");

        } else if (matches.size() == 1) {
            /* Set the ID from the existing user on the instance to save. */
            GroupeaseUserDto existingUser = matches.get(0);
            LOGGER.debug("Existing user found. Updating user ID '{}'", existingUser.getId());
            toSave.setId(existingUser.getId());
            LOGGER.debug("Saving user: {}", toSave);

        } else {
            LOGGER.debug("No existing user found. Saving new user: {}", toSave);
        }

        /* Save changes. */
        GroupeaseUserDto groupeaseUserDto = entityManager.merge(toSave);

        /* Refresh instance. */
        entityManager.flush();
        entityManager.refresh(groupeaseUserDto);

        LOGGER.debug("Saved user: {}", groupeaseUserDto);

        return GroupeaseUser.Builder.from(groupeaseUserDto)
                .build();
    }

}
