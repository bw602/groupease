package io.github.groupease.exception.mapper;

import java.lang.invoke.MethodHandles;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.codahale.metrics.annotation.Timed;
import io.github.groupease.exception.ActionForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * {@link ExceptionMapper} for {@link ActionForbiddenException}s.
 * Returns a 403 Forbidden status code and the error as JSON.
 */
@Immutable
@Provider
public class ActionForbiddenExceptionMapper implements ExceptionMapper<ActionForbiddenException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Nonnull
    @Override
    @Timed
    public Response toResponse(
            @Nonnull ActionForbiddenException actionForbiddenException
    ) {
        LOGGER.error("An exception was thrown and is being return to the client.", actionForbiddenException);

        requireNonNull(actionForbiddenException);

        /* Create payload to send to client. */
        GroupeaseClientError groupeaseClientError = GroupeaseClientError.Builder
                .from(actionForbiddenException)
                .build();

        /* Return JSON response. */
        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(groupeaseClientError)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
