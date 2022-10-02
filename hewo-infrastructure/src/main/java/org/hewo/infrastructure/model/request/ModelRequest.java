package org.hewo.infrastructure.model.request;

import io.swagger.annotations.ApiModelProperty;

public class ModelRequest<T> extends BaseRequest {

    @ApiModelProperty(notes = "请求的实体")
    protected T data;

    public ModelRequest() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}