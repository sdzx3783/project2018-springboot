package com.hotent.core.datahandler;

import org.springframework.context.ApplicationEvent;

public class UpdDataEvent extends ApplicationEvent {
	private static final long serialVersionUID = -656796814656037536L;

	public UpdDataEvent(Object source) {
		super(source);
	}
}