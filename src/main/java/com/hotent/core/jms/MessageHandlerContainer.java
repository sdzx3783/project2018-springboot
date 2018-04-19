package com.hotent.core.jms;

import com.hotent.core.jms.IMessageHandler;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHandlerContainer {
	protected Logger logger = LoggerFactory.getLogger(MessageHandlerContainer.class);
	private Map<String, IMessageHandler> handlersMap = new LinkedHashMap();

	public Map<String, IMessageHandler> getHandlerMap() {
		return this.handlersMap;
	}

	public void setHandlersMap(Map<String, IMessageHandler> map) {
		this.handlersMap = map;
	}

	public IMessageHandler getHandler(String key) {
		return (IMessageHandler) this.handlersMap.get(key);
	}
}