package io.github.groupease.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.groupease.user.GroupeaseUserDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="GroupeaseUser")
public class GroupeaseUser
{
    @Id
    private Long id;
    private String providerUserId;
    private String name;
    private String nickname;
    private String email;
    private String pictureUrl;

    @UpdateTimestamp
    @Column(name = "lastUpdatedOn")
    private Instant lastUpdatedOn;

    @OneToMany(mappedBy = "userProfile")
    private List<Member> memberList;

    // Constructors
    public GroupeaseUser() {}

    /**
     * Conversion constructor from auth library user profile to local db copy
     * @param authZeroUser The user profile data from the authentication service (Auth0)
     */
    public GroupeaseUser(GroupeaseUserDto authZeroUser)
    {
        providerUserId = authZeroUser.getProviderUserId();
        name = authZeroUser.getName();
        nickname = authZeroUser.getNickname();
        email = authZeroUser.getEmail();
        pictureUrl = authZeroUser.getPictureUrl();
    }

    // Infrastructure
    @Override
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
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
    public String getProviderUserId() {
        return providerUserId;
    }

    /**
     * Gets the name of the user
     * @return The user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the user's preferred nickname
     * @return The user's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets the user's email address
     * @return The user's email address
     */
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
    public Instant getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    /**
     * Gets the list of {@link Member} objects that indicate which channels the user belongs to
     * @return The list channel memberships
     */
    @JsonIgnore
    public List<Member> getMemberList() { return memberList; }
}
