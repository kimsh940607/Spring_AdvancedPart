package hello.proxy.config.v2_dynamicproxy.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.util.PatternMatchUtils;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class LogTraceFilterHandler implements InvocationHandler{

	private final Object target;
	private final LogTrace logTrace;
	private final String[] patterns;
	
	public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
		this.target = target;
		this.logTrace = logTrace;
		this.patterns = patterns;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		// 메서드 이름 필터 method.getName();
		String methodName = method.getName();
		// save, request, reque*, *est
		if(!PatternMatchUtils.simpleMatch(patterns, methodName)) {
			return method.invoke(target, args);
		}
		
		TraceStatus traceStatus = null;
		try {
			String message =  method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
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
