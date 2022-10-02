package org.hewo.infrastructure.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;

public class BaseResponse {

    @ApiModelProperty(notes = "响应编码")
    protected Integer code;

    @ApiModelProperty(notes = "响应消息")
    protected String msg;
    @JsonIgnore
    private String languageKey;
    @JsonIgnore
    private Object[] params;

    public BaseResponse() {
    }

    public BaseResponse(Integer code) {
        this(code, null, null);
    }

    public BaseResponse(Integer code, String languageKey) {
        this(code, languageKey, null);
    }

    public BaseResponse(Integer code, String languageKey, Object[] params) {
        this.code = code;
        this.params = params;
        this.languageKey = languageKey;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getLanguageKey() {
        return this.languageKey;
    }

    public Object[] getParams() {
        return this.params;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setLanguageKey(final String languageKey) {
        this.languageKey = languageKey;
    }

    public void setParams(final Object[] params) {
        this.params = params;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseResponse)) {
            return false;
        } else {
            BaseResponse other = (BaseResponse) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                Object this$languageKey = this.getLanguageKey();
                Object other$languageKey = other.getLanguageKey();
                if (this$languageKey == null) {
                    if (other$languageKey != null) {
                        return false;
                    }
                } else if (!this$languageKey.equals(other$languageKey)) {
                    return false;
                }

                if (!Arrays.deepEquals(this.getParams(), other.getParams())) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseResponse;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $languageKey = this.getLanguageKey();
        result = result * 59 + ($languageKey == null ? 43 : $languageKey.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.getParams());
        return result;
    }

    @Override
    public String toString() {
        return "BaseResponse(code=" + this.getCode() + ", msg=" + this.getMsg() + ", languageKey=" + this.getLanguageKey() + ", params=" + Arrays.deepToString(this.getParams()) + ")";
    }
}