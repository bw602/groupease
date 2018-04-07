package io.github.groupease.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.invoke.MethodHandles;

import static java.util.Objects.requireNonNull;

public class DataAccess
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EntityManager entityManager;

    private ChannelInvitationDao channelInvitationDao;
    private ChannelJoinRequestDao cjrDao;
    private GroupDao groupDao;
    private GroupInvitationDao groupInvitationDao;
    private GroupJoinRequestDao groupJoinRequestDao;
    private MemberDao memberDao;
    private GroupeaseUserDao userProfileDao;
    private EntityTransaction entityTransaction;

    /**
     * Injectable constructor.
     *
     * @param entityManager
     */
    @Inject
    public DataAccess(@Nonnull EntityManager entityManager)
    {
        this.entityManager = requireNonNull(entityManager);
    }

    public void beginTransaction()
    {
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
    }

    public void commitTransaction()
    {
        entityManager.flush();
        entityTransaction.commit();
    }

    public ChannelInvitationDao channelInvitation()
    {
        if(channelInvitationDao == null)
        {
            channelInvitationDao = new ChannelInvitationDao(entityManager);
        }

        return channelInvitationDao;
    }

    public ChannelJoinRequestDao channelJoinRequest()
    {
        if(cjrDao == null)
        {
            cjrDao = new ChannelJoinRequestDao(entityManager);
        }

        return cjrDao;
    }

    public GroupDao group() {
        if (groupDao == null) {
            groupDao = new GroupDao(entityManager);
        }

        return groupDao;
    }

    public GroupInvitationDao groupInvitation()
    {
        if(groupInvitationDao == null)
        {
            groupInvitationDao = new GroupInvitationDao(entityManager);
        }

        return groupInvitationDao;
    }

    public GroupJoinRequestDao groupJoinRequest()
    {
        if(groupJoinRequestDao == null)
        {
            groupJoinRequestDao = new GroupJoinRequestDao(entityManager);
        }

        return groupJoinRequestDao;
    }

    public MemberDao member()
    {
        if(memberDao == null)
        {
            memberDao = new MemberDao(entityManager);
        }

        return memberDao;
    }

    public GroupeaseUserDao userProfile()
    {
        if(userProfileDao == null)
        {
            userProfileDao = new GroupeaseUserDao(entityManager);
        }

        return userProfileDao;
    }
}
