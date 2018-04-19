package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Artifact;
import com.hotent.core.bpmn20.entity.Assignment;
import com.hotent.core.bpmn20.entity.Auditing;
import com.hotent.core.bpmn20.entity.CategoryValue;
import com.hotent.core.bpmn20.entity.ComplexBehaviorDefinition;
import com.hotent.core.bpmn20.entity.ConversationAssociation;
import com.hotent.core.bpmn20.entity.ConversationLink;
import com.hotent.core.bpmn20.entity.ConversationNode;
import com.hotent.core.bpmn20.entity.CorrelationKey;
import com.hotent.core.bpmn20.entity.CorrelationPropertyBinding;
import com.hotent.core.bpmn20.entity.CorrelationPropertyRetrievalExpression;
import com.hotent.core.bpmn20.entity.CorrelationSubscription;
import com.hotent.core.bpmn20.entity.DataAssociation;
import com.hotent.core.bpmn20.entity.DataInput;
import com.hotent.core.bpmn20.entity.DataOutput;
import com.hotent.core.bpmn20.entity.DataState;
import com.hotent.core.bpmn20.entity.Documentation;
import com.hotent.core.bpmn20.entity.ExtensionElements;
import com.hotent.core.bpmn20.entity.FlowElement;
import com.hotent.core.bpmn20.entity.InputOutputBinding;
import com.hotent.core.bpmn20.entity.InputOutputSpecification;
import com.hotent.core.bpmn20.entity.InputSet;
import com.hotent.core.bpmn20.entity.Lane;
import com.hotent.core.bpmn20.entity.LaneSet;
import com.hotent.core.bpmn20.entity.LoopCharacteristics;
import com.hotent.core.bpmn20.entity.MessageFlow;
import com.hotent.core.bpmn20.entity.MessageFlowAssociation;
import com.hotent.core.bpmn20.entity.Monitoring;
import com.hotent.core.bpmn20.entity.Operation;
import com.hotent.core.bpmn20.entity.OutputSet;
import com.hotent.core.bpmn20.entity.Participant;
import com.hotent.core.bpmn20.entity.ParticipantAssociation;
import com.hotent.core.bpmn20.entity.ParticipantMultiplicity;
import com.hotent.core.bpmn20.entity.Property;
import com.hotent.core.bpmn20.entity.Relationship;
import com.hotent.core.bpmn20.entity.Rendering;
import com.hotent.core.bpmn20.entity.ResourceAssignmentExpression;
import com.hotent.core.bpmn20.entity.ResourceParameter;
import com.hotent.core.bpmn20.entity.ResourceParameterBinding;
import com.hotent.core.bpmn20.entity.ResourceRole;
import com.hotent.core.bpmn20.entity.RootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tBaseElement", propOrder = {"documentation", "extensionElements"})
@XmlSeeAlso({Operation.class, CorrelationSubscription.class, ResourceAssignmentExpression.class, Monitoring.class,
		Participant.class, ParticipantMultiplicity.class, InputSet.class, OutputSet.class, Relationship.class,
		Assignment.class, MessageFlow.class, InputOutputBinding.class, ResourceParameter.class, Property.class,
		DataInput.class, ComplexBehaviorDefinition.class, MessageFlowAssociation.class, ConversationLink.class,
		DataAssociation.class, ParticipantAssociation.class, CategoryValue.class, LoopCharacteristics.class,
		CorrelationPropertyBinding.class, ResourceRole.class, ConversationNode.class, Lane.class,
		CorrelationPropertyRetrievalExpression.class, DataState.class, LaneSet.class, ConversationAssociation.class,
		InputOutputSpecification.class, CorrelationKey.class, ResourceParameterBinding.class, Rendering.class,
		FlowElement.class, RootElement.class, Auditing.class, Artifact.class, DataOutput.class})
public abstract class BaseElement {
	protected List<Documentation> documentation;
	protected ExtensionElements extensionElements;
	@XmlAttribute
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAnyAttribute
	private Map<QName, String> otherAttributes = new HashMap();

	public List<Documentation> getDocumentation() {
		if (this.documentation == null) {
			this.documentation = new ArrayList();
		}

		return this.documentation;
	}

	public ExtensionElements getExtensionElements() {
		return this.extensionElements;
	}

	public void setExtensionElements(ExtensionElements value) {
		this.extensionElements = value;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public Map<QName, String> getOtherAttributes() {
		return this.otherAttributes;
	}
}