package by.grsu.schedule.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "schedule_pull_task")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SchedulePullTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    SchedulePullStatus status;

    @Column(name = "trigger", nullable = false)
    @Enumerated(EnumType.STRING)
    PullTaskTrigger trigger;

    @Column(name = "details")
    String details;

    @CreationTimestamp
    @Column(name = "create_timestamp", nullable = false, updatable = false)
    OffsetDateTime createTimestamp;

    @CreationTimestamp
    @Column(name = "update_timestamp", nullable = false)
    OffsetDateTime updateTimestamp;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    String createdBy;

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SchedulePullTaskEntity that = (SchedulePullTaskEntity) object;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public enum SchedulePullStatus {
        PENDING,
        IN_PROGRESS,
        CANCELLED,
        COMPLETED,
        FAILED
    }

    public enum PullTaskTrigger {
        MANUAL,
        SCHEDULED
    }
}
