package io.github.groupease.user.retrieval;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;

import io.github.groupease.GroupeaseTestGuiceModule;
import io.github.groupease.user.GroupeaseUserDto;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link Auth0UserRetrievalService}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class Auth0UserRetrievalServiceTest {

    @Inject
    @UserProfile
    private WebTarget webTarget;

    @Inject
    private Invocation.Builder invocationBuilder;

    @Inject
    private Auth0UserDto auth0UserDto;

    @Inject
    private Provider<GroupeaseUserDto> groupeaseUserDtoProvider;

    @Mock
    private Provider<String> authTokenProvider;

    private GroupeaseUserDto groupeaseUserDto;

    private Auth0UserRetrievalService toTest;

    /**
     * Set up tests.
     *
     * @throws Exception on error.
     */
    @BeforeMethod
    public void setUp() throws Exception {
        /* Initialize local mocks. */
        initMocks(this);

        /* Get new instance of GroupeaseUserDto for each test since it is mutable. */
        groupeaseUserDto = groupeaseUserDtoProvider.get();

        /* Reset all injected mocks between tests. */
        reset(
                invocationBuilder,
                webTarget
        );

        /* Get instance to test. Not injecting so we can mock Provider. */
        toTest = new Auth0UserRetrievalService(
                authTokenProvider,
                webTarget
        );
    }

    /**
     * It should throw {@link NullPointerException} when auth token is null.
     *
     * @throws Exception on error.
     */
    @Test(expectedExceptions = NullPointerException.class)
    public void testFetchWhenTokenNull() throws Exception {
        /* Train the mocks. */
        when(authTokenProvider.get()).thenReturn(null);

        /* Make the call. */
        toTest.fetch();
    }

    /**
     * It should return {@link GroupeaseUserDto} matching the {@link Auth0UserDto}.
     *
     * @throws Exception on error.
     */
    @Test
    public void testFetchWhenSuccess() throws Exception {
        /* Set up test. */

        /* GroupeaseUserDto will not have DB-generated fields. */
        groupeaseUserDto.setId(null);
        groupeaseUserDto.setLastUpdatedOn(null);

        String authToken = "some token";
        String authHeader = "Bearer " + authToken;

        /* Train the mocks. */
        when(authTokenProvider.get()).thenReturn(authToken);
        when(webTarget.request()).thenReturn(invocationBuilder);
        when(
                invocationBuilder.header(
                        HttpHeaders.AUTHORIZATION,
                        authHeader
                )
        ).thenReturn(invocationBuilder);
        when(invocationBuilder.get(Auth0UserDto.class)).thenReturn(auth0UserDto);

        /* Make the call. */
        GroupeaseUserDto actual = toTest.fetch();

        /* Verify results. */
        assertEquals(actual, groupeaseUserDto);
    }

}
