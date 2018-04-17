package com.hotent.core.api.bpm;

import com.hotent.core.api.bpm.model.IBpmDefinition;

public interface IBpmDefinitionService {
	IBpmDefinition getMainDefByActDefKey(String arg0);

	IBpmDefinition getByActDefId(String arg0);
}