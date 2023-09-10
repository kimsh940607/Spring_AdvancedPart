package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

/**
 * Thread Tdd
 *
 */
@Slf4j
public class FieldService {

	private String nameStore;
	
	public String logic(String name) {
		
		log.info("���� name {} -> nameStore={}", name, nameStore);
		nameStore = name;
		sleep(1000);
		log.info("��ȸ nameStore = {}", nameStore);
		return nameStore;
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
