package org.hewo.infrastructure.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -8713729953750745290L;
    private int code;
    @JsonIgnore
    private Object[] params;
    @JsonIgnore
    private String languageKey;

    public BusinessException(int code) {
        this.code = code;
    }

    public BusinessException(int code, String languageKey) {
        this(code, languageKey, (Object[]) null);
    }

    public BusinessException(int code, Object[] params) {
        this.code = code;
        this.params = params;
    }

    public BusinessException(int code, String languageKey, Object[] params) {
        this.code = code;
        this.params = params;
        this.languageKey = languageKey;
    }

    public int getCode() {
        return this.code;
    }

    public Object[] getParams() {
        return this.params;
    }

    public String getLanguageKey() {
        return this.languageKey;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    @JsonIgnore
    public void setParams(final Object[] params) {
        this.params = params;
    }

    @JsonIgnore
    public void setLanguageKey(final String languageKey) {
        this.languageKey = languageKey;
    }
}
