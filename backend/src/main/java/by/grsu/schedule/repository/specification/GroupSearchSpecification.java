package by.grsu.schedule.repository.specification;

import by.grsu.schedule.domain.GroupEntity;
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
public class GroupSearchSpecification implements Specification<GroupEntity> {
    String title;

    @Override
    public Predicate toPredicate(Root<GroupEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (title != null) {
            predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        query.orderBy(cb.asc(root.get("title")));
        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
