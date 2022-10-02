package org.hewo.infrastructure.utils;

import java.util.UUID;

public class StringUtil {
    /**
     * 创建一个32位字符+数字组合的UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
