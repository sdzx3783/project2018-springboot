package com.hotent.core.util;

import com.hotent.core.util.AppUtil;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContextEvent;
import javax.xml.transform.TransformerFactoryConfigurationError;

public class ClassLoadUtil {
	public static String transform(String id, String name, String xml)
			throws TransformerFactoryConfigurationError, Exception {
		if (StringUtil.isEmpty(xml)) {
			return "";
		} else {
			HashMap map = new HashMap();
			map.put("id", id);
			map.put("name", name);
			String xlstPath = FileUtil.getClassesPath()
					+ "com/hotent/core/bpm/graph/transform.xsl".replace("/", File.separator);
			xml = xml.trim();
			String str = Dom4jUtil.transXmlByXslt(xml, xlstPath, map);
			str = str.replace("&lt;", "<").replace("&gt;", ">").replace("xmlns=\"\"", "").replace("&amp;", "&");
			Pattern regex = Pattern.compile("name=\".*?\"");

			String strReplace;
			String strReplaceWith;
			for (Matcher regexMatcher = regex.matcher(str); regexMatcher
					.find(); str = str.replace(strReplace, strReplaceWith)) {
				strReplace = regexMatcher.group(0);
				strReplaceWith = strReplace.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
			}

			return str;
		}
	}

	public static void contextInitialized(ServletContextEvent event) {
		AppUtil.init(event.getServletContext());
	}
}