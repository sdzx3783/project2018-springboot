package com.hotent.core.util;

import com.hotent.core.util.FileUtil;
import com.hotent.core.util.MyX509TrustManager;
import com.hotent.core.util.StringUtil;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtil {
	public static String sendData(String url, String data, String charset) {
		BufferedReader bufferedReader = null;

		try {
			URL uRL = new URL(url);
			URLConnection conn = uRL.openConnection();
			conn.setDoOutput(true);
			if (StringUtil.isNotEmpty(data)) {
				OutputStream e = conn.getOutputStream();
				e.write(data.getBytes(charset));
				e.flush();
				e.close();
			}

			bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer e1 = new StringBuffer();

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				e1.append(line);
			}

			bufferedReader.close();
			return e1.toString();
		} catch (MalformedURLException arg7) {
			arg7.printStackTrace();
			return "";
		} catch (IOException arg8) {
			arg8.printStackTrace();
			return "";
		}
	}

	public static String getContentByUrl(String url, String charset) throws ParseException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		if (StringUtil.isEmpty(charset)) {
			String contentEncoding = "iso-8859-1";
			Header line = response.getFirstHeader("Content-Type");
			String entity = line.getValue().toLowerCase();
			if (entity.indexOf("gbk") <= -1 && entity.indexOf("gb2312") <= -1 && entity.indexOf("gb18030") <= -1) {
				if (entity.indexOf("utf-8") > -1) {
					contentEncoding = "utf-8";
				} else if (entity.indexOf("big5") > -1) {
					contentEncoding = "big5";
				}
			} else {
				contentEncoding = "gb18030";
			}

			charset = contentEncoding;
		}

		Header contentEncoding1 = response.getFirstHeader("Content-Encoding");
		StatusLine line1 = response.getStatusLine();
		if (line1.getStatusCode() != 200) {
			return "";
		} else {
			HttpEntity entity1 = response.getEntity();
			Object is;
			if (contentEncoding1 != null && contentEncoding1.getValue().toLowerCase().equals("gzip")) {
				is = new GZIPInputStream(entity1.getContent());
			} else {
				is = entity1.getContent();
			}

			String str = FileUtil.inputStream2String((InputStream) is, charset);
			((InputStream) is).close();
			return str;
		}
	}

	public static String getContentByUrl(String url) throws ParseException, IOException {
		return getContentByUrl(url, "");
	}

	public static void saveRemoteFile(String remoteFile, String localFile) throws ParseException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(remoteFile);
		HttpResponse response = httpclient.execute(httpget);
		Header contentEncoding = response.getFirstHeader("Content-Encoding");
		StatusLine line = response.getStatusLine();
		if (line.getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			Object is;
			if (contentEncoding != null && contentEncoding.getValue().toLowerCase().equals("gzip")) {
				is = new GZIPInputStream(entity.getContent());
			} else {
				is = entity.getContent();
			}

			FileUtil.createFolder(localFile, true);
			FileOutputStream fs = new FileOutputStream(localFile);
			int bytesum = 0;
			boolean byteread = false;
			byte[] buffer = new byte[30000];

			int byteread1;
			while ((byteread1 = ((InputStream) is).read(buffer)) != -1) {
				bytesum += byteread1;
				fs.write(buffer, 0, byteread1);
			}

			((InputStream) is).close();
			fs.close();
		}

	}

	public static String sendHttpsRequest(String url, String params, String requestMethod) {
		String str = null;

		try {
			HttpsURLConnection conn = getHttpsConnection(url);
			conn.setRequestMethod(requestMethod);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			if (StringUtil.isNotEmpty(params)) {
				OutputStream e = conn.getOutputStream();
				e.write(params.getBytes("utf-8"));
				e.close();
			}

			str = getOutPut(conn);
			return str;
		} catch (KeyManagementException arg5) {
			throw new RuntimeException("远程服务器请求失败！" + arg5.getMessage(), arg5);
		} catch (NoSuchAlgorithmException arg6) {
			throw new RuntimeException("远程服务器请求失败！" + arg6.getMessage(), arg6);
		} catch (NoSuchProviderException arg7) {
			throw new RuntimeException("远程服务器请求失败！" + arg7.getMessage(), arg7);
		} catch (IOException arg8) {
			throw new RuntimeException("远程服务器请求失败！" + arg8.getMessage(), arg8);
		}
	}

	public static HttpsURLConnection getHttpsConnection(String accessUrl)
			throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
		URL url = new URL(accessUrl);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		TrustManager[] tm = new TrustManager[]{new MyX509TrustManager()};
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init((KeyManager[]) null, tm, new SecureRandom());
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		connection.setSSLSocketFactory(ssf);
		return connection;
	}

	public static String getOutPut(HttpsURLConnection conn) throws IOException {
		InputStream inputStream = conn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuffer buffer = new StringBuffer();
		String str = null;

		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}

		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		conn.disconnect();
		return buffer.toString();
	}

	public static String uploadFile(String url, String filePath) throws IOException {
		String result = null;
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			URL urlObj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
			con.setRequestMethod("POST");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			byte[] head = sb.toString().getBytes("utf-8");
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.write(head);
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			boolean bytes = false;
			byte[] bufferOut = new byte[1024];

			int bytes1;
			while ((bytes1 = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes1);
			}

			in.close();
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");
			out.write(foot);
			out.flush();
			out.close();
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String e = null;

				while ((e = reader.readLine()) != null) {
					buffer.append(e);
				}

				if (result == null) {
					result = buffer.toString();
				}
			} catch (IOException arg19) {
				System.out.println("发送POST请求出现异常！" + arg19);
				arg19.printStackTrace();
				throw new IOException("数据读取异常");
			} finally {
				if (reader != null) {
					reader.close();
				}

			}

			return result;
		} else {
			throw new IOException("文件不存在");
		}
	}
}