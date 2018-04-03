package io.github.groupease.user.retrieval;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import io.github.groupease.user.GroupeaseUserDto;

/**
 * Business service for fetching user profile data.
 */
@Immutable
public interface UserRetrievalService {

    @Nonnull
    GroupeaseUserDto fetch();

}
