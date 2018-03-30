package io.github.groupease;

import java.security.interfaces.RSAPublicKey;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Clock;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.typesafe.config.Config;
import io.github.groupease.auth.AuthToken;
import io.github.groupease.channel.Channel;
import io.github.groupease.channel.ChannelDao;
import io.github.groupease.channel.ChannelDto;
import io.github.groupease.channel.ChannelService;
import io.github.groupease.config.guice.NoCache;
import io.github.groupease.exception.mapper.GroupeaseClientError;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Guice module for the test environment.
 */
public class GroupeaseTestGuiceModule extends AbstractModule {

    @Mock
    private Algorithm algorithm;

    @Mock
    private ChannelDao channelDao;

    @Mock
    private ChannelService channelService;

    @Mock
    private Clock clock;

    @Mock
    private Config config;

    @Mock
    private DecodedJWT decodedJwt;

    @Mock
    private FeatureContext featureContext;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private Jwk jwk;

    @Mock
    private JwkProvider jwkProvider;

    @Mock
    private JWT jwt;

    @Mock
    private ResourceInfo resourceInfo;

    @Mock
    private RSAPublicKey rsaPublicKey;

    @Override
    protected void configure() {
        initMocks(this);

        bind(Algorithm.class).toInstance(algorithm);
        bind(ChannelDao.class).toInstance(channelDao);
        bind(ChannelService.class).toInstance(channelService);
        bind(Clock.class).toInstance(clock);
        bind(Config.class).toInstance(config);
        bind(DecodedJWT.class).annotatedWith(AuthToken.class).toInstance(decodedJwt);
        bind(FeatureContext.class).toInstance(featureContext);
        bind(HttpServletRequest.class).toInstance(httpServletRequest);
        bind(Jwk.class).toInstance(jwk);
        bind(JwkProvider.class).toInstance(jwkProvider);
        bind(JWT.class).toInstance(jwt);
        bind(JwkProvider.class).annotatedWith(NoCache.class).toInstance(jwkProvider);
        bind(ResourceInfo.class).toInstance(resourceInfo);
        bind(RSAPublicKey.class).toInstance(rsaPublicKey);
    }

    /**
     * Provides a {@link Channel} instance for testing.
     *
     * @param channelDto the injected {@link ChannelDto} test instance.
     * @return the test {@link Channel} instance.
     */
    @Provides
    private Channel provideChannel(
            ChannelDto channelDto
    ) {
        return Channel.Builder.from(channelDto)
                .build();
    }

    /**
     * Provides a {@link ChannelDto} instance for testing.
     *
     * @return the test {@link ChannelDto} instance.
     */
    @Provides
    private ChannelDto provideChannelDto() {
        ChannelDto channelDto = new ChannelDto();

        channelDto.setId(12345L);
        channelDto.setName("Channel Name");
        channelDto.setDescription("Channel Description");
        channelDto.setLastUpdatedOn(Instant.ofEpochMilli(1519000000000L));

        return channelDto;
    }

    /**
     * Provides a {@link GroupeaseClientError} instance for testing.
     *
     * @return the {@link GroupeaseClientError} test instance.
     */
    @Provides
    private GroupeaseClientError provideGroupeaseClientError() {
        return GroupeaseClientError.builder()
                .withMessage("Some error message")
                .withType("Some class name")
                .build();
    }

}
