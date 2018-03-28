package io.github.groupease.exception.mapper;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.groupease.GroupeaseTestGuiceModule;
import io.github.groupease.exception.NotSignedInException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Unit tests for {@link NotSignedInExceptionMapper}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class NotSignedInExceptionMapperTest {

    @Inject
    private Provider<NotSignedInExceptionMapper> toTestProvider;

    private NotSignedInExceptionMapper toTest;

    /**
     * Set up tests.
     *
     * @throws Exception on error.
     */
    @BeforeMethod
    public void setUp() throws Exception {
        /* Get instance to test. */
        toTest = toTestProvider.get();
    }

    /**
     * It should throw {@link NullPointerException} when notSignedInException is null.
     *
     * @throws Exception on error.
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void testToResponseWhenNull() throws Exception {
        /* Make the call. */
        toTest.toResponse(null);
    }

    /**
     * It should return error response containing matching client error.
     *
     * @throws Exception on error.
     */
    @Test
    public void testToResponseWhenSuccess() throws Exception {
        /* Set up test. */
        String message = "Some message";
        String type = NotSignedInException.class.getName();
        NotSignedInException exception = new NotSignedInException(message);

        GroupeaseClientError clientError = GroupeaseClientError
                .builder()
                .withMessage(message)
                .withType(type)
                .build();

        Response expected = Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(clientError)
                .type(MediaType.APPLICATION_JSON)
                .build();

        /* Make the call. */
        Response actual = toTest.toResponse(exception);

        /* Verify results. Response does not implement equals. */
        assertEquals(actual.getStatus(), expected.getStatus());
        assertEquals(actual.getEntity(), expected.getEntity());
        assertEquals(actual.getMediaType(), expected.getMediaType());
    }

}
