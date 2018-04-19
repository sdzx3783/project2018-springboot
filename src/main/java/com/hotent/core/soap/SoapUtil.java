package com.hotent.core.soap;

import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.soap.SoapUtil.1;
import com.hotent.core.soap.SoapUtil.InvokeException;
import com.hotent.core.soap.SoapUtil.RequestBuilder;
import com.hotent.core.soap.SoapUtil.ResponseBuilder;
import com.hotent.core.util.StringUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import sun.misc.BASE64Encoder;

public class SoapUtil {
	private static Logger logger = LoggerFactory.getLogger(SoapUtil.class);
	private static Integer _connTimeout = Integer.valueOf(0);
	private static Integer _readTimeout = Integer.valueOf(0);
	private static GroovyScriptEngine engine = new GroovyScriptEngine();

	private static Object getWholeObject(Map variables, String binding) throws Exception {
		Object obj = null;
		Pattern regex = Pattern.compile("(\\w*)\\..*");
		Matcher regexMatcher = regex.matcher(binding);
		if (regexMatcher.find()) {
			String varKey = regexMatcher.group(1);
			obj = variables.get(varKey);
		} else {
			obj = PropertyUtils.getProperty(variables, binding);
		}

		return obj;
	}

	private static void checkFault(SOAPMessage message) throws SOAPException, InvokeException {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		SOAPBody body = envelope.getBody();
		SOAPFault fault = body.getFault();
		if (fault != null && fault.getFaultCode() != null) {
			throw new InvokeException(fault.getFaultCode(), fault.getFaultString());
		}
	}

	private static String getAttribute(Node node, String name) {
		Node tmp = node.getAttributes().getNamedItem(name);
		return tmp != null ? tmp.getTextContent() : null;
	}

	private static SOAPMessage invoke(URL invokeURL, SOAPMessage request) throws Exception {
      SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
      SOAPConnection connection = null;

      SOAPMessage arg5;
      try {
         URL ex = new URL((URL)null, invokeURL.toString(), new 1());
         connection = soapConnFactory.createConnection();
         SOAPMessage reply = connection.call(request, ex);
         arg5 = reply;
      } catch (Exception arg9) {
         throw arg9;
      } finally {
         if(connection != null) {
            connection.close();
         }

      }

      return arg5;
   }

	public static SOAPMessage execute(Map variables, JSONObject jObject) throws Exception {
		JSONArray inputs = jObject.getJSONArray("inputs");
		JSONArray inputParams = null;
		if (jObject.containsKey("inputParams")) {
			inputParams = jObject.getJSONArray("inputParams");
		}

		String url = jObject.getString("url");
		String namespace = jObject.getString("namespace");
		String method = jObject.getString("method");
		String soapaction = "";
		if (jObject.containsKey("soapaction")) {
			soapaction = jObject.getString("soapaction");
		}

		if (!StringUtil.isEmpty(url) && !StringUtil.isEmpty(namespace) && !StringUtil.isEmpty(method)) {
			SOAPMessage requestMessage = RequestBuilder.build(inputs, inputParams, namespace, method, variables,
					soapaction);
			SOAPMessage responseMessage = invoke(new URL(url), requestMessage);
			checkFault(responseMessage);
			return responseMessage;
		} else {
			throw new Exception("没有获取到webservice的调用地址、名称空间或调用方法.");
		}
	}

	public static void invoke(Map variables, JSONArray jArray) throws Exception {
		if (jArray.size() == 0) {
			logger.warn("没有找到webservice的调用配置.", jArray);
		} else {
			try {
				Iterator e = jArray.iterator();

				while (true) {
					while (e.hasNext()) {
						Object obj = e.next();
						JSONObject jObject = (JSONObject) obj;
						JSONArray inputs = jObject.getJSONArray("inputs");
						JSONArray outputs = jObject.getJSONArray("outputs");
						JSONArray inputParams = null;
						if (jObject.containsKey("inputParams")) {
							inputParams = jObject.getJSONArray("inputParams");
						}

						String url = jObject.getString("url");
						String namespace = jObject.getString("namespace");
						String method = jObject.getString("method");
						String soapaction = "";
						if (jObject.containsKey("soapaction")) {
							soapaction = jObject.getString("soapaction");
						}

						if (!StringUtil.isEmpty(url) && !StringUtil.isEmpty(namespace) && !StringUtil.isEmpty(method)) {
							SOAPMessage requestMessage = RequestBuilder.build(inputs, inputParams, namespace, method,
									variables, soapaction);
							SOAPMessage responseMessage = invoke(new URL(url), requestMessage);
							ResponseBuilder.build(variables, outputs, responseMessage);
						} else {
							logger.warn("没有获取到webservice的调用地址、名称空间或调用方法.", jObject);
						}
					}

					return;
				}
			} catch (Exception arg13) {
				logger.error("调用webservice出错.", arg13);
				throw arg13;
			}
		}
	}

	private static String getXFDLString() throws IOException {
		File file = new File("D:\\dev\\bpmx3\\contract.xfdl");
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] temp = new byte[1024];
		Object buf = null;
		boolean length = false;

		int length1;
		while ((length1 = fis.read(temp, 0, 1024)) != -1) {
			bout.write(temp, 0, length1);
		}

		byte[] buf1 = bout.toByteArray();
		BASE64Encoder sunEncoder = new BASE64Encoder();
		String str = sunEncoder.encodeBuffer(buf1).toString();
		return str;
	}
}