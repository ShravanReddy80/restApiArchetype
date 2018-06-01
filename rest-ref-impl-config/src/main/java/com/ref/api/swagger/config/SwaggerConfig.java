package com.ref.api.swagger.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StopWatch;

import com.ref.api.config.SwaggerProperties;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("!prod")
public class SwaggerConfig {

    private final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);

    @Autowired
    private SwaggerProperties swaggerProps;

    @Bean
    public Docket refImplApi() {

        log.debug("Starting Swagger");

        StopWatch watch = new StopWatch();
        watch.start();
        // Docket docket = new
        // Docket(DocumentationType.SWAGGER_2).groupName(swaggerProps.getApiGroupName())
        // .apiInfo(apiInfo()).useDefaultResponseMessages(false).select().apis(RequestHandlerSelectors.withClassAnnotation(Api.class))//apis(RequestHandlerSelectors.any())
        // .paths(refImplApiPaths()).build();

        Docket docket = new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build()
                .pathMapping("/").apiInfo(apiInfo()).useDefaultResponseMessages(false);

        watch.stop();
        log.debug("Swagger docket : " + docket);

        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());

        return docket;
    }

    /**
     * We could have used .paths(PathSelectors.any()). This would make the code
     * easier. However, the documentation will have unnecessary URLs in it. Like the
     * '/' URL even if you are not planning on exposing it.
     */
    // private Predicate<String> refImplApiPaths() {
    // String regexPath = swaggerProps.getContextPath() + "/.*";
    // log.debug("Swagger regexPath ::" + regexPath);
    // return regex(regexPath);
    // // return or(regex("/ref-impl.*"), regex("/api/javainuse.*"));
    // }

    private ApiInfo apiInfo() {
        log.debug(swaggerProps.toString());
        return new ApiInfoBuilder().title(swaggerProps.getApiInfoTitle())
                .description(swaggerProps.getApiInfoDescription())
                .termsOfServiceUrl(swaggerProps.getTermsOfServiceUrl())
                .contact(new Contact(swaggerProps.getContactName(), swaggerProps.getContactUrl(),
                        swaggerProps.getEmail()))
                .license(swaggerProps.getLicenseName()).licenseUrl(swaggerProps.getLicenseUrl())
                .version(swaggerProps.getApiVersion()).build();
    }

}