package io.github.groupease.model;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="ChannelJoinRequest")
public class ChannelJoinRequest {
    @Id
    private Long id;
    private long channelId;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "ID")
    private LegacyUserProfileToDelete requestor;

    private String comments;

    @UpdateTimestamp
    private Instant lastUpdate;
}
