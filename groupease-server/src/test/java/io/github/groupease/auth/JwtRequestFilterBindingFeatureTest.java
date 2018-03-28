package io.github.groupease.auth;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import io.github.groupease.GroupeaseTestGuiceModule;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link JwtRequestFilterBindingFeature}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class JwtRequestFilterBindingFeatureTest {

    @Inject
    private FeatureContext featureContext;

    @Inject
    private ResourceInfo resourceInfo;

    @Inject
    private Provider<JwtRequestFilterBindingFeature> toTestProvider;

    private JwtRequestFilterBindingFeature toTest;

    /**
     * Set up tests.
     *
     * @throws Exception on error.
     */
    @BeforeMethod
    public void setUp() throws Exception {
        /* Reset all injected mocks between tests. */
        reset(
                featureContext,
                resourceInfo
        );

        /* Get instance to test. Not injecting so we can mock Provider. */
        toTest = toTestProvider.get();
    }

    /**
     * It should register {@link JwtRequestFilter}.
     *
     * @throws Exception on error.
     */
    @Test
    public void testConfigure() throws Exception {
        /* Make the call. */
        toTest.configure(resourceInfo, featureContext);

        /* Verify results. */
        verify(featureContext).register(JwtRequestFilter.class);
    }

}
