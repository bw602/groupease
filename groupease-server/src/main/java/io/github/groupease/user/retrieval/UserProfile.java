package io.github.groupease.user.retrieval;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Annotation used to qualify instances related to a user profile.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface UserProfile {
}
