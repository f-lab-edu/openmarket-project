package oort.cloud.openmarket.common.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 *   OffsetDateTime 형식을 LocalDateTime 형식으로 바꾸는 Util 클래스
 */
public class LocalDateTimeFromOffsetDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return OffsetDateTime.parse(p.getText()).toLocalDateTime();
    }
}
