package org.hewo.infrastructure.model.response;

import java.util.List;

public class PageResponse<T> extends ListResponse<T> {
    protected long total;
    protected int pageSize;
    protected int pageCount;

    public PageResponse() {
        this.pageSize = 10;
    }

    public PageResponse(int code, String languageKey) {
        this(code, languageKey, 0L, 0, (List)null);
    }

    public PageResponse(long total, int pageSize, List<T> data) {
        this(0, (String)null, total, pageSize, data);
    }

    public PageResponse(int code, String languageKey, long total, int pageSize, List<T> data) {
        this(code, total, pageSize, data, languageKey, (Object[])null);
    }

    public PageResponse(int code, long total, int pageSize, List<T> data, String languageKey, Object[] params) {
        super(code, languageKey, data, params);
        this.pageSize = 10;
        this.total = total;
        this.pageSize = pageSize;
        this.pageCount = (int)Math.ceil((double)total / (double)pageSize);
    }

    public long getTotal() {
        return this.total;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setTotal(final long total) {
        this.total = total;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageCount(final int pageCount) {
        this.pageCount = pageCount;
    }
}
