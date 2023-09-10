package hello.advanced.app.v2;

import org.springframework.stereotype.Repository;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;

/**
 * @author sh.kim
 * Example VO
 */
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

	private final HelloTraceV2 trace;
	
	public void save(TraceId traceId, String itemId) {
		
		TraceStatus status =  null;
		
		try {
			status = trace.beginSync(traceId, "OrderService.save()");
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
