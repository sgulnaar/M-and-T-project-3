/*
 * Copyright (c) 2019 www.roche.com.
 * All rights reserved.
 */

package com.example.transaction.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    private static final String GROUP_NAME = "TRANSACTION-v%s";

    @Bean
    public Docket swaggerApi1() {
        final String version = "1";
        return buildDocket(version);
    }

    private Docket buildDocket(String version) {
        return new Docket(DocumentationType.SWAGGER_2).groupName(String.format(GROUP_NAME, version))
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiEndPointsInfo(version))
            .globalOperationParameters(globalHeaders());
    }

    private ApiInfo apiEndPointsInfo(String version) {
        return new ApiInfoBuilder().title("TRANSACTION API")
            .description("Documentation TRANSACTION API v" + version)
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version(version)
            .build();
    }

    private List<Parameter> globalHeaders() {
        ParameterBuilder authorizationHeader = new ParameterBuilder();
        authorizationHeader.name("Authorization").allowEmptyValue(false).modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        List<Parameter> headers = new ArrayList<>();
        headers.add(authorizationHeader.build());
        return headers;
    }

}
