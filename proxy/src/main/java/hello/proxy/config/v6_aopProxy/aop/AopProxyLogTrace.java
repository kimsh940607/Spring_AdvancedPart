package hello.proxy.config.v6_aopProxy.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Import;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Import({AppV1Config.class, AppV2Config.class})
public class AopProxyLogTrace {
	
	private final LogTrace logTrace;
	
	public AopProxyLogTrace(LogTrace logTrace) {
		this.logTrace = logTrace;
	}

	@Around("execution(* hello.proxy.app..*(..))")
	public Object aspectAopTrace (ProceedingJoinPoint joinPoint) throws Throwable {
		// log.info("[LOG INFO] : {} ", joinPoint.getSignature());
		TraceStatus traceStatus = null;
		try {
			String message =  joinPoint.getSignature().toShortString();
			traceStatus = logTrace.begin(message);
			Object result = joinPoint.proceed();
			logTrace.end(traceStatus);
			return result;
		}catch(Exception e) {
			logTrace.exception(traceStatus, e);
			throw e;
		}
	}
}
