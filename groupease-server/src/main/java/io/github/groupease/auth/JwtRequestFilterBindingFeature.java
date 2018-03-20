package io.github.groupease.auth;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import com.codahale.metrics.annotation.Timed;

/**
 * Feature that binds the {@link JwtRequestFilter}.
 */
public class JwtRequestFilterBindingFeature implements DynamicFeature {

    @Override
    @Timed
    public void configure(
            ResourceInfo resourceInfo,
            FeatureContext context
    ) {
        context.register(JwtRequestFilter.class);
    }

}
