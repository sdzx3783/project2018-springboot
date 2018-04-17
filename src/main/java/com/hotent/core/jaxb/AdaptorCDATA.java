package com.hotent.core.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AdaptorCDATA extends XmlAdapter<String, String> {
	public String marshal(String source) throws Exception {
		return "<![CDATA[" + source + "]]>";
	}

	public String unmarshal(String arg) throws Exception {
		return arg;
	}
}