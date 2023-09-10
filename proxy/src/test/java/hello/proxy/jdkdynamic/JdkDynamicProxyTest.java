package hello.proxy.jdkdynamic;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;

import hello.proxy.jdkdynamic.code.AImpl;
import hello.proxy.jdkdynamic.code.AInterface;
import hello.proxy.jdkdynamic.code.TimeInvokeHandle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JdkDynamicProxyTest {

	@Test
	void dynamicProxy() {
		AInterface aImpl = new AImpl();
		
		TimeInvokeHandle timeInvokeHandle = new TimeInvokeHandle(aImpl);
		
		AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[] {AInterface.class}, timeInvokeHandle);
		proxy.Call();
		
		log.info("AInterface class = {}", aImpl.getClass());
		log.info("proxy class = {}", proxy.getClass());
	}
	
}
