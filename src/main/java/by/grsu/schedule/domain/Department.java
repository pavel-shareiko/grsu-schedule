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
@Table(name = "department")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Department {
    @Id
    Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    String title;
}
