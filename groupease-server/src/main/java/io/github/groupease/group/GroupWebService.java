package io.github.groupease.group;

import javax.annotation.Nonnull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import javax.inject.Inject;

import io.github.groupease.model.Group;
import io.github.groupease.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * REST-ful web service for {@link Group} instances.
 */
@Path("groups")
@Produces(MediaType.APPLICATION_JSON)
public class GroupWebService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final GroupDao groupDao;

    @Inject
    public GroupWebService(@Nonnull GroupDao groupDao)
    {
        this.groupDao = groupDao;
    }

    public List<Group> listInChannel(long channelId)
    {
        LOGGER.debug("GroupWebService.listInChannel({})", channelId);
        //Todo: auth check that caller is member of channel

        return groupDao.listInChannel(channelId);
    }

    @GET
    @Path("{id}")
    @Timed
    @Nonnull
    public Group getById(@PathParam("id") long id)
    {
        LOGGER.debug("GroupWebService.getById({})", id);
        // Todo: auth check that caller is member of channel

        return groupDao.getById(id);
    }

    @GET
    @Path("{id}/members")
    public List<Member> getMembers(@PathParam("id") long id)
    {
        Group result = groupDao.getById(id);
        if(result != null)
        {
            return result.getMembers();
        }

        throw new NotFoundException();
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id)
    {
        LOGGER.debug("GroupWebService.delete({})", id);
        // Todo: what is deletion logic? Any member? Only when last member quits?

        Group victim = groupDao.getById(id);
        if(victim != null) {
            groupDao.delete(victim);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Group create(@Nonnull Group newGroup)
    {
        LOGGER.debug("GroupWebService.create()");

        // Todo: verify caller is a member of specified channel

        // Todo: Fail if the caller tried to provide an ID

        groupDao.create(newGroup);
        return newGroup;
    }
}
