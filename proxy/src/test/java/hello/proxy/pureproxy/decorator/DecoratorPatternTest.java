package hello.proxy.pureproxy.decorator;

import org.junit.jupiter.api.Test;

import hello.proxy.pureproxy.decorator.code.Component;
import hello.proxy.pureproxy.decorator.code.DecoratorPatternClient;
import hello.proxy.pureproxy.decorator.code.MessageDecorator;
import hello.proxy.pureproxy.decorator.code.RealComponent;
import hello.proxy.pureproxy.decorator.code.TimeDecorator;

public class DecoratorPatternTest {
	
	@Test
	void noDecoratorPattern() {
		Component realComponent = new RealComponent();
		DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(realComponent);
		decoratorPatternClient.execute();
	}
	
	@Test
	void DecoratorPattern() {
		Component realComponent = new RealComponent();
		MessageDecorator messageDecorator = new MessageDecorator(realComponent);
		DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(messageDecorator);
		
		decoratorPatternClient.execute();
	}
	
	@Test
	void TimeDecoratorPattern() {
		Component realComponent = new RealComponent();
		MessageDecorator messageDecorator = new MessageDecorator(realComponent);
		TimeDecorator timeDecorator = new TimeDecorator(messageDecorator);
		DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(timeDecorator);
		
		decoratorPatternClient.execute();
	}

}
