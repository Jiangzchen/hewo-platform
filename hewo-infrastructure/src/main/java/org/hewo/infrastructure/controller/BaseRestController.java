package org.hewo.infrastructure.controller;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseRestController {
    private Long userId;
    private String userCode;
    private String langueId;

    protected HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    protected HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return requestAttributes.getResponse();
    }

    protected HttpSession getSession() {
        HttpServletRequest request = this.getRequest();
        return request.getSession();
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getLangueId() {
        return this.langueId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public void setLangueId(final String langueId) {
        this.langueId = langueId;
    }
}
