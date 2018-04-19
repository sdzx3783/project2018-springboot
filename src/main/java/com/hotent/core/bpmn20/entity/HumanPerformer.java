package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Performer;
import com.hotent.core.bpmn20.entity.PotentialOwner;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tHumanPerformer")
@XmlSeeAlso({PotentialOwner.class})
public class HumanPerformer extends Performer {
}