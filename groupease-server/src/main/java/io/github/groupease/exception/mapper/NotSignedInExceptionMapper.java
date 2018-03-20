package io.github.groupease.exception.mapper;

import java.lang.invoke.MethodHandles;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.codahale.metrics.annotation.Timed;
import io.github.groupease.exception.NotSignedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * {@link ExceptionMapper} for {@link NotSignedInException}s.
 * Returns a 401 Unauthorized status code, a "WWW-Authenticate: Bearer" header and the error as JSON.
 */
@Immutable
@Provider
public class NotSignedInExceptionMapper implements ExceptionMapper<NotSignedInException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Nonnull
    @Override
    @Timed
    public Response toResponse(
            @Nonnull NotSignedInException notSignedInException
    ) {
        LOGGER.error("An exception was thrown and is being return to the client.", notSignedInException);

        requireNonNull(notSignedInException);

        /* Create payload to send to client. */
        GroupeaseClientError groupeaseClientError = GroupeaseClientError.Builder
                .from(notSignedInException)
                .build();

        /* Return JSON response. */
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE, "Bearer")
                .entity(groupeaseClientError)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
