package by.grsu.schedule.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "group")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group {
    @Id
    Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    String title;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "faculty_id")
    Faculty faculty;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id")
    Department department;
}
