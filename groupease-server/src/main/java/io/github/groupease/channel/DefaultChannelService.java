package io.github.groupease.channel;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

import com.codahale.metrics.annotation.Timed;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link ChannelService}.
 */
@Immutable
public class DefaultChannelService implements ChannelService {

    private final ChannelDao channelDao;

    /**
     * Injectable constructor.
     *
     * @param channelDao {@link ChannelDao} instance.
     */
    @Inject
    public DefaultChannelService(
            @Nonnull ChannelDao channelDao
    ) {
        this.channelDao = requireNonNull(channelDao);
    }

    @Timed
    @Nonnull
    @Override
    public List<Channel> list() {
        return channelDao.list();
    }

    @Timed
    @Nonnull
    @Override
    public Channel getById(
            long id
    ) {
        return channelDao.getById(id);
    }

    @Timed
    @Nonnull
    @Override
    public Channel update(
            @Nonnull ChannelDto toUpdate
    ) {
        return channelDao.update(toUpdate);
    }

    @Timed
    @Nonnull
    @Override
    public Channel create(
            @Nonnull ChannelDto toCreate
    ) {
        return channelDao.create(toCreate);
    }

    @Timed
    @Override
    public void delete(
            long id
    ) {
        channelDao.delete(id);
    }

}
