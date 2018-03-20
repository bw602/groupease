package io.github.groupease.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Annotation used to qualify the non-verifying encoded JWT auth token.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface AuthToken {
}
