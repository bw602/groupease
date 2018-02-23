package io.github.groupease;

import java.time.Instant;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.github.groupease.channel.Channel;
import io.github.groupease.channel.ChannelDao;
import io.github.groupease.channel.ChannelDto;
import io.github.groupease.channel.ChannelService;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Guice module for the test environment.
 */
public class GroupeaseTestGuiceModule extends AbstractModule {

    @Mock
    private ChannelDao channelDao;

    @Mock
    private ChannelService channelService;

    @Override
    protected void configure() {
        initMocks(this);

        bind(ChannelDao.class).toInstance(channelDao);
        bind(ChannelService.class).toInstance(channelService);
    }

    /**
     * Provides a {@link Channel} instance for testing.
     *
     * @param channelDto the injected {@link ChannelDto} test instance.
     * @return the test {@link Channel} instance.
     */
    @Provides
    private Channel provideChannel(
            ChannelDto channelDto
    ) {
        return Channel.Builder.from(channelDto)
                .build();
    }

    /**
     * Provides a {@link ChannelDto} instance for testing.
     *
     * @return the test {@link ChannelDto} instance.
     */
    @Provides
    private ChannelDto provideChannelDto() {
        ChannelDto channelDto = new ChannelDto();

        channelDto.setId(12345L);
        channelDto.setName("Channel Name");
        channelDto.setDescription("Channel Description");
        channelDto.setCreatedOn(Instant.ofEpochMilli(1518000000000L));
        channelDto.setLastUpdatedOn(Instant.ofEpochMilli(1519000000000L));

        return channelDto;
    }

}
