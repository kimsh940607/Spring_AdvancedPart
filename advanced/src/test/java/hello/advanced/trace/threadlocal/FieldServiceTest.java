package hello.advanced.trace.threadlocal;

import org.junit.jupiter.api.Test;

import hello.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;

/**
 * FieldService TDD
 *
 */
@Slf4j
public class FieldServiceTest {

	private FieldService fieldService = new FieldService();
	
	@Test
	void field() {
		log.info("main Start");
		Runnable userA = () -> {
			fieldService.logic("userA");
		};
		
		Runnable userB = () -> {
			fieldService.logic("userB");
		};
		
		Thread threadA = new Thread(userA);
		threadA.setName("thread-A");
		
		Thread threadB = new Thread(userB);
		threadB.setName("thread-B");
		
		threadA.start();
		sleep(100); // 동시성 문제가 아에 발생 안함.
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
