package oort.cloud.openmarket.common.cusor;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CursorPageRequest {
    @NotBlank
    private String sortKey;

    private String cursor;

    @Min(20)
    @Max(100)
    private int size;

    public String getSortKey() {
        return sortKey;
    }

    public String getCursor() {
        return cursor;
    }

    public int getSize() {
        return size;
    }
}
