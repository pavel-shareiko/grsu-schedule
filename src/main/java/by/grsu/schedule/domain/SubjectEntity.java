package by.grsu.schedule.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "subject")
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title", nullable = false)
    String title;

    @CreationTimestamp
    @Column(name = "create_timestamp", nullable = false, updatable = false)
    OffsetDateTime createTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp", nullable = false)
    OffsetDateTime updateTimestamp;

    @OneToOne(mappedBy = "subject", orphanRemoval = true)
    SubjectCardEntity subjectCard;

}
