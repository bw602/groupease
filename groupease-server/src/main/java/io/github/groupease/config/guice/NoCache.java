package io.github.groupease.config.guice;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Annotation used to qualify instances that explicitly do NOT cache.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface NoCache {
}
