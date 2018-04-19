package com.hotent.core.bpm.model;

import com.hotent.core.api.bpm.model.IProcessRun;
import com.hotent.core.api.bpm.model.ITaskOpinion;
import com.hotent.core.bpm.util.BpmUtil;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
public class ProcessCmd {
	private String actDefId;
	private String flowKey;
	private String taskId;
	private Long runId = Long.valueOf(0L);
	private String subject = "";
	private String destTask;
	private String[] lastDestTaskIds;
	private String[] lastDestTaskUids;
	private List<TaskExecutor> taskExecutors = new ArrayList();
	private String businessKey = "";
	private Long stackId;
	private boolean skipPreHandler = false;
	private boolean skipAfterHandler = false;
	private Integer isBack = Integer.valueOf(0);
	private boolean isRecover = false;
	private boolean isOnlyCompleteTask = false;
	private Short voteAgree = Short.valueOf(1);
	private String voteContent = "";
	private String voteFieldName = "";
	private Map<String, Object> variables = new HashMap();
	private String formData = "";
	private Map formDataMap = new HashMap();
	private String currentUserId = "";
	private IProcessRun processRun = null;
	private String userAccount = null;
	private boolean invokeExternal = false;
	private String informType = "";
	private String informStart = "";
	private Short isManage = Short.valueOf(0);
	private String appCode = "";
	private String dynamicTask;
	private Short jumpType;
	private String startNode;
	private Long relRunId = Long.valueOf(0L);
	private Map<String, Object> transientVars = new HashMap();
	private boolean localApi = false;
	private boolean skip = false;
	private boolean fromMobile = false;
	private boolean startFlow = false;

	public ProcessCmd() {
	}

	public ProcessCmd(String flowKey) {
		this.flowKey = flowKey;
	}

	public ProcessCmd(String flowKey, boolean skipPreHandler, boolean skipAfterHandler) {
		this.flowKey = flowKey;
		this.skipPreHandler = skipPreHandler;
		this.skipAfterHandler = skipAfterHandler;
	}

