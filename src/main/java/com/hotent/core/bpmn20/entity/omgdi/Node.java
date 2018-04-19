package com.hotent.core.bpmn20.entity.omgdi;

import com.hotent.core.bpmn20.entity.omgdi.DiagramElement;
import com.hotent.core.bpmn20.entity.omgdi.Label;
import com.hotent.core.bpmn20.entity.omgdi.Plane;
import com.hotent.core.bpmn20.entity.omgdi.Shape;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Node")
@XmlSeeAlso({Plane.class, Shape.class, Label.class})
public abstract class Node extends DiagramElement {
}