package org.hewo.infrastructure.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

public class LanguageUtil {
    private static final Logger log = LogManager.getLogger(LanguageUtil.class);
    public static String EN = "en";
    public static String ZH = "zh_chs";
    public static String FIX = "comm.";
    public static String LEFT = "[";
    public static String RIGHT = "]";
    private static ConcurrentHashMap<String, ResourceBundle> languageMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, String> globalLanguageMap = new ConcurrentHashMap();

    public LanguageUtil() {
    }

    public static String getMessage(String language, String languageKey, Object[] params) {
        if (StringUtils.isEmpty(language)) {
            languageKey = ZH;
        }

        String msg = null;

        try {
            msg = getLanguageMapValue(language, languageKey);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return null != params ? MessageFormat.format(msg, params) : msg;
    }

    public static String getMessageByParams(String languageKey, Object[] params) {
        return getMessage(ThreadLocalGroup.getLangueId(), languageKey, params);
    }

    public static String getMessage(String language, String key) {
        if (StringUtils.isEmpty(language)) {
            language = ZH;
        }

        String msg = "";

        try {
            msg = getLanguageMapValue(language, key);
            msg = msg.replaceAll("\\s*|\t|\r|\n", "");
            return StringUtils.isEmpty(msg) ? key : msg;
        } catch (MissingResourceException var4) {
            return key;
        }
    }

    public static String getMessage(String language, String[] languageKeys) {
        if (null != languageKeys && languageKeys.length != 0) {
            StringBuilder sb = new StringBuilder();
            String[] var3 = languageKeys;
            int var4 = languageKeys.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String languageKey = var3[var5];
                sb.append(getMessage(language, languageKey, (Object[])null));
            }

            return null == sb ? "" : sb.toString();
        } else {
            return "";
        }
    }

    public static String getMessage(String key) {
        String language = ThreadLocalGroup.getLangueId();
        return getMessage(language, key);
    }

    public static Map<String, ResourceBundle> getLanguageMap() {
        return languageMap;
    }

    public static String getValue(String name, String language) {
        if (StrUtil.isEmpty(language)) {
            language = ZH;
        }

        String msg = getLanguageMapValue(language, name);
        return msg;
    }

    public static <T> void convertToList(List<T> list) {
        if (CollUtil.isNotEmpty(list)) {
            Iterator var1 = list.iterator();

            while(var1.hasNext()) {
                T t = (T) var1.next();
                Object columnKey = ReflectUtil.getFieldValue(t, "columnKey");
                String columnTitle = getValue((String)columnKey, ThreadLocalGroup.getLangueId());
                ReflectUtil.setFieldValue(t, "columnTitle", columnTitle);
            }
        }

    }

    public static <T> void convertToList(List<T> list, String key, String value) {
        if (CollUtil.isNotEmpty(list)) {
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                T t = (T) var3.next();
                Object columnKey = ReflectUtil.getFieldValue(t, key);
                String columnTitle = getValue((String)columnKey, ThreadLocalGroup.getLangueId());
                ReflectUtil.setFieldValue(t, value, columnTitle);
            }
        }

    }

    public static <T> void convertToList(List<T> list, String key, String value, String langueId) {
        if (CollUtil.isNotEmpty(list)) {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                T t = (T) var4.next();
                Object columnKey = ReflectUtil.getFieldValue(t, key);
                String columnTitle = getValue((String)columnKey, langueId);
                ReflectUtil.setFieldValue(t, value, columnTitle);
            }
        }

    }

    public static void put(String key, ResourceBundle value) {
        if (!StringUtils.isEmpty(key) && value != null) {
            languageMap.put(key, value);
        }

    }

    public static void putGlobalMap(String key, String value) {
        if (!StringUtils.isEmpty(key) && value != null) {
            globalLanguageMap.put(key, value);
        }

    }

    public static String getLanguageMapValue(String fieldKey, String languageTypeKey) {
        if (!StringUtils.isEmpty(fieldKey)) {
            ResourceBundle resourceBundle = (ResourceBundle)languageMap.get(fieldKey);
            if (resourceBundle != null) {
                String value = languageTypeKey;

                try {
                    value = resourceBundle.getString(languageTypeKey);
                } catch (Exception var5) {
                    log.warn(var5.getMessage());
                }

                return convertGlobalKey(value, languageTypeKey);
            }
        }

        return "";
    }

    public static String convertGlobalKey(String msg, String languageTypeKey) {
        if (!StringUtils.isEmpty(msg) && msg.indexOf(LEFT) != -1 && msg.indexOf(RIGHT) != -1) {
            int start = msg.indexOf(LEFT) + 1;
            int end = msg.indexOf(RIGHT);
            String globalFieldKey = msg.substring(start, end);
            String globalValue = getGlobalLanguageMapValue(globalFieldKey, languageTypeKey);
            msg = msg.replace(LEFT, "");
            msg = msg.replace(RIGHT, "");
            return msg.replace(globalFieldKey, globalValue);
        } else {
            return msg;
        }
    }

    public static String getGlobalLanguageMapValue(String fieldKey, String languageTypeKey) {
        return !StringUtils.isEmpty(fieldKey) && !StringUtils.isEmpty(languageTypeKey) ? (String)globalLanguageMap.getOrDefault(languageTypeKey + FIX + fieldKey, "") : "";
    }

    public static String getPath() {
        String path = System.getProperty("user.dir");
        path = "file:" + path + "/language/*.properties";
        String Sysname = System.getProperties().getProperty("os.name");
        if (Sysname.indexOf("Windows") != -1) {
            path = path.replace("\\", "/");
        } else {
            path = path.replace("/", "\\");
        }

        return path;
    }

}