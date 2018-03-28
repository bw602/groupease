package io.github.groupease.auth;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.codahale.metrics.annotation.Timed;
import com.typesafe.config.Config;

import static java.util.Objects.requireNonNull;

/**
 * Provides an instance of {@link JwkProvider} for validating JWT signatures.
 * Does not provide any caching.
 */
public class NoCacheGroupeaseJwkProvider implements Provider<JwkProvider> {

    private final String jwkDomain;

    /**
     * Injectable constructor.
     *
     * @param config for getting application configuration.
     */
    @Inject
    public NoCacheGroupeaseJwkProvider(
            @Nonnull Config config
    ) {
        requireNonNull(config);
        this.jwkDomain = config.getString("groupease.auth.jwkDomain");
    }

    @Override
    @Nonnull
    @Timed
    public JwkProvider get() {
        return new UrlJwkProvider(jwkDomain);
    }

}
