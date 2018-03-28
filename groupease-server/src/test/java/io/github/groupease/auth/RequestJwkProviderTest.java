package io.github.groupease.auth;

import javax.inject.Inject;
import javax.inject.Provider;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.JWT;
import io.github.groupease.GroupeaseTestGuiceModule;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link RequestJwkProvider}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class RequestJwkProviderTest {

    /* No "kid" field. */
    private static final String AUTH_TOKEN_NO_KEY =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.e30.DMCAvRgzrcf5w0Z879BsqzcrnDFKBY_GN6c3qKOUFtQ";

    private static final String AUTH_TOKEN_VALID =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik4wUXhPVUl3UXpnNU5UY3lOVVkwTmpFMk56Z3hSVVZFTkRrMU5ESTFSVVZGUmtNek5EY3pRdyJ9.eyJpc3MiOiJodHRwczovL21ja29vbi5hdXRoMC5jb20vIiwic3ViIjoiZ29vZ2xlLW9hdXRoMnwxMTE3NDYxNDM5NTcxMDkzNTQxOTciLCJhdWQiOlsiaHR0cHM6Ly9ncm91cGVhc2UuaGVyb2t1YXBwLmNvbSIsImh0dHBzOi8vbWNrb29uLmF1dGgwLmNvbS91c2VyaW5mbyJdLCJpYXQiOjE1MjIwMDI3MDYsImV4cCI6MTUyMjAwOTkwNiwiYXpwIjoiaWVlWUpUUFpmdzFqWlR2d0hqVjRIOUM0RWNNclQxQ3giLCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIn0.QnrCvnmPVTrUH1v_DNQp_wp1suyQDGGQSAoXWxPldf_Ed45dSdS1tqtni48dwO5HFPo-09pgHozsEtAGTlLchW0QLK6ooPI-V2drO1V43-00wlJTLwnHrL-9qUDbWSpoAiFhCtiFMzV9Q_3giNbD5ICGJXBdzgeApnscvgzp4_N6xslG1674S0EQKUQzl6cukl0AOcBuf_K2Fsf3SEjYc6CxRGqkpgKixzT_i-ax1zYkJubiIJGCs1VA4rStXJQ9_KwuRFeCL5yhiiqevvODXEPzoMAcoAVgYsJxqA1wWNOxOLG_pWxgUJI_cP_Au_pbp2g2TgkLY3F7C70pIgoOxg";

    private static final String KEY_ID = "N0QxOUIwQzg5NTcyNUY0NjE2NzgxRUVENDk1NDI1RUVFRkMzNDczQw";

    @Inject
    private Jwk jwk;

    @Inject
    private JwkProvider jwkProvider;

    @Inject
    private JWT jwt;

    @Mock
    private Provider<String> authTokenProvider;

    private RequestJwkProvider toTest;

    /**
     * Set up tests.
     *
     * @throws Exception on error.
     */
    @BeforeMethod
    public void setUp() throws Exception {
        /* Initialize local mocks. */
        initMocks(this);

        /* Reset all injected mocks between tests. */
        reset(
                jwk,
                jwkProvider,
                jwt
        );

        /* Get instance to test. Not injecting so we can mock Provider. */
        toTest = new RequestJwkProvider(
                jwkProvider,
                authTokenProvider
        );
    }

    /**
     * It should return null when authToken is null.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenAuthTokenIsNull() throws Exception {
        /* Set up test. */
        final Jwk expected = null;

        /* Train the mocks. */
        when(authTokenProvider.get()).thenReturn(null);

        /* Make the call. */
        Jwk actual = toTest.get();

        /* Verify result. */
        assertEquals(actual, expected);
    }

    /**
     * It should return null when key ID (kid) is null.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenKeyIdIsNull() throws Exception {
        /* Set up test. */
        final Jwk expected = null;

        /* Train the mocks. */
        when(authTokenProvider.get()).thenReturn(AUTH_TOKEN_NO_KEY);

        /* Make the call. */
        Jwk actual = toTest.get();

        /* Verify result. */
        assertEquals(actual, expected);
    }

    /**
     * It should return null on {@link JwkException}.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenJwkException() throws Exception {
        /* Set up test. */
        final Jwk expected = null;

        /* Train the mocks. */
        when(authTokenProvider.get()).thenReturn(AUTH_TOKEN_VALID);
        when(jwkProvider.get(KEY_ID)).thenThrow(JwkException.class);

        /* Make the call. */
        Jwk actual = toTest.get();

        /* Verify result. */
        assertEquals(actual, expected);
    }

    /**
     * It should return JWK when authToken and key ID are valid and key ID is known.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenSuccess() throws Exception {
        /* Set up test. */
        final Jwk expected = jwk;

        /* Train the mocks. */
        when(authTokenProvider.get()).thenReturn(AUTH_TOKEN_VALID);
        when(jwkProvider.get(KEY_ID)).thenReturn(jwk);

        /* Make the call. */
        Jwk actual = toTest.get();

        /* Verify result. */
        assertEquals(actual, expected);
    }

}
