package hello.advanced.app.v0;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceV0 {

	private final OrderRepositoryV0 orderRepository; // final�� ������ ��� RequiredArgsConstructor�� ���� DI����
	
	public void orderItem(String itemId) {
		orderRepository.save(itemId);
	}
}
