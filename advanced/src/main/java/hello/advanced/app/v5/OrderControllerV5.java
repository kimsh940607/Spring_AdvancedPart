package hello.advanced.app.v5;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.advanced.trace.callback.TraceCallBack;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;

@RestController
public class OrderControllerV5 {

	private final OrderServiceV5 orderService;
	private final TraceTemplate traceTemplate; 
	
	public OrderControllerV5 (OrderServiceV5 orderService, LogTrace logTrace) {
		this.orderService = orderService;
		this.traceTemplate = new TraceTemplate(logTrace);
	}
	/**
	 * 최초의 begin은 최초 시작이므로 begin으로 실행 이후 beginSync 호출
	 */
	@GetMapping("/v5/request")
	public String request(String itemId) {

		return traceTemplate.execute("OrderController.request()", new TraceCallBack<String>() {
			@Override
			public String call() {
				orderService.orderItem(itemId);
				return "OK";
			}
			
		});
	}
}
