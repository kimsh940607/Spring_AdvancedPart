package hello.proxy.config.v2_dynamicproxy.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class LogTraceBasicHandler implements InvocationHandler{

	private final Object target;
	private final LogTrace logTrace;
	
	public LogTraceBasicHandler(Object target, LogTrace logTrace) {
		this.target = target;
		this.logTrace = logTrace;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		TraceStatus traceStatus = null;
		try {
			String message =  method.getDeclaringClass().getSimpleName() + "." + method.getClass() + "()";
			traceStatus = logTrace.begin(message);
			Object result = method.invoke(target, args);
			logTrace.end(traceStatus);
			return result;
		}catch(Exception e) {
			logTrace.exception(traceStatus, e);
			throw e;
		}	
	}
	
	
}
