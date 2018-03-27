package io.github.groupease.auth;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;

import com.auth0.jwk.GuavaCachedJwkProvider;
import com.auth0.jwk.JwkProvider;
import com.codahale.metrics.annotation.Timed;
import io.github.groupease.config.guice.NoCache;

import static java.util.Objects.requireNonNull;

/**
 * Provides a {@link JwkProvider} that will cache JWKs for some time.
 */
public class CachingGroupeaseJwkProvider implements Provider<JwkProvider> {

    private final JwkProvider noCacheJwkProvider;

    /**
     * Injectable constructor.
     *
     * @param noCacheJwkProvider underlying non-caching {@link JwkProvider}.
     */
    @Inject
    public CachingGroupeaseJwkProvider(
            @Nonnull @NoCache JwkProvider noCacheJwkProvider
    ) {
        this.noCacheJwkProvider = requireNonNull(noCacheJwkProvider);
    }

    @Nonnull
    @Override
    @Timed
    public JwkProvider get() {
        return new GuavaCachedJwkProvider(noCacheJwkProvider);
    }

}
