package io.github.groupease.auth;

import javax.inject.Singleton;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.inject.AbstractModule;
import io.github.groupease.config.guice.NoCache;

/**
 * Guice module for the auth package.
 */
public class AuthGuiceModule extends AbstractModule {

    @Override
    protected void configure() {

        /* Token from current request. */
        bind(String.class).annotatedWith(AuthToken.class).toProvider(AuthTokenStringProvider.class);

        /* JWK configuration. */
        bind(JwkProvider.class).annotatedWith(NoCache.class).toProvider(NoCacheGroupeaseJwkProvider.class);
        bind(JwkProvider.class).toProvider(CachingGroupeaseJwkProvider.class).in(Singleton.class);
        bind(Jwk.class).toProvider(RequestJwkProvider.class);

        /* JWT verification Algorithm. */
        bind(Algorithm.class).toProvider(JwtAlgorithmProvider.class);

        /* JWT verifier to check validity and authenticity. */
        bind(JWTVerifier.class).toProvider(JwtVerifierProvider.class);

        /* Verified auth token JWT. */
        bind(DecodedJWT.class).annotatedWith(AuthToken.class).toProvider(ValidatedAuthJwtProvider.class);

        /* ID for the current user. */
        bind(String.class).annotatedWith(CurrentUserId.class).toProvider(CurrentUserIdProvider.class);

    }

}
