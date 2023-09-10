package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component{

	Component component;
	
	public MessageDecorator(Component component) {
		this.component = component;
	}
	
	@Override
	public String operation() {
		log.info("MessageDecoratorPattern");
		String result = component.operation();
		String decoResult = "******" + result + "******";
		log.info("MessageDecoratorPattern 적용 전 {}, 적용 후 {}", result, decoResult);
		return decoResult;
	}
}
