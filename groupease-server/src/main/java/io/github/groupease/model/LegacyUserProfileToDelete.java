package io.github.groupease.model;

import io.github.groupease.model.Member;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name="UserProfile")
public class LegacyUserProfileToDelete
{
    @Id
    private Long id;
    private String authId;
    private String name;
    private String nickName;
    private String email;
    private String photoUrl;

    @UpdateTimestamp
    private Instant lastUpdate;

    @OneToMany(mappedBy = "userProfile")
    private List<Member> memberList;
/*
    public LegacyUserProfileToDelete(Long id, String authId, String name, String nickName, String email, String photoUrl, Instant lastUpdate) {
        this.id = id;
        this.authId = authId;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.photoUrl = photoUrl;
        this.lastUpdate = lastUpdate;
    }
*/
    public Long getId() {
        return id;
    }

    public String getAuthId() {
        return authId;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }
}
