package hello.proxy.config.v3_proxyfactory;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ProxyFactoryConfigV2 {
	@Bean
	public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
		// target
		OrderRepositoryV2 orderRepository = new OrderRepositoryV2();
		ProxyFactory proxyFactory = new ProxyFactory(orderRepository);
		proxyFactory.addAdvisor(this.getAdvisor(logTrace));
		
		OrderRepositoryV2 proxy = (OrderRepositoryV2) proxyFactory.getProxy();
		
		log.info("targetClass = {}, proxyClass = {}", orderRepository.getClass(), proxy.getClass());
		return proxy;
	}

	@Bean
	public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
		// target
		OrderServiceV2 orderService = new OrderServiceV2(orderRepositoryV2(logTrace));
		ProxyFactory proxyFactory = new ProxyFactory(orderService);
		proxyFactory.addAdvisor(this.getAdvisor(logTrace));
		
		OrderServiceV2 proxy = (OrderServiceV2) proxyFactory.getProxy();
		
		log.info("targetClass = {}, proxyClass = {}", orderService.getClass(), proxy.getClass());
		
		return proxy;
	}

	@Bean
	public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
		// target
		OrderControllerV2 orderController = new OrderControllerV2(orderServiceV2(logTrace));
		ProxyFactory proxyFactory = new ProxyFactory(orderController);
		proxyFactory.addAdvisor(this.getAdvisor(logTrace));
		
		OrderControllerV2 proxy = (OrderControllerV2) proxyFactory.getProxy();
		
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
