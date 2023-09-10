package hello.proxy.jdkdynamic.code;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeInvokeHandle implements InvocationHandler{

	private Object target;
	
	public TimeInvokeHandle(AInterface aImpl) {
		this.target = aImpl;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		log.info("Start");
		long startMillis = System.currentTimeMillis();
		Object result = method.invoke(this.target, args);
		long endMillis = System.currentTimeMillis();
		log.info("result time = {}", endMillis - startMillis);
		return result;
	}

}
