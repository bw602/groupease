package io.github.groupease.group;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static java.util.Objects.requireNonNull;

import io.github.groupease.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class GroupDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EntityManager entityManager;

    /**
     * Injectable constructor.
     *
     * @param entityManager
     */
    @Inject
    public GroupDao(@Nonnull EntityManager entityManager)
    {
        this.entityManager = requireNonNull(entityManager);
    }

    public List<Group> listInChannel(long channelId) {
        LOGGER.debug("GroupDao.listInChannel({}) called", channelId);

        TypedQuery<Group> results = entityManager.createQuery("SELECT group FROM Group group WHERE group.channelId = " + channelId +" ORDER BY group.name ASC", Group.class);
        return results.getResultList();
    }

    public Group getById(long id)
    {
        LOGGER.debug("GroupDao.getById({}) called", id);

        return entityManager.find(Group.class, id);

        //throw new NotFoundException();
    }

    public void delete(@Nonnull Group group)
    {
        LOGGER.debug("GroupDao.delete({}) called", group.getName());

        entityManager.remove(group);
    }

    public void create(@Nonnull Group group)
    {
        LOGGER.debug("GroupDao.Create(channelId={}, name={}, description={})", group.getChannelId(), group.getName(), group.getDescription());
        entityManager.persist(group);
    }

    public void update(@Nonnull Group group)
    {
        LOGGER.debug("GroupDao.Create(id={}, channelId={}, name={}, description={})", group.getId(), group.getChannelId(), group.getName(), group.getDescription());

    }
}
