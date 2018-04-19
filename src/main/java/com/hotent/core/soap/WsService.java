package com.hotent.core.soap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WsService {
	private String invokeURL = "";
	private String namespace = "";

	public WsService(String invokeURL, String namespace) {
		this.invokeURL = invokeURL;
		this.namespace = namespace;
	}

	private static String buildSoapMsg(String namespace, Boolean hasSoapAction, String method, String param) {
		StringBuffer sb = new StringBuffer();
		sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:impl=\"");
		sb.append(namespace);
		sb.append("\"><soapenv:Header/><soapenv:Body>");
		sb.append("<impl:");
		sb.append(method);
		sb.append(">");
		sb.append(param);
		sb.append("</impl:");
		sb.append(method);
		sb.append("></soapenv:Body></soapenv:Envelope>");
		String soapMsg = sb.toString();
		if (hasSoapAction.booleanValue()) {
			soapMsg = soapMsg.replace("$_param_prefix_$", "impl:");
		} else {
			soapMsg = soapMsg.replace("$_param_prefix_$", "");
		}

		return soapMsg;
	}

	private String doInvoke(String method, String param) throws IOException {
		OutputStream outputStreamWriter = null;
		BufferedReader bufferedReader = null;

		try {
			String e = "";
			String soapMsg = buildSoapMsg(this.namespace, Boolean.valueOf(false), method, param);
			URL url = new URL(this.invokeURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
			byte[] sendContents = soapMsg.getBytes("utf-8");
			conn.setRequestProperty("Content-Length", String.valueOf(sendContents.length));
			conn.setRequestProperty("User-Agent", "");
			if (e != "") {
				conn.setRequestProperty("SOAPAction", e);
			}

			outputStreamWriter = conn.getOutputStream();
			outputStreamWriter.write(sendContents, 0, sendContents.length);
			outputStreamWriter.flush();
			outputStreamWriter.close();
			bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			StringBuffer response = new StringBuffer();

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				response.append(line);
			}

			String xml = response.toString();
			String arg12 = xml;
			return arg12;
		} catch (Exception arg16) {
			arg16.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}

		}

		return "";
	}

	public String doInvoke(String method, Map<String, String> param) {
		StringBuffer paramSb = new StringBuffer();
		Iterator e = param.keySet().iterator();

		while (e.hasNext()) {
			String key = (String) e.next();
			String val = (String) param.get(key);
			paramSb.append("<" + key + ">");
			paramSb.append(val);
			paramSb.append("</" + key + ">");
		}

		try {
			return this.doInvoke(method, paramSb.toString());
		} catch (Exception arg6) {
			return "";
		}
	}

	public static void main(String[] args) {
		String invokeURL = "http://192.168.1.231:8080/bpm_zw/service/UserOrgService";
		String namespace = "http://impl.webservice.platform.hotent.com/";
		WsService service = new WsService(invokeURL, namespace);
		HashMap param = new HashMap();
		param.put("account", "123456");
		service.doInvoke("deleteUser", (Map) param);
	}
}