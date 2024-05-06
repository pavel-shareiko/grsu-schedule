package by.grsu.schedule.domain;

import by.grsu.schedule.model.analytics.AnalysisStatus;
import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedBy;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "analysis_result")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AnalysisResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "module_name", nullable = false)
    String moduleName;

    @Type(JsonType.class)
    @Column(name = "context", nullable = false, columnDefinition = "jsonb")
    JsonNode context;

    @Type(JsonType.class)
    @Column(name = "result", nullable = false, columnDefinition = "jsonb")
    JsonNode result;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    AnalysisStatus status;

    @CreationTimestamp
    @Column(name = "create_timestamp", nullable = false, updatable = false)
    OffsetDateTime createTimestamp;

    @Column(name = "created_by")
    @CreatedBy
    String createdBy;

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AnalysisResultEntity that = (AnalysisResultEntity) object;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
