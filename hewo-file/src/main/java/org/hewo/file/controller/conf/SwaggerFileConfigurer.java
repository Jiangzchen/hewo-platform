package org.hewo.file.controller.conf;

import org.hewo.infrastructure.swagger.Swagger2Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerFileConfigurer {

    @Bean
    public Docket fileStandardApi() {
        String moduleCode = "file";
        String moduleName = "标准文件服务";
        String basePackage = "org.hewo.file";
        return Swagger2Configuration.docket(moduleCode, moduleName, basePackage);
    }
}
