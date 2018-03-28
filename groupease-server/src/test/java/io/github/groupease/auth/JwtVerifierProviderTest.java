package io.github.groupease.auth;

import javax.inject.Inject;
import javax.inject.Provider;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.typesafe.config.Config;
import io.github.groupease.GroupeaseTestGuiceModule;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link JwtVerifierProvider}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class JwtVerifierProviderTest {

    @Inject
    private Algorithm algorithm;

    @Inject
    private Config config;

    @Mock
    private Provider<Algorithm> algorithmProvider;

    private JwtVerifierProvider toTest;

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
        reset(
                algorithm,
                config
        );

        /* Get instance to test. Not injecting so we can mock Provider. */
        toTest = new JwtVerifierProvider(
                algorithmProvider,
                config
        );
    }

    /**
     * It should return null when algorithm is null.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenAlgorithmNull() throws Exception {
        /* Set up test. */
        final JWTVerifier expected = null;

        /* Train the mocks. */
        when(config.getString("groupease.auth.jwtVerification.issuer"))
                .thenReturn("issuer");

        when(config.getString("groupease.auth.jwtVerification.audience"))
                .thenReturn("audience");

        when(config.getString("groupease.auth.jwtVerification.leeway"))
                .thenReturn("leeway");

        when(algorithmProvider.get())
                .thenReturn(null);

        /* Make the call. */
        JWTVerifier actual = toTest.get();

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should return {@link JWTVerifier} when algorithm is NOT null.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenAlgorithmNotNull() throws Exception {
        /* Train the mocks. */
        when(config.getString("groupease.auth.jwtVerification.issuer"))
                .thenReturn("issuer");

        when(config.getString("groupease.auth.jwtVerification.audience"))
                .thenReturn("audience");

        when(config.getString("groupease.auth.jwtVerification.leeway"))
                .thenReturn("leeway");

        when(algorithmProvider.get())
                .thenReturn(algorithm);

        /* Make the call. */
        JWTVerifier actual = toTest.get();

        /* Verify results. */
        assertNotNull(actual);
    }

}
