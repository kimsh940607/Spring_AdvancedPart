package hello.advanced.app.v1;

import org.springframework.stereotype.Repository;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;

/**
 * @author sh.kim
 * Example VO
 */
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {

	private final HelloTraceV1 trace;
	
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
