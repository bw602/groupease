package io.github.groupease.config.guice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import io.github.groupease.config.typesafe.ConfigProvider;

/**
 * Guice module for bindings in the Groupease application.
 */
public class GroupeaseGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Config.class).toProvider(ConfigProvider.class).asEagerSingleton();
        bind(ObjectMapper.class).toProvider(ObjectMapperProvider.class);
    }

}
