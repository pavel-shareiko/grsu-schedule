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

    @Override
    public Predicate toPredicate(Root<TeacherEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }

        if (surname != null) {
            predicates.add(cb.like(cb.lower(root.get("surname")), surname.toLowerCase() + "%"));
        }

        query.orderBy(cb.asc(root.get("surname")));
        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
