package hello.advanced.app.v3;

import org.springframework.stereotype.Repository;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

/**
 * @author sh.kim
 * Example VO
 */
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV3 {

	private final LogTrace trace;
	
	public void save(String itemId) {
		
		TraceStatus status =  null;
		
		try {
			status = trace.begin("OrderService.save()");
			if(itemId.equals("ex")) {
				throw new IllegalStateException("���� �߻�");
			}
			sleep(1000);
			trace.end(status);

		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
		
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
