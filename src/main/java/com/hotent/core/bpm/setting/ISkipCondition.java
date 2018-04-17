package com.hotent.core.bpm.setting;

import com.hotent.core.api.org.model.ISysUser;
import org.activiti.engine.task.Task;

public interface ISkipCondition {
	boolean canSkip(Task arg0);

	ISysUser getExecutor();

	String getTitle();
}