package by.grsu.schedule.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "teacher")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Teacher {

    @Id
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "surname")
    String surname;

    @Column(name = "patronym")
    String patronym;

    @Column(name = "post")
    String post;

    @Column(name = "phone")
    String phone;

    @Column(name = "descr")
    String descr;

    @Column(name = "email")
    String email;

    @Column(name = "skype")
    String skype;
}
