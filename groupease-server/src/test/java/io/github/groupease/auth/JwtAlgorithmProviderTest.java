package io.github.groupease.auth;

import java.security.interfaces.RSAPublicKey;

import javax.inject.Inject;
import javax.inject.Provider;

import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import com.auth0.jwt.algorithms.Algorithm;
import io.github.groupease.GroupeaseTestGuiceModule;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link JwtAlgorithmProvider}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class JwtAlgorithmProviderTest {

    @Inject
    private Jwk jwk;

    @Inject
    private RSAPublicKey rsaPublicKey;

    @Mock
    private Provider<Jwk> jwkProvider;

    private JwtAlgorithmProvider toTest;

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
                jwk,
                rsaPublicKey
        );

        /* Get instance to test. Not injecting so we can mock Provider. */
        toTest = new JwtAlgorithmProvider(jwkProvider);
    }

    /**
     * It should return null when injected jwk provider returns null.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenJwkIsNull() throws Exception {
        /* Set up test. */
        final Algorithm expected = null;

        /* Train the mocks. */
        when(jwkProvider.get()).thenReturn(null);

        /* Make the call. */
        Algorithm actual = toTest.get();

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should return null when there is a {@link InvalidPublicKeyException}.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenInvalidPublicKeyException() throws Exception {
        /* Set up test. */
        final Algorithm expected = null;

        /* Train the mocks. */
        when(jwkProvider.get()).thenReturn(jwk);
        when(jwk.getPublicKey()).thenThrow(InvalidPublicKeyException.class);

        /* Make the call. */
        Algorithm actual = toTest.get();

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should return RSAAlgorithm when JWK non-null and valid.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenValid() throws Exception {
        /* Train the mocks. */
        when(jwkProvider.get()).thenReturn(jwk);
        when(jwk.getPublicKey()).thenReturn(rsaPublicKey);

        /* Make the call. */
        Algorithm actual = toTest.get();

        /* Verify results. */
        assertEquals(actual.getName(), "RS256");
    }

}
