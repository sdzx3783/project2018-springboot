package com.hotent.core.datakey.impl;

import com.hotent.core.util.UniqueIdUtil;
import org.activiti.engine.impl.cfg.IdGenerator;

public class ActivitiIdGenerator implements IdGenerator {
	public String getNextId() {
		return String.valueOf(UniqueIdUtil.genId());
	}
}