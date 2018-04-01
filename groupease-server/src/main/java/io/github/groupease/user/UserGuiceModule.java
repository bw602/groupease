package io.github.groupease.user;

import com.google.inject.AbstractModule;
import io.github.groupease.user.retrieval.UserRetrievalGuiceModule;

/**
 * Guice module for the user package.
 */
public class UserGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new UserRetrievalGuiceModule());

        bind(UserDao.class).to(JpaUserDao.class);
        bind(UserService.class).to(DefaultUserService.class);
    }

}
