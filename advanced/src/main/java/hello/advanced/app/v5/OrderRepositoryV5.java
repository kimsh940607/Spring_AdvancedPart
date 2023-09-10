package hello.advanced.app.v5;

import org.springframework.stereotype.Repository;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;

/**
 * @author sh.kim
 * Example VO
 */
@Repository
public class OrderRepositoryV5 {

	private final TraceTemplate traceTemplate;
	
	public OrderRepositoryV5 (LogTrace trace) {
		traceTemplate = new TraceTemplate(trace);
	}
	
	public void save(String itemId) {
		
		traceTemplate.execute("OrderService.save()", () -> {
			if(itemId.equals("ex")) {
				throw new IllegalStateException("���� �߻�");
			}
			sleep(1000);
			return null;
		});
		
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
