package com.hotent.core.bpmn20.entity.omgdi;

import com.hotent.core.bpmn20.entity.bpmndi.BPMNEdge;
import com.hotent.core.bpmn20.entity.omgdi.Edge;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LabeledEdge")
@XmlSeeAlso({BPMNEdge.class})
public abstract class LabeledEdge extends Edge {
}