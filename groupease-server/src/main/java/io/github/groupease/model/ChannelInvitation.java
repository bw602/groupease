package io.github.groupease.model;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="ChannelInvitation")
public class ChannelInvitation {
    @Id
    private Long id;
    private long channelId;

    @ManyToOne
    @JoinColumn(name = "SenderID", referencedColumnName = "ID")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "RecipientID", referencedColumnName = "ID")
    private UserProfile recipient;

    private String comments;

    @UpdateTimestamp
    private Instant lastUpdate;
}
