package io.github.groupease.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.groupease.user.GroupeaseUserDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.UpdateTimestamp;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name="GroupeaseUser")
public class GroupeaseUser
{
    @Id
    private Long id;
    private String providerUserId;
    private String name;
    private String nickName;
    private String email;
    private String pictureUrl;

    @UpdateTimestamp
    @Column(name = "lastUpdatedOn")
    private Instant lastUpdate;

    @OneToMany(mappedBy = "userProfile")
    private List<Member> memberList;

    // Constructors
    public GroupeaseUser() {}

    /**
     * Conversion constructor from auth library user profile to local db copy
     * @param dto The user profile data from the authentication service (Auth0)
     */
    public GroupeaseUser(GroupeaseUserDto dto)
    {
        id = dto.getId();
        providerUserId = dto.getProviderUserId();
        name = dto.getName();
        nickName = dto.getNickname();
        email = dto.getEmail();
        pictureUrl = dto.getPictureUrl();
        lastUpdate = dto.getLastUpdatedOn();
    }

    /**
     * Conversion constructor from auth library user profile to local db copy with ID override.
     * Use when merging refreshed profile information with an existing profile in the database
     * @param dto The user profile data from the authentication service (Auth0)
     * @param id The unique ID for the user
     */
    public GroupeaseUser(GroupeaseUserDto dto, long id)
    {
        this.id = id;
        providerUserId = dto.getProviderUserId();
        name = dto.getName();
        nickName = dto.getNickname();
        email = dto.getEmail();
        pictureUrl = dto.getPictureUrl();
        lastUpdate = dto.getLastUpdatedOn();
    }

    // Infrastructure
    @Override
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    // Accessors

    /**
     * Gets the unique numeric identifier of this user in the database
     * @return The numeric ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the string identifier that identifies this user with the authentication service
     * @return The user's identifier with the authentication service
     */
    @JsonIgnore
    @Nonnull
    public String getProviderUserId() {
        return providerUserId;
    }

    /**
     * Gets the name of the user
     * @return The user's name
     */
    @Nonnull
    public String getName() {
        return name;
    }

    /**
     * Gets the user's preferred nickname
     * @return The user's nickname
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Gets the user's email address
     * @return The user's email address
     */
    @Nonnull
    public String getEmail() {
        return email;
    }

    /**
     * Gets the URL of the user's personal logo/avatar/profile picture
     * @return The URL of the user's picture
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * Gets the time the persisted version of this object was last modified
     * @return The last update time
     */
    public Instant getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Gets the list of {@link Member} objects that indicate which channels the user belongs to
     * @return The list channel memberships
     */
    @JsonIgnore
    public List<Member> getMemberList() { return memberList; }
}
