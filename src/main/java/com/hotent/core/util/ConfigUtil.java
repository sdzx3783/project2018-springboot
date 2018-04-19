package com.hotent.core.util;

import com.hotent.core.util.Dom4jUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.dom4j.Document;
import org.dom4j.Element;

public class ConfigUtil {
	private Document doc = null;
	private static ConfigUtil config = null;
	private static Lock lock = new ReentrantLock();

	private ConfigUtil() {
		InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream("conf/viewconfig.xml");
		this.doc = Dom4jUtil.loadXml(is);

		try {
			is.close();
		} catch (IOException arg2) {
			arg2.printStackTrace();
		}

	}

	public static ConfigUtil getInstance() {
		if (config == null) {
			lock.lock();

			try {
				if (config == null) {
					config = new ConfigUtil();
				}
			} finally {
				lock.unlock();
			}
		}

		return config;
	}

	public String getValue(String category, String id) {
		String template = "category[@id=\'%s\']/view[@name=\'%s\']";
		String filter = String.format(template, new Object[]{category, id});
		Element root = this.doc.getRootElement();
		Element el = (Element) root.selectSingleNode(filter);
		return el != null ? el.attributeValue("value") : "";
	}

	public static String getVal(String category, String id) {
		return getInstance().getValue(category, id);
	}
}