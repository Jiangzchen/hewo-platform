package org.hewo.infrastructure.model.request;

import io.swagger.annotations.ApiModelProperty;

public class BaseRequest {

    @ApiModelProperty(notes = "模块id")
    protected String moduleId;

    public BaseRequest() {
    }

    public String getModuleId() {
        return this.moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
}