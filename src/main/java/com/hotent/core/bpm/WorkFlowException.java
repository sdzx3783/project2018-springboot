package com.hotent.core.bpm;

public class WorkFlowException extends RuntimeException {
	private static final long serialVersionUID = -7289238698048230824L;

	public WorkFlowException(String msg) {
		super(msg);
	}

	public WorkFlowException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
}