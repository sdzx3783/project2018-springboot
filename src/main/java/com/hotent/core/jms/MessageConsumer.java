package com.hotent.core.jms;

import com.hotent.core.jms.IJmsHandler;
import com.hotent.core.util.BeanUtils;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageConsumer {
	private Map<String, IJmsHandler> handlers = new HashMap();
	protected Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

	public void setHandlers(Map<String, IJmsHandler> handlers) {
		this.handlers = handlers;
	}

	public void sendMessage(Object model) throws Exception {
		if (BeanUtils.isNotEmpty(this.handlers) && BeanUtils.isNotEmpty(model)) {
			IJmsHandler jmsHandler = (IJmsHandler) this.handlers.get(model.getClass().getName());
			if (jmsHandler != null) {
				jmsHandler.handMessage(model);
			} else {
				this.logger.info(model.toString());
			}

		} else {
			throw new Exception("Object:[" + model + "] is not  entity Object ");
		}
	}
}