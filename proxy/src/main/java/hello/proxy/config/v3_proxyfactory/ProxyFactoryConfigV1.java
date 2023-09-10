package hello.proxy.config.v3_proxyfactory;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderControllerV1Impl;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderRepositoryV1Impl;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.app.v1.OrderServiceV1Impl;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ProxyFactoryConfigV1 {

	@Bean
	public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
		// target
		OrderRepositoryV1 orderRepository = new OrderRepositoryV1Impl();
		ProxyFactory proxyFactory = new ProxyFactory(orderRepository);
		proxyFactory.addAdvisor(this.getAdvisor(logTrace));
		
		OrderRepositoryV1 proxy = (OrderRepositoryV1) proxyFactory.getProxy();
		
		log.info("targetClass = {}, proxyClass = {}", orderRepository.getClass(), proxy.getClass());
		return proxy;
	}

	@Bean
	public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
		// target
		OrderServiceV1 orderService = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
		ProxyFactory proxyFactory = new ProxyFactory(orderService);
		proxyFactory.addAdvisor(this.getAdvisor(logTrace));
		
		OrderServiceV1 proxy = (OrderServiceV1) proxyFactory.getProxy();
		
		log.info("targetClass = {}, proxyClass = {}", orderService.getClass(), proxy.getClass());
		
		return proxy;
	}

	@Bean
	public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
		// target
		OrderControllerV1 orderController = new OrderControllerV1Impl(orderServiceV1(logTrace));
		ProxyFactory proxyFactory = new ProxyFactory(orderController);
		proxyFactory.addAdvisor(this.getAdvisor(logTrace));
		
		OrderControllerV1 proxy = (OrderControllerV1) proxyFactory.getProxy();
		
		log.info("targetClass = {}, proxyClass = {}", orderController.getClass(), proxy.getClass());
		
		return proxy;
	}

	private Advisor getAdvisor(LogTrace logTrace) {
		// PointCut
		NameMatchMethodPointcut pointCut = new NameMatchMethodPointcut();
		pointCut.setMappedNames("request*", "save*", "order*");
		
		// advice
		LogTraceAdvice logTraceAdvice = new LogTraceAdvice(logTrace);
		return new DefaultPointcutAdvisor(pointCut, logTraceAdvice);
	}
	
	
}
