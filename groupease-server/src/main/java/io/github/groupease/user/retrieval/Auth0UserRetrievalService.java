package io.github.groupease.user.retrieval;

import java.lang.invoke.MethodHandles;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;

import io.github.groupease.auth.AuthToken;
import io.github.groupease.user.GroupeaseUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * Implementation of {@link UserRetrievalService} that fetches user profile from Auth0.
 */
@Immutable
public class Auth0UserRetrievalService implements UserRetrievalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final Provider<String> authTokenProvider;
    private final WebTarget webTarget;

    /**
     * Injectable constructor.
     *
     * @param authTokenProvider to get the potentially null auth token for current request.
     * @param webTarget {@link WebTarget} for creating Auth0 User Profile REST requests.
     */
    @Inject
    public Auth0UserRetrievalService(
            @Nonnull @AuthToken Provider<String> authTokenProvider,
            @Nonnull @UserProfile WebTarget webTarget
    ) {
        this.authTokenProvider = requireNonNull(authTokenProvider);
        this.webTarget = requireNonNull(webTarget);
    }

    @Nonnull
    @Override
    public GroupeaseUserDto fetch() {

        LOGGER.debug("Fetching current user from Auth0.");

        String authToken = authTokenProvider.get();

        requireNonNull(authToken, "Auth token missing!");

        String authHeader = "Bearer " + authToken;

        LOGGER.debug("Using auth header: {}", authHeader);

        Auth0UserDto auth0UserDto = webTarget.request()
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .get(Auth0UserDto.class);

        LOGGER.debug("Auth0 profile retrieved: {}", auth0UserDto);

        return convert(auth0UserDto);
    }

    /**
     * Converts an {@link Auth0UserDto} instance into a {@link GroupeaseUserDto} instance.
     *
     * @param auth0UserDto instance to convert.
     * @return a new {@link GroupeaseUserDto} instance.
     */
    private GroupeaseUserDto convert(
            @Nonnull Auth0UserDto auth0UserDto
    ) {
        LOGGER.debug("Converting Auth0UserDto to GroupeaseUserDto: {}", auth0UserDto);

        requireNonNull(auth0UserDto);

        GroupeaseUserDto groupeaseUserDto = new GroupeaseUserDto();

        /* Set the providerUserId to "sub", the subject ID. */
        groupeaseUserDto.setProviderUserId(auth0UserDto.getSub());

        /* Set other matching fields. */
        groupeaseUserDto.setEmail(auth0UserDto.getEmail());
        groupeaseUserDto.setName(auth0UserDto.getName());
        groupeaseUserDto.setNickname(auth0UserDto.getNickname());
        groupeaseUserDto.setPictureUrl(auth0UserDto.getPicture());

        LOGGER.debug("Converted Auth0UserDto to GroupeaseUserDto: {}", groupeaseUserDto);

        return groupeaseUserDto;
    }

}
