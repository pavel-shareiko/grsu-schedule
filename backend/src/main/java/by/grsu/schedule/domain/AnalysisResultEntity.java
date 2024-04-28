package by.grsu.schedule.domain;

import by.grsu.schedule.model.analytics.AnalysisStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.proxy.HibernateProxy;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
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
    Map<String, Object> context = new HashMap<>();

    @Type(JsonType.class)
    @Column(name = "result", nullable = false, columnDefinition = "jsonb")
    Map<String, Object> result = new HashMap<>();

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    AnalysisStatus status;

    @CreationTimestamp
    @Column(name = "create_timestamp", nullable = false, updatable = false)
    OffsetDateTime createTimestamp;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AnalysisResultEntity that = (AnalysisResultEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
