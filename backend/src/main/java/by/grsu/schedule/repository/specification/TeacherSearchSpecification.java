package by.grsu.schedule.repository.specification;

import by.grsu.schedule.domain.TeacherEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TeacherSearchSpecification implements Specification<TeacherEntity> {
    Long id;
    String surname;
    String name;
    String patronym;
    String post;
    String email;

    @Override
    public Predicate toPredicate(Root<TeacherEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }

        if (surname != null) {
            predicates.add(cb.like(cb.lower(root.get("surname")), surname.toLowerCase() + "%"));
        }

        if (name != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), name.toLowerCase() + "%"));
        }

        if (patronym != null) {
            predicates.add(cb.like(cb.lower(root.get("patronym")), patronym.toLowerCase() + "%"));
        }

        if (post != null) {
            predicates.add(cb.like(cb.lower(root.get("post")), "%" + post.toLowerCase() + "%"));
        }

        if (email != null) {
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }

        query.orderBy(cb.asc(root.get("surname")));
        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
