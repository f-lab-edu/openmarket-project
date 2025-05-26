package oort.cloud.openmarket.common.paging.cusor;

import java.util.List;

public class CursorPageResponse<T>{
    private List<T> content;
    private String nextCursor;

    public CursorPageResponse(List<T> content, String nextCursor) {
        this.content = content;
        this.nextCursor = nextCursor;
    }

    public List<T> getContent() {
        return content;
    }

    public String getNextCursor() {
        return nextCursor;
    }
}
