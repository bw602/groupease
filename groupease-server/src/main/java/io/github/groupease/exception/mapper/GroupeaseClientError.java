package io.github.groupease.exception.mapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.google.common.base.Strings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

/**
 * Domain object representing an error condition to be returned to the client.
 */
@Immutable
public final class GroupeaseClientError {

    private final String type;

    private final String message;

    /**
     * Creates a new {@link GroupeaseClientError} builder.
     *
     * @return the new builder.
     */
    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Private constructor.
     * Defaults message to empty string; type must be non-null.
     *
     * @param builder {@link GroupeaseClientError.Builder} to use to create instance.
     */
    private GroupeaseClientError(
            @Nonnull Builder builder
    ) {
        requireNonNull(builder);
        this.type = requireNonNull(builder.getType());
        this.message = Strings.nullToEmpty(builder.getMessage());
    }

    @Nonnull
    public String getType() {
        return type;
    }

    @Nonnull
    public String getMessage() {
        return message;
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
     * Builder for constructing {@link GroupeaseClientError} instances.
     */
    public static class Builder {

        private String type;
        private String message;

        /**
         * Creates a new builder from an existing {@link GroupeaseClientError}.
         *
         * @param GroupeaseClientError the {@link GroupeaseClientError} to copy data from.
         * @return the new builder.
         */
        @Nonnull
        public static Builder from(
                @Nonnull GroupeaseClientError GroupeaseClientError
        ) {
            return builder()
                    .withType(GroupeaseClientError.getType())
                    .withMessage(GroupeaseClientError.getMessage());
        }

        /**
         * Creates a new builder from an existing {@link Throwable}.
         *
         * @param throwable the {@link Throwable} to copy data from.
         * @return the new builder.
         */
        @Nonnull
        public static Builder from(
                @Nonnull Throwable throwable
        ) {
            return builder()
                    .withType(throwable.getClass().getName())
                    .withMessage(throwable.getMessage());
        }

        @Nullable
        public String getType() {
            return type;
        }

        /**
         * Sets type and returns the builder.
         *
         * @param type the type to set.
         * @return the builder.
         */
        @Nonnull
        public Builder withType(
                @Nullable String type
        ) {
            this.type = type;
            return this;
        }

        @Nullable
        public String getMessage() {
            return message;
        }

        /**
         * Sets message and returns the builder.
         *
         * @param message the message to set.
         * @return the builder.
         */
        @Nonnull
        public Builder withMessage(
                @Nullable String message
        ) {
            this.message = message;
            return this;
        }

        /**
         * Creates a new {@link GroupeaseClientError} from the builder's properties.
         *
         * @return the new {@link GroupeaseClientError} instance.
         */
        @Nonnull
        public GroupeaseClientError build() {
            return new GroupeaseClientError(this);
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
