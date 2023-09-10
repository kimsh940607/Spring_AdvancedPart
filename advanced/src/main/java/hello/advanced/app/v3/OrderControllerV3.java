package hello.advanced.app.v3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

	private final OrderServiceV3 orderService;
	private final LogTrace trace;
	
	/**
	 * 최초의 begin은 최초 시작이므로 begin으로 실행 이후 beginSync 호출
	 */
	@GetMapping("/v3/request")
	public String request(String itemId) {

		TraceStatus status =  null;
		
		try {
			status = trace.begin("OrderController.request()");
			orderService.orderItem(itemId);
			trace.end(status);
			return "OK";
			
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}
}
