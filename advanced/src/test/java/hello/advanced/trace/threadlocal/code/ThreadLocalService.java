package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

/**
 * Thread Tdd
 *
 */
@Slf4j
public class ThreadLocalService {

	private ThreadLocal<String> nameStore = new ThreadLocal<>();
	
	/**
	 * ThreadLocal
	 * 값 저장 ThreadLocal.set(xxx);
	 * 값 조회 ThreadLocal.get();
	 * 값 삭제 ThreadLocal.remove();
	 */
	public String logic(String name) {
		
		log.info("저장 name {} -> nameStore={}", name, nameStore.get());
		nameStore.set(name);
		sleep(1000);
		log.info("조회 nameStore = {}", nameStore.get());
		return nameStore.get();
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
