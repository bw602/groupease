package io.github.groupease.user.retrieval;

import javax.ws.rs.client.WebTarget;

import com.google.inject.AbstractModule;

/**
 * Guice module for the user.retrieval package.
 */
public class UserRetrievalGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserRetrievalService.class).to(Auth0UserRetrievalService.class);
        bind(WebTarget.class).annotatedWith(UserProfile.class).toProvider(Auth0UserWebTargetProvider.class);
    }

}
