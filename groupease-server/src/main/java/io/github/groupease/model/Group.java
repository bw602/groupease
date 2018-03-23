package io.github.groupease.model;

import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

// Represents a Group being formed in a Channel (course section)
@Entity
@Table(name = "ChannelGroup")
public final class Group implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long channelId;
    private String name;
    private String description;

    @UpdateTimestamp
    private Instant lastUpdate;

    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name="GroupMember", inverseJoinColumns = @JoinColumn(name="MemberID"),
            joinColumns = @JoinColumn(name = "GroupID"))
    private List<Member> members = new ArrayList<Member>();

    // Accessor methods
    public Long getId() { return id; }
    public long getChannelId() { return channelId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Instant getLastUpdate() { return lastUpdate; }
    public List<Member> getMembers() { return members; }
}
