package hello.advanced.trace.callback;

/**
 * 함수형 인터페이스 
 */
@FunctionalInterface
public interface TraceCallBack<T> {
	T call(); // 
}
