package io.github.groupease.auth;

import java.lang.invoke.MethodHandles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * Provides the validated and trusted {@link DecodedJWT} for the auth token on the current request.
 */
public class ValidatedAuthJwtProvider implements Provider<DecodedJWT> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final Provider<String> authTokenProvider;
    private final Provider<JWTVerifier> jwtVerifierProvider;

    /**
     * Injectable constructor.
     *
     * @param authTokenProvider provides the encoded auth token from the request header.
     * @param jwtVerifierProvider provides the verifier for the JWT auth token.
     */
    @Inject
    public ValidatedAuthJwtProvider(
            @Nonnull @AuthToken Provider<String> authTokenProvider,
            @Nonnull Provider<JWTVerifier> jwtVerifierProvider
    ) {
        this.authTokenProvider = requireNonNull(authTokenProvider);
        this.jwtVerifierProvider = requireNonNull(jwtVerifierProvider);
    }

    @Override
    @Nullable
    @Timed
    public DecodedJWT get() {

        String authToken = authTokenProvider.get();
        JWTVerifier jwtVerifier = jwtVerifierProvider.get();

        DecodedJWT validAuthJwt = null;

        if (authToken != null && jwtVerifier != null) {
            try {
                validAuthJwt = jwtVerifier.verify(authToken);
            } catch (JWTVerificationException jwtVerificationException) {
                LOGGER.warn("Invalid auth token JWT!", jwtVerificationException);
            }
        }

        return validAuthJwt;
    }

}
