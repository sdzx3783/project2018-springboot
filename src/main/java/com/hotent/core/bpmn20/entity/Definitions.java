package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Extension;
import com.hotent.core.bpmn20.entity.Import;
import com.hotent.core.bpmn20.entity.Relationship;
import com.hotent.core.bpmn20.entity.RootElement;
import com.hotent.core.bpmn20.entity.bpmndi.BPMNDiagram;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tDefinitions", propOrder = {"_import", "extension", "rootElement", "bpmnDiagram", "relationship"})
public class Definitions {
	@XmlElement(name = "import")
	protected List<Import> _import;
	protected List<Extension> extension;
	@XmlElementRef(name = "rootElement", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<? extends RootElement>> rootElement;
	@XmlElement(name = "BPMNDiagram", namespace = "http://www.omg.org/spec/BPMN/20100524/DI")
	protected List<BPMNDiagram> bpmnDiagram;
	protected List<Relationship> relationship;
	@XmlAttribute
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAttribute
	protected String name;
	@XmlAttribute(required = true)
	@XmlSchemaType(name = "anyURI")
	protected String targetNamespace;
	@XmlAttribute
	@XmlSchemaType(name = "anyURI")
	protected String expressionLanguage;
	@XmlAttribute
	@XmlSchemaType(name = "anyURI")
	protected String typeLanguage;
	@XmlAttribute
	protected String exporter;
	@XmlAttribute
	protected String exporterVersion;
	@XmlAnyAttribute
	private Map<QName, String> otherAttributes = new HashMap();

	public List<Import> getImport() {
		if (this._import == null) {
			this._import = new ArrayList();
		}

		return this._import;
	}

	public List<Extension> getExtension() {
		if (this.extension == null) {
			this.extension = new ArrayList();
		}

		return this.extension;
	}

	public List<JAXBElement<? extends RootElement>> getRootElement() {
		if (this.rootElement == null) {
			this.rootElement = new ArrayList();
		}

		return this.rootElement;
	}

	public List<BPMNDiagram> getBPMNDiagram() {
		if (this.bpmnDiagram == null) {
			this.bpmnDiagram = new ArrayList();
		}

		return this.bpmnDiagram;
	}

	public List<Relationship> getRelationship() {
		if (this.relationship == null) {
			this.relationship = new ArrayList();
		}

		return this.relationship;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getTargetNamespace() {
		return this.targetNamespace;
	}

	public void setTargetNamespace(String value) {
		this.targetNamespace = value;
	}

	public String getExpressionLanguage() {
		return this.expressionLanguage == null ? "http://www.w3.org/1999/XPath" : this.expressionLanguage;
	}

	public void setExpressionLanguage(String value) {
		this.expressionLanguage = value;
	}

	public String getTypeLanguage() {
		return this.typeLanguage == null ? "http://www.w3.org/2001/XMLSchema" : this.typeLanguage;
	}

	public void setTypeLanguage(String value) {
		this.typeLanguage = value;
	}

	public String getExporter() {
		return this.exporter;
	}

	public void setExporter(String value) {
		this.exporter = value;
	}

	public String getExporterVersion() {
		return this.exporterVersion;
	}

	public void setExporterVersion(String value) {
		this.exporterVersion = value;
	}

	public Map<QName, String> getOtherAttributes() {
		return this.otherAttributes;
	}
}