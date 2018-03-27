package io.github.groupease.channel;

import java.time.Instant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.google.common.base.Strings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

/**
 * Domain object representing the scope within users and groups operate (e.g. a single section of a course).
 */
@Immutable
public final class Channel {

    private long id;
    private String name;
    private String description;
    private Instant createdOn;
    private Instant lastUpdatedOn;

    /**
     * Creates a new {@link Channel.Builder}.
     *
     * @return the new builder instance.
     */
    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Private constructor.
     * Defaults description to empty string; all other properties must be non-null.
     *
     * @param builder {@link Channel.Builder} to use to create instance.
     */
    private Channel(
            @Nonnull Builder builder
    ) {
        this.id = requireNonNull(builder.getId());
        this.name = requireNonNull(builder.getName());
        this.description = Strings.nullToEmpty(builder.getDescription());
        this.createdOn = requireNonNull(builder.getCreatedOn());
        this.lastUpdatedOn = requireNonNull(builder.getLastUpdatedOn());
    }

    public long getId() {
        return id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    @Nonnull
    public Instant getCreatedOn() {
        return createdOn;
    }

    @Nonnull
    public Instant getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    @Override
    public boolean equals(Object o) {
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
     * Builder for creating {@link Channel} instances.
     */
    public static class Builder {

        private Long id;
        private String name;
        private String description;
        private Instant createdOn;
        private Instant lastUpdatedOn;

        /**
         * Creates a new {@link Channel} builder from an existing {@link Channel}.
         *
         * @param channel the {@link Channel} from which to copy data.
         * @return the new builder.
         */
        @Nonnull
        public static Builder from(
                @Nonnull Channel channel
        ) {
            requireNonNull(channel, "channel cannot be null");

            return Channel.builder()
                    .withId(channel.getId())
                    .withName(channel.getName())
                    .withDescription(channel.getDescription())
                    .withCreatedOn(channel.getCreatedOn())
                    .withLastUpdatedOn(channel.getLastUpdatedOn());
        }

        /**
         * Creates a new {@link Channel} builder from an existing {@link ChannelDto}.
         *
         * @param channelDto the {@link ChannelDto} from which to copy data.
         * @return the new builder.
         */
        @Nonnull
        public static Builder from(
                @Nonnull ChannelDto channelDto
        ) {
            requireNonNull(channelDto, "channelDto cannot be null");

            return Channel.builder()
                    .withId(channelDto.getId())
                    .withName(channelDto.getName())
                    .withDescription(channelDto.getDescription())
                    .withCreatedOn(channelDto.getCreatedOn())
                    .withLastUpdatedOn(channelDto.getLastUpdatedOn());
        }

        @Nullable
        public Long getId() {
            return id;
        }

        /**
         * Sets the id and returns the builder.
         *
         * @param id the id to set.
         * @return the builder.
         */
        @Nonnull
        public Builder withId(
                @Nullable Long id
        ) {
            this.id = id;
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
        public String getDescription() {
            return description;
        }

        /**
         * Sets the description and returns the builder.
         *
         * @param description the description to set.
         * @return the builder.
         */
        @Nonnull
        public Builder withDescription(
                @Nullable String description
        ) {
            this.description = description;
            return this;
        }

        @Nullable
        public Instant getCreatedOn() {
            return createdOn;
        }

        /**
         * Sets the createdOn and returns the builder.
         *
         * @param createdOn the createdOn to set.
         * @return the builder.
         */
        @Nonnull
        public Builder withCreatedOn(
                @Nullable Instant createdOn
        ) {
            this.createdOn = createdOn;
            return this;
        }

        @Nullable
        public Instant getLastUpdatedOn() {
            return lastUpdatedOn;
        }

        /**
         * Sets the lastUpdatedOn and returns the builder.
         *
         * @param lastUpdatedOn the lastUpdatedOn to set.
         * @return the builder.
         */
        @Nonnull
        public Builder withLastUpdatedOn(
                @Nullable Instant lastUpdatedOn
        ) {
            this.lastUpdatedOn = lastUpdatedOn;
            return this;
        }

        /**
         * Creates a new {@link Channel} from the builder's properties.
         *
         * @return the new {@link Channel} instance.
         */
        @Nonnull
        public Channel build() {
            return new Channel(this);
        }

        @Override
        public boolean equals(Object o) {
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
