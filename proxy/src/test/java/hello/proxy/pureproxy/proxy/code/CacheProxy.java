package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject{

	private Subject target;
	
	private String cacheValue;
	
	
	/**
	 * Client -> Proxy -> RealSubject
	 */
	public CacheProxy (Subject subject) {
		this.target = subject;
	}
	
	@Override
	public String operation() {
		log.info("프록시 객체 호출");
		if(this.cacheValue == null) {
			this.cacheValue = target.operation(); // "data" String return
		} 
		return cacheValue;
	}
}
