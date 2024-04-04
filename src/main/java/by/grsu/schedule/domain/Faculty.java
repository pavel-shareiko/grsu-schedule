package by.grsu.schedule.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "faculty")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Faculty {
    @Id
    Long id;

    @NotNull
    @Column(name = "ext_id", nullable = false)
    Long externalId;

    @NotNull
    @Column(name = "faculty_name", nullable = false)
    String name;
}
