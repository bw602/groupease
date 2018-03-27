package io.github.groupease.auth;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;
import javax.inject.Provider;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.codahale.metrics.annotation.Timed;

import static java.util.Objects.requireNonNull;

/**
 * Provides the ID for the current user, or null if one does not exist.
 */
@Immutable
public class CurrentUserIdProvider implements Provider<String> {

    private final Provider<DecodedJWT> validatedAuthJwtProvider;

    /**
     * Injectable constructor.
     *
     * @param validatedAuthJwtProvider provides the decoded and trusted JWT auth token on the current request.
     */
    @Inject
    public CurrentUserIdProvider(
            @Nonnull @AuthToken Provider<DecodedJWT> validatedAuthJwtProvider
    ) {
        this.validatedAuthJwtProvider = requireNonNull(validatedAuthJwtProvider);
    }

    @Nullable
    @Override
    @Timed
    public String get() {

        DecodedJWT validatedAuthJwt = validatedAuthJwtProvider.get();

        String currentUserId = null;

        if (validatedAuthJwt != null) {
            currentUserId = validatedAuthJwt.getSubject();
        }

        return currentUserId;
    }

}
