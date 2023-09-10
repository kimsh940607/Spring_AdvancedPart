package hello.advanced.trace.threadlocal;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.threadlocal.code.FieldService;
import hello.advanced.trace.threadlocal.code.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;

/**
 * FieldService TDD
 *
 */
@Slf4j
public class ThreadLocalServiceTest {

	private ThreadLocalService service = new ThreadLocalService();
	
	@Test
	void field() {
		log.info("main Start");
		Runnable userA = () -> {
			service.logic("userA");
		};
		
		Runnable userB = () -> {
			service.logic("userB");
		};
		
		Thread threadA = new Thread(userA);
		threadA.setName("thread-A");
		
		Thread threadB = new Thread(userB);
		threadB.setName("thread-B");
		
		threadA.start();
		sleep(100); // ���ü� ������ �ƿ� �߻� ����.
		threadB.start();
		
		sleep(3000);
		log.info("main Exit");
		
	}
	
	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
