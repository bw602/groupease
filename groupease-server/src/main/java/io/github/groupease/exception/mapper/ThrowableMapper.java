package io.github.groupease.exception.mapper;

import java.lang.invoke.MethodHandles;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * Default {@link ExceptionMapper} for exceptions without a more specific mapper.
 * Returns a 500 server error status code and the error as JSON.
 */
@Immutable
@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Nonnull
    @Override
    @Timed
    public Response toResponse(
            @Nonnull Throwable throwable
    ) {
        LOGGER.error("An exception was thrown and is being return to the client.", throwable);

        requireNonNull(throwable);

        /* Create payload to send to client. */
        GroupeaseClientError groupeaseClientError = GroupeaseClientError.Builder
                .from(throwable)
                .build();

        /* Return JSON response. */
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(groupeaseClientError)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
