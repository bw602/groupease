package io.github.groupease.channel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Hard-coded {@link ChannelDao} that doesn't really use a DB.
 */
public class MockChannelDao implements ChannelDao {

    @Nonnull
    @Override
    public List<Channel> list() {
        return new ArrayList<>();
    }

    @Nonnull
    @Override
    public Channel getById(
            long id
    ) {
        return Channel.builder()
                .withId(id)
                .withName("Channel Name")
                .withDescription("Channel Description")
                .withCreatedOn(Instant.ofEpochMilli(1518000000000L))
                .withLastUpdatedOn(Instant.ofEpochMilli(1519000000000L))
                .build();
    }

    @Nonnull
    @Override
    public Channel update(
            @Nonnull ChannelDto toUpdate
    ) {
        return Channel.Builder.from(toUpdate)
                .build();
    }

    @Nonnull
    @Override
    public Channel create(
            @Nonnull ChannelDto toCreate
    ) {
        return Channel.Builder.from(toCreate)
                .build();
    }

    @Override
    public void delete(
            long id
    ) {

    }

}
