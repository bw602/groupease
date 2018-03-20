package io.github.groupease.auth;

import javax.inject.Inject;
import javax.inject.Provider;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.groupease.GroupeaseTestGuiceModule;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link CurrentUserIdProvider}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class CurrentUserIdProviderTest {

    @Inject
    @AuthToken
    private DecodedJWT decodedJwt;

    @Mock
    private Provider<DecodedJWT> validatedAuthJwtProvider;

    private CurrentUserIdProvider toTest;

    /**
     * Set up tests.
     *
     * @throws Exception on error.
     */
    @BeforeMethod
    public void setUp() throws Exception {
        /* Initialize local mocks. */
        initMocks(this);

        /* Reset all injected mocks between tests. */
        reset(decodedJwt);

        /* Get instance to test. Not injecting so we can mock Provider. */
        toTest = new CurrentUserIdProvider(validatedAuthJwtProvider);
    }

    /**
     * It should return null when validatedAuthJwtProvider provides null.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenAuthJwtNull() throws Exception {
        /* Set up test. */
        final String expected = null;

        /* Train the mocks. */
        when(validatedAuthJwtProvider.get()).thenReturn(null);

        /* Make the call. */
        String actual = toTest.get();

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should return subject when validatedAuthJwtProvider provides non-null instance.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenAuthJwtIsNotNull() throws Exception {
        /* Set up test. */
        final String expected = "some subject";

        /* Train the mocks. */
        when(validatedAuthJwtProvider.get()).thenReturn(decodedJwt);
        when(decodedJwt.getSubject()).thenReturn(expected);

        /* Make the call. */
        String actual = toTest.get();

        /* Verify results. */
        assertEquals(actual, expected);
    }

}
