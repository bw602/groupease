package io.github.groupease.user;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * REST-ful web service for {@link GroupeaseUser} instances.
 */
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Immutable
public class UserWebService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final UserService userService;

    /**
     * Injectable constructor.
     *
     * @param userService logic service layer for {@link GroupeaseUser} operations.
     */
    @Inject
    public UserWebService(
            @Nonnull UserService userService
    ) {
        this.userService = requireNonNull(userService);
    }

    /**
     * Fetch all {@link GroupeaseUser} instances in the system.
     *
     * @return the list of all {@link GroupeaseUser} instances.
     */
    @GET
    @Timed
    @Nonnull
    public List<GroupeaseUser> list() {
        LOGGER.debug("UserWebService.list() called.");
        return userService.list();
    }

    /**
     * Fetch a {@link GroupeaseUser} instance by its ID.
     *
     * @param id the ID of the {@link GroupeaseUser} instance to fetch.
     * @return the matching {@link GroupeaseUser} instance.
     */
    @GET
    @Path("{id}")
    @Timed
    @Nonnull
    public GroupeaseUser getById(
            @PathParam("id") Long id
    ) {
        LOGGER.debug("UserWebService.getForUser({}) called.", id);
        return userService.getById(id);
    }

    /**
     * Updates the current user's data in the system from the identity provider.
     *
     * @return the updated {@link GroupeaseUser} instance.
     */
    @PUT
    @Path("current")
    @Timed
    @Nonnull
    public GroupeaseUser updateCurrentUser() {
        LOGGER.debug("UserWebService.updateCurrentUser() called.");
        return userService.updateCurrentUser();
    }

}
