package com.hotent.core.sms.impl;

import com.hotent.core.sms.IShortMessage;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoneMessageImpl implements IShortMessage {
	private static NoneMessageImpl instance;
	private static Lock lock = new ReentrantLock();
	protected Logger logger = LoggerFactory.getLogger(NoneMessageImpl.class);

	public boolean sendSms(List<String> mobiles, String message) {
		this.logger.info("不支持的短信类型...");
		return false;
	}

	public static NoneMessageImpl getInstance() {
		if (instance == null) {
			lock.lock();

			try {
				if (instance == null) {
					instance = new NoneMessageImpl();
				}
			} finally {
				lock.unlock();
			}
		}

		return instance;
	}
}