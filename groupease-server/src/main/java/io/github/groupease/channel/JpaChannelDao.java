package io.github.groupease.channel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static java.util.Objects.requireNonNull;

/**
 * JPA implementation of {@link ChannelDao}.
 */
public class JpaChannelDao implements ChannelDao {

    private final EntityManager entityManager;

    /**
     * Injectable constructor.
     *
     * @param entityManager
     */
    @Inject
    public JpaChannelDao(
            @Nonnull EntityManager entityManager
    ) {
        this.entityManager = requireNonNull(entityManager);
    }

    @Nonnull
    @Override
    public List<Channel> list() {
        TypedQuery<ChannelDto> query = entityManager.createQuery(
                "SELECT dto FROM ChannelDto dto ORDER BY dto.name ASC",
                ChannelDto.class
        );
        List<ChannelDto> channelDtoList = query.getResultList();

        List<Channel> channels = new ArrayList<>();

        for (ChannelDto channelDto : channelDtoList) {
            channels.add(
                    Channel.Builder.from(channelDto)
                            .build()
            );
        }

        return channels;
    }

    @Nonnull
    @Override
    public Channel getById(
            long id
    ) {
        ChannelDto channelDto = entityManager.find(
                ChannelDto.class,
                id
        );

        return Channel.Builder.from(channelDto)
                .build();
    }

    @Nonnull
    @Override
    public Channel update(
            @Nonnull ChannelDto toUpdate
    ) {
        ChannelDto channelDto = entityManager.merge(toUpdate);

        return Channel.Builder.from(channelDto)
                .build();
    }

    @Nonnull
    @Override
    public Channel create(
            @Nonnull ChannelDto toCreate
    ) {
        entityManager.persist(toCreate);

        return Channel.Builder.from(toCreate)
                .build();
    }

    @Override
    public void delete(long id) {
        ChannelDto channelDto = entityManager.find(
                ChannelDto.class,
                id
        );
        entityManager.remove(channelDto);
    }

}
