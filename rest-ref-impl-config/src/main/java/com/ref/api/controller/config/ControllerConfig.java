package com.ref.api.controller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class ControllerConfig extends WebMvcConfigurerAdapter {
    
//    @Value("${server.contextPath}")
//    private String contextPath;
    
//    private static Logger logger = LoggerFactory.getLogger(ControllerConfig.class);
//    
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer){
//        configurer.defaultContentType(MediaType.APPLICATION_JSON);
//        configurer.ignoreAcceptHeader(false);
//        configurer.defaultContentTypeStrategy(new HeaderContentNegotiationStrategy());
//    }
    
    
//    @Bean
//    public WebMvcRegistrationsAdapter webMvcRegistrationsHandlerMapping() {
//        
//        return new WebMvcRegistrationsAdapter() {
//            
//            @Value("${server.contextPath}")
//            private String contextPath;
//            
//            @Override
//            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
//                return new RequestMappingHandlerMapping() {
//                    private final String API_BASE_PATH = contextPath;
//
//                    @Override
//                    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
//                        Class<?> beanType = method.getDeclaringClass();
//                        if (AnnotationUtils.findAnnotation(beanType, RestController.class) != null) {
//                            PatternsRequestCondition apiPattern = new PatternsRequestCondition(API_BASE_PATH)
//                                    .combine(mapping.getPatternsCondition());
//
//                            mapping = new RequestMappingInfo(mapping.getName(), apiPattern,
//                                    mapping.getMethodsCondition(), mapping.getParamsCondition(),
//                                    mapping.getHeadersCondition(), mapping.getConsumesCondition(),
//                                    mapping.getProducesCondition(), mapping.getCustomCondition());
//                        }
//
//                        super.registerHandlerMethod(handler, method, mapping);
//                        
//                        logger.debug("returning new RequestMappingInfo" + mapping);
//                    }
//                };
//            }
//        };
//   
//    }
}