	public String getActDefId() {
		return this.actDefId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Map<String, Object> getVariables() {
		return this.variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public void putVariables(Map<String, Object> variables) {
		this.variables.putAll(variables);
	}

	public void addVariable(String key, Object obj) {
		this.variables.put(key, obj);
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDestTask() {
		return this.destTask;
	}

	public void setDestTask(String destTask) {
		this.destTask = destTask;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getFlowKey() {
		return this.flowKey;
	}

	public void setFlowKey(String flowKey) {
		this.flowKey = flowKey;
	}

	public Integer isBack() {
		return this.isBack;
	}

	public void setBack(Integer isBack) {
		this.isBack = isBack;
	}

	public boolean isRecover() {
		return this.isRecover;
	}

	public void setRecover(boolean isRecover) {
		this.isRecover = isRecover;
	}

	public Short getVoteAgree() {
		return this.voteAgree;
	}

	public void setVoteAgree(Short voteAgree) {
		if (ITaskOpinion.STATUS_RECOVER.equals(voteAgree)) {
			this.setRecover(true);
		}

		this.voteAgree = voteAgree;
	}

	public String getVoteContent() {
		return this.voteContent;
	}

	public void setVoteContent(String voteContent) {
		this.voteContent = voteContent;
	}

	public Long getStackId() {
		return this.stackId;
	}

	public void setStackId(Long stackId) {
		this.stackId = stackId;
	}

	public String getFormData() {
		return this.formData;
	}

	public void setFormData(String formData) {
		this.formData = formData;
	}

	public Map getFormDataMap() {
		return this.formDataMap;
	}

	public void setFormDataMap(Map formDataMap) {
		this.formDataMap = formDataMap;
	}

	public String getCurrentUserId() {
		return this.currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public IProcessRun getProcessRun() {
		return this.processRun;
	}

	public void setProcessRun(IProcessRun processRun) {
		this.businessKey = processRun.getBusinessKey();
		this.processRun = processRun;
	}

	public String getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String[] getLastDestTaskIds() {
		return this.lastDestTaskIds;
	}

	public void setLastDestTaskIds(String[] lastDestTaskIds) {
		this.lastDestTaskIds = lastDestTaskIds;
	}

	public String[] getLastDestTaskUids() {
		return this.lastDestTaskUids;
	}

	public void setLastDestTaskUids(String[] lastDestTaskUids) {
		this.lastDestTaskUids = lastDestTaskUids;
	}

	public boolean isOnlyCompleteTask() {
		return this.isOnlyCompleteTask;
	}

	public void setOnlyCompleteTask(boolean isOnlyCompleteTask) {
		this.isOnlyCompleteTask = isOnlyCompleteTask;
	}

	public boolean isInvokeExternal() {
		return this.invokeExternal;
	}

	public void setInvokeExternal(boolean invokeExternal) {
		this.invokeExternal = invokeExternal;
	}

	public String getInformType() {
		return this.informType;
	}

	public void setInformType(String informType) {
		this.informType = informType;
	}

	public boolean isSkipPreHandler() {
		return this.skipPreHandler;
	}

	public void setSkipPreHandler(boolean skipPreHandler) {
		this.skipPreHandler = skipPreHandler;
	}

	public boolean isSkipAfterHandler() {
		return this.skipAfterHandler;
	}

	public void setSkipAfterHandler(boolean skipAfterHandler) {
		this.skipAfterHandler = skipAfterHandler;
	}

	public Map<String, List<TaskExecutor>> getTaskExecutor() {
		HashMap map = new HashMap();
		if (BeanUtils.isEmpty(this.lastDestTaskIds)) {
			return map;
		} else {
			for (int i = 0; i < this.lastDestTaskIds.length; ++i) {
				String nodeId = this.lastDestTaskIds[i];
				String executor = this.lastDestTaskUids[i];
				if (!StringUtil.isEmpty(executor)) {
					List list = BpmUtil.getTaskExecutors(executor);
					map.put(nodeId, list);
				}
			}

			return map;
		}
	}

	public List<TaskExecutor> getTaskExecutors() {
		return this.taskExecutors;
	}

	public void setTaskExecutors(List<TaskExecutor> taskExecutors) {
		this.taskExecutors = taskExecutors;
	}

	public Long getRunId() {
		return this.runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public Short getIsManage() {
		return this.isManage;
	}

	public void setIsManage(Short isManage) {
		this.isManage = isManage;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getDynamicTask() {
		return this.dynamicTask;
	}

	public void setDynamicTask(String dynamicTask) {
		this.dynamicTask = dynamicTask;
	}

	public Short getJumpType() {
		return this.jumpType;
	}

	public void setJumpType(Short jumpType) {
		this.jumpType = jumpType;
	}

	public String getInformStart() {
		return this.informStart;
	}

	public void setInformStart(String informStart) {
		this.informStart = informStart;
	}

	public String getStartNode() {
		return this.startNode;
	}

	public void setStartNode(String startNode) {
		this.startNode = startNode;
	}

	public String getVoteFieldName() {
		return this.voteFieldName;
	}

	public void setVoteFieldName(String voteFieldName) {
		this.voteFieldName = voteFieldName;
	}

	public Long getRelRunId() {
		return this.relRunId == null ? Long.valueOf(0L) : this.relRunId;
	}

	public void setRelRunId(Long relRunId) {
		this.relRunId = relRunId;
	}

	public Map<String, Object> getTransientVars() {
		return this.transientVars;
	}

	public void setTransientVars(Map<String, Object> transientVars) {
		this.transientVars = transientVars;
	}

	public void addTransientVar(String key, Object object) {
		this.transientVars.put(key, object);
	}

	public Object getTransientVar(String key) {
		return this.transientVars.get(key);
	}

	public boolean isLocalApi() {
		return this.localApi;
	}

	public void setLocalApi(boolean localApi) {
		this.localApi = localApi;
	}

	public boolean isSkip() {
		return this.skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public boolean isFromMobile() {
		return this.fromMobile;
	}

	public void setFromMobile(boolean fromMobile) {
		this.fromMobile = fromMobile;
	}

	public boolean isStartFlow() {
		return this.startFlow;
	}

	public void setStartFlow(boolean startFlow) {
		this.startFlow = startFlow;
	}

	public String toString() {
		return "ProcessCmd [actDefId=" + this.actDefId + ", flowKey=" + this.flowKey + ", taskId=" + this.taskId
				+ ", runId=" + this.runId + ", destTask=" + this.destTask + ", isBack=" + this.isBack + ", isRecover="
				+ this.isRecover + ", isOnlyCompleteTask=" + this.isOnlyCompleteTask + ", voteAgree=" + this.voteAgree
				+ ", informType=" + this.informType + "]";
	}
}