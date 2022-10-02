package org.hewo.infrastructure.model.response;

import java.util.List;

public class ListResponse<T> extends BaseResponse {
    protected List<T> data;

    public ListResponse() {
    }

    public ListResponse(Integer code) {
        super(code);
    }

    public ListResponse(Integer code, String languageKey) {
        super(code, languageKey, (Object[])null);
    }

    public ListResponse(Integer code, String languageKey, Object[] params) {
        super(code, languageKey, params);
    }

    public ListResponse(Integer code, String languageKey, List<T> data) {
        this(code, languageKey, data, (Object[])null);
    }

    public ListResponse(Integer code, String languageKey, List<T> data, Object[] params) {
        super(code, languageKey, params);
        this.setData(data);
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(final List<T> data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ListResponse)) {
            return false;
        } else {
            ListResponse<?> other = (ListResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ListResponse;
    }
    public String toString() {
        return "ListResponse(data=" + this.getData() + ")";
    }
}
