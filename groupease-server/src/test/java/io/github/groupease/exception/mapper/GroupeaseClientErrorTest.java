package io.github.groupease.exception.mapper;

import javax.inject.Inject;

import io.github.groupease.GroupeaseTestGuiceModule;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Unit tests for {@link GroupeaseClientError}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class GroupeaseClientErrorTest {

    @Inject
    private GroupeaseClientError clientError;

    /**
     * It should be equal to an instance built by a builder from it.
     *
     * @throws Exception on error.
     */
    @Test
    public void testBuildFromExisting() throws Exception {
        GroupeaseClientError clientErrorCopy = GroupeaseClientError.Builder
                .from(clientError)
                .build();

        assertEquals(clientErrorCopy, clientError);
    }

    /**
     * It should be able to be built from an exception.
     *
     * @throws Exception on error.
     */
    @Test
    public void testBuildFromException() throws Exception {
        /* Set up test. */
        String message = "Some message";
        String type = RuntimeException.class.getName();

        GroupeaseClientError expected = GroupeaseClientError.builder()
                .withMessage(message)
                .withType(type)
                .build();

        RuntimeException runtimeException = new RuntimeException(message);

        /* Make the call. */
        GroupeaseClientError actual = GroupeaseClientError.Builder
                .from(runtimeException)
                .build();

        assertEquals(actual, expected);
    }

}
