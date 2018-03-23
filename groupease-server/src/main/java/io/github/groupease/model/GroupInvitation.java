package io.github.groupease.model;

import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="GroupInvitation")
public class GroupInvitation {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GroupID", referencedColumnName = "ID")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "RecipientID", referencedColumnName = "ID")
    private Member recipient;

    private String comments;

    @UpdateTimestamp
    private Instant lastUpdate;
}
