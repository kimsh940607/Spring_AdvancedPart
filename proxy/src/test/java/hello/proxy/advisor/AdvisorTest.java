package hello.proxy.advisor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdvisorTest {

	@Test
	void advisorTest1() {
		ServiceInterface target = new ServiceImpl(); // target Class
		ProxyFactory proxyFactory = new ProxyFactory(target);
		// advisor 생성
		DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice()); // PointCut, 수행 할 로직 TimeAdvice
		proxyFactory.addAdvisor(defaultPointcutAdvisor);
		ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy(); 
		
		proxy.save();
		proxy.request();
	}
	
	@Test
	@DisplayName("커스텀 포인트 컷")
	void advisorTest2() {
		ServiceInterface target = new ServiceImpl(); // target Class
		ProxyFactory proxyFactory = new ProxyFactory(target);
		// advisor 생성
		DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(new CustomPointCut(), new TimeAdvice()); // PointCut, 수행 할 로직 TimeAdvice
		proxyFactory.addAdvisor(defaultPointcutAdvisor);
		ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy(); 
		
		proxy.save();
		proxy.request();
	}
	
	@Test
	@DisplayName("여러 advisor")
	void advisorTest3() {
		ServiceInterface target = new ServiceImpl(); // target Class
		ProxyFactory proxyFactory = new ProxyFactory(target);
		// advisor 생성
		DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new MyPointCut()); // PointCut, 수행 할 로직 TimeAdvice
		proxyFactory.addAdvisor(defaultPointcutAdvisor);
		ServiceInterface proxy1 = (ServiceInterface) proxyFactory.getProxy();
		
		ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);
		// advisor 생성
		DefaultPointcutAdvisor defaultPointcutAdvisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new MyPointCut2()); // PointCut, 수행 할 로직 TimeAdvice
		proxyFactory2.addAdvisor(defaultPointcutAdvisor2);
		ServiceInterface proxy2 = (ServiceInterface) proxyFactory2.getProxy();
		
		proxy2.save();
		
		//proxy.request();
	}

	@Test
	@DisplayName("하나의 proxy, 여러 advisor")
	void advisorTest4() {

		DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new MyPointCut());
		DefaultPointcutAdvisor defaultPointcutAdvisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new MyPointCut2());
		ServiceInterface serviceInterface = new ServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(serviceInterface);
		proxyFactory.addAdvisor(defaultPointcutAdvisor2);
		proxyFactory.addAdvisor(defaultPointcutAdvisor);
		
		ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
		proxy.save();
		
		
		//proxy.request();
	}
	
	@Slf4j
	static class MyPointCut implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			log.info("advisor 1");
			return invocation.proceed();
		}
		
	}

	@Slf4j
	static class MyPointCut2 implements MethodInterceptor {
		
		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			log.info("advisor 2");
			return invocation.proceed();
		}
		
	}
	
	static class CustomPointCut implements Pointcut {

		@Override
		public ClassFilter getClassFilter() {
			return ClassFilter.TRUE;
		}

		@Override
		public MethodMatcher getMethodMatcher() {
			return new CustomMethodMatcher();
		}
		
	}
	
	@Slf4j
	static class CustomMethodMatcher implements MethodMatcher {

		private String pattern = "save";
		
		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			boolean result = method.getName().equals(pattern);
			log.info("포인트 컷 호출 method = {},  targetClass = {}", method.getName(), targetClass);
			log.info("포인트 컷 result = {}", result);
			return result;
		}

		@Override
		public boolean isRuntime() {
			// TODO Auto-generated method stub
			return false; // false이면 위의 matches true면 아래 matches
		}

		@Override
		public boolean matches(Method method, Class<?> targetClass, Object... args) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}

}
