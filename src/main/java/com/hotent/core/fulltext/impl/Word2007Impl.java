package com.hotent.core.fulltext.impl;

import com.hotent.core.fulltext.IDocument;
import java.io.IOException;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

public class Word2007Impl implements IDocument {
	private String fileName = "";

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String extract() {
		String str = "";

		try {
			OPCPackage opcPackage = POIXMLDocument.openPackage(this.fileName);

			try {
				XWPFWordExtractor e = new XWPFWordExtractor(opcPackage);
				str = e.getText();
			} catch (XmlException arg4) {
				arg4.printStackTrace();
			} catch (OpenXML4JException arg5) {
				arg5.printStackTrace();
			}

			return str;
		} catch (IOException arg6) {
			arg6.printStackTrace();
			return "";
		}
	}
}