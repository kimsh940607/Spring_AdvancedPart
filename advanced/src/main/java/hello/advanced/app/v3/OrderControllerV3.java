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
	 * ������ begin�� ���� �����̹Ƿ� begin���� ���� ���� beginSync ȣ��
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
