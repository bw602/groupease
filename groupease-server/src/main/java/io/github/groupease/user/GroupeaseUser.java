package io.github.groupease.user;

import java.time.Instant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

/**
 * Domain object to represent a person that uses the application.
 */
@Immutable
public class GroupeaseUser {

    private final long id;
    private final String providerUserId;
    private final String email;
    private final String name;
    private final String nickname;
    private final String pictureUrl;
    private final Instant lastUpdatedOn;

    /**
     * Creates a new {@link GroupeaseUser.Builder}.
     *
     * @return the new builder instance.
     */
    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Private constructor.
     *
     * @param builder {@link GroupeaseUser.Builder} to use to create instance.
     */
    private GroupeaseUser(
            @Nonnull Builder builder
    ) {
        id = requireNonNull(builder.getId());
        providerUserId = requireNonNull(builder.getProviderUserId());
        email = requireNonNull(builder.getEmail());
        name = requireNonNull(builder.getName());
        nickname = builder.getNickname();
        pictureUrl = builder.getPictureUrl();
        lastUpdatedOn = requireNonNull(builder.getLastUpdatedOn());
    }

    public long getId() {
        return id;
    }

    @Nonnull
    public String getProviderUserId() {
        return providerUserId;
    }

    @Nonnull
    public String getEmail() {
        return email;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nullable
    public String getNickname() {
        return nickname;
    }

    @Nullable
    public String getPictureUrl() {
        return pictureUrl;
    }

    @Nonnull
    public Instant getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    @Override
    public boolean equals(
            @Nullable Object o
    ) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Nonnull
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static class Builder {

        private Long id;
        private String providerUserId;
        private String email;
        private String name;
        private String nickname;
        private String pictureUrl;
        private Instant lastUpdatedOn;

        /**
         * Creates a new {@link GroupeaseUser} builder from an existing {@link GroupeaseUser}.
         *
         * @param groupeaseUser the {@link GroupeaseUser} from which to copy data.
         * @return the new builder.
         */
        @Nonnull
        public static Builder from(
                @Nonnull GroupeaseUser groupeaseUser
        ) {
            requireNonNull(groupeaseUser, "groupeaseUser cannot be null");

            return GroupeaseUser.builder()
                    .withId(groupeaseUser.getId())
                    .withProviderUserId(groupeaseUser.getProviderUserId())
                    .withEmail(groupeaseUser.getEmail())
                    .withName(groupeaseUser.getName())
                    .withNickname(groupeaseUser.getNickname())
                    .withPictureUrl(groupeaseUser.getPictureUrl())
                    .withLastUpdatedOn(groupeaseUser.getLastUpdatedOn());
        }

        /**
         * Creates a new {@link GroupeaseUser} builder from an existing {@link GroupeaseUserDto}.
         *
         * @param groupeaseUserDto the {@link GroupeaseUserDto} from which to copy data.
         * @return the new builder.
         */
        @Nonnull
        public static Builder from(
                @Nonnull GroupeaseUserDto groupeaseUserDto
        ) {
            requireNonNull(groupeaseUserDto, "groupeaseUserDto cannot be null");

            return GroupeaseUser.builder()
                    .withId(groupeaseUserDto.getId())
                    .withProviderUserId(groupeaseUserDto.getProviderUserId())
                    .withEmail(groupeaseUserDto.getEmail())
                    .withName(groupeaseUserDto.getName())
                    .withNickname(groupeaseUserDto.getNickname())
                    .withPictureUrl(groupeaseUserDto.getPictureUrl())
                    .withLastUpdatedOn(groupeaseUserDto.getLastUpdatedOn());
        }

        /**
         * Creates a new {@link GroupeaseUser} from the builder's properties.
         *
         * @return the new {@link GroupeaseUser} instance.
         */
        @Nonnull
        public GroupeaseUser build() {
            return new GroupeaseUser(this);
        }

        @Nullable
        public Long getId() {
            return id;
        }

        @Nonnull
        public Builder withId(
                @Nullable Long id
        ) {
            this.id = id;
            return this;
        }

        @Nullable
        public String getProviderUserId() {
            return providerUserId;
        }

        @Nonnull
        public Builder withProviderUserId(
                @Nullable String providerUserId
        ) {
            this.providerUserId = providerUserId;
            return this;
        }

        @Nullable
        public String getEmail() {
            return email;
        }

        @Nonnull
        public Builder withEmail(
                @Nullable String email
        ) {
            this.email = email;
            return this;
        }

        @Nullable
        public String getName() {
            return name;
        }

        @Nonnull
        public Builder withName(
                @Nullable String name
        ) {
            this.name = name;
            return this;
        }

        @Nullable
        public String getNickname() {
            return nickname;
        }

        @Nonnull
        public Builder withNickname(
                @Nullable String nickname
        ) {
            this.nickname = nickname;
            return this;
        }

        @Nullable
        public String getPictureUrl() {
            return pictureUrl;
        }

        @Nonnull
        public Builder withPictureUrl(
                @Nullable String pictureUrl
        ) {
            this.pictureUrl = pictureUrl;
            return this;
        }

        @Nullable
        public Instant getLastUpdatedOn() {
            return lastUpdatedOn;
        }

        @Nonnull
        public Builder withLastUpdatedOn(
                @Nullable Instant lastUpdatedOn
        ) {
            this.lastUpdatedOn = lastUpdatedOn;
            return this;
        }

        @Override
        public boolean equals(
                @Nullable Object o
        ) {
            return EqualsBuilder.reflectionEquals(this, o);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        @Nonnull
        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

    }

}
