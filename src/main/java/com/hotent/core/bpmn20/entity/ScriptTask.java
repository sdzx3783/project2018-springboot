package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Script;
import com.hotent.core.bpmn20.entity.Task;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tScriptTask", propOrder = {"script"})
public class ScriptTask extends Task {
	protected Script script;
	@XmlAttribute
	protected String scriptFormat;

	public Script getScript() {
		return this.script;
	}

	public void setScript(Script value) {
		this.script = value;
	}

	public String getScriptFormat() {
		return this.scriptFormat;
	}

	public void setScriptFormat(String value) {
		this.scriptFormat = value;
	}
}