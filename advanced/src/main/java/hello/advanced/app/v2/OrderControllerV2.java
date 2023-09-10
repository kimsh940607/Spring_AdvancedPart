package hello.advanced.app.v2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

	private final OrderServiceV2 orderService;
	private final HelloTraceV2 trace;
	
	/**
	 * 최초의 begin은 최초 시작이므로 begin으로 실행 이후 beginSync 호출
	 */
	@GetMapping("/v2/request")
	public String request(String itemId) {

		TraceStatus status =  null;
		
		try {
			status = trace.begin("OrderController.request()");
			orderService.orderItem(status.getTraceId() ,itemId);
			trace.end(status);
			return "OK";
			
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}
}
