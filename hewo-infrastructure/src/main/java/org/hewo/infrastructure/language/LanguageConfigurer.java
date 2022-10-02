package org.hewo.infrastructure.language;

import org.hewo.infrastructure.utils.LanguageUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

@Configuration
public class LanguageConfigurer {
    public LanguageConfigurer() {
    }

    static {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            Resource[] resources = resolver.getResources("classpath:language/*.properties");
            Resource[] resourceFiles = resources;
            int var3 = resources.length;

            int var4;
            String filename;
            String key;
            for(var4 = 0; var4 < var3; ++var4) {
                Resource resource = resourceFiles[var4];
                filename = resource.getFilename();
                int start = filename.indexOf("_") + 1;
                int end = filename.indexOf(".");
                filename = filename.substring(start, end);
                BufferedInputStream inputStream = new BufferedInputStream(resource.getInputStream());
                ResourceBundle resourceBundle = new PropertyResourceBundle(inputStream);
                Enumeration en = resourceBundle.getKeys();

                while(en.hasMoreElements()) {
                    key = en.nextElement().toString();
                    key = resourceBundle.getString(key);
                    LanguageUtil.putGlobalMap(filename + LanguageUtil.FIX + key, key);
                }

                LanguageUtil.put(filename, resourceBundle);
                inputStream.close();
            }

            resourceFiles = resolver.getResources(LanguageUtil.getPath());
            Resource[] var17 = resourceFiles;
            var4 = resourceFiles.length;

            for(int var18 = 0; var18 < var4; ++var18) {
                Resource resourceFile = var17[var18];
                BufferedInputStream inputStream = new BufferedInputStream(resourceFile.getInputStream());
                ResourceBundle resourceBundle = new PropertyResourceBundle(inputStream);
                filename = resourceFile.getFilename();
                int start = filename.indexOf("_") + 1;
                int end = filename.indexOf(".");
                String language = filename.substring(start, end);
                Enumeration en = resourceBundle.getKeys();

                while(en.hasMoreElements()) {
                    key = en.nextElement().toString();
                    String value = resourceBundle.getString(key);
                    LanguageUtil.putGlobalMap(language + LanguageUtil.FIX + key, value);
                }

                LanguageUtil.put(language, resourceBundle);
                inputStream.close();
            }
        } catch (IOException var16) {
            var16.printStackTrace();
        }

    }
}
