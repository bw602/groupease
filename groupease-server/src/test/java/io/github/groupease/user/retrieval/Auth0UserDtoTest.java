package io.github.groupease.user.retrieval;

import javax.inject.Inject;

import io.github.groupease.GroupeaseTestGuiceModule;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Unit tests for {@link Auth0UserDto}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class Auth0UserDtoTest {

    @Inject
    private Auth0UserDto auth0UserDto;

    /**
     * It should be equal to a builder built from the instance.
     *
     * @throws Exception on error.
     */
    @Test
    public void testBuildFromExisting() throws Exception {
        Auth0UserDto auth0UserDtoCopy = Auth0UserDto.Builder.from(auth0UserDto).build();
        assertEquals(auth0UserDtoCopy, auth0UserDto);
    }

}
