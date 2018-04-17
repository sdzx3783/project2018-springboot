package com.hotent.core.sms.impl;

import com.hotent.core.api.util.PropertyUtil;
import com.hotent.core.sms.IShortMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShortMessageImpl implements IShortMessage {
	private static ShortMessageImpl instance;
	private static Lock lock = new ReentrantLock();
	String url;
	String userID;
	String account;
	String password;
	String content;
	String sendTime;
	int sendType;
	String postFixNumber;
	int sign;

	public void initial() {
		this.url = PropertyUtil.getByAlias("smsUrl");
		this.userID = PropertyUtil.getByAlias("smsUserID");
		this.account = PropertyUtil.getByAlias("smsAccount");
		this.password = PropertyUtil.getByAlias("smsPassword");
		this.content = PropertyUtil.getByAlias("smsContent");
		this.sendTime = PropertyUtil.getByAlias("smsSendTime", "");
		this.sendType = PropertyUtil.getIntByAlias("smssendType", Integer.valueOf(0)).intValue();
		this.postFixNumber = PropertyUtil.getByAlias("smsPostFixNumber");
		this.sign = PropertyUtil.getIntByAlias("smsSign", Integer.valueOf(0)).intValue();
	}

	public boolean sendSms(List<String> mobiles, String message) {
		this.initial();
		String envelop = this.generateEnvelop(mobiles, message);
		OutputStreamWriter outputStreamWriter = null;
		BufferedReader bufferedReader = null;

		try {
			URL uRL = new URL(this.url);
			URLConnection conn = uRL.openConnection();
			conn.setDoOutput(true);
			outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
			outputStreamWriter.write(envelop);
			outputStreamWriter.flush();
			bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer e = new StringBuffer();

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				e.append(line);
			}

			outputStreamWriter.close();
			bufferedReader.close();
			return true;
		} catch (MalformedURLException arg9) {
			arg9.printStackTrace();
			return false;
		} catch (IOException arg10) {
			arg10.printStackTrace();
			return false;
		}
	}

	private String generateEnvelop(List<String> mobiles, String content) {
		StringBuffer phones = new StringBuffer();
		Iterator wsdlStr = mobiles.iterator();

		while (wsdlStr.hasNext()) {
			String mobile = (String) wsdlStr.next();
			phones.append(mobile);
			phones.append(",");
		}

		phones.deleteCharAt(phones.length() - 1);
		String wsdlStr1 = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.sms.com\"><soapenv:Header/><soapenv:Body><soap:directSend><soap:userID>"
				+ this.userID + "</soap:userID>" + "<soap:account>" + this.account + "</soap:account>"
				+ "<soap:password>" + this.password + "</soap:password>" + "<soap:phones>" + phones + "</soap:phones>"
				+ "<soap:content>" + content + "</soap:content>" + "<soap:sendTime>" + this.sendTime
				+ "</soap:sendTime>" + "<soap:sendType>" + this.sendType + "</soap:sendType>" + "<soap:postFixNumber>"
				+ this.postFixNumber + "</soap:postFixNumber>" + "<soap:sign>" + this.sign + "</soap:sign>"
				+ "</soap:directSend>" + "</soapenv:Body>" + "</soapenv:Envelope>";
		return wsdlStr1;
	}

	public static ShortMessageImpl getInstance() {
		if (instance == null) {
			lock.lock();

			try {
				if (instance == null) {
					instance = new ShortMessageImpl();
				}
			} finally {
				lock.unlock();
			}
		}

		return instance;
	}
}