package io.github.groupease.channel;

import javax.inject.Inject;

import io.github.groupease.GroupeaseTestGuiceModule;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Unit tests for {@link Channel}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class ChannelTest {

    @Inject
    private Channel channel;

    @Inject
    private ChannelDto channelDto;

    /**
     * It should be equal to a builder built from the instance.
     *
     * @throws Exception on error.
     */
    @Test
    public void testBuildFromExistingChannel() throws Exception {
        Channel channelCopy = Channel.Builder.from(channel).build();
        assertEquals(channelCopy, channel);
    }

    /**
     * It should be equal to a builder built from a filled {@link ChannelDto}.
     *
     * @throws Exception on error.
     */
    @Test
    public void testBuildFromChannelDto() throws Exception {
        Channel channelCopy = Channel.Builder.from(channelDto).build();
        assertEquals(channelCopy, channel);
    }

}
