package com.hotent.core.util;

import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.TimeUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXValidator;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.XMLErrorHandler;
import org.xml.sax.SAXException;

public class Dom4jUtil {
	private static final Log logger = LogFactory.getLog(Dom4jUtil.class);

	public static Document loadXml(String s) {
		Document document = null;

		try {
			document = DocumentHelper.parseText(s);
		} catch (Exception arg2) {
			arg2.printStackTrace();
		}

		return document;
	}

	public static Document load(String filename, String encode) {
		Document document = null;

		try {
			SAXReader ex = new SAXReader();
			ex.setEncoding(encode);
			document = ex.read(new File(filename));
		} catch (Exception arg3) {
			;
		}

		return document;
	}

	public static Document loadXml(String xml, String encode) throws UnsupportedEncodingException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes(encode));
		return loadXml((InputStream) inputStream, encode);
	}

	public static Document loadXml(InputStream is) {
		return loadXml(is, "utf-8");
	}

	public static Document loadXml(InputStream is, String charset) {
		Document document = null;

		try {
			SAXReader ex = new SAXReader();
			ex.setEncoding(charset);
			document = ex.read(is);
		} catch (Exception arg3) {
			arg3.printStackTrace();
		}

		return document;
	}

	public static void write(Document document, String fileName) throws IOException {
		String xml = document.asXML();
		FileUtil.writeFile(fileName, xml);
	}

	public static void write(String str, String fileName) throws IOException, DocumentException {
		Document document = DocumentHelper.parseText(str);
		write(document, fileName);
	}

	public Document load(URL url) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(url);
		return document;
	}

	public static Document load(String filename) {
		Document document = null;

		try {
			SAXReader ex = new SAXReader();
			document = ex.read(new File(filename));
			document.normalize();
		} catch (Exception arg2) {
			arg2.printStackTrace();
		}

		return document;
	}

	public static String transFormXsl(String xml, String xsl, Map<String, String> map) throws Exception {
		StringReader xmlReader = new StringReader(xml);
		StringReader xslReader = new StringReader(xsl);
		System.setProperty("javax.xml.transform.TransformerFactory",
				"org.apache.xalan.processor.TransformerFactoryImpl");
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(new StreamSource(xslReader));
		if (map != null) {
			Iterator xmlSource = map.entrySet().iterator();

			while (xmlSource.hasNext()) {
				Entry writer = (Entry) xmlSource.next();
				transformer.setParameter((String) writer.getKey(), writer.getValue());
			}
		}

		StreamSource xmlSource1 = new StreamSource(xmlReader);
		StringWriter writer1 = new StringWriter();
		StreamResult result = new StreamResult(writer1);
		transformer.transform(xmlSource1, result);
		return writer1.toString();
	}

	public static String transXmlByXslt(String xml, String xslPath, Map<String, String> map) throws Exception {
		Document document = loadXml(xml);
		document.setXMLEncoding("UTF-8");
		Document result = styleDocument(document, xslPath, map);
		return docToString(result);
	}

	public static String transFileXmlByXslt(String xmlPath, String xslPath, Map<String, String> map) throws Exception {
		Document document = load(xmlPath);
		document.setXMLEncoding("UTF-8");
		Document result = styleDocument(document, xslPath, map);
		return docToString(result);
	}

	public static String docToString(Document document) {
		String s = "";

		try {
			ByteArrayOutputStream ex = new ByteArrayOutputStream();
			OutputFormat format = new OutputFormat("  ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(ex, format);
			writer.write(document);
			s = ex.toString("UTF-8");
		} catch (Exception arg4) {
			logger.error("docToString error:" + arg4.getMessage());
		}

		return s;
	}

	public static String docToPrettyString(Document document) {
		return docToPrettyString(document, true);
	}

	public static String docToPrettyString(Document document, boolean removeHead) {
		String result = "";

		try {
			StringWriter ex = new StringWriter();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setSuppressDeclaration(removeHead);
			XMLWriter xmlWriter = new XMLWriter(ex, format);
			xmlWriter.write(document);
			result = ex.toString();
		} catch (Exception arg5) {
			logger.error("docToString error:" + arg5.getMessage());
		}

		return result;
	}

	public static Document styleDocument(Document document, String stylesheet, Map<String, String> map)
			throws Exception {
		System.setProperty("javax.xml.transform.TransformerFactory",
				"org.apache.xalan.processor.TransformerFactoryImpl");
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(new StreamSource(stylesheet));
		if (map != null) {
			Iterator source = map.entrySet().iterator();

			while (source.hasNext()) {
				Entry result = (Entry) source.next();
				transformer.setParameter((String) result.getKey(), result.getValue());
			}
		}

		DocumentSource source1 = new DocumentSource(document);
		DocumentResult result1 = new DocumentResult();
		transformer.transform(source1, result1);
		Document transformedDoc = result1.getDocument();
		return transformedDoc;
	}

	public static String validXmlBySchema(String xml, String schema) {
		String result = "";

		try {
			XMLErrorHandler ex = new XMLErrorHandler();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(true);
			factory.setNamespaceAware(true);
			SAXParser parser = factory.newSAXParser();
			SAXReader xmlReader = new SAXReader();
			Document xmlDocument = xmlReader.read(new File(xml));
			parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
					"http://www.w3.org/2001/XMLSchema");
			parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", "file:" + schema);
			SAXValidator validator = new SAXValidator(parser.getXMLReader());
			validator.setErrorHandler(ex);
			validator.validate(xmlDocument);
			new XMLWriter(OutputFormat.createPrettyPrint());
			if (ex.getErrors().hasContent()) {
				result = "<result success=\'0\'>XML文件通过XSD文件校验失败,请检查xml是否符合指定格式!</result>";
			} else {
				result = "<result success=\'1\'>XML文件通过XSD文件校验成功!</result>";
			}
		} catch (Exception arg9) {
			result = "<result success=\'0\'>XML文件通过XSD文件校验失败:" + arg9.getMessage() + "</result>";
		}

		return result;
	}

	public static boolean validByXsd(String xsdPath, InputStream xmlData) {
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		File schemaFile = new File(xsdPath);
		Schema schema = null;

		try {
			schema = schemaFactory.newSchema(schemaFile);
		} catch (SAXException arg8) {
			arg8.printStackTrace();
		}

		Validator validator = schema.newValidator();
		StreamSource source = new StreamSource(xmlData);

		try {
			validator.validate(source);
			return true;
		} catch (Exception arg7) {
			logger.info(arg7.getMessage());
			return false;
		}
	}

	public static String getString(Element element, String attrName) {
		return getString(element, attrName, Boolean.valueOf(false));
	}

	public static String getString(Element element, String attrName, Boolean fuzzy) {
		if (BeanUtils.isEmpty(element)) {
			return null;
		} else {
			String val = element.attributeValue(attrName);
			if (StringUtil.isEmpty(val)) {
				return null;
			} else {
				if (fuzzy.booleanValue()) {
					val = "%" + val + "%";
				}

				return val;
			}
		}
	}

	public static void addAttribute(Element element, String attrName, Object val) {
		addAttribute(element, attrName, val, "yyyy-MM-dd HH:mm:ss");
	}

	public static void addAttribute(Element element, String attrName, Object val, String format) {
		if (!BeanUtils.isEmpty(val)) {
			if (val instanceof Date) {
				String dateStr = TimeUtil.getDateTimeString((Date) val, format);
				element.addAttribute(attrName, dateStr);
			} else {
				element.addAttribute(attrName, val.toString());
			}

		}
	}
}