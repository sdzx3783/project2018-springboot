package com.hotent.core.fulltext.impl;

import com.hotent.core.fulltext.IDocument;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PdfImpl implements IDocument {
	private String fileName = "";

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String extract() {
		String result = null;
		FileInputStream is = null;
		PDDocument document = null;

		try {
			is = new FileInputStream(this.fileName);
			PDFParser e = new PDFParser(is);
			e.parse();
			document = e.getPDDocument();
			PDFTextStripper stripper = new PDFTextStripper();
			result = stripper.getText(document);
		} catch (FileNotFoundException arg20) {
			arg20.printStackTrace();
		} catch (IOException arg21) {
			arg21.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException arg19) {
					arg19.printStackTrace();
				}
			}

			if (document != null) {
				try {
					document.close();
				} catch (IOException arg18) {
					arg18.printStackTrace();
				}
			}

		}

		return result;
	}
}