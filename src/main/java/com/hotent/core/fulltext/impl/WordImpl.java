package com.hotent.core.fulltext.impl;

import com.hotent.core.fulltext.IDocument;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class WordImpl implements IDocument {
	private String fileName = "";

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String extract() {
		String str = "";
		FileInputStream in = null;

		try {
			in = new FileInputStream(this.fileName);
			WordExtractor e = new WordExtractor(in);
			str = e.getText();
		} catch (FileNotFoundException arg13) {
			arg13.printStackTrace();
		} catch (Exception arg14) {
			;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException arg12) {
					arg12.printStackTrace();
				}
			}

		}

		return str;
	}
}