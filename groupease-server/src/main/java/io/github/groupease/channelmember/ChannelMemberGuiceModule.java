package io.github.groupease.channelmember;

import com.google.inject.AbstractModule;

/**
 * Guice module for the channel member package.
 */
public class ChannelMemberGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ChannelMemberService.class).to(DefaultChannelMemberService.class);
    }

}
