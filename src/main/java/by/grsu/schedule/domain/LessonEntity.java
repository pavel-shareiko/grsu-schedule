package by.grsu.schedule.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "lesson")
public class LessonEntity {
    @Id
    Long id;

    @Column(name = "date", nullable = false)
    LocalDate date;

    @Column(name = "time_start", nullable = false)
    LocalTime timeStart;

    @Column(name = "time_end", nullable = false)
    LocalTime timeEnd;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "lesson_teacher",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    @ToString.Exclude
    Set<TeacherEntity> teachers = new LinkedHashSet<>();

    @Column(name = "label", nullable = false)
    String label;

    @Column(name = "room", nullable = false)
    String room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    @ToString.Exclude
    SubjectEntity subject;

    @Column(name = "is_remote", nullable = false)
    boolean isRemote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_type_id")
    @ToString.Exclude
    LessonTypeEntity type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    @ToString.Exclude
    AddressEntity address;

    @CreationTimestamp
    @Column(name = "create_timestamp", nullable = false, updatable = false)
    OffsetDateTime createTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp", nullable = false)
    OffsetDateTime updateTimestamp;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "lesson_group",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    @ToString.Exclude
    Set<GroupEntity> groups = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LessonEntity lesson = (LessonEntity) o;
        return getId() != null && Objects.equals(getId(), lesson.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
