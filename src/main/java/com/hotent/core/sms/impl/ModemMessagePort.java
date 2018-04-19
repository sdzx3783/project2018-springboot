package com.hotent.core.sms.impl;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModemMessagePort {
	public static final int PACKET_LENGTH = 500;
	private static ModemMessagePort port;
	private static Lock lock = new ReentrantLock();
	protected Logger logger = LoggerFactory.getLogger(ModemMessagePort.class);
	SerialPort serialPort;
	CommPortIdentifier identifier;
	String PortName;
	OutputStream out;
	InputStream in;
	String appname = "SerialBean";
	int timeOut;
	int baudrate;
	int dataBits;
	int stopBits;
	int parity;

	public static ModemMessagePort getInstance() {
		if (port == null) {
			lock.lock();

			try {
				if (port == null) {
					port = new ModemMessagePort();
				}
			} finally {
				lock.unlock();
			}
		}

		return port;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public void initialize(int timeOut, int baudrate, int dataBits, int stopBits, int parity) {
		this.timeOut = timeOut;
		this.baudrate = baudrate;
		this.dataBits = dataBits;
		this.stopBits = stopBits;
		this.parity = parity;
	}

	public boolean openPort(String portName) {
		boolean rsBool = false;
		this.PortName = portName;

		try {
			this.identifier = this.getCommPort();
			if (this.identifier != null && !this.identifier.isCurrentlyOwned()) {
				this.serialPort = (SerialPort) this.identifier.open(this.appname, this.timeOut);
				this.in = this.serialPort.getInputStream();
				this.out = this.serialPort.getOutputStream();
				this.serialPort.setSerialPortParams(this.baudrate, this.dataBits, this.stopBits, this.parity);
				this.serialPort.setDTR(true);
				this.serialPort.setRTS(true);
				rsBool = true;
			}
		} catch (PortInUseException arg3) {
			;
		} catch (Exception arg4) {
			;
		}

		return rsBool;
	}

	public CommPortIdentifier getCommPort() throws Exception {
		CommPortIdentifier portIdRs = null;
		portIdRs = CommPortIdentifier.getPortIdentifier(this.PortName);
		return portIdRs;
	}

	public char[] readPackData() throws Exception {
		byte[] readBuffer = new byte[500];
		char[] msgPack = null;
		boolean numBytes = false;

		while (this.in.available() > 0) {
			int arg5 = this.in.read(readBuffer);
			Object arg4 = null;
			msgPack = new char[arg5];

			for (int i = 0; i < arg5; ++i) {
				msgPack[i] = (char) (readBuffer[i] & 255);
			}
		}

		return msgPack;
	}

	public void writePort(char[] bytes) throws IOException {
		char[] arr$ = bytes;
		int len$ = bytes.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			char b = arr$[i$];
			this.writePort(b);
		}

	}

	public void writePort(char b) throws IOException {
		this.out.write(b);
		this.out.flush();
	}

	public void closePort() {
		if (this.out != null) {
			try {
				this.out.close();
				this.in.close();
				this.out = null;
				this.in = null;
			} catch (IOException arg1) {
				;
			}
		}

		if (this.serialPort != null) {
			this.serialPort.close();
			this.serialPort = null;
		}

	}

	public List<String> getAllComPorts() {
		ArrayList comList = new ArrayList();
		this.logger.info("[sms]准备获取所有端口…");
		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier portIdRs = null;

		while (en.hasMoreElements()) {
			portIdRs = (CommPortIdentifier) en.nextElement();
			if (portIdRs.getPortType() == 1) {
				comList.add(portIdRs.getName());
			}
		}

		this.logger.info("[sms]获取到:" + comList.size() + "个端口");
		return comList;
	}
}