package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Activity;
import com.hotent.core.bpmn20.entity.BusinessRuleTask;
import com.hotent.core.bpmn20.entity.ManualTask;
import com.hotent.core.bpmn20.entity.ReceiveTask;
import com.hotent.core.bpmn20.entity.ScriptTask;
import com.hotent.core.bpmn20.entity.SendTask;
import com.hotent.core.bpmn20.entity.ServiceTask;
import com.hotent.core.bpmn20.entity.UserTask;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tTask")
@XmlSeeAlso({ManualTask.class, ServiceTask.class, ScriptTask.class, ReceiveTask.class, BusinessRuleTask.class,
		SendTask.class, UserTask.class})
public class Task extends Activity {
}