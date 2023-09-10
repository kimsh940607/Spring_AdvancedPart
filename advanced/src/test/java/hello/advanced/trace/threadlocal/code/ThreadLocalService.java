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
	 * �� ���� ThreadLocal.set(xxx);
	 * �� ��ȸ ThreadLocal.get();
	 * �� ���� ThreadLocal.remove();
	 */
	public String logic(String name) {
		
		log.info("���� name {} -> nameStore={}", name, nameStore.get());
		nameStore.set(name);
		sleep(1000);
		log.info("��ȸ nameStore = {}", nameStore.get());
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
