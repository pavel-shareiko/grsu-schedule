package by.grsu.schedule.repository.specification;

import by.grsu.schedule.domain.FacultyEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FacultySearchSpecification implements Specification<FacultyEntity> {
    Long id;
    String title;

    @Override
    public Predicate toPredicate(Root<FacultyEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (id != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), id));
        }

        if (title != null) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("title")),
                            "%" + title.toLowerCase() + "%"
                    )
            );
        }

        query.orderBy(criteriaBuilder.asc(root.get("id")));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
