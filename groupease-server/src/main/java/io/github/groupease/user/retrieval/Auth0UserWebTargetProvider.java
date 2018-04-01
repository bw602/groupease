package io.github.groupease.user.retrieval;

import java.lang.invoke.MethodHandles;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * Provides the {@link WebTarget} for the Auth0 User Profile REST service.
 */
@Immutable
public class Auth0UserWebTargetProvider implements Provider<WebTarget> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final Client client;
    private final String url;

    /**
     * Injectable constructor.
     *
     * @param client {@link Client} instance.
     * @param config {@link Config} for application configuration.
     */
    @Inject
    public Auth0UserWebTargetProvider(
            @Nonnull Client client,
            @Nonnull Config config
    ) {
        this.client = requireNonNull(client);
        this.url = config.getString("groupease.user.profile.url");
    }

    @Nonnull
    @Override
    public WebTarget get() {
        LOGGER.debug("Returning WebTarget for url: {}", url);
        return client.target(url);
    }

}
