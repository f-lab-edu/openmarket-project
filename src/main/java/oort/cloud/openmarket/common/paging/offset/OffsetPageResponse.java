package oort.cloud.openmarket.common.paging.offset;

import java.util.List;

public class OffsetPageResponse<T> {
    private List<T> contents;
    private Long page;
    private int size;
    private Long totalCount;

    public OffsetPageResponse(List<T> contents, long page, int size, long totalCount) {
        this.contents = contents;
        this.page = page + 1;
        this.size = size;
        this.totalCount = totalCount;
    }

    public List<T> getContents() {
        return contents;
    }

    public long getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalCount() {
        return totalCount;
    }
}
