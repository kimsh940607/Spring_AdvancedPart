package hello.advanced.app.v0;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceV0 {

	private final OrderRepositoryV0 orderRepository; // final로 선언한 경우 RequiredArgsConstructor에 의해 DI형성
	
	public void orderItem(String itemId) {
		orderRepository.save(itemId);
	}
}
