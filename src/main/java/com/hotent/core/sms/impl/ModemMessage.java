package com.hotent.core.sms.impl;

import com.hotent.core.sms.IShortMessage;
import com.hotent.core.sms.impl.ModemMessageOperator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.AGateway.Protocols;
import org.smslib.Message.MessageEncodings;
import org.smslib.modem.SerialModemGateway;

public class ModemMessage implements IShortMessage {
	private static ModemMessage instance = null;
	private static Lock lock = new ReentrantLock();
	private boolean serviceHasOpen = false;
	private Service srv = null;
	private SerialModemGateway gateway;
	private static String smsGroup = "smsgruop";
	protected Logger logger = LoggerFactory.getLogger(ModemMessage.class);

	public static ModemMessage getInstance() {
		if (instance == null) {
			lock.lock();

			try {
				if (instance == null) {
					instance = new ModemMessage();
				}
			} finally {
				lock.unlock();
			}
		}

		return instance;
	}

	private boolean initial(String com, int baudRate, String pin) {
		boolean rsbool = true;
		this.srv = new Service();
		this.gateway = new SerialModemGateway("SMSLINK", com, baudRate, "", "");
		this.gateway.setOutbound(true);
		this.gateway.setInbound(true);
		this.gateway.setProtocol(Protocols.PDU);
		this.gateway.setSimPin(pin);

		try {
			this.srv.addGateway(this.gateway);
		} catch (GatewayException arg5) {
			rsbool = false;
			arg5.printStackTrace();
		}

		if (rsbool) {
			rsbool = this.startService();
		}

		return rsbool;
	}

	private boolean sendMessage(List<String> phoneList, String message) {
		boolean rsbool = true;
		Iterator msg = phoneList.iterator();

		while (msg.hasNext()) {
			String e = (String) msg.next();
			this.srv.addToGroup(smsGroup, e);
		}

		OutboundMessage msg1 = new OutboundMessage(smsGroup, message);
		msg1.setEncoding(MessageEncodings.ENCUCS2);

		try {
			this.srv.sendMessage(msg1);
			Iterator e1 = phoneList.iterator();

			while (e1.hasNext()) {
				String phone = (String) e1.next();
				this.srv.removeFromGroup(smsGroup, phone);
			}
		} catch (Exception arg6) {
			rsbool = false;
			arg6.printStackTrace();
		}

		return rsbool;
	}

	private boolean startService() {
		boolean rsbool = true;

		try {
			this.srv.startService();
			this.srv.createGroup(smsGroup);
		} catch (Exception arg2) {
			rsbool = false;
			arg2.printStackTrace();
		}

		return rsbool;
	}

	public boolean stopService() {
		boolean rsbool = true;

		try {
			if (this.srv != null) {
				this.srv.stopService();
				this.serviceHasOpen = false;
			}
		} catch (Exception arg2) {
			rsbool = false;
			arg2.printStackTrace();
		}

		return rsbool;
	}

	public boolean sendSms(List<String> mobiles, String message) {
		if (this.serviceHasOpen) {
			return this.sendMessage(mobiles, message);
		} else {
			String comStr = ModemMessageOperator.getInstance().getRightComStr();
			if (comStr == null) {
				this.logger.info("[SMS]未能获取到可以发送短信的串口。");
			}

			this.logger.info("[SMS]开始使用串口:" + comStr + "发送短信。");
			if (comStr != null) {
				if (this.initial(comStr, 9600, "0000")) {
					this.serviceHasOpen = true;
					return this.sendMessage(mobiles, message);
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	public static void main(String[] args) {
		ArrayList list = new ArrayList();
		list.add("15992494490");
		ModemMessage msg = getInstance();
		msg.sendSms(list, "hello 庄晓辉");
	}
}