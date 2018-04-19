package com.hotent.core.jms;

import com.hotent.core.jms.IJmsHandler;
import com.hotent.core.jms.IMessageHandler;
import com.hotent.core.jms.MessageHandlerContainer;
import com.hotent.core.model.MessageModel;
import javax.annotation.Resource;

public class MessageHandler implements IJmsHandler {
	@Resource
	MessageHandlerContainer messageHandlerContainer;

	public void handMessage(Object model) {
		MessageModel msgModel = (MessageModel) model;
		String type = msgModel.getInformType();
		IMessageHandler handler = this.messageHandlerContainer.getHandler(type);
		handler.handMessage(msgModel);
	}
}