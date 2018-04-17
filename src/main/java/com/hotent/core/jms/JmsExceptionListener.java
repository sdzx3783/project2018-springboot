package com.hotent.core.jms;

import com.hotent.core.util.ExceptionUtil;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsExceptionListener implements ExceptionListener {
	protected Logger logger = LoggerFactory.getLogger(JmsExceptionListener.class);

	public void onException(JMSException ex) {
		ex.printStackTrace();
		String message = ExceptionUtil.getExceptionMessage(ex);
		this.logger.error(message);
	}
}