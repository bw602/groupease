package io.github.groupease.config.database;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.typesafe.config.Config;

import static java.util.Objects.requireNonNull;

/**
 * Guice module for configuring the database.
 */
public class DatabaseGuiceModule extends AbstractModule {

    private final Config config;

    /**
     * Constructor.
     *
     * @param config to fetch configuration.
     */
    public DatabaseGuiceModule(
            @Nonnull Config config
    ) {
        this.config = requireNonNull(config);
    }

    @Override
    protected void configure() {

        String persistenceUnitName = config.getString(
                "groupease.db.persistenceUnitName"
        );

        Map<String, String> dbProperties = getDbProperties();

        install(
                new JpaPersistModule(persistenceUnitName)
                        .properties(dbProperties)
        );
    }

    private Map<String, String> getDbProperties() {
        Map<String, String> dbProperties = new HashMap<>();

        /* Get DB properties sub-Config from config. */
        Config dbPropertyConfig = config.getConfig("groupease.db.properties");

        dbProperties.put(
                "javax.persistence.jdbc.driver",
                dbPropertyConfig.getString("driver")
        );

        dbProperties.put(
                "javax.persistence.jdbc.url",
                dbPropertyConfig.getString("url")
        );

        dbProperties.put(
                "hibernate.dialect",
                dbPropertyConfig.getString("dialect")
        );

        dbProperties.put(
                "hibernate.default_schema",
                dbPropertyConfig.getString("schema")
        );

        dbProperties.put(
                "hibernate.show_sql",
                dbPropertyConfig.getString("showSql")
        );

        dbProperties.put(
                "hibernate.format_sql",
                dbPropertyConfig.getString("formatSql")
        );

        return dbProperties;
    }

}
