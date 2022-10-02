package org.hewo.infrastructure.utils;

public class FileUtil {
    public static String getFileExtName(String fileName) {
        if (fileName != null) {
            int i = fileName.lastIndexOf(46);
            return i > -1 ? fileName.substring(i + 1).toLowerCase() : null;
        } else {
            return null;
        }
    }
}
