package org.hewo.infrastructure.swagger;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.springframework.stereotype.Component;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Component
@EnableSwagger2
public class Swagger2Configuration {
    public Swagger2Configuration() {
    }

    public static Docket docket(String moduleCode, String moduleName, String basePackage) {
        String name = String.format("%s-%s", moduleCode, moduleName);
        return (new Docket(DocumentationType.SWAGGER_2)).apiInfo(apiInfo(name)).select().apis(RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.any()).build().groupName(name).securitySchemes(securitySchemes()).securityContexts(securityContexts());
    }

    private static List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        apiKeyList.add(new ApiKey("lang", "lang", "header"));
        apiKeyList.add(new ApiKey("RefreshToken", "RefreshToken", "header"));
        return apiKeyList;
    }

    private static List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("^(?!auth).*$")).build());
        return securityContexts;
    }

    private static List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        securityReferences.add(new SecurityReference("lang", authorizationScopes));
        securityReferences.add(new SecurityReference("RefreshToken", authorizationScopes));
        return securityReferences;
    }

    public static Predicate<RequestHandler> basePackage(final String... basePackage) {
        return (input) -> {
            return (Boolean)declaringClass(input).transform(handlerPackage(basePackage)).or(true);
        };
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String... basePackage) {
        return (input) -> {
            String[] var2 = basePackage;
            int var3 = basePackage.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String strPackage = var2[var4];
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }

            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler requestHandler) {
        return Optional.fromNullable(requestHandler.declaringClass());
    }

    private static ApiInfo apiInfo(String moduleName) {
        return (new ApiInfoBuilder()).title(moduleName).description("广州和我网络科技有限公司版权所有").version("1.0.0.0").build();
    }
}