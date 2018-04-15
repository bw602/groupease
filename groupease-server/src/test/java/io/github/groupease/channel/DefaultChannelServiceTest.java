package io.github.groupease.channel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.collect.ImmutableList;
import io.github.groupease.GroupeaseTestGuiceModule;
import io.github.groupease.db.MemberDao;
import io.github.groupease.model.Member;
import io.github.groupease.user.GroupeaseUser;
import io.github.groupease.user.UserService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link DefaultChannelService}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class DefaultChannelServiceTest {

    @Inject
    private ChannelDao channelDao;

    @Inject
    private MemberDao memberDao;

    @Inject
    private UserService userService;

    @Inject
    private Channel channel;

    @Inject
    private GroupeaseUser groupeaseUser;

    @Inject
    private Provider<ChannelDto> channelDtoProvider;

    @Inject
    private Provider<DefaultChannelService> toTestProvider;

    private DefaultChannelService toTest;

    private ChannelDto channelDto;

    /**
     * Set up tests.
     *
     * @throws Exception on error.
     */
    @BeforeMethod
    public void setUp() throws Exception {
        /* Reset all injected mocks between tests. */
        reset(
                channelDao,
                memberDao,
                userService
        );

        /* Get new instance of ChannelDto for each test since it is mutable. */
        channelDto = channelDtoProvider.get();

        /* Get instance to test. */
        toTest = toTestProvider.get();
    }

    /**
     * It should call {@link ChannelDao#list()} and return the result.
     *
     * @throws Exception on error.
     */
    @Test
    public void testList() throws Exception {
        /* Set up test. */
        List<Channel> expected = ImmutableList.of(channel);

        /* Train the mocks. */
        when(channelDao.list()).thenReturn(expected);

        /* Make the call. */
        List<Channel> actual = toTest.list();

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should call {@link ChannelDao#getById(long)} and return the result.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetById() throws Exception {
        /* Set up test. */
        Channel expected = channel;

        /* Train the mocks. */
        when(channelDao.getById(channel.getId())).thenReturn(expected);

        /* Make the call. */
        Channel actual = toTest.getById(channel.getId());

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should call {@link ChannelDao#update(ChannelDto)} and return the result.
     *
     * @throws Exception on error.
     */
    @Test
    public void testUpdate() throws Exception {
        /* Set up test. */
        Channel expected = channel;

        Member member = new Member();
        member.setOwner(true);

        /* Train the mocks. */
        when(userService.updateCurrentUser()).thenReturn(groupeaseUser);
        when(memberDao.getForUser(groupeaseUser.getId(), channel.getId())).thenReturn(member);
        when(channelDao.update(channelDto)).thenReturn(expected);

        /* Make the call. */
        Channel actual = toTest.update(channelDto);

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should throw {@link ChannelEditByNonOwnerException} when user is not a member.
     *
     * @throws Exception on error.
     */
    @Test(expectedExceptions = ChannelEditByNonOwnerException.class)
    public void testUpdateWhenNotMember() throws Exception {
        /* Train the mocks. */
        when(userService.updateCurrentUser()).thenReturn(groupeaseUser);
        when(memberDao.getForUser(groupeaseUser.getId(), channel.getId())).thenReturn(null);

        /* Make the call. */
        toTest.update(channelDto);
    }

    /**
     * It should throw {@link ChannelEditByNonOwnerException} when user is not an owner.
     *
     * @throws Exception on error.
     */
    @Test(expectedExceptions = ChannelEditByNonOwnerException.class)
    public void testUpdateWhenNotOwner() throws Exception {
        /* Set up test. */
        Member member = new Member();
        member.setOwner(false);

        /* Train the mocks. */
        when(userService.updateCurrentUser()).thenReturn(groupeaseUser);
        when(memberDao.getForUser(groupeaseUser.getId(), channel.getId())).thenReturn(member);

        /* Make the call. */
        toTest.update(channelDto);
    }

    /**
     * It should throw {@link NullPointerException} when input is null.
     *
     * @throws Exception on error.
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void testUpdateWhenDtoNull() throws Exception {
        /* Make the call. */
        toTest.update(null);
    }

    /**
     * It should throw {@link ChannelNameMissingException} when name is null.
     *
     * @throws Exception on error.
     */
    @Test(expectedExceptions = ChannelNameMissingException.class)
    public void testUpdateWhenNameNull() throws Exception {
        /* Set up test. */
        channelDto.setName(null);

        /* Make the call. */
        toTest.update(channelDto);
    }

    /**
     * It should throw {@link ChannelNameMissingException} when name is blank.
     *
     * @throws Exception on error.
     */
    @Test(expectedExceptions = ChannelNameMissingException.class)
    public void testUpdateWhenNameBlank() throws Exception {
        /* Set up test. */
        channelDto.setName("");

        /* Make the call. */
        toTest.update(channelDto);
    }

    /**
     * It should call {@link ChannelDao#create(ChannelDto)} and return the result.
     *
     * @throws Exception on error.
     */
    @Test
    public void testCreate() throws Exception {
        /* Set up test. */
        Channel expected = channel;

        channelDto.setId(null);

        /* Train the mocks. */
        when(channelDao.create(channelDto)).thenReturn(expected);
        when(userService.updateCurrentUser()).thenReturn(groupeaseUser);

        /* Make the call. */
        Channel actual = toTest.create(channelDto);

        /* Verify results. */
        assertEquals(actual, expected);

        /* Creating user should be owner. */
        verify(memberDao).create(groupeaseUser.getId(), channel.getId(), true);
    }

    /**
     * It should throw {@link NewChannelHasIdException} when channel to create already has an ID.
     *
     * @throws Exception on error.
     */
    @Test(expectedExceptions = NewChannelHasIdException.class)
    public void testCreateAlreadyHasId() throws Exception {
        /* Make the call. */
        toTest.create(channelDto);
    }

    /**
     * It should throw {@link NullPointerException} when input is null.
     *
     * @throws Exception on error.
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void testCreateWhenDtoNull() throws Exception {
        /* Make the call. */
        toTest.create(null);
    }

    /**
     * It should throw {@link ChannelNameMissingException} when name is null.
     *
     * @throws Exception on error.
     */
    @Test(expectedExceptions = ChannelNameMissingException.class)
    public void testCreateWhenNameNull() throws Exception {
        /* Set up test. */
        channelDto.setId(null);
        channelDto.setName(null);

        /* Make the call. */
        toTest.create(channelDto);
    }

    /**
     * It should throw {@link ChannelNameMissingException} when name is blank.
     *
     * @throws Exception on error.
     */
    @Test(expectedExceptions = ChannelNameMissingException.class)
    public void testCreateWhenNameBlank() throws Exception {
        /* Set up test. */
        channelDto.setId(null);
        channelDto.setName("");

        /* Make the call. */
        toTest.create(channelDto);
    }

    /**
     * It should call {@link ChannelDao#delete(long)} with the provided ID and succeed when user is channel owner.
     *
     * @throws Exception on error.
     */
    @Test
    public void testDelete() throws Exception {
        /* Set up test. */
        Member member = new Member();
        member.setOwner(true);

        /* Train the mocks. */
        when(userService.updateCurrentUser()).thenReturn(groupeaseUser);
        when(memberDao.getForUser(groupeaseUser.getId(), channel.getId())).thenReturn(member);

        /* Make the call. */
        toTest.delete(channel.getId());

        /* Verify results. */
        verify(channelDao).delete(channel.getId());
    }

    /**
     * It should throw {@link ChannelEditByNonOwnerException} when user is not a channel member.
     *
     * @throws Exception on error.
     */
    @Test(expectedExceptions = ChannelEditByNonOwnerException.class)
    public void testDeleteWhenNotMember() throws Exception {
        /* Train the mocks. */
        when(userService.updateCurrentUser()).thenReturn(groupeaseUser);
        when(memberDao.getForUser(groupeaseUser.getId(), channel.getId())).thenReturn(null);

        /* Make the call. */
        toTest.delete(channel.getId());
    }

    /**
     * It should throw {@link ChannelEditByNonOwnerException} when user is not a channel member.
     *
     * @throws Exception on error.
     */
    @Test(expectedExceptions = ChannelEditByNonOwnerException.class)
    public void testDeleteWhenNotOwner() throws Exception {
        /* Set up test. */
        Member member = new Member();
        member.setOwner(false);

        /* Train the mocks. */
        when(userService.updateCurrentUser()).thenReturn(groupeaseUser);
        when(memberDao.getForUser(groupeaseUser.getId(), channel.getId())).thenReturn(member);

        /* Make the call. */
        toTest.delete(channel.getId());
    }

}
