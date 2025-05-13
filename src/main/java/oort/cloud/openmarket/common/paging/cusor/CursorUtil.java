package oort.cloud.openmarket.common.paging.cusor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public final class CursorUtil {
    private final ObjectMapper objectMapper;

    public CursorUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <F extends Enum<F> & CursorField> String createCursor(Cursor<F> cursor) {
        try {
            Map<String, Comparable<?>> jsonMap = new HashMap<>();
            for (Map.Entry<F, Comparable<?>> entry : cursor.getAll().entrySet()) {
                jsonMap.put(entry.getKey().getFieldName(), entry.getValue());
            }
            byte[] bytes = objectMapper.writeValueAsBytes(jsonMap);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <F extends Enum<F> & CursorField> Cursor<F> decodeCursor(String encodedCursor, Class<F> enumClass) {
        Cursor<F> cursor = new Cursor<>();
        if (encodedCursor == null || encodedCursor.isEmpty()) {
            return cursor;
        }
        try {
            byte[] decoded = Base64.getDecoder().decode(encodedCursor);
            Map<String, Object> jsonMap = objectMapper.readValue(decoded, new TypeReference<>() {});
            for (F field : EnumSet.allOf(enumClass)) {
                String fieldKey = field.getFieldName();
                if (jsonMap.containsKey(fieldKey)) {
                    Object value = jsonMap.get(fieldKey);
                    if (value instanceof Comparable<?>) {
                        cursor.put(field, (Comparable<?>) value);
                    }
                }
            }
            return cursor;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}