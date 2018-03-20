package io.github.groupease.auth;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Iterables;
import com.typesafe.config.Config;

import static java.util.Objects.requireNonNull;

/**
 * Provides a {@link JWTVerifier} for validating the auth token for the current request.
 */
public class JwtVerifierProvider implements Provider<JWTVerifier> {

    private final Provider<Algorithm> algorithmProvider;
    private final String issuer;
    private final List<String> audience;
    private final long leeway;

    @Inject
    public JwtVerifierProvider(
            @Nonnull Provider<Algorithm> algorithmProvider,
            @Nonnull Config config
    ) {
        this.algorithmProvider = requireNonNull(algorithmProvider);
        requireNonNull(config);
        issuer = config.getString("groupease.auth.jwtVerification.issuer");
        audience = config.getStringList("groupease.auth.jwtVerification.audience");
        leeway = config.getLong("groupease.auth.jwtVerification.leeway");
    }

    @Override
    @Nullable
    @Timed
    public JWTVerifier get() {
        Algorithm algorithm = algorithmProvider.get();

        JWTVerifier jwtVerifier = null;

        if (algorithm != null) {
            jwtVerifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .withAudience(
                            Iterables.toArray(audience, String.class)
                    )
                    .acceptLeeway(leeway)
                    .build();
        }

        return jwtVerifier;
    }

}
