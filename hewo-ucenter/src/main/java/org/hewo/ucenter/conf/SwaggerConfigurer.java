package org.hewo.ucenter.conf;

import org.hewo.infrastructure.swagger.Swagger2Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurer {

    @Bean
    public Docket ucenterStandardApi() {
        String moduleCode = "ucenter";
        String moduleName = "标准用户服务";
        String basePackage = "org.hewo.ucenter";
        return Swagger2Configuration.docket(moduleCode, moduleName, basePackage);
    }
}
