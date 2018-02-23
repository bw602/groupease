package io.github.groupease.channel;

import com.google.inject.AbstractModule;

/**
 * Guice module for the channel package.
 */
public class ChannelGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        //bind(ChannelDao.class).to(MockChannelDao.class);
        bind(ChannelDao.class).to(JpaChannelDao.class);
        bind(ChannelService.class).to(DefaultChannelService.class);
    }

}
