package com.hotent.core.sms.impl;

import com.hotent.core.sms.impl.ModemMessagePort;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModemMessageOperator {
	private static ModemMessageOperator instance;
	private static ModemMessagePort messagePort;
	private static Lock lock = new ReentrantLock();
	protected static Logger logger = LoggerFactory.getLogger(ModemMessageOperator.class);
	int portId;
	int baudrate;
	int timeOut;
	int dataBits;
	int stopBits;
	int parity;
	int funCode;
	int dataLen;
	int appendMillsec;
	int bytes;
	int frameInterval;

	private ModemMessageOperator() {
		messagePort = ModemMessagePort.getInstance();
		this.timeOut = 60;
		this.baudrate = 9600;
		this.dataBits = 8;
		this.stopBits = 1;
		this.parity = 0;
		this.funCode = 3;
		this.dataLen = 4;
		this.appendMillsec = 38;
		this.bytes = 20;
	}

	public static ModemMessageOperator getInstance() {
		if (instance == null) {
			lock.lock();

			try {
				if (instance == null) {
					instance = new ModemMessageOperator();
				}
			} catch (Exception arg3) {
				logger.info("[sms]error:" + arg3.getMessage());
			} finally {
				lock.unlock();
			}
		}

		return instance;
	}

	public boolean openPort(String portStr, int baudrate, String appName) {
		boolean rsBool = false;
		messagePort.initialize(this.timeOut, baudrate, this.dataBits, this.stopBits, this.parity);
		messagePort.setAppname(appName.toUpperCase());
		if (messagePort.openPort(portStr)) {
			rsBool = true;
			this.frameInterval = getFrameInterval(this.appendMillsec, this.bytes, baudrate);
		}

		return rsBool;
	}

	public void writeByte(char[] rs) throws Exception {
		messagePort.writePort(rs);
		Thread.sleep((long) this.frameInterval);
	}

	public boolean readByte(String portStr) throws Exception {
		boolean rsbool = false;
		String rsStr = "";
		char[] rsByte = messagePort.readPackData();
		if (rsByte != null) {
			char[] arr$ = rsByte;
			int len$ = rsByte.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				char c = arr$[i$];
				rsStr = rsStr + c;
			}

			if (rsStr.indexOf("OK") > 0) {
				logger.info("找到" + portStr + ":短信模块串口");
				rsbool = true;
			}
		} else {
			logger.info(portStr + ":不是短信模块串口");
		}

		return rsbool;
	}

	public String getRightComStr() {
		String rightCom = null;
		List portList = messagePort.getAllComPorts();
		if (portList.size() > 0) {
			Iterator i$ = portList.iterator();

			while (i$.hasNext()) {
				String portStr = (String) i$.next();
				if (this.testSms(portStr)) {
					rightCom = portStr;
					break;
				}
			}
		}

		return rightCom;
	}

	private boolean testSms(String portStr) {
		boolean rsBool = false;

		try {
			rsBool = instance.openPort(portStr, this.baudrate, "sms_port");
			String e = "AT\r";
			char[] atOrder = e.toCharArray();
			if (rsBool) {
				instance.writeByte(atOrder);
			}

			if (rsBool) {
				rsBool = instance.readByte(portStr);
				instance.closePort();
			}
		} catch (Exception arg4) {
			rsBool = false;
			arg4.printStackTrace();
		}

		return rsBool;
	}

	public void closePort() {
		messagePort.closePort();
	}

	public static int getFrameInterval(int appendMillsec, int dataLen, int baudrate) {
		int rsInt = (int) Math.ceil((double) ((float) (12 * (dataLen + 4) * 1000) / (float) baudrate)) + appendMillsec;
		return rsInt;
	}

	public static final String bytesToHexString(char[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);

		for (int i = 0; i < bArray.length; ++i) {
			String sTemp = Integer.toHexString(255 & bArray[i]);
			if (sTemp.length() < 2) {
				sb.append(0);
			}

			sb.append(sTemp.toUpperCase() + " ");
		}

		return sb.toString();
	}
}