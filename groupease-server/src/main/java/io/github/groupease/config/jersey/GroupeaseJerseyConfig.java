package io.github.groupease.config.jersey;

import java.lang.invoke.MethodHandles;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.github.groupease.config.guice.GroupeaseContextListener;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * Set up Jersey for the Groupease application.
 */
public class GroupeaseJerseyConfig extends ResourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Injectable constructor.
     *
     * @param serviceLocator - {@link ServiceLocator}.
     */
    @Inject
    public GroupeaseJerseyConfig(
            @Nonnull ServiceLocator serviceLocator
    ) {

        LOGGER.info("Creating GroupeaseJerseyConfig.");

        requireNonNull(serviceLocator);

        /* Recursively scans this package for annotated REST Endpoints. */
        packages("io.github.groupease");

        LOGGER.info("Loading Guice Bridge.");

        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(
                GroupeaseContextListener.getGuiceInjector()
        );

        LOGGER.info("Guice Bridge Loaded.");

        /* Register Jackson Module. */
        register(ObjectMapperContextResolver.class);
        register(JacksonJaxbJsonProvider.class);

        LOGGER.info("Jackson Loaded.");

        LOGGER.info("GroupeaseJerseyConfig Created.");
    }

}
