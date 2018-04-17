package com.hotent.core.bpm.util;

import com.hotent.core.util.AppUtil;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.interceptor.CommandExecutor;

public class ActivitiUtil {
	public static CommandExecutor getCommandExecutor() {
		ProcessEngineImpl engine = (ProcessEngineImpl) AppUtil.getBean(ProcessEngine.class);
		CommandExecutor cmdExecutor = engine.getProcessEngineConfiguration().getCommandExecutor();
		return cmdExecutor;
	}
}