package hello.advanced.app.v5;

import org.springframework.stereotype.Service;

import hello.advanced.trace.callback.TraceCallBack;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;

@Service
public class OrderServiceV5 {

	private final OrderRepositoryV5 orderRepository; // final로 선언한 경우 RequiredArgsConstructor에 의해 DI형성
	private final TraceTemplate traceTemplate;
	
	public OrderServiceV5 (OrderRepositoryV5 orderRepository, LogTrace trace) {
		this.orderRepository = orderRepository;
		this.traceTemplate = new TraceTemplate(trace);
	}
	
	public void orderItem(String itemId) {
		
		traceTemplate.execute("OrderService.orderItem()", () -> {
				orderRepository.save(itemId);
				return null;
		});
	}
}
