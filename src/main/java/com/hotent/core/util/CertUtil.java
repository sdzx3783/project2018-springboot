package com.hotent.core.util;

import com.hotent.core.util.CertUtil.SavingTrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CertUtil {
	public static Log logger = LogFactory.getLog(CertUtil.class);
	private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();

	public static void main(String[] args) {
		File file = get("smtp.gmail.com", 465);
		logger.info(file.getAbsolutePath());
	}

	public static File get(String host, int port) {
		FileInputStream in = null;
		SSLSocket socket = null;
		FileOutputStream out = null;

		File file;
		try {
			char[] e = "changeit".toCharArray();
			file = new File("jssecacerts");
			if (!file.isFile()) {
				char e1 = File.separatorChar;
				File context = new File(System.getProperty("java.home") + e1 + "lib" + e1 + "security");
				file = new File(context, "jssecacerts");
				if (!file.isFile()) {
					file = new File(context, "cacerts");
				}
			}

			in = new FileInputStream(file);
			KeyStore arg53 = KeyStore.getInstance(KeyStore.getDefaultType());
			arg53.load(in, e);
			SSLContext arg54 = SSLContext.getInstance("TLS");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(arg53);
			X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
			SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
			arg54.init((KeyManager[]) null, new TrustManager[]{tm}, (SecureRandom) null);
			SSLSocketFactory factory = arg54.getSocketFactory();
			socket = (SSLSocket) factory.createSocket(host, port);
			socket.setSoTimeout(10000);
			if (socket != null) {
				socket.startHandshake();
			}

			X509Certificate[] chain = SavingTrustManager.access$000(tm);
			MessageDigest sha1;
			if (chain != null) {
				sha1 = MessageDigest.getInstance("SHA1");
				MessageDigest md5 = MessageDigest.getInstance("MD5");

				X509Certificate cert;
				for (int k = 0; k < chain.length; ++k) {
					cert = chain[k];
					sha1.update(cert.getEncoded());
					md5.update(cert.getEncoded());
				}

				byte arg55 = 0;
				cert = chain[arg55];
				String alias = host + "-" + (arg55 + 1);
				arg53.setCertificateEntry(alias, cert);
				File cafile = new File("jssecacerts");
				out = new FileOutputStream(cafile);
				arg53.store(out, e);
				logger.debug(">>>>   Added certificate to keystore \'jssecacerts\' using alias \'" + alias + "\'");
				File arg19 = cafile;
				return arg19;
			}

			sha1 = null;
			return sha1;
		} catch (SSLException arg44) {
			logger.debug("明文连接,javax.net.ssl.SSLException:" + arg44.getMessage());
			file = null;
			return file;
		} catch (KeyStoreException arg45) {
			arg45.printStackTrace();
			file = null;
		} catch (FileNotFoundException arg46) {
			arg46.printStackTrace();
			file = null;
			return file;
		} catch (NoSuchAlgorithmException arg47) {
			arg47.printStackTrace();
			file = null;
			return file;
		} catch (SocketTimeoutException arg48) {
			logger.debug("邮件发送超时");
			file = null;
			return file;
		} catch (CertificateException arg49) {
			arg49.printStackTrace();
			file = null;
			return file;
		} catch (IOException arg50) {
			arg50.printStackTrace();
			file = null;
			return file;
		} catch (KeyManagementException arg51) {
			arg51.printStackTrace();
			file = null;
			return file;
		} finally {
			logger.info("关闭连接...");

			try {
				if (in != null) {
					in.close();
				}

				if (socket != null) {
					socket.close();
				}

				if (out != null) {
					out.close();
				}
			} catch (IOException arg43) {
				arg43.printStackTrace();
				logger.info("关闭连接失败!");
			}

		}

		return file;
	}

	private static String toHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 3);
		byte[] arr$ = bytes;
		int len$ = bytes.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			byte b = arr$[i$];
			int arg5 = b & 255;
			sb.append(HEXDIGITS[arg5 >> 4]);
			sb.append(HEXDIGITS[arg5 & 15]);
			sb.append(' ');
		}

		return sb.toString();
	}
}