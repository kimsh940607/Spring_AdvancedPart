package hello.advanced.trace.callback;

/**
 * �Լ��� �������̽� 
 */
@FunctionalInterface
public interface TraceCallBack<T> {
	T call(); // 
}
