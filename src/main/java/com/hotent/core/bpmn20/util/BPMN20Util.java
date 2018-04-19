package com.hotent.core.bpmn20.util;

import com.hotent.core.bpmn20.ContextFactory;
import com.hotent.core.bpmn20.entity.Definitions;
import com.hotent.core.bpmn20.entity.ExtensionElements;
import com.hotent.core.bpmn20.entity.FlowElement;
import com.hotent.core.bpmn20.entity.ObjectFactory;
import com.hotent.core.bpmn20.entity.Process;
import com.hotent.core.bpmn20.entity.RootElement;
import com.hotent.core.bpmn20.entity.SubProcess;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

public class BPMN20Util {
	public static List<FlowElement> getFlowElementByType(Process process, boolean deepIntoSub, Class... flowTypes) {
		ArrayList flowElements = new ArrayList();
		List jaxbElementFlowElements = process.getFlowElement();
		Iterator i$ = jaxbElementFlowElements.iterator();

		while (i$.hasNext()) {
			JAXBElement jAXBElement = (JAXBElement) i$.next();
			FlowElement flowElement = (FlowElement) jAXBElement.getValue();
			Class[] arr$ = flowTypes;
			int len$ = flowTypes.length;

			for (int i$1 = 0; i$1 < len$; ++i$1) {
				Class flowType = arr$[i$1];
				if (flowType.isInstance(flowElement)) {
					flowElements.add(flowElement);
					break;
				}
			}

			if (deepIntoSub && flowElement instanceof SubProcess) {
				flowElements.addAll(getFlowElementByTypeInSubProcess((SubProcess) flowElement, true, flowTypes));
			}
		}

		return flowElements;
	}

	public static List<FlowElement> getFlowElementByTypeInSubProcess(SubProcess subProcess, boolean deepIntoSub,
			Class... flowTypes) {
		ArrayList flowElements = new ArrayList();
		List jaxbElementFlowElements = subProcess.getFlowElement();
		Iterator i$ = jaxbElementFlowElements.iterator();

		while (i$.hasNext()) {
			JAXBElement jAXBElement = (JAXBElement) i$.next();
			FlowElement flowElement = (FlowElement) jAXBElement.getValue();
			Class[] arr$ = flowTypes;
			int len$ = flowTypes.length;

			for (int i$1 = 0; i$1 < len$; ++i$1) {
				Class flowType = arr$[i$1];
				if (flowType.isInstance(flowElement)) {
					flowElements.add(flowElement);
					break;
				}
			}

			if (deepIntoSub && flowElement instanceof SubProcess) {
				flowElements.addAll(getFlowElementByTypeInSubProcess((SubProcess) flowElement, true, flowTypes));
			}
		}

		return flowElements;
	}

	public static OutputStream marshall(Object jaxbElement, OutputStream os) throws JAXBException {
		JAXBContext jctx = JAXBContext.newInstance(new Class[]{ObjectFactory.class});
		Marshaller marshaller = jctx.createMarshaller();
		marshaller.marshal(jaxbElement, os);
		return os;
	}

	public static Object unmarshall(InputStream is, Class... classes) throws JAXBException, IOException {
		JAXBContext jctx = ContextFactory.newInstance(classes);
		Unmarshaller unmarshaller = jctx.createUnmarshaller();
		Object obj = unmarshaller.unmarshal(is);
		return obj;
	}

	public static Object unmarshall(String bpmnxml, Class<? extends Object> classes) throws JAXBException, IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(bpmnxml.getBytes());
		return unmarshall((InputStream) is, (Class[]) (new Class[]{classes}));
	}

	public static Definitions createDefinitions(InputStream is) throws JAXBException, IOException {
		JAXBElement jAXBElement = (JAXBElement) unmarshall(is,
				new Class[]{ObjectFactory.class, com.hotent.core.bpmn20.entity.activiti.ObjectFactory.class,
						com.hotent.core.bpmn20.entity.omgdc.ObjectFactory.class,
						com.hotent.core.bpmn20.entity.omgdi.ObjectFactory.class,
						com.hotent.core.bpmn20.entity.ht.ObjectFactory.class,
						com.hotent.core.bpmn20.entity.bpmndi.ObjectFactory.class});
		Definitions definitions = (Definitions) jAXBElement.getValue();
		return definitions;
	}

	public static Definitions createDefinitions(String bpmnxml) throws JAXBException, IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(bpmnxml.getBytes());
		return createDefinitions((InputStream) is);
	}

	public static List<Process> getProcess(InputStream is) throws JAXBException, IOException {
		ArrayList processes = new ArrayList();
		Definitions definitions = createDefinitions(is);
		List bPMNElements = definitions.getRootElement();
		Iterator i$ = bPMNElements.iterator();

		while (i$.hasNext()) {
			JAXBElement jAXBe = (JAXBElement) i$.next();
			RootElement element = (RootElement) jAXBe.getValue();
			if (element instanceof Process) {
				processes.add((Process) element);
			}
		}

		return processes;
	}

	public static List<Process> getProcess(String bpmnxml) throws JAXBException, IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(bpmnxml.getBytes("UTF-8"));
		return getProcess((InputStream) is);
	}

	public static List<Object> getFlowElementExtension(FlowElement flowElement, QName qname) {
		ArrayList extensions = new ArrayList();
		ExtensionElements extensionElements = flowElement.getExtensionElements();
		if (extensionElements == null) {
			return extensions;
		} else {
			List objects = extensionElements.getAny();
			Iterator i$ = objects.iterator();

			while (i$.hasNext()) {
				Object obj = i$.next();
				if (obj instanceof JAXBElement) {
					JAXBElement extension = (JAXBElement) obj;
					if (extension.getName().equals(qname)) {
						extensions.add(extension.getValue());
					}
				}
			}

			return extensions;
		}
	}
}