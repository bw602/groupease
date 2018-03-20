package io.github.groupease.auth;

import java.lang.invoke.MethodHandles;
import java.security.interfaces.RSAPublicKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import com.auth0.jwt.algorithms.Algorithm;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * Provides the {@link Algorithm} for validating JWT authenticity.
 */
public class JwtAlgorithmProvider implements Provider<Algorithm> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final Provider<Jwk> jwkProvider;

    /**
     * Injectable constructor.
     *
     * @param jwkProvider to get the {@link Jwk} to use to validate the auth token for the current request.
     */
    @Inject
    public JwtAlgorithmProvider(
            @Nonnull Provider<Jwk> jwkProvider
    ) {
        this.jwkProvider = requireNonNull(jwkProvider);
    }

    @Nullable
    @Override
    @Timed
    public Algorithm get() {

        Jwk jwk = jwkProvider.get();

        Algorithm algorithm = null;

        if (jwk != null) {
            try {
                algorithm = Algorithm.RSA256(
                        (RSAPublicKey) jwk.getPublicKey(),
                        null
                );
            } catch (InvalidPublicKeyException invalidPublicKeyException) {
                /* Log issue, and continue to return null. */
                LOGGER.warn(
                        "Failure to create JWT Algorithm using JWK for the current request",
                        invalidPublicKeyException
                );
            }
        }

        return algorithm;
    }

}
