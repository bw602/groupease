package io.github.groupease.user;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.persist.Transactional;
import io.github.groupease.model.GroupeaseUser;
import io.github.groupease.user.retrieval.UserRetrievalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link UserService}.
 */
@Immutable
public class DefaultUserService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final UserRetrievalService userRetrievalService;

    private final UserDao userDao;

    /**
     * Injectable constructor.
     *
     * @param userRetrievalService to retrieve the current user's profile.
     * @param userDao {@link UserDao} instance.
     */
    @Inject
    public DefaultUserService(
            @Nonnull UserRetrievalService userRetrievalService,
            @Nonnull UserDao userDao
    ) {
        this.userRetrievalService = requireNonNull(userRetrievalService);
        this.userDao = requireNonNull(userDao);
    }

    @Nonnull
    @Override
    @Timed
    public List<GroupeaseUser> list() {
        LOGGER.debug("DefaultUserService.list() called.");
        return userDao.list();
    }

    @Nonnull
    @Override
    @Timed
    public GroupeaseUser getById(long id) {
        LOGGER.debug("DefaultUserService.getById({}) called.", id);
        return userDao.getById(id);
    }

    @Nonnull
    @Override
    @Timed
    @Transactional
    public GroupeaseUser updateCurrentUser() {
        LOGGER.debug("DefaultUserService.updateCurrentUser() called.");

        /* Fetch the user data. */
        GroupeaseUserDto groupeaseUserDto = userRetrievalService.fetch();

        return userDao.save(groupeaseUserDto);
    }

}
