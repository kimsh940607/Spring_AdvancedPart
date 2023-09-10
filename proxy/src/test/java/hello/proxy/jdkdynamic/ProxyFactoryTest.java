package hello.proxy.jdkdynamic;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyFactoryTest {

	@Test
	@DisplayName("인터페이스 존재 dyProxy")
	void dyProxyFactory() {
		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvice(new TimeAdvice());
		ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
		
		log.info("targetClass = {}", target.getClass());
		log.info("proxyClass = {}", proxy.getClass()); //dyProxy
		
		proxy.save();
		
		/*
		 * assertThat(AopUtils.isAopProxy(proxy)).isTrue();
		 * assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
		 * assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
		 */
	}
	
	@Test
	@DisplayName("인터페이스 x CGLIB")
	void cglibProxyFactory() {
		ConcreteService target = new ConcreteService();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.addAdvice(new TimeAdvice());
		ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
		
		log.info("targetClass = {}", target.getClass());
		log.info("proxyClass = {}", proxy.getClass()); //cglib
		
		proxy.call();
		
		/*
		 * assertThat(AopUtils.isAopProxy(proxy)).isTrue();
		 * assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
		 * assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
		 */
	}
	
	@Test
	@DisplayName("proxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용")
	void proxyTargetClass() {
		ServiceInterface target = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.setProxyTargetClass(true);
		proxyFactory.addAdvice(new TimeAdvice());
		ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
		
	 	log.info("targetClass = {}", target.getClass());
		log.info("proxyClass = {}", proxy.getClass()); //dyProxy
		
		proxy.save();
		
		/*
		 * assertThat(AopUtils.isAopProxy(proxy)).isTrue();
		 * assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
		 * assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
		 */
	}
}
