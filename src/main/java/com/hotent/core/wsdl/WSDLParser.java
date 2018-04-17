package com.hotent.core.wsdl;

import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.wsdl.OperationInfo;
import com.hotent.core.wsdl.ParameterInfo;
import com.hotent.core.wsdl.ServiceInfo;
import com.ibm.wsdl.ImportImpl;
import com.ibm.wsdl.ServiceImpl;
import com.ibm.wsdl.extensions.schema.SchemaImpl;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.wsdl.Binding;
import javax.wsdl.BindingOperation;
import javax.wsdl.Definition;
import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Output;
import javax.wsdl.Part;
import javax.wsdl.Port;
import javax.wsdl.Types;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.http.HTTPAddress;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPOperation;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.apache.ws.commons.schema.XmlSchemaComplexContentExtension;
import org.apache.ws.commons.schema.XmlSchemaComplexType;
import org.apache.ws.commons.schema.XmlSchemaContent;
import org.apache.ws.commons.schema.XmlSchemaContentModel;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaImport;
import org.apache.ws.commons.schema.XmlSchemaObject;
import org.apache.ws.commons.schema.XmlSchemaParticle;
import org.apache.ws.commons.schema.XmlSchemaSequence;
import org.apache.ws.commons.schema.XmlSchemaSequenceMember;
import org.apache.ws.commons.schema.XmlSchemaType;
import org.apache.ws.commons.schema.utils.XmlSchemaRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class WSDLParser {
	protected static Logger logger = LoggerFactory.getLogger(WSDLParser.class);
	private Map<String, XmlSchemaType> complexTypes = new HashMap();
	private String currentNameSpace = "";
	private Map<String, ServiceInfo> services = new HashMap();

	private static void getParam(ParameterInfo parameterInfo) {
		if (parameterInfo.getIsComplext() == ParameterInfo.COMPLEX_YES) {
			if (!"parameters".equals(parameterInfo.getName())) {
				logger.info("--" + parameterInfo.getType() + "复杂类型开始:--");
			}

			Map tempMap = parameterInfo.getComplextParams();
			Set keys = tempMap.keySet();
			Iterator otheriterator = keys.iterator();

			while (otheriterator.hasNext()) {
				Object key = otheriterator.next();
				ParameterInfo parameter = (ParameterInfo) tempMap.get(key);
				getParam(parameter);
			}

			if (!"parameters".equals(parameterInfo.getName())) {
				logger.info("--" + parameterInfo.getType() + "复杂类型end:--");
			}
		} else {
			logger.info(
					"  --inputparamName:" + parameterInfo.getName() + "  --inputparamType:" + parameterInfo.getType());
		}

	}

	public static void main(String[] args) {
		String wsdlURI = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl";

		try {
			WSDLParser e1 = new WSDLParser(wsdlURI);
			Collection serviceInfos = e1.getServices().values();
			Iterator it = serviceInfos.iterator();

			while (it.hasNext()) {
				ServiceInfo serviceInfo = (ServiceInfo) it.next();
				Map operationList = serviceInfo.getOperations();
				Set keys = operationList.keySet();
				Iterator iterator = keys.iterator();

				while (iterator.hasNext()) {
					Object key = iterator.next();
					System.out.println(key);
					OperationInfo info = (OperationInfo) operationList.get(key);
					List inputParams = info.getInputParams();
					Iterator it1 = inputParams.iterator();

					while (it1.hasNext()) {
						ParameterInfo tempinfo = (ParameterInfo) it1.next();
						getParam(tempinfo);
					}
				}
			}
		} catch (Exception arg13) {
			arg13.printStackTrace();
		}

	}

	public WSDLParser(String wsdlURI) throws WSDLException {
		this.parseWSDL(wsdlURI);
	}

	private boolean containOperation(ServiceInfo serviceInfo, OperationInfo operationInfo) {
		Map operations = serviceInfo.getOperations();
		return operations.get(operationInfo.getOperationName()) != null;
	}

	private boolean containService(String serviceName) {
		return this.services.get(serviceName) != null;
	}

	private boolean containType(XmlSchemaType type) {
		String name = type.getName();
		return name != null && this.complexTypes.get(name) != null;
	}

	public Map<String, XmlSchemaType> getComplexTypes() {
		return this.complexTypes;
	}

	public String getCurrentNameSpace() {
		return this.currentNameSpace;
	}

	public Map<String, ServiceInfo> getServices() {
		return this.services;
	}

	private void parseWSDL(String wsdlURI) throws WSDLException {
		WSDLFactory wsdlFactory = WSDLFactory.newInstance();
		WSDLReader reader = wsdlFactory.newWSDLReader();
		Definition defintion = reader.readWSDL(wsdlURI);
		this.processImport(defintion);
		this.processTypes(defintion);
		Map servicesMap = defintion.getAllServices();
		Set serviceKeys = servicesMap.keySet();
		Iterator iterator = serviceKeys.iterator();

		while (iterator.hasNext()) {
			Object key = iterator.next();
			ServiceImpl service = (ServiceImpl) servicesMap.get(key);
			this.processService(service, wsdlURI);
		}

	}

	private boolean processComplex(ParameterInfo parameterInfo, String typeName, String partName, String attrName,
			Boolean isList) {
		if (typeName == null) {
			return false;
		} else {
			XmlSchemaType xmlSchemaType = (XmlSchemaType) this.complexTypes.get(typeName);
			if (xmlSchemaType == null) {
				return false;
			} else {
				ParameterInfo complexType = new ParameterInfo();
				complexType.setType(typeName);
				complexType.setIsComplext(ParameterInfo.COMPLEX_YES);
				complexType.setIsList(isList);
				if (typeName.contains("Response") || partName.equals(typeName)) {
					complexType.setIsShow(ParameterInfo.SHOW_NO);
				}

				XmlSchemaParticle xmlSchemaParticle = ((XmlSchemaComplexType) xmlSchemaType).getParticle();
				if (xmlSchemaParticle == null) {
					return false;
				} else {
					List xmlSchemaObjectCollection = ((XmlSchemaSequence) xmlSchemaParticle).getItems();
					int count = xmlSchemaObjectCollection.size();

					for (int j = 0; j < count; ++j) {
						XmlSchemaSequenceMember xmlSchemaObject = (XmlSchemaSequenceMember) xmlSchemaObjectCollection
								.get(j);
						if (xmlSchemaObject instanceof XmlSchemaElement) {
							XmlSchemaElement xmlSchemaElement = (XmlSchemaElement) xmlSchemaObject;
							long max = xmlSchemaElement.getMaxOccurs();
							String elementName = xmlSchemaElement.getName();
							XmlSchemaType xmlSType = xmlSchemaElement.getSchemaType();
							if (xmlSType instanceof XmlSchemaComplexType) {
								String simpleType = xmlSType.getName();
								complexType.setParentComplext(parameterInfo.getParentComplext());
								if (StringUtil.isNotEmpty(simpleType)) {
									if (parameterInfo.getParentComplext().get(xmlSType.getName()) != null) {
										break;
									}

									this.processComplex(complexType, xmlSType.getName(), partName, elementName,
											Boolean.valueOf(max > 1L));
								} else {
									complexType.getParentComplext().put(elementName, elementName);
									xmlSType.setName(elementName);
									this.processComplexType(xmlSType);
									this.processComplex(complexType, elementName, partName, elementName,
											Boolean.valueOf(false));
								}
							} else {
								String t;
								if (xmlSchemaElement.isRef()) {
									XmlSchemaRef arg19 = xmlSchemaElement.getRef();
									t = arg19.getTargetQName().getLocalPart();
									this.processComplex(complexType, t, partName, elementName, Boolean.valueOf(false));
								} else {
									ParameterInfo arg20 = new ParameterInfo();
									arg20.setName(elementName);
									t = xmlSType.getName();
									if (max > 1L) {
										t = "List{" + t + "}";
									}

									arg20.setType(t);
									complexType.getComplextParams().put(elementName, arg20);
								}
							}
						}
					}

					if (StringUtil.isEmpty(attrName)) {
						parameterInfo.getComplextParams().put(typeName, complexType);
					} else {
						parameterInfo.getComplextParams().put(attrName, complexType);
					}

					return true;
				}
			}
		}
	}

	private void processComplexType(XmlSchemaType type) {
		String typeName = type.getName();
		if (type instanceof XmlSchemaComplexType) {
			if (!this.containType(type)) {
				this.complexTypes.put(typeName, type);
				XmlSchemaComplexType xmlSchemaComplexType = (XmlSchemaComplexType) type;
				XmlSchemaContentModel xmlSchemaContentModel = xmlSchemaComplexType.getContentModel();
				XmlSchemaParticle xmlSchemaParticle = xmlSchemaComplexType.getParticle();
				if (xmlSchemaParticle == null && xmlSchemaContentModel != null) {
					XmlSchemaContent xmlSchemaSequence = xmlSchemaContentModel.getContent();
					if (xmlSchemaSequence instanceof XmlSchemaComplexContentExtension) {
						XmlSchemaComplexContentExtension xmlSchemaObjectCollection = (XmlSchemaComplexContentExtension) xmlSchemaSequence;
						XmlSchemaParticle count = xmlSchemaObjectCollection.getParticle();
						xmlSchemaComplexType.setParticle(count);
					}
				}

				if (xmlSchemaParticle instanceof XmlSchemaSequence) {
					XmlSchemaSequence arg12 = (XmlSchemaSequence) xmlSchemaParticle;
					List arg13 = arg12.getItems();
					int arg14 = arg13.size();

					for (int i = 0; i < arg14; ++i) {
						XmlSchemaSequenceMember xmlSchemaObject = (XmlSchemaSequenceMember) arg13.get(i);
						if (xmlSchemaObject instanceof XmlSchemaElement) {
							XmlSchemaElement xmlSchemaElement = (XmlSchemaElement) xmlSchemaObject;
							XmlSchemaType xmlSchemaType = xmlSchemaElement.getSchemaType();
							if (xmlSchemaType != null && xmlSchemaType instanceof XmlSchemaComplexType) {
								this.processComplexType(xmlSchemaType);
							}
						}
					}

				}
			}
		}
	}

	private void processImport(Definition defintion) throws WSDLException {
		this.currentNameSpace = defintion.getTargetNamespace();
		Map impMap = defintion.getImports();
		Iterator keys = impMap.keySet().iterator();

		while (keys.hasNext()) {
			Object key = keys.next();
			Vector importImpls = (Vector) impMap.get(key);
			ImportImpl imp = (ImportImpl) importImpls.elementAt(0);
			this.currentNameSpace = imp.getNamespaceURI();
			this.parseWSDL(imp.getLocationURI());
		}

	}

	private void processInputParam(OperationInfo operationInfo, Input input) {
		List inputParams = operationInfo.getInputParams();
		Message message = input.getMessage();
		Map partMap = message.getParts();
		this.processParam(operationInfo, partMap, inputParams);
	}

	private void processOperation(ServiceInfo serviceInfo, BindingOperation bindingOperation) {
		Operation operation = bindingOperation.getOperation();
		String operationName = operation.getName();
		OperationInfo operationInfo = new OperationInfo();
		operationInfo.setOperationName(operationName);
		if (!this.containOperation(serviceInfo, operationInfo)) {
			serviceInfo.getOperations().put(operationName, operationInfo);
		}

		List extensions = bindingOperation.getExtensibilityElements();
		if (extensions != null) {
			for (int input = 0; input < extensions.size(); ++input) {
				ExtensibilityElement output = (ExtensibilityElement) extensions.get(input);
				if (output instanceof SOAPOperation) {
					SOAPOperation soapOp = (SOAPOperation) output;
					String soapUri = soapOp.getSoapActionURI();
					operationInfo.setInputAction(soapUri);
				}
			}
		}

		Input arg10 = operation.getInput();
		this.processInputParam(operationInfo, arg10);
		Output arg11 = operation.getOutput();
		this.processOutputParam(operationInfo, arg11);
	}

	private void processOutputParam(OperationInfo operationInfo, Output output) {
		if (output != null) {
			List outputParams = operationInfo.getOutputParams();
			Message message = output.getMessage();
			Map partMap = message.getParts();
			this.processParam(operationInfo, partMap, outputParams);
		}
	}

	private void processParam(OperationInfo operationInfo, Map<?, ?> partMap, List<ParameterInfo> params) {
		Collection parts = partMap.values();

		ParameterInfo parameterInfo;
		for (Iterator iterator = parts.iterator(); iterator.hasNext(); params.add(parameterInfo)) {
			Part part = (Part) iterator.next();
			String partName = part.getName();
			parameterInfo = new ParameterInfo();
			String typeName = null;
			QName qName = part.getTypeName();
			if (qName != null) {
				typeName = qName.getLocalPart();
			} else {
				typeName = part.getElementName().getLocalPart();
			}

			parameterInfo.setName(partName);
			parameterInfo.setType(typeName);
			if ("parameters".equals(partName)) {
				parameterInfo.setIsShow(ParameterInfo.SHOW_NO);
			}

			String operationName = operationInfo.getOperationName();
			if (this.processComplex(parameterInfo, typeName, operationName, (String) null, Boolean.valueOf(false))) {
				parameterInfo.setIsComplext(ParameterInfo.COMPLEX_YES);
			}
		}

	}

	private void processService(ServiceImpl service, String wsdlUrl) {
		String serviceName = service.getQName().getLocalPart();
		if (!this.containService(serviceName)) {
			ServiceInfo serviceInfo = new ServiceInfo();
			serviceInfo.setWsdlUrl(wsdlUrl);
			String invokeUrl = wsdlUrl;
			if (wsdlUrl.matches(".*\\?(?i)wsdl$")) {
				invokeUrl = wsdlUrl.substring(0, wsdlUrl.lastIndexOf("?"));
			}

			serviceInfo.setHttpAddress(invokeUrl);
			serviceInfo.setName(serviceName);
			serviceInfo.setTargetNamespace(this.currentNameSpace);
			this.services.put(serviceName, serviceInfo);
			Collection ports = service.getPorts().values();
			Iterator iterator = ports.iterator();

			while (iterator.hasNext()) {
				Port port = (Port) iterator.next();
				List list = port.getExtensibilityElements();
				Iterator binding = list.iterator();

				while (binding.hasNext()) {
					Object operations = binding.next();
					String bindingOperation;
					if (operations instanceof HTTPAddress) {
						HTTPAddress iterator2 = (HTTPAddress) operations;
						bindingOperation = iterator2.getLocationURI();
						serviceInfo.setHttpAddress(bindingOperation);
					}

					if (operations instanceof SOAPAddress) {
						SOAPAddress iterator21 = (SOAPAddress) operations;
						bindingOperation = iterator21.getLocationURI();
						serviceInfo.setHttpAddress(bindingOperation);
					}
				}

				Binding binding1 = port.getBinding();
				List operations1 = binding1.getBindingOperations();
				Iterator iterator22 = operations1.iterator();

				while (iterator22.hasNext()) {
					BindingOperation bindingOperation1 = (BindingOperation) iterator22.next();
					this.processOperation(serviceInfo, bindingOperation1);
				}
			}

		}
	}

	private void processTypes(Definition defintion) {
		XmlSchemaCollection xmlSchemaCollection = new XmlSchemaCollection();
		Types types = defintion.getTypes();
		if (types != null) {
			List list = types.getExtensibilityElements();
			Iterator iterator = list.iterator();

			while (iterator.hasNext()) {
				SchemaImpl schemaImpl = (SchemaImpl) iterator.next();
				Element element = schemaImpl.getElement();
				XmlSchema xmlSchema = xmlSchemaCollection.read(element);
				this.processXmlSchema(xmlSchema);
			}

		}
	}

	private void processXmlSchema(XmlSchema xmlSchema) {
		if (!BeanUtils.isEmpty(xmlSchema)) {
			Map smlSchemaObjectTable = xmlSchema.getSchemaTypes();
			List xmlSchemaObjs = xmlSchema.getItems();
			Set smlSchemaKeys = smlSchemaObjectTable.keySet();
			Iterator i$ = smlSchemaKeys.iterator();

			while (i$.hasNext()) {
				Object xmlSchemaObj = i$.next();
				XmlSchemaType xmlSchemaElement = (XmlSchemaType) smlSchemaObjectTable.get(xmlSchemaObj);
				this.processComplexType(xmlSchemaElement);
			}

			i$ = xmlSchemaObjs.iterator();

			while (i$.hasNext()) {
				XmlSchemaObject xmlSchemaObj1 = (XmlSchemaObject) i$.next();
				if (xmlSchemaObj1 instanceof XmlSchemaImport) {
					XmlSchemaImport xmlSchemaElement1 = (XmlSchemaImport) xmlSchemaObj1;
					XmlSchema schemaName = xmlSchemaElement1.getSchema();
					this.processXmlSchema(schemaName);
				}

				if (xmlSchemaObj1 instanceof XmlSchemaElement) {
					XmlSchemaElement xmlSchemaElement2 = (XmlSchemaElement) xmlSchemaObj1;
					String schemaName1 = xmlSchemaElement2.getName();
					XmlSchemaType typevalue = xmlSchemaElement2.getSchemaType();
					typevalue.setName(schemaName1);
					this.processComplexType(typevalue);
				}
			}

		}
	}
}