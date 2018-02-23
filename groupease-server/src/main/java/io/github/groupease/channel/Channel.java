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
     * Private constructor.
     *
     * @param id the numeric identifier for this channel.
     * @param name the display name.
     * @param description the channel description.
     * @param createdOn the date this channel was created.
     * @param lastUpdatedOn the date this channel was last updated.
     */
    private Channel(
            long id,
            @Nonnull String name,
            @Nonnull String description,
            @Nonnull Instant createdOn,
            @Nonnull Instant lastUpdatedOn
    ) {
        this.id = id;
        this.name = requireNonNull(name);
        this.description = requireNonNull(description);
        this.createdOn = requireNonNull(createdOn);
        this.lastUpdatedOn = requireNonNull(lastUpdatedOn);
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
         * Creates a new {@link Channel} builder.
         *
         * @return the new builder
         */
        @Nonnull
        public static Builder builder() {
            return new Builder();
        }

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

            return builder()
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

            return builder()
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
            /* Defaults description to empty string; other properties are required. */
            return new Channel(
                    getId(),
                    getName(),
                    Strings.nullToEmpty(getDescription()),
                    getCreatedOn(),
                    getLastUpdatedOn()
            );
        }

        @Override
        public boolean equals(Object o) {
            return EqualsBuilder.reflectionEquals(this, o);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

    }

}
