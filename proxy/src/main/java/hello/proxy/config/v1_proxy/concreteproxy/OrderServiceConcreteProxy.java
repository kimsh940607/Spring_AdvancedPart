package hello.proxy.config.v1_proxy.concreteproxy;

import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2{
	
	private final OrderServiceV2 target;
	
	private final LogTrace logTrace;
	
	public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
		super(null); // OrderServiceV2의 필드 생성자가 존재하여 super 필수지만 부모 클래스의 생성자가 필요없으므로 null 전달.
		this.target = target;
		this.logTrace = logTrace;
	}
	
	@Override
    public void orderItem(String itemId) {
		TraceStatus traceStatus = null;
		try {
			traceStatus = logTrace.begin("OrderService.request()");
			target.orderItem(itemId);
			logTrace.end(traceStatus);
			
		}catch(Exception e) {
			logTrace.exception(traceStatus, e);
			throw e;
		}		
    }
}
