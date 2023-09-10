package hello.advanced.trace.callback;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;

public class TraceTemplate {

	private LogTrace trace;
	
	public TraceTemplate (LogTrace logTrace) {
		this.trace = logTrace;
	}
	
	public <T> T execute(String message, TraceCallBack<T> callback) {
		TraceStatus status = null;
		try {
			status = trace.begin(message);
			T result = callback.call(); // ·ÎÁ÷
			trace.end(status);
			return result; 
			
		} catch(Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}
	
}
