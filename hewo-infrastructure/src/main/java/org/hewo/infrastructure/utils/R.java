package org.hewo.infrastructure.utils;


import org.hewo.infrastructure.constant.MessageConstants;
import org.hewo.infrastructure.model.response.BaseResponse;
import org.hewo.infrastructure.model.response.ModelResponse;

public class R {
    public R() {
    }

    public static BaseResponse success() {
        return new BaseResponse(0, MessageConstants.CODE_MAP.get(0));
    }

    public static BaseResponse error(int code) {
        return new BaseResponse(code);
    }

    public static BaseResponse error(int code, String languageKey, Object[] params) {
        return new BaseResponse(code, languageKey, params);
    }

    public static <T> ModelResponse<T> success(T data) {
        return new ModelResponse<>(0, MessageConstants.CODE_MAP.get(0), data);
    }

    public static <T> ModelResponse<T> error(int code, T data) {
        return new ModelResponse<>(code, null, data);
    }

    public static <T> ModelResponse<T> error(int code, String languageKey, T data) {
        return new ModelResponse<>(code, languageKey, data);
    }

    public static <T> ModelResponse<T> error(int code, Object[] params, String languageKey) {
        return new ModelResponse<>(code, languageKey, params);
    }

    public static <T> ModelResponse<T> error(int code, T data, Object[] params, String languageKey) {
        return new ModelResponse<>(code, languageKey, params, data);
    }

    public static BaseResponse error(int code, String languageKey, Object[] params, Object obj) {
        return new BaseResponse(code, languageKey, params);
    }

}