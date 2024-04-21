package by.grsu.schedule.repository.specification;

import by.grsu.schedule.domain.GroupEntity;
import by.grsu.schedule.domain.LessonEntity;
import by.grsu.schedule.domain.TeacherEntity;
import jakarta.persistence.criteria.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@Builder
@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LessonSearchSpecification implements Specification<LessonEntity> {
    Long groupId;
    Long teacherId;
    LocalDate from;
    LocalDate to;

    @Override
    public Predicate toPredicate(Root<LessonEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (groupId != null) {
            Join<LessonEntity, GroupEntity> groupJoin = root.join("groups");
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(groupJoin.get("id"), groupId));
        }

        if (teacherId != null) {
            Join<LessonEntity, TeacherEntity> teacherJoin = root.join("teachers");
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(teacherJoin.get("id"), teacherId));
        }

        if (from != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("date"), from));
        }

        if (to != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("date"), to));
        }

        query.orderBy(criteriaBuilder.asc(root.get("date")), criteriaBuilder.asc(root.get("timeStart")));
        return predicate;
    }
}
