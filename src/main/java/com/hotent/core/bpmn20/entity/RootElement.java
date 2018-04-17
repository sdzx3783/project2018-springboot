package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import com.hotent.core.bpmn20.entity.CallableElement;
import com.hotent.core.bpmn20.entity.Category;
import com.hotent.core.bpmn20.entity.Collaboration;
import com.hotent.core.bpmn20.entity.CorrelationProperty;
import com.hotent.core.bpmn20.entity.DataStore;
import com.hotent.core.bpmn20.entity.EndPoint;
import com.hotent.core.bpmn20.entity.Error;
import com.hotent.core.bpmn20.entity.Escalation;
import com.hotent.core.bpmn20.entity.EventDefinition;
import com.hotent.core.bpmn20.entity.Interface;
import com.hotent.core.bpmn20.entity.ItemDefinition;
import com.hotent.core.bpmn20.entity.Message;
import com.hotent.core.bpmn20.entity.PartnerEntity;
import com.hotent.core.bpmn20.entity.PartnerRole;
import com.hotent.core.bpmn20.entity.Resource;
import com.hotent.core.bpmn20.entity.Signal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tRootElement")
@XmlSeeAlso({ItemDefinition.class, Category.class, EndPoint.class, PartnerRole.class, PartnerEntity.class,
		Collaboration.class, Signal.class, EventDefinition.class, DataStore.class, Error.class, Resource.class,
		Interface.class, CorrelationProperty.class, Message.class, CallableElement.class, Escalation.class})
public abstract class RootElement extends BaseElement {
}