package ee.svekko.eventmgr.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "event_enrolment")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventEnrolment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "id_code", nullable = false)
    private String idCode;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}
