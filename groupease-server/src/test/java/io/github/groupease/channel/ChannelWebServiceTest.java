package io.github.groupease.channel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.collect.ImmutableList;
import io.github.groupease.GroupeaseTestGuiceModule;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link ChannelWebService}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class ChannelWebServiceTest {

    @Inject
    private ChannelService channelService;

    @Inject
    private Channel channel;

    @Inject
    private ChannelDto channelDto;

    @Inject
    private Provider<ChannelWebService> toTestProvider;

    private ChannelWebService toTest;

    /**
     * Set up tests.
     *
     * @throws Exception on error.
     */
    @BeforeMethod
    public void setUp() throws Exception {
        /* Reset all injected mocks between tests. */
        reset(channelService);

        /* Get instance to test. */
        toTest = toTestProvider.get();
    }

    /**
     * It should call {@link ChannelService#list()} and return the result.
     *
     * @throws Exception on error.
     */
    @Test
    public void testList() throws Exception {
        /* Set up test. */
        List<Channel> expected = ImmutableList.of(channel);

        /* Train the mocks. */
        when(channelService.list()).thenReturn(expected);

        /* Make the call. */
        List<Channel> actual = toTest.list();

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should call {@link ChannelService#getById(long)} and return the result.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetById() throws Exception {
        /* Set up test. */
        Channel expected = channel;

        /* Train the mocks. */
        when(channelService.getById(channel.getId())).thenReturn(expected);

        /* Make the call. */
        Channel actual = toTest.getById(channel.getId());

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should call {@link ChannelService#update(ChannelDto)} and return the result.
     *
     * @throws Exception on error.
     */
    @Test
    public void testUpdate() throws Exception {
        /* Set up test. */
        Channel expected = channel;

        /* Train the mocks. */
        when(channelService.update(channelDto)).thenReturn(expected);

        /* Make the call. */
        Channel actual = toTest.update(
                channelDto.getId(),
                channelDto
        );

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should throw {@link ChannelIdMismatchException} if ID does not match {@link ChannelDto} to update.
     *
     * @throws Exception on error.
     */
    @Test(expectedExceptions = ChannelIdMismatchException.class)
    public void testUpdateIdMismatch() throws Exception {
        /* Make the call. */
        toTest.update(
                channelDto.getId() + 1L,
                channelDto
        );
    }

    /**
     * It should call {@link ChannelService#create(ChannelDto)} and return the result.
     *
     * @throws Exception on error.
     */
    @Test
    public void testCreate() throws Exception {
        /* Set up test. */
        Channel expected = channel;

        /* Train the mocks. */
        when(channelService.create(channelDto)).thenReturn(expected);

        /* Make the call. */
        Channel actual = toTest.create(channelDto);

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should call {@link ChannelService#delete(long)} with the provided ID.
     *
     * @throws Exception on error.
     */
    @Test
    public void testDelete() throws Exception {
        /* Make the call. */
        toTest.delete(channel.getId());

        /* Verify results. */
        verify(channelService).delete(channel.getId());
    }

}
