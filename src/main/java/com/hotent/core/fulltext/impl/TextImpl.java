package com.hotent.core.fulltext.impl;

import com.hotent.core.fulltext.IDocument;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class TextImpl implements IDocument {
	private String fileName = "";

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String extract() {
		StringBuffer sb = new StringBuffer();

		try {
			File e = new File(this.fileName);
			String charset = getCharset(e);
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(this.fileName), charset));

			String str;
			while ((str = in.readLine()) != null) {
				sb.append(str + "\r\n");
			}

			in.close();
		} catch (UnsupportedEncodingException arg5) {
			arg5.printStackTrace();
		} catch (FileNotFoundException arg6) {
			arg6.printStackTrace();
		} catch (Exception arg7) {
			;
		}

		return sb.toString();
	}

	public static String getCharset(File file) {
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];

		try {
			boolean e = false;
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				return charset;
			}

			if (first3Bytes[0] == -1 && first3Bytes[1] == -2) {
				charset = "UTF-16LE";
				e = true;
			} else if (first3Bytes[0] == -2 && first3Bytes[1] == -1) {
				charset = "UTF-16BE";
				e = true;
			} else if (first3Bytes[0] == -17 && first3Bytes[1] == -69 && first3Bytes[2] == -65) {
				charset = "UTF-8";
				e = true;
			}

			bis.reset();
			if (!e) {
				int loc = 0;

				label74 : do {
					do {
						if ((read = bis.read()) == -1) {
							break label74;
						}

						++loc;
						if (read >= 240 || 128 <= read && read <= 191) {
							break label74;
						}

						if (192 <= read && read <= 223) {
							read = bis.read();
							continue label74;
						}
					} while (224 > read || read > 239);

					read = bis.read();
					if (128 <= read && read <= 191) {
						read = bis.read();
						if (128 <= read && read <= 191) {
							charset = "UTF-8";
						}
					}
					break;
				} while (128 <= read && read <= 191);
			}

			bis.close();
		} catch (Exception arg6) {
			arg6.printStackTrace();
		}

		return charset;
	}
}