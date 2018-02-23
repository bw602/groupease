package io.github.groupease.config.guice;

import java.lang.invoke.MethodHandles;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ensures that the Guice Injector is created when the app is deployed.
 */
public class GroupeaseContextListener extends GuiceServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Injector guiceInjector;

    private static Config createConfig() {
        LOGGER.info("Creating Typesafe Config.");

        Config config = ConfigFactory.load();

        LOGGER.info("Typesafe Config Created.");

        return config;
    }

    private static void createInjector() {
        LOGGER.info("Creating Guice Injector.");

        Config config = createConfig();

        guiceInjector = Guice.createInjector(
                new GroupeaseGuiceModule(config)
        );

        LOGGER.info("Guice Injector Created.");
    }

    /**
     * Statically exposes accessor for Guice Injector.
     *
     * @return Guice {@link Injector}.
     */
    public static synchronized Injector getGuiceInjector() {
        if (guiceInjector == null) {
            createInjector();
        }
        return guiceInjector;
    }

    @Override
    protected Injector getInjector() {
        return GroupeaseContextListener.getGuiceInjector();
    }

}
