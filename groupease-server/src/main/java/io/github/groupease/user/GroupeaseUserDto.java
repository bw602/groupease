package io.github.groupease.user;

import java.time.Instant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *  Data Transfer Object entity for passing {@link GroupeaseUser} data from client and to/from the database.
 */
@Entity
@Cacheable
@Table(name = "GroupeaseUser")
public class GroupeaseUserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String providerUserId;

    private String email;

    @NotNull
    private String name;

    private String nickname;

    private String pictureUrl;

    @UpdateTimestamp
    private Instant lastUpdatedOn;

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(
            @Nullable Long id
    ) {
        this.id = id;
    }

    @Nullable
    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(
            @Nullable String providerUserId
    ) {
        this.providerUserId = providerUserId;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(
            @Nullable String email
    ) {
        this.email = email;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(
            @Nullable String name
    ) {
        this.name = name;
    }

    @Nullable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(
            @Nullable String nickname
    ) {
        this.nickname = nickname;
    }

    @Nullable
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(
            @Nullable String pictureUrl
    ) {
        this.pictureUrl = pictureUrl;
    }

    @Nullable
    public Instant getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(
            @Nullable Instant lastUpdatedOn
    ) {
        this.lastUpdatedOn = lastUpdatedOn;
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
