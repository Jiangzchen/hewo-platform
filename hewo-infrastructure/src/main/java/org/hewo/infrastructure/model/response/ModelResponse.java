package org.hewo.infrastructure.model.response;

import io.swagger.annotations.ApiModelProperty;

public class ModelResponse<T> extends BaseResponse {

    @ApiModelProperty(notes = "响应实体类")
    protected T data;

    public ModelResponse() {
    }

    public ModelResponse(Integer code) {
        super(code, null);
    }

    public ModelResponse(Integer code, String languageKey) {
        super(code, languageKey);
    }

    public ModelResponse(Integer code, String languageKey, Object[] params) {
        super(code, languageKey, params);
    }

    public ModelResponse(Integer code, String languageKey, T data) {
        this(code, languageKey, null, data);
    }

    public ModelResponse(Integer code, String languageKey, Object[] params, T data) {
        super(code, languageKey, params);
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ModelResponse)) {
            return false;
        } else {
            ModelResponse<?> other = (ModelResponse) o;
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

    @Override
    protected boolean canEqual(final Object other) {
        return other instanceof ModelResponse;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ModelResponse(data=" + this.getData() + ")";
    }
}