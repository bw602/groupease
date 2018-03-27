package io.github.groupease.exception.mapper;

import java.lang.invoke.MethodHandles;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.codahale.metrics.annotation.Timed;
import io.github.groupease.exception.UniquenessConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * {@link ExceptionMapper} for {@link UniquenessConflictException}s.
 * Returns a 409 Conflict status code and the error as JSON.
 */
@Immutable
@Provider
public class UniquenessConflictExceptionMapper implements ExceptionMapper<UniquenessConflictException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Nonnull
    @Override
    @Timed
    public Response toResponse(
            @Nonnull UniquenessConflictException uniquenessConflictException
    ) {
        LOGGER.error("An exception was thrown and is being return to the client.", uniquenessConflictException);

        requireNonNull(uniquenessConflictException);

        /* Create payload to send to client. */
        GroupeaseClientError groupeaseClientError = GroupeaseClientError.Builder
                .from(uniquenessConflictException)
                .build();

        /* Return JSON response. */
        return Response
                .status(Response.Status.CONFLICT)
                .entity(groupeaseClientError)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
