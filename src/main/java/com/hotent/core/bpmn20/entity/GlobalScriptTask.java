package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.GlobalTask;
import com.hotent.core.bpmn20.entity.Script;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tGlobalScriptTask", propOrder = {"script"})
public class GlobalScriptTask extends GlobalTask {
	protected Script script;
	@XmlAttribute
	@XmlSchemaType(name = "anyURI")
	protected String scriptLanguage;

	public Script getScript() {
		return this.script;
	}

	public void setScript(Script value) {
		this.script = value;
	}

	public String getScriptLanguage() {
		return this.scriptLanguage;
	}

	public void setScriptLanguage(String value) {
		this.scriptLanguage = value;
	}
}