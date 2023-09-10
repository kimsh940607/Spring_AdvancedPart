package hello.proxy.postprocessor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicTest {

	@Test
	void basicConfig() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(BasicConfig.class);
		A a = ac.getBean("BeanA", A.class);
		a.a(); // 정상적으로 bean 등록
		
		// 등록하지 않으 b 호출
		Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean(B.class));
	}
	
	@Configuration
	static class BasicConfig {
		@Bean(name="BeanA")
		public A a() {
			return new A();
		}
	}
	
	@Slf4j
	static class A {
		public void a () {
			log.info("A 객체");
		}
	}
	
	@Slf4j
	static class B {
		public void b () {
			log.info("B 객체");
		}
	}
	
}
