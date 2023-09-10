package hello.advanced.app.v0;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/**
 * @author sh.kim
 * Example VO
 */
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {

	public void save(String itemId) {
		if(itemId.equals("ex")) {
			throw new IllegalStateException("���� �߻�");
		}
		sleep(1000);
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
