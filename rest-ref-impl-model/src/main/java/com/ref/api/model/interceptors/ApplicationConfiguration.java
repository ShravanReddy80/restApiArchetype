package com.ref.api.model.interceptors;

//@SpringBootApplication
//@EnableAspectJAutoProxy
public class ApplicationConfiguration {

	// public @Bean CustomizableTraceInterceptor interceptor() {
	//
	// CustomizableTraceInterceptor interceptor = new
	// CustomizableTraceInterceptor();
	// interceptor.setEnterMessage("Entering $[methodName]($[arguments]).");
	// interceptor.setExitMessage(
	// "Leaving $[methodName](..) with return value $[returnValue], took
	// $[invocationTime]ms.");
	//
	// return interceptor;
	// }
	//
	// public @Bean Advisor traceAdvisor() {
	//
	// AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	// pointcut.setExpression("execution(public *
	// org.springframework.data.repository.Repository+.*(..))");
	//
	// return new DefaultPointcutAdvisor(pointcut, interceptor());
	// }
}