package com.hotent.core.jms;

import com.hotent.core.model.MessageModel;

public interface IMessageHandler {
	String getTitle();

	boolean getIsDefaultChecked();

	void handMessage(MessageModel arg0);
}