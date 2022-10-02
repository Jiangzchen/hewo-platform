package org.hewo.infrastructure.interceptor;

import org.hewo.infrastructure.annotation.PassToken;
import org.hewo.infrastructure.constant.ResourceConstant;
import org.hewo.infrastructure.controller.BaseRestController;
import org.hewo.infrastructure.exception.BusinessException;
import org.hewo.infrastructure.utils.JwtUtil;
import org.hewo.infrastructure.utils.LanguageUtil;
import org.hewo.infrastructure.utils.RedisUtil;
import org.hewo.infrastructure.utils.ThreadLocalGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

public class JwtAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    private static final Long ADMIN_ID = 6877239110796543L;
    private static final Long EXPIRE_TIME = 43200L;
    private static final Long EFFECTIVE_RANGE_TIME = 7200L;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
        try {
            String token = request.getHeader(ResourceConstant.AUTHORIZATION);
            String refreshToken = request.getHeader(ResourceConstant.REFRESH_TOKEN);
            String langueId = request.getHeader(ResourceConstant.LANG);
            ThreadLocalGroup.set(ResourceConstant.LANG, Optional.ofNullable(langueId).orElse(LanguageUtil.ZH));

            // 如果不是映射到方法直接通过
            if(!(object instanceof HandlerMethod)){
                return true;
            }else {
                HandlerMethod handlerMethod = (HandlerMethod) object;
                Method method = handlerMethod.getMethod();
                if (method.isAnnotationPresent(PassToken.class)) {
                    PassToken passToken = (PassToken) method.getAnnotation(PassToken.class);
                    if (passToken.required()) {
                        return true;
                    }
                }

                if (StringUtils.isEmpty(token)) {
                    throw new BusinessException(10004, "10004");
                } else {
                    JwtUtil.verify(token);
                    if (this.redisUtil.hget(refreshToken, "token") == null) {
                        throw new BusinessException(10006, "10006");
                    } else {
                        if (this.redisUtil.getExpire(refreshToken) <= EFFECTIVE_RANGE_TIME) {
                            this.redisUtil.expire(refreshToken, EXPIRE_TIME);
                        }

                        Object bean = handlerMethod.getBean();
                        Long userId = ADMIN_ID;
                        if (token != null && JwtUtil.getUserId(token) != null) {
                            userId = Long.valueOf(JwtUtil.getUserId(token));
                        }

                        if (bean instanceof BaseRestController) {
                            BaseRestController controller = (BaseRestController)bean;
                            controller.setUserId(userId);
                            controller.setLangueId(langueId);
                        }

                        ThreadLocalGroup.setUserId(userId);
                        ThreadLocalGroup.setRefreshToken(refreshToken);
                        ThreadLocalGroup.setToken(token);
                        ThreadLocalGroup.setSessionId(request.getSession().getId());
                        return true;
                    }
                }
            }
        } catch (BusinessException var12) {
            this.configAllowOrigin(response);
            throw var12;
        } catch (Exception var13) {
            this.configAllowOrigin(response);
            throw new BusinessException(10001, "10001");
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalGroup.remove();
    }

    private void configAllowOrigin(HttpServletResponse httpServletResponse) {
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        httpServletResponse.addHeader("Access-Control-Max-Age", "3600");
    }
}
