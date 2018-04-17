package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Expression;
import com.hotent.core.bpmn20.entity.LoopCharacteristics;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tStandardLoopCharacteristics", propOrder = {"loopCondition"})
public class StandardLoopCharacteristics extends LoopCharacteristics {
	protected Expression loopCondition;
	@XmlAttribute
	protected Boolean testBefore;
	@XmlAttribute
	protected BigInteger loopMaximum;

	public Expression getLoopCondition() {
		return this.loopCondition;
	}

	public void setLoopCondition(Expression value) {
		this.loopCondition = value;
	}

	public boolean isTestBefore() {
		return this.testBefore == null ? false : this.testBefore.booleanValue();
	}

	public void setTestBefore(Boolean value) {
		this.testBefore = value;
	}

	public BigInteger getLoopMaximum() {
		return this.loopMaximum;
	}

	public void setLoopMaximum(BigInteger value) {
		this.loopMaximum = value;
	}
}