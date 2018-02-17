package io.github.groupease.config.jersey;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

import static java.util.Objects.requireNonNull;

/**
 * Provides Guice's ObjectMapper to Jersey.
 */
@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper objectMapper;

    /**
     * Injectable constructor.
     *
     * @param objectMapper - Configured Jackson {@link ObjectMapper}.
     */
    @Inject
    public ObjectMapperContextResolver(
            @Nonnull ObjectMapper objectMapper
    ) {
        this.objectMapper = requireNonNull(objectMapper);
    }

    @Override
    public ObjectMapper getContext(
            Class<?> type
    ) {
        return objectMapper;
    }

}
