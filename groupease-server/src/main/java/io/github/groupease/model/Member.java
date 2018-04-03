package io.github.groupease.model;

import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="Member")
public class Member implements Serializable
{
    @Id
    private Long id;
    private long userId;
    private long channelId;
    private boolean isOwner;
    // Add additional per-channel profile fields here once determined

    @UpdateTimestamp
    private Instant lastUpdate;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "ID")
    private LegacyUserProfileToDelete userProfile;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private List<Group> groups;

    // Accessor functions
    public Long getId() { return id; }
    public boolean isOwner() { return isOwner; }

    public LegacyUserProfileToDelete getUserProfile() {
        return userProfile;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }
}
