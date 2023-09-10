package hello.advanced.app.v4;

import org.springframework.stereotype.Service;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {

	private final OrderRepositoryV4 orderRepository; // final로 선언한 경우 RequiredArgsConstructor에 의해 DI형성
	private final LogTrace trace;
	
	public void orderItem(String itemId) {
		
		AbstractTemplate<Void> template = new AbstractTemplate(trace) {

			@Override
			protected Object call() {
				orderRepository.save(itemId);
				return null;
			}
		};
		template.execute("OrderService.orderItem()");
	}
}
