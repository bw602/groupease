package io.github.groupease.user.retrieval;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

/**
 * Data Transfer Object representing the user data Auth0 provides.
 */
@Immutable
@JsonDeserialize(builder = Auth0UserDto.Builder.class)
public class Auth0UserDto {

    private final String email;
    private final String familyName;
    private final String gender;
    private final String givenName;
    private final String locale;
    private final String name;
    private final String nickname;
    private final String picture;
    private final String sub;
    private final String updatedAt;

    /**
     * Creates a new {@link Auth0UserDto.Builder}.
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
     * @param builder {@link Auth0UserDto.Builder} to use to create instance.
     */
    private Auth0UserDto(
            @Nonnull Builder builder
    ) {
        this.email = builder.getEmail();
        this.familyName = builder.getFamilyName();
        this.gender = builder.getGender();
        this.givenName = builder.getGivenName();
        this.locale = builder.getLocale();
        this.name = builder.getName();
        this.nickname = builder.getNickname();
        this.picture = builder.getPicture();
        this.sub = builder.getSub();
        this.updatedAt = builder.getUpdatedAt();
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getFamilyName() {
        return familyName;
    }

    @Nullable
    public String getGender() {
        return gender;
    }

    @Nullable
    public String getGivenName() {
        return givenName;
    }

    @Nullable
    public String getLocale() {
        return locale;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getNickname() {
        return nickname;
    }

    @Nullable
    public String getPicture() {
        return picture;
    }

    @Nullable
    public String getSub() {
        return sub;
    }

    @Nullable
    public String getUpdatedAt() {
        return updatedAt;
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

    /**
     * Builder for creating {@link Auth0UserDto} instances.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Builder {

        private String email;
        private String familyName;
        private String gender;
        private String givenName;
        private String locale;
        private String name;
        private String nickname;
        private String picture;
        private String sub;
        private String updatedAt;

        /**
         * Creates a new {@link Auth0UserDto} builder from an existing {@link Auth0UserDto}.
         *
         * @param auth0UserDto the {@link Auth0UserDto} from which to copy data.
         * @return the new builder.
         */
        @Nonnull
        public static Builder from(
                @Nonnull Auth0UserDto auth0UserDto
        ) {
            requireNonNull(auth0UserDto, "auth0UserDto cannot be null");

            return Auth0UserDto.builder()
                    .withEmail(auth0UserDto.getEmail())
                    .withFamilyName(auth0UserDto.getFamilyName())
                    .withGender(auth0UserDto.getGender())
                    .withGivenName(auth0UserDto.getGivenName())
                    .withLocale(auth0UserDto.getLocale())
                    .withName(auth0UserDto.getName())
                    .withNickname(auth0UserDto.getNickname())
                    .withPicture(auth0UserDto.getPicture())
                    .withSub(auth0UserDto.getSub())
                    .withUpdatedAt(auth0UserDto.getUpdatedAt());
        }

        @Nullable
        public String getEmail() {
            return email;
        }

        /**
         * Sets the email and returns the builder.
         *
         * @param email the email to set.
         * @return the builder.
         */
        @Nonnull
        public Builder withEmail(
                @Nullable String email
        ) {
            this.email = email;
            return this;
        }

        @Nullable
        public String getFamilyName() {
            return familyName;
        }

        /**
         * Sets the familyName and returns the builder.
         *
         * @param familyName the familyName to set.
         * @return the builder.
         */
        @JsonProperty("family_name")
        @Nonnull
        public Builder withFamilyName(
                @Nullable String familyName
        ) {
            this.familyName = familyName;
            return this;
        }

        @Nullable
        public String getGender() {
            return gender;
        }

        /**
         * Sets the gender and returns the builder.
         *
         * @param gender the gender to set.
         * @return the builder.
         */
        @Nonnull
        public Builder withGender(
                @Nullable String gender
        ) {
            this.gender = gender;
            return this;
        }

        @Nullable
        public String getGivenName() {
            return givenName;
        }

        /**
         * Sets the givenName and returns the builder.
         *
         * @param givenName the givenName to set.
         * @return the builder.
         */
        @JsonProperty("given_name")
        @Nonnull
        public Builder withGivenName(
                @Nullable String givenName
        ) {
            this.givenName = givenName;
            return this;
        }

        @Nullable
        public String getLocale() {
            return locale;
        }

        /**
         * Sets the locale and returns the builder.
         *
         * @param locale the locale to set.
         * @return the builder.
         */
        @Nonnull
        public Builder withLocale(
                @Nullable String locale
        ) {
            this.locale = locale;
            return this;
        }

        @Nullable
        public String getName() {
            return name;
        }

        /**
         * Sets the name and returns the builder.
         *
         * @param name the name to set.
         * @return the builder.
         */
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

        /**
         * Sets the nickname and returns the builder.
         *
         * @param nickname the nickname to set.
         * @return the builder.
         */
        @Nonnull
        public Builder withNickname(
                @Nullable String nickname
        ) {
            this.nickname = nickname;
            return this;
        }

        @Nullable
        public String getPicture() {
            return picture;
        }

        /**
         * Sets the picture and returns the builder.
         *
         * @param picture the picture to set.
         * @return the builder.
         */
        @Nonnull
        public Builder withPicture(
                @Nullable String picture
        ) {
            this.picture = picture;
            return this;
        }

        @Nullable
        public String getSub() {
            return sub;
        }

        /**
         * Sets the sub and returns the builder.
         *
         * @param sub the sub to set.
         * @return the builder.
         */
        @Nonnull
        public Builder withSub(
                @Nullable String sub
        ) {
            this.sub = sub;
            return this;
        }

        @Nullable
        public String getUpdatedAt() {
            return updatedAt;
        }

        /**
         * Sets the updatedAt and returns the builder.
         *
         * @param updatedAt the updatedAt to set.
         * @return the builder.
         */
        @JsonProperty("updated_at")
        @Nonnull
        public Builder withUpdatedAt(
                @Nullable String updatedAt
        ) {
            this.updatedAt = updatedAt;
            return this;
        }

        /**
         * Creates a new {@link Auth0UserDto} from the builder's properties.
         *
         * @return the new {@link Auth0UserDto} instance.
         */
        @Nonnull
        public Auth0UserDto build() {
            return new Auth0UserDto(this);
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
