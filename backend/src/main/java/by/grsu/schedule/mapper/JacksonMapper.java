package by.grsu.schedule.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.mapstruct.Mapper;

import java.util.Map;

@Mapper
public abstract class JacksonMapper {
    public static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public Map<String, Object> toMap(Object o) {
        return OBJECT_MAPPER.convertValue(o, new TypeReference<>() {
        });
    }

    public JsonNode toJsonNode(Object o) {
        return OBJECT_MAPPER.valueToTree(o);
    }
}
