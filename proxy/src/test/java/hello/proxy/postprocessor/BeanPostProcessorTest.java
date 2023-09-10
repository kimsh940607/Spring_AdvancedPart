package hello.proxy.postprocessor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanPostProcessorTest {

	@Test
	void basicConfig() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(BasicBeanPocessor.class);
		B a = ac.getBean("BeanA", B.class);
		a.b(); // 정상적으로 bean 등록
		
		//A는 bean으로 등록 되지 않는다
		Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean(A.class));
	}
	
	@Configuration
	static class BasicBeanPocessor {
		@Bean(name="BeanA")
		public A a() {
			return new A();
		}
		
		@Bean
		public BeanPostProcessorClass beanPostProcessorClass(){
			return new BeanPostProcessorClass();
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
	
	@Slf4j
	static class BeanPostProcessorClass implements BeanPostProcessor{
		@Override
		public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
			log.info("bean = {}, beanName = {}", bean, beanName);
			if(bean instanceof A) {
				return new B();
			}
			return bean;
		}
	}
}
