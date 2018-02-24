package io.github.groupease.channel;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link ChannelService}.
 */
@Immutable
public class DefaultChannelService implements ChannelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
        LOGGER.debug("DefaultChannelService.list() called.");
        return channelDao.list();
    }

    @Timed
    @Nonnull
    @Override
    public Channel getById(
            long id
    ) {
        LOGGER.debug("DefaultChannelService.getById({}) called.", id);
        return channelDao.getById(id);
    }

    @Timed
    @Transactional
    @Nonnull
    @Override
    public Channel update(
            @Nonnull ChannelDto toUpdate
    ) {
        LOGGER.debug("DefaultChannelService.update({}) called.", toUpdate);
        return channelDao.update(toUpdate);
    }

    @Timed
    @Transactional
    @Nonnull
    @Override
    public Channel create(
            @Nonnull ChannelDto toCreate
    ) {
        LOGGER.debug("DefaultChannelService.create({}) called.", toCreate);
        return channelDao.create(toCreate);
    }

    @Timed
    @Transactional
    @Override
    public void delete(
            long id
    ) {
        LOGGER.debug("DefaultChannelService.delete({}) called.", id);
        channelDao.delete(id);
    }

}
