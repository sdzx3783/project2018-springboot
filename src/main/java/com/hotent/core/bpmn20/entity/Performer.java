package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.HumanPerformer;
import com.hotent.core.bpmn20.entity.ResourceRole;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tPerformer")
@XmlSeeAlso({HumanPerformer.class})
public class Performer extends ResourceRole {
}