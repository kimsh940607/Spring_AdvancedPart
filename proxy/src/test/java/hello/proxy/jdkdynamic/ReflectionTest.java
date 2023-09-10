package hello.proxy.jdkdynamic;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionTest {

	@Test
	void reflection0() throws Exception {
		Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");
		
		Hello target = new Hello();
		Method method = classHello.getMethod("CallA"); //Hello class의 메타 데이터에 getMethod로 CallA를 가져온다.
		this.dynamicCall(method, target);
		
		Method method2 = classHello.getMethod("CallB"); //Hello class의 메타 데이터에 getMethod로 CallA를 가져온다.
		this.dynamicCall(method2, target);
	}
	
	private void dynamicCall(Method method, Object target) throws Exception {
		log.info("Start");
		Object result = method.invoke(target);
		log.info("result={}", result);
		
	}
	
	static class Hello {
		public String CallA() {
			log.info("CallA 실행");
			return "A";
		}
		
		public String CallB() {
			log.info("CallB 실행");
			return "B";
		}
	}
}
