package io.github.groupease.config.guice;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import io.github.groupease.channel.ChannelGuiceModule;
import io.github.groupease.config.database.DatabaseGuiceModule;

import static java.util.Objects.requireNonNull;

/**
 * Guice module for bindings in the Groupease application.
 */
public class GroupeaseGuiceModule extends AbstractModule {

    private final Config config;

    /**
     * Constructor.
     *
     * @param config to bind and use in modules.
     */
    public GroupeaseGuiceModule(
            @Nonnull Config config
    ) {
        this.config = requireNonNull(config);
    }

    @Override
    protected void configure() {

        /* Install other modules. */
        install(new DatabaseGuiceModule(config));
        install(new ChannelGuiceModule());
        install(new GroupeaseServletGuiceModule());

        /* Add bindings. */
        bind(Config.class).toInstance(config);
        bind(ObjectMapper.class).toProvider(ObjectMapperProvider.class);
    }

}
