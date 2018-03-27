package io.github.groupease.exception.mapper;

import java.lang.invoke.MethodHandles;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.codahale.metrics.annotation.Timed;
import io.github.groupease.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * {@link ExceptionMapper} for {@link ResourceNotFoundException}s.
 * Returns a 404 not found status code and the error as JSON.
 */
@Immutable
@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Nonnull
    @Override
    @Timed
    public Response toResponse(
            @Nonnull ResourceNotFoundException resourceNotFoundException
    ) {
        LOGGER.error("An exception was thrown and is being return to the client.", resourceNotFoundException);

        requireNonNull(resourceNotFoundException);

        /* Create payload to send to client. */
        GroupeaseClientError groupeaseClientError = GroupeaseClientError.Builder
                .from(resourceNotFoundException)
                .build();

        /* Return JSON response. */
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(groupeaseClientError)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
