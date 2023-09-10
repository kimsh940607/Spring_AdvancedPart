package hello.proxy.common.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		log.info("Start");
		long startMillis = System.currentTimeMillis();
		Object result = invocation.proceed();
		long endMillis = System.currentTimeMillis();
		log.info("result time = {}", endMillis - startMillis);
		return result;
	}

}
