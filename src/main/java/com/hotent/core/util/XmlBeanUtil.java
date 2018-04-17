package com.hotent.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXResult;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

public class XmlBeanUtil {
	public static Object unmarshall(String xml, Class clsToUnbound) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(new Class[]{clsToUnbound});
		return unmarshall(jc, xml);
	}

	public static String marshall(Object serObj, Class clsToBound) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(new Class[]{clsToBound});
		return marshall(jc, serObj);
	}

	public static String marshall(Object serObj, Class clsToBound, String[] cdataEls) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(new Class[]{clsToBound});
		return marshall(jc, serObj, cdataEls);
	}

	public static Object unmarshall(InputStream is, Class<?> clsToUnbound) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(new Class[]{clsToUnbound});
		return unmarshall(jc, is);
	}

	private static Object unmarshall(JAXBContext jc, InputStream is) throws JAXBException {
		Unmarshaller u = jc.createUnmarshaller();
		return u.unmarshal(is);
	}

	private static Object unmarshall(JAXBContext jc, String xml) throws JAXBException {
		Unmarshaller u = jc.createUnmarshaller();
		return u.unmarshal(new StringReader(xml));
	}

	private static String marshall(JAXBContext jc, Object serObj) throws JAXBException, PropertyException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Marshaller m = jc.createMarshaller();
		m.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
		m.setProperty("jaxb.encoding", System.getProperty("file.encoding"));
		m.marshal(serObj, out);
		return out.toString();
	}

	private static String marshall(JAXBContext jc, Object serObj, String[] cdataEls)
			throws JAXBException, PropertyException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Marshaller m = jc.createMarshaller();
		m.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
		m.setProperty("jaxb.encoding", System.getProperty("file.encoding"));
		XMLSerializer serializer = getXMLSerializer(cdataEls);
		serializer.setOutputByteStream(out);
		SAXResult result = null;

		try {
			result = new SAXResult(serializer.asContentHandler());
		} catch (IOException arg7) {
			arg7.printStackTrace();
		}

		m.marshal(serObj, result);
		return out.toString();
	}

	private static XMLSerializer getXMLSerializer(String[] aryProperty) {
		OutputFormat of = new OutputFormat();
		of.setCDataElements(aryProperty);
		of.setPreserveSpace(true);
		of.setIndenting(true);
		XMLSerializer serializer = new XMLSerializer(of);
		return serializer;
	}
}