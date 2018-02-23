package io.github.groupease.config.guice;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.ServletModule;
import io.github.groupease.config.jersey.GroupeaseJerseyConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

/**
 * Guice Module for configuring the Groupease servlets and filters.
 */
public class GroupeaseServletGuiceModule extends ServletModule {

    @Override
    protected void configureServlets() {
        configureJpaFilter();
        configureJerseyFilter();
    }

    private void configureJpaFilter() {
        filter("/*").through(PersistFilter.class);
    }

    private void configureJerseyFilter() {

        bind(ServletContainer.class).in(Singleton.class);

        Map<String, String> jerseyParams = new HashMap<>();

        jerseyParams.put(
                ServletProperties.JAXRS_APPLICATION_CLASS,
                GroupeaseJerseyConfig.class.getName()
        );

        /* Set to TracingConfig.ALL to add tracing headers to response while debugging. */
        String tracing = TracingConfig.OFF.name();
        if ("true".equals(System.getProperty("jerseyTracingOn"))) {
            tracing = TracingConfig.ALL.name();
        }

        jerseyParams.put(
                ServerProperties.TRACING,
                tracing
        );

        serve("/api/*").with(ServletContainer.class, jerseyParams);

    }

}
