package oort.cloud.openmarket.common.cusor;

import oort.cloud.openmarket.common.cusor.CursorField;
import oort.cloud.openmarket.products.enums.ProductsCursorField;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cursor<F extends Enum<F> & CursorField>{

    private Map<F, Comparable<?>> values;

    public Cursor(){
        this.values = new HashMap<>();
    }

    public void put(F key, Comparable<?> value) {
        values.put(key, value);
    }

    public <T> T get(CursorField field, Class<T> type) {
        Comparable<?> value = values.get(field);

        if(value == null) return null;

        if (type == LocalDateTime.class && value instanceof String str) {
            return type.cast(LocalDateTime.parse(str));
        }
        if (type == Long.class && value instanceof Integer intValue) {
            return type.cast(intValue.longValue());
        }
        if (type == Integer.class && value instanceof Long longValue) {
            return type.cast(longValue.intValue());
        }

        return type.cast(value);
    }

    public Map<F, Comparable<?>> getAll() {
        return Collections.unmodifiableMap(values);
    }

    @Override
    public String toString() {
        return "Cursor{" +
                "values=" + values +
                '}';
    }
}
