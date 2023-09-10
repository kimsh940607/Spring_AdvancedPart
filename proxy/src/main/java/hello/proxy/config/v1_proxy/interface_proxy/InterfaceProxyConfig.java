package hello.proxy.config.v1_proxy.interface_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderControllerV1Impl;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderRepositoryV1Impl;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.app.v1.OrderServiceV1Impl;
import hello.proxy.trace.logtrace.LogTrace;

@Configuration
public class InterfaceProxyConfig {

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) { // InterfaceProxy 호출
    	OrderControllerV1Impl controller = new OrderControllerV1Impl(orderService(logTrace));
    	return new OrderControllerInterfaceProxy(controller, logTrace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
    	OrderServiceV1Impl orderService = new OrderServiceV1Impl(orderRepository(logTrace));
    	return new OrderServiceInterfaceProxy(orderService, logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
    	OrderRepositoryV1Impl orderRepository = new OrderRepositoryV1Impl();
    	return new OrderRepositoryInterfaceProxy(orderRepository, logTrace);
    }
}
