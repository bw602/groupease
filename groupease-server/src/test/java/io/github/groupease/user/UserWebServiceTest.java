package io.github.groupease.user;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.collect.ImmutableList;
import io.github.groupease.GroupeaseTestGuiceModule;
import io.github.groupease.model.GroupeaseUser;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link UserWebService}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class UserWebServiceTest {

    @Inject
    private UserService userService;

    @Inject
    private GroupeaseUser groupeaseUser;

    @Inject
    private Provider<UserWebService> toTestProvider;

    private UserWebService toTest;

    /**
     * Set up tests.
     *
     * @throws Exception on error.
     */
    @BeforeMethod
    public void setUp() throws Exception {
        /* Reset all injected mocks between tests. */
        reset(userService);

        /* Get instance to test. */
        toTest = toTestProvider.get();
    }

    /**
     * It should call {@link UserService#list()} and return the result.
     *
     * @throws Exception on error.
     */
    @Test
    public void testList() throws Exception {
        /* Set up test. */
        List<GroupeaseUser> expected = ImmutableList.of(groupeaseUser);

        /* Train the mocks. */
        when(userService.list()).thenReturn(expected);

        /* Make the call. */
        List<GroupeaseUser> actual = toTest.list();

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should call {@link UserService#getById(long)} and return the result.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetById() throws Exception {
        /* Set up test. */
        GroupeaseUser expected = groupeaseUser;

        /* Train the mocks. */
        when(userService.getById(groupeaseUser.getId())).thenReturn(expected);

        /* Make the call. */
        GroupeaseUser actual = toTest.getById(groupeaseUser.getId());

        /* Verify results. */
        assertEquals(actual, expected);
    }

    /**
     * It should call {@link UserService#updateCurrentUser()} and return the result.
     *
     * @throws Exception on error.
     */
    @Test
    public void testUpdateCurrentUser() throws Exception {
        /* Set up test. */
        GroupeaseUser expected = groupeaseUser;

        /* Train the mocks. */
        when(userService.updateCurrentUser()).thenReturn(expected);

        /* Make the call. */
        GroupeaseUser actual = toTest.updateCurrentUser();

        /* Verify results. */
        assertEquals(actual, expected);
    }

}
