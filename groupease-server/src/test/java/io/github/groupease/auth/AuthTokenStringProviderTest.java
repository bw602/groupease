package io.github.groupease.auth;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import io.github.groupease.GroupeaseTestGuiceModule;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link AuthTokenStringProvider}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class AuthTokenStringProviderTest {

    @Inject
    private HttpServletRequest httpServletRequest;

    @Inject
    private Provider<AuthTokenStringProvider> toTestProvider;

    private AuthTokenStringProvider toTest;

    /**
     * Set up tests.
     *
     * @throws Exception on error.
     */
    @BeforeMethod
    public void setUp() throws Exception {
        /* Reset all injected mocks between tests. */
        reset(httpServletRequest);

        /* Get instance to test. */
        toTest = toTestProvider.get();
    }

    /**
     * It should return null when there is no authorization header.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenNoAuthHeader() throws Exception {
        /* Set up test. */
        final String expected = null;

        /* Train the mocks. */
        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn(null);

        /* Make the call. */
        String actual = toTest.get();

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should return null when there is no "Bearer" in the authorization header.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenAuthHeaderNoBearer() throws Exception {
        /* Set up test. */
        final String expected = null;

        /* Train the mocks. */
        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn("stuff");

        /* Make the call. */
        String actual = toTest.get();

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should return the auth token when "Bearer" is present in authorization header.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenAuthHeaderWithBearer() throws Exception {
        /* Set up test. */
        final String expected = "some auth token";

        /* Train the mocks. */
        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn("Bearer " + expected);

        /* Make the call. */
        String actual = toTest.get();

        /* Verify results. */
        assertEquals(actual, expected);
    }

}
