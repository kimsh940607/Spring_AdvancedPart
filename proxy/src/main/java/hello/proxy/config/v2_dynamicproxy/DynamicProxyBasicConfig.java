package hello.proxy.config.v2_dynamicproxy;

import java.lang.reflect.Proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderControllerV1Impl;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderRepositoryV1Impl;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.app.v1.OrderServiceV1Impl;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceBasicHandler;
import hello.proxy.trace.logtrace.LogTrace;

@Configuration
public class DynamicProxyBasicConfig {

	@Bean
	public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
		OrderRepositoryV1 orderRepositoryV1 = new OrderRepositoryV1Impl();
		// proxy 생성
		OrderRepositoryV1 proxy = (OrderRepositoryV1) Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(), 
					new Class[] {OrderRepositoryV1.class}, 
					new LogTraceBasicHandler(orderRepositoryV1, logTrace));
		return proxy;
	}
	
	@Bean
	public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
		OrderServiceV1 orderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
		
		// proxy 생성
		OrderServiceV1 proxy = (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(), 
					new Class[] {OrderServiceV1.class}, 
					new LogTraceBasicHandler(orderServiceV1, logTrace));
		
		return proxy;
	}
	
	@Bean
	public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
		OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(orderServiceV1(logTrace));
		
		// proxy 생성
		OrderControllerV1 proxy = (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(), 
					new Class[] {OrderControllerV1.class}, 
					new LogTraceBasicHandler(orderControllerV1, logTrace));
		
		return proxy;
	}
}
