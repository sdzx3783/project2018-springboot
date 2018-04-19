package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElementWithMixedContent;
import com.hotent.core.bpmn20.entity.FormalExpression;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tExpression")
@XmlSeeAlso({FormalExpression.class})
public class Expression extends BaseElementWithMixedContent {
}