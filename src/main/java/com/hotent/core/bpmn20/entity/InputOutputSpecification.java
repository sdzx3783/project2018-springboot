package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import com.hotent.core.bpmn20.entity.DataInput;
import com.hotent.core.bpmn20.entity.DataOutput;
import com.hotent.core.bpmn20.entity.InputSet;
import com.hotent.core.bpmn20.entity.OutputSet;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tInputOutputSpecification", propOrder = {"dataInput", "dataOutput", "inputSet", "outputSet"})
public class InputOutputSpecification extends BaseElement {
	protected List<DataInput> dataInput;
	protected List<DataOutput> dataOutput;
	@XmlElement(required = true)
	protected List<InputSet> inputSet;
	@XmlElement(required = true)
	protected List<OutputSet> outputSet;

	public List<DataInput> getDataInput() {
		if (this.dataInput == null) {
			this.dataInput = new ArrayList();
		}

		return this.dataInput;
	}

	public List<DataOutput> getDataOutput() {
		if (this.dataOutput == null) {
			this.dataOutput = new ArrayList();
		}

		return this.dataOutput;
	}

	public List<InputSet> getInputSet() {
		if (this.inputSet == null) {
			this.inputSet = new ArrayList();
		}

		return this.inputSet;
	}

	public List<OutputSet> getOutputSet() {
		if (this.outputSet == null) {
			this.outputSet = new ArrayList();
		}

		return this.outputSet;
	}
}