package io.github.groupease.auth;

import javax.inject.Inject;
import javax.inject.Provider;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.typesafe.config.Config;
import io.github.groupease.GroupeaseTestGuiceModule;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link NoCacheGroupeaseJwkProvider}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class NoCacheGroupeaseJwkProviderTest {

    @Inject
    private Config config;

    @Inject
    private Provider<NoCacheGroupeaseJwkProvider> toTestProvider;

    private NoCacheGroupeaseJwkProvider toTest;

    /**
     * Set up tests.
     *
     * @throws Exception on error.
     */
    @BeforeMethod
    public void setUp() throws Exception {
        /* Reset all injected mocks between tests. */
        reset(config);

        /* Train the mocks used in toTest's constructor. */
        when(config.getString("groupease.auth.jwkDomain"))
                .thenReturn("https://mckoon.auth0.com");

        /* Get instance to test. Not injecting so we can mock Provider. */
        toTest = toTestProvider.get();
    }

    /**
     * It should return JWK provider using domain from config.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGet() throws Exception {
        /* Make the call. */
        JwkProvider actual = toTest.get();

        /* Verify results. */
        assertTrue(actual instanceof UrlJwkProvider);
    }

}
