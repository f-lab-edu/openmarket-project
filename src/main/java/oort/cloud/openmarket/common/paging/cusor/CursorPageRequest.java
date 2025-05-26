package oort.cloud.openmarket.common.paging.cusor;

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

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public void setSize(int size) {
        this.size = size;
    }

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
