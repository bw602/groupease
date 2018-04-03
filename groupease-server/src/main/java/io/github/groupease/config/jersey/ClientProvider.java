package io.github.groupease.config.jersey;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import static java.util.Objects.requireNonNull;

/**
 * Provides the configured Jersey client for communication with REST servers.
 */
@Immutable
public class ClientProvider implements Provider<Client> {

    private final ObjectMapperContextResolver objectMapperContextResolver;

    /**
     * Injectable constructor.
     *
     * @param objectMapperContextResolver to register.
     */
    @Inject
    public ClientProvider(
            @Nonnull ObjectMapperContextResolver objectMapperContextResolver
    ) {
        this.objectMapperContextResolver = requireNonNull(objectMapperContextResolver);
    }

    @Nonnull
    @Override
    public Client get() {
        return ClientBuilder.newClient()
                .register(objectMapperContextResolver)
                .register(JacksonJaxbJsonProvider.class);
    }

}
