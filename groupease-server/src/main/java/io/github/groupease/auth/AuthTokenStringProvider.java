package io.github.groupease.auth;

import java.lang.invoke.MethodHandles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * Provides the bearer token if present from the current request's authentication header.
 */
public class AuthTokenStringProvider implements Provider<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String AUTHENTICATION_SCHEME = "Bearer";

    private final Provider<HttpServletRequest> requestProvider;

    /**
     * Injectable constructor.
     *
     * @param requestProvider to get the current HTTP request.
     */
    @Inject
    public AuthTokenStringProvider(
            @Nonnull Provider<HttpServletRequest> requestProvider
    ) {
        this.requestProvider = requireNonNull(requestProvider);
    }

    @Override
    @Nullable
    @Timed
    public String get() {
        HttpServletRequest request = requestProvider.get();

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        LOGGER.debug("Request has auth header: '{}'", authorizationHeader);

        String authToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith(AUTHENTICATION_SCHEME)) {
            authToken = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        }

        LOGGER.debug("Request has Auth JWT: '{}'", authToken);

        return authToken;
    }

}
