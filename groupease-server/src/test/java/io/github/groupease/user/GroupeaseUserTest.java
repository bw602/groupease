package io.github.groupease.user;

import javax.inject.Inject;

import io.github.groupease.GroupeaseTestGuiceModule;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Unit tests for {@link GroupeaseUser}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class GroupeaseUserTest {

    @Inject
    private GroupeaseUser groupeaseUser;

    @Inject
    private GroupeaseUserDto groupeaseUserDto;

    /**
     * It should be equal to a builder built from the instance.
     *
     * @throws Exception on error.
     */
    @Test
    public void testBuildFromExistingGroupeaseUser() throws Exception {
        GroupeaseUser groupeaseUserCopy = GroupeaseUser.Builder.from(groupeaseUser).build();
        assertEquals(groupeaseUserCopy, groupeaseUser);
    }

    /**
     * It should be equal to a builder built from a filled {@link GroupeaseUserDto}.
     *
     * @throws Exception on error.
     */
    @Test
    public void testBuildFromGroupeaseUserDto() throws Exception {
        GroupeaseUser groupeaseUserCopy = GroupeaseUser.Builder.from(groupeaseUserDto).build();
        assertEquals(groupeaseUserCopy, groupeaseUser);
    }

}
