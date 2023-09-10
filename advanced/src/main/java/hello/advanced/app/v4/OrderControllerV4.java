package hello.advanced.app.v4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {

	private final OrderServiceV4 orderService;
	private final LogTrace trace;
	
	/**
	 * 최초의 begin은 최초 시작이므로 begin으로 실행 이후 beginSync 호출
	 */
	@GetMapping("/v4/request")
	public String request(String itemId) {

		
		AbstractTemplate<String> template = new AbstractTemplate(trace) {

			@Override
			protected Object call() {
				orderService.orderItem(itemId);
				return "OK";
			}
		
		};
		return template.execute("OrderController.request()");
	}
}
