package by.grsu.schedule.repository.specification;

import by.grsu.schedule.domain.AnalysisResultEntity;
import by.grsu.schedule.model.analytics.AnalysisStatus;
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
public class AnalysisResultSearchSpecification implements Specification<AnalysisResultEntity> {
    Long id;
    String moduleName;
    AnalysisStatus status;

    @Override
    public Predicate toPredicate(Root<AnalysisResultEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }

        if (moduleName != null) {
            predicates.add(cb.equal(root.get("moduleName"), moduleName));
        }

        if (status != null) {
            predicates.add(cb.equal(root.get("status"), status));
        }

        query.orderBy(cb.desc(root.get("createTimestamp")));
        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
