package com.anurag.iot.data.api.config;


import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;*/


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class SwaggerConfig {







    /*

    private static final String BASIC_AUTH = "basicAuth";
    private static final String BEARER_AUTH = "Bearer";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/recipes/api/v1/.*";
    Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    @Bean
    public Docket apiDocket(){
        RequestParameterBuilder parameterBuilder = new RequestParameterBuilder();
        parameterBuilder.name("Authorization")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .description("Authorization Token Required")
                        .defaultValue("BEARER").required(true).build();
        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(parameterBuilder.build());

        //ResponseMessagesReader responseMessagesReader = new ResponseMessagesReader(TypeNameExtractor)



        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.anurag.iop.data.config"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo()).globalRequestParameters(aParameters)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(securitySchemes());
    }

    private ApiInfo getApiInfo(){
        return new ApiInfo(
                "Menu Recipe App",
                "App for Recipe Maintainence!",
                "1.0.0",
                "Terms of Service",
                new Contact("Anurag Mishra", "hotelmenu.com", "anurag171@gmail.com"),
                "",
                "",
                Collections.emptyList());
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private List<SecurityScheme> securitySchemes() {
        return Arrays.asList(new BasicAuth(BASIC_AUTH), new ApiKey(BEARER_AUTH, "Authorization", "header"));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(Arrays.asList(basicAuthReference(), bearerAuthReference())).forPaths(PathSelectors.ant("/products/**")).build();
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference(BASIC_AUTH, new AuthorizationScope[0]);
    }

    private SecurityReference bearerAuthReference() {
        return new SecurityReference(BEARER_AUTH, new AuthorizationScope[0]);
    }

*/}

