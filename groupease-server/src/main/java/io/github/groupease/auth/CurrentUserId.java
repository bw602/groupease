package io.github.groupease.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Annotation used to qualify the unique ID for the current user.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface CurrentUserId {
}
