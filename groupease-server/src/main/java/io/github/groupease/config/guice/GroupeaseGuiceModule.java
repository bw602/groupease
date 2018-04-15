package io.github.groupease.config.guice;

import javax.annotation.Nonnull;
import javax.ws.rs.client.Client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import io.github.groupease.auth.AuthGuiceModule;
import io.github.groupease.channel.ChannelGuiceModule;
import io.github.groupease.channelmember.ChannelMemberGuiceModule;
import io.github.groupease.config.database.DatabaseGuiceModule;
import io.github.groupease.config.jersey.ClientProvider;
import io.github.groupease.user.UserGuiceModule;

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
        install(new AuthGuiceModule());
        install(new ChannelGuiceModule());
        install(new ChannelMemberGuiceModule());
        install(new DatabaseGuiceModule(config));
        install(new GroupeaseServletGuiceModule());
        install(new UserGuiceModule());

        /* Add bindings. */
        bind(Client.class).toProvider(ClientProvider.class);
        bind(Config.class).toInstance(config);
        bind(ObjectMapper.class).toProvider(ObjectMapperProvider.class);
    }

}
