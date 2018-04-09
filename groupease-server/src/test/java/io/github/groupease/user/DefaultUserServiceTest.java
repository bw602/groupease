package io.github.groupease.user;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.collect.ImmutableList;
import io.github.groupease.GroupeaseTestGuiceModule;
import io.github.groupease.model.GroupeaseUser;
import io.github.groupease.user.retrieval.UserRetrievalService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link DefaultUserService}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class DefaultUserServiceTest {

    @Inject
    private UserRetrievalService userRetrievalService;

    @Inject
    private UserDao userDao;

    @Inject
    private GroupeaseUser groupeaseUser;

    @Inject
    private Provider<GroupeaseUserDto> groupeaseUserDtoProvider;

    @Inject
    private Provider<DefaultUserService> toTestProvider;

    private DefaultUserService toTest;

    private GroupeaseUserDto groupeaseUserDto;

    /**
     * Set up tests.
     *
     * @throws Exception on error.
     */
    @BeforeMethod
    public void setUp() throws Exception {
        /* Reset all injected mocks between tests. */
        reset(
                userRetrievalService,
                userDao
        );

        /* Get new instance of GroupeaseUserDto for each test since it is mutable. */
        groupeaseUserDto = groupeaseUserDtoProvider.get();

        /* Get instance to test. */
        toTest = toTestProvider.get();
    }

    /**
     * It should call {@link UserDao#list()} and return the result.
     *
     * @throws Exception on error.
     */
    @Test
    public void testList() throws Exception {
        /* Set up test. */
        List<GroupeaseUser> expected = ImmutableList.of(groupeaseUser);

        /* Train the mocks. */
        when(userDao.list()).thenReturn(expected);

        /* Make the call. */
        List<GroupeaseUser> actual = toTest.list();

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should call {@link UserDao#getById(long)} and return the result.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetById() throws Exception {
        /* Set up test. */
        GroupeaseUser expected = groupeaseUser;

        /* Train the mocks. */
        when(userDao.getById(groupeaseUser.getId())).thenReturn(expected);

        /* Make the call. */
        GroupeaseUser actual = toTest.getById(groupeaseUser.getId());

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should fetch user data, save it, and return the result.
     *
     * @throws Exception on error.
     */
    @Test
    public void testUpdateCurrentUser() throws Exception {
        /* Set up test. */
        GroupeaseUser expected = groupeaseUser;

        /* GroupeaseUserDto will not have DB-generated fields. */
        groupeaseUserDto.setId(null);
        groupeaseUserDto.setLastUpdatedOn(null);

        /* Train the mocks. */
        when(userRetrievalService.fetch()).thenReturn(groupeaseUserDto);
        when(userDao.save(groupeaseUserDto)).thenReturn(groupeaseUser);

        /* Make the call. */
        GroupeaseUser actual = toTest.updateCurrentUser();

        /* Verify results. */
        assertEquals(actual, expected);
    }

}
