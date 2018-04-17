package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Artifact;
import com.hotent.core.bpmn20.entity.ChoreographyActivity;
import com.hotent.core.bpmn20.entity.FlowElement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tSubChoreography", propOrder = {"flowElement", "artifact"})
public class SubChoreography extends ChoreographyActivity {
	@XmlElementRef(name = "flowElement", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<? extends FlowElement>> flowElement;
	@XmlElementRef(name = "artifact", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<? extends Artifact>> artifact;

	public List<JAXBElement<? extends FlowElement>> getFlowElement() {
		if (this.flowElement == null) {
			this.flowElement = new ArrayList();
		}

		return this.flowElement;
	}

	public List<JAXBElement<? extends Artifact>> getArtifact() {
		if (this.artifact == null) {
			this.artifact = new ArrayList();
		}

		return this.artifact;
	}
}