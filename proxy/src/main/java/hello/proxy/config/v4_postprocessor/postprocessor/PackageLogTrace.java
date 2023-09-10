package hello.proxy.config.v4_postprocessor.postprocessor;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PackageLogTrace implements BeanPostProcessor{

	private final String basePackages;
	private final Advisor advisor;
	
	public PackageLogTrace(String basePackages, Advisor advisor) {
		this.basePackages = basePackages;
		this.advisor = advisor;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.info("native boot bean bean = {}, beanName = {}", bean, beanName);
		
		// 프록시 적용 대상 여부 체크
		// 프록시 적용 대상이 아니면 원본을 그대로 진행
		String packageName = bean.getClass().getPackageName();
		if(!packageName.startsWith(this.basePackages)) {
			return bean;
		}
		// ProxyFactory() --> target을 넣어줌.
		ProxyFactory proxyFactory = new ProxyFactory(bean);
		proxyFactory.addAdvisor(this.advisor);
		Object proxy = proxyFactory.getProxy(); 
		
		log.info("hello proxy app beanr bean class = {}, proxy class = {}", bean.getClass(), proxy.getClass());
		
		return proxy;
	}
}
