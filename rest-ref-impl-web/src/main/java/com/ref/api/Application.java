package com.ref.api;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ref.api.config.AppProperties;
import com.ref.api.config.GlobalProperties;
import com.ref.api.config.SwaggerProperties;

@ComponentScan({ "com.ref.api" })
//@EnableJpaRepositories(basePackages = "com.mydevgeek.repo")
//@EntityScan(basePackages = "com.mydevgeek.domain")
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Autowired
    private Environment env;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private GlobalProperties globalProperties;
    
    @Autowired
    private SwaggerProperties swaggerProperties;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String... args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    private void init() {

        if(env.getActiveProfiles() != null && env.getActiveProfiles().length > 0) {
            String[] profiles = env.getActiveProfiles();
            StringBuilder currentProfiles = new StringBuilder();
            for (int i = 0; i < profiles.length; i++) {
                String profile = profiles[i];
                currentProfiles.append(profile).append(",");
            }

            logger.debug("Using Profile : " + currentProfiles.substring(0, currentProfiles.length() - 1));
            
        }else {
            logger.debug("Using Profile : " + System.getenv("spring.profiles.active"));
        }
        
        logger.info(appProperties.toString());
        logger.info(globalProperties.toString());
        logger.info(swaggerProperties.toString());
    }

}
