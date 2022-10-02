package org.hewo.infrastructure.model.response.conf;

import cn.hutool.core.util.StrUtil;
import org.hewo.infrastructure.constant.MessageConstants;
import org.hewo.infrastructure.constant.ResourceConstant;
import org.hewo.infrastructure.exception.BusinessException;
import org.hewo.infrastructure.utils.LanguageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Component
public class RegentPlatformExceptionResolver extends DefaultErrorAttributes implements HandlerExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(RegentPlatformExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        String language = request.getHeader(ResourceConstant.LANG);
        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        int code = 500;
        String message = (String) MessageConstants.CODE_MAP.get(10001);
        if (e.getClass() == BusinessException.class) {
            BusinessException businessException = (BusinessException)e;
            code = businessException.getCode();
            message = this.getExceptionMessage(businessException, language);
        } else if (e instanceof MethodArgumentNotValidException) {
            code = 400;
            StringBuilder sb = new StringBuilder();
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException)e;
            BindingResult bindingResult = ex.getBindingResult();
            String spaceStr = StrUtil.isNotBlank(language) && LanguageUtil.EN.contains(language) ? " " : "";
            bindingResult.getFieldErrors().stream().forEach((v) -> {
                sb.append(v.getField()).append(": ").append(this.getValidationFiledMessage(v.getField(), language)).append(spaceStr).append(v.getDefaultMessage()).append("; ");
            });
            message = sb.toString();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String errorStack = sw.toString();
        e.printStackTrace();
        logger.error("@AfterException：线程id：" + Thread.currentThread().getId() + " 状态码：" + code + " 信息：" + message + " exception: " + e.toString());
        Map<String, Object> attributes = new LinkedHashMap();
        attributes.put("code", code);
        attributes.put("msg", message);
        attributes.put("errorStack", errorStack);
        view.setAttributesMap(attributes);
        mav.setView(view);
        return mav;
    }

    public String getExceptionMessage(BusinessException businessException, String language) {
        if (StringUtils.isEmpty(language)) {
            language = LanguageUtil.ZH;
        }

        String message = businessException.getMessage();
        String key = StringUtils.isEmpty(businessException.getLanguageKey()) ? businessException.getCode() + "" : businessException.getLanguageKey();

        try {
            ResourceBundle externalResourceBundle = (ResourceBundle) LanguageUtil.getLanguageMap().get(language);
            if (externalResourceBundle != null && externalResourceBundle.containsKey(key)) {
                message = externalResourceBundle.getString(StringUtils.isEmpty(businessException.getLanguageKey()) ? businessException.getCode() + "" : businessException.getLanguageKey());
                message = LanguageUtil.convertGlobalKey(message, language);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return businessException.getParams() != null && !StringUtils.isEmpty(message) ? MessageFormat.format(message, businessException.getParams()) : message;
    }

    private String getValidationFiledMessage(String fieldName, String language) {
        if (StringUtils.isEmpty(language)) {
            language = LanguageUtil.ZH;
        }

        if (!StringUtils.isEmpty(fieldName)) {
            int index = fieldName.lastIndexOf(".");
            fieldName = fieldName.substring(index + 1);
        }

        try {
            Locale locale = new Locale(language);
            ResourceBundle resourceBundle = ResourceBundle.getBundle(ResourceConstant.VALIDATION_LANGUAGE_FILE_PREFIX, locale);
            fieldName = resourceBundle.getString(fieldName);
        } catch (Exception var5) {
            logger.warn(var5.getMessage());
        }

        return fieldName;
    }
}
