package io.github.groupease.user;

import io.github.groupease.model.GroupeaseUser;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Business service for operations on {@link GroupeaseUser} instances.
 */
public interface UserService {

    /**
     * Fetch all {@link GroupeaseUser} instances in the system.
     *
     * @return the list of all {@link GroupeaseUser} instances.
     */
    @Nonnull
    List<GroupeaseUser> list();

    /**
     * Fetch a {@link GroupeaseUser} instance by its ID.
     *
     * @param id the ID of the {@link GroupeaseUser} instance to fetch.
     * @return the matching {@link GroupeaseUser} instance.
     */
    @Nonnull
    GroupeaseUser getById(
            long id
    );

    /**
     * Updates the current user's data in the system from the identity provider.
     *
     * @return the updated {@link GroupeaseUser} instance.
     */
    @Nonnull
    GroupeaseUser updateCurrentUser();

}
