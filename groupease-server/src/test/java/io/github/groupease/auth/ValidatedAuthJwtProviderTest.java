package io.github.groupease.auth;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Clock;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.groupease.GroupeaseTestGuiceModule;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

/**
 * Unit tests for {@link ValidatedAuthJwtProvider}.
 */
@Guice(modules = GroupeaseTestGuiceModule.class)
public class ValidatedAuthJwtProviderTest {

    private static final String AUTH_TOKEN_VALID =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik4wUXhPVUl3UXpnNU5UY3lOVVkwTmpFMk56Z3hSVVZFTkRrMU5ESTFSVVZGUmtNek5EY3pRdyJ9.eyJpc3MiOiJodHRwczovL21ja29vbi5hdXRoMC5jb20vIiwic3ViIjoiZ29vZ2xlLW9hdXRoMnwxMTE3NDYxNDM5NTcxMDkzNTQxOTciLCJhdWQiOlsiaHR0cHM6Ly9ncm91cGVhc2UuaGVyb2t1YXBwLmNvbSIsImh0dHBzOi8vbWNrb29uLmF1dGgwLmNvbS91c2VyaW5mbyJdLCJpYXQiOjE1MjIwMDI3MDYsImV4cCI6MTUyMjAwOTkwNiwiYXpwIjoiaWVlWUpUUFpmdzFqWlR2d0hqVjRIOUM0RWNNclQxQ3giLCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIn0.QnrCvnmPVTrUH1v_DNQp_wp1suyQDGGQSAoXWxPldf_Ed45dSdS1tqtni48dwO5HFPo-09pgHozsEtAGTlLchW0QLK6ooPI-V2drO1V43-00wlJTLwnHrL-9qUDbWSpoAiFhCtiFMzV9Q_3giNbD5ICGJXBdzgeApnscvgzp4_N6xslG1674S0EQKUQzl6cukl0AOcBuf_K2Fsf3SEjYc6CxRGqkpgKixzT_i-ax1zYkJubiIJGCs1VA4rStXJQ9_KwuRFeCL5yhiiqevvODXEPzoMAcoAVgYsJxqA1wWNOxOLG_pWxgUJI_cP_Au_pbp2g2TgkLY3F7C70pIgoOxg";

    @Inject
    private Algorithm algorithm;

    @Inject
    private Clock clock;

    @Inject
    @AuthToken
    private DecodedJWT decodedJwt;

    @Mock
    private Provider<String> authTokenProvider;

    @Mock
    private Provider<JWTVerifier> jwtVerifierProvider;

    private ValidatedAuthJwtProvider toTest;

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
                algorithm,
                clock,
                decodedJwt
        );

        /* Get instance to test. Not injecting so we can mock Provider. */
        toTest = new ValidatedAuthJwtProvider(
                authTokenProvider,
                jwtVerifierProvider
        );
    }

    /**
     * Gets a {@link JWTVerifier} with a modified date.
     *
     * @return {@link JWTVerifier} instance.
     */
    private JWTVerifier provideJwtVerifier() {
        JWTVerifier.BaseVerification verification = (JWTVerifier.BaseVerification) JWT.require(algorithm)
                .acceptLeeway(30);

        when(clock.getToday()).thenReturn(new Date(1522009896000L));

        return verification.build(clock);
    }

    /**
     * It should return null when authToken is null and jwtVerifier is null.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenTokenNullAndVerifierNull() throws Exception {
        /* Set up test. */
        final DecodedJWT expected = null;

        /* Train the mocks. */
        when(authTokenProvider.get()).thenReturn(null);
        when(jwtVerifierProvider.get()).thenReturn(null);

        /* Make the call. */
        DecodedJWT actual = toTest.get();

        /* Verify result. */
        assertEquals(actual, expected);
    }

    /**
     * It should return null when authToken is null and jwtVerifier is NOT null.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenTokenNullAndVerifierNotNull() throws Exception {
        /* Set up test. */
        final DecodedJWT expected = null;

        JWTVerifier jwtVerifier = provideJwtVerifier();

        /* Train the mocks. */
        when(authTokenProvider.get()).thenReturn(null);
        when(jwtVerifierProvider.get()).thenReturn(jwtVerifier);

        /* Make the call. */
        DecodedJWT actual = toTest.get();

        /* Verify result. */
        assertEquals(actual, expected);
    }

    /**
     * It should return null when authToken is NOT null and jwtVerifier is null.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenTokenNotNullAndVerifierNull() throws Exception {
        /* Set up test. */
        final DecodedJWT expected = null;

        /* Train the mocks. */
        when(authTokenProvider.get()).thenReturn(AUTH_TOKEN_VALID);
        when(jwtVerifierProvider.get()).thenReturn(null);

        /* Make the call. */
        DecodedJWT actual = toTest.get();

        /* Verify result. */
        assertEquals(actual, expected);
    }

    /**
     * It should return null when authToken & jwtVerifier NOT null and {@link JWTVerificationException}.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenJWTVerificationException() throws Exception {
        /* Set up test. */
        final DecodedJWT expected = null;

        JWTVerifier jwtVerifier = provideJwtVerifier();

        /* Train the mocks. */
        when(authTokenProvider.get()).thenReturn(AUTH_TOKEN_VALID);
        when(jwtVerifierProvider.get()).thenReturn(jwtVerifier);
        when(algorithm.getName()).thenReturn("RS256");
        doThrow(SignatureVerificationException.class).when(algorithm).verify(any(DecodedJWT.class));

        /* Make the call. */
        DecodedJWT actual = toTest.get();

        /* Verify result. */
        assertEquals(actual, expected);
    }

    /**
     * It should return {@link DecodedJWT} when authToken & jwtVerifier NOT null.
     *
     * @throws Exception on error.
     */
    @Test
    public void testGetWhenSuccess() throws Exception {
        /* Set up test. */
        JWTVerifier jwtVerifier = provideJwtVerifier();

        /* Train the mocks. */
        when(authTokenProvider.get()).thenReturn(AUTH_TOKEN_VALID);
        when(jwtVerifierProvider.get()).thenReturn(jwtVerifier);
        when(algorithm.getName()).thenReturn("RS256");

        /* Make the call. */
        DecodedJWT actual = toTest.get();

        /* Verify result. */
        assertNotNull(actual);
    }

}
