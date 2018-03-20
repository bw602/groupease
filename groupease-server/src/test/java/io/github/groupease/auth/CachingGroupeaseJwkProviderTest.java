package io.github.groupease.auth;

import javax.inject.Inject;
import javax.inject.Provider;

import com.auth0.jwk.GuavaCachedJwkProvider;
import com.auth0.jwk.JwkProvider;
import io.github.groupease.GroupeaseTestGuiceModule;
import io.github.groupease.config.guice.NoCache;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link CachingGroupeaseJwkProvider}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class CachingGroupeaseJwkProviderTest {

    @Inject
    @NoCache
    private JwkProvider noCacheJwkProvider;

    @Inject
    private Provider<CachingGroupeaseJwkProvider> toTestProvider;

    private CachingGroupeaseJwkProvider toTest;

    /**
     * Set up tests.
     *
     * @throws Exception on error.
     */
    @BeforeMethod
    public void setUp() throws Exception {
        /* Reset all injected mocks between tests. */
        reset(noCacheJwkProvider);

        /* Get instance to test. */
        toTest = toTestProvider.get();
    }

    /**
     * It should return a Guava Cache wrapping the injected jwkProvider.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGet() throws Exception {
        /* Make the call. */
        JwkProvider actual = toTest.get();

        /* Verify results. */
        assertTrue(actual instanceof GuavaCachedJwkProvider);
    }

}
