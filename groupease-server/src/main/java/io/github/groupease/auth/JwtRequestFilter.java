package io.github.groupease.auth;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.annotation.Nonnull;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Injector;
import com.google.inject.Key;
import io.github.groupease.config.guice.GroupeaseContextListener;
import io.github.groupease.exception.NotSignedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * Filter that validates JWT authentication tokens on each request.
 */
public class JwtRequestFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    @Timed
    public void filter(
            @Nonnull ContainerRequestContext requestContext
    ) throws IOException {

        requireNonNull(requestContext);

        Injector guiceInjector = GroupeaseContextListener.getGuiceInjector();

        String currentUserId = guiceInjector.getInstance(
                Key.get(String.class, CurrentUserId.class)
        );

        LOGGER.debug("Filtering request using current user ID '{}'", currentUserId);

        if (currentUserId == null) {
            throw new NotSignedInException("Authentication is required.");
        }

    }

}
