package hello.proxy.config.v4_postprocessor;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.config.v4_postprocessor.postprocessor.PackageLogTrace;
import hello.proxy.trace.logtrace.LogTrace;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class PostProcessConfig {
	private final String basePackage = "hello.proxy.app";
	
	@Bean
	public PackageLogTrace packageLogTrace(LogTrace logTrace) {
		return new PackageLogTrace(basePackage, this.getAdvisor(logTrace));
	}

	private Advisor getAdvisor(LogTrace logTrace) {
		String[] pattern = {"request*", "order*", "save*"}; 
		NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
		nameMatchMethodPointcut.setMappedNames(pattern);
		// DefaultPointcutAdvisor ("PointCut", advice(수행 할 메소드));
		DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(nameMatchMethodPointcut, new LogTraceAdvice(logTrace));
		return defaultPointcutAdvisor;
	}
}
