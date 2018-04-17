package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Association;
import com.hotent.core.bpmn20.entity.BaseElement;
import com.hotent.core.bpmn20.entity.Group;
import com.hotent.core.bpmn20.entity.TextAnnotation;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tArtifact")
@XmlSeeAlso({Association.class, Group.class, TextAnnotation.class})
public abstract class Artifact extends BaseElement {
}