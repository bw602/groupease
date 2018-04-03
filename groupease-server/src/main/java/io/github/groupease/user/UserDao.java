package io.github.groupease.user;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * DAO layer for fetching and changing {@link GroupeaseUser} instances.
 */
public interface UserDao {

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
     * Save a {@link GroupeaseUserDto} instance.
     * This method may update an existing instance or create a new one.
     *
     * @param toSave the {@link GroupeaseUserDto} instance to save.
     * @return the saved {@link GroupeaseUserDto} instance.
     */
    @Nonnull
    GroupeaseUser save(
            @Nonnull GroupeaseUserDto toSave
    );

}
