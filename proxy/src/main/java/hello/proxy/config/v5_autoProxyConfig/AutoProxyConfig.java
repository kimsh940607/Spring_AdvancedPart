package hello.proxy.config.v5_autoProxyConfig;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

//	@Bean
	public Advisor advisor1(LogTrace logTrace) {
		// pointcut 정의
		// advice 정의
		NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
		nameMatchMethodPointcut.setMappedNames("request*", "save*", "order*");
		LogTraceAdvice advice = new LogTraceAdvice(logTrace);
		return new DefaultPointcutAdvisor(nameMatchMethodPointcut, advice);
	}

//	@Bean
	public Advisor advisor2(LogTrace logTrace) {
		// pointcut 정의
		// advice 정의
//		NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
//		nameMatchMethodPointcut.setMappedNames("request*", "save*", "order*");
		AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
		aspectJExpressionPointcut.setExpression("execution(* hello.proxy.app..*(..))");
		
		LogTraceAdvice advice = new LogTraceAdvice(logTrace);
		return new DefaultPointcutAdvisor(aspectJExpressionPointcut, advice);
	}

	@Bean
	public Advisor advisor3(LogTrace logTrace) {
		// pointcut 정의
		// advice 정의
//		NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
//		nameMatchMethodPointcut.setMappedNames("request*", "save*", "order*");
		AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
		aspectJExpressionPointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))");
		
		LogTraceAdvice advice = new LogTraceAdvice(logTrace);
		return new DefaultPointcutAdvisor(aspectJExpressionPointcut, advice);
	}
}
