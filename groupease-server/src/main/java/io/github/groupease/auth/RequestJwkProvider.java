package io.github.groupease.auth;

import java.lang.invoke.MethodHandles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.JWT;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * Provides the {@link Jwk} to use to validate the current request.
 * Returns null if the request does not have an auth token with a valid signing key ID (kid).
 */
public class RequestJwkProvider implements Provider<Jwk> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final JwkProvider jwkProvider;
    private final Provider<String> authTokenProvider;

    /**
     * Injectable constructor.
     *
     * @param jwkProvider to retrieve the JWK for a given key ID (kid).
     * @param authTokenProvider to get the potentially null auth token for current request.
     */
    @Inject
    public RequestJwkProvider(
            @Nonnull JwkProvider jwkProvider,
            @Nonnull @AuthToken Provider<String> authTokenProvider
    ) {
        this.jwkProvider = requireNonNull(jwkProvider);
        this.authTokenProvider = requireNonNull(authTokenProvider);
    }

    @Override
    @Nullable
    @Timed
    public Jwk get() {

        Jwk jwk = null;

        String authToken = authTokenProvider.get();

        if (authToken != null) {

            /* Decode authToken to get key ID (kid). JWT authenticity NOT validated here. */
            String keyId = JWT.decode(authToken).getKeyId();

            LOGGER.debug("JWK Key ID for request: '{}'", keyId);

            if (keyId != null) {
                try {
                    jwk = jwkProvider.get(keyId);
                } catch (JwkException jwkException) {
                    /* Log bad request criteria, and continue to return null. */
                    LOGGER.warn("Request specified invalid JWK key ID (kid)", jwkException);
                }
            }
        }

        return jwk;
    }

}
