package hello.proxy.config.v6_aopProxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.proxy.config.v6_aopProxy.aop.AopProxyLogTrace;
import hello.proxy.trace.logtrace.LogTrace;

@Configuration
public class AopConfig {

	@Bean
	public AopProxyLogTrace aopProxyLogTrace(LogTrace logTrace) {
		return new AopProxyLogTrace(logTrace); 
	}
}
