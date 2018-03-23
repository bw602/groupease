package io.github.groupease.model;

import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "GroupJoinRequest")
public class GroupJoinRequest {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GroupID", referencedColumnName = "ID")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "SenderID", referencedColumnName = "ID")
    private Member sender;

    private String comments;

    @UpdateTimestamp
    private Instant lastUpdate;
}
