package com.hotent.core.model;

import com.hotent.core.api.org.ISysUserService;
import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.util.AppUtil;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.UnsatisfiedDependencyException;

public class TaskExecutor implements Serializable {
	public static final int EXACT_NOEXACT = 0;
	public static final int EXACT_EXACT_USER = 1;
	public static final int EXACT_EXACT_SECOND = 2;
	public static final int EXACT_USER_GROUP = 3;
	private static final long serialVersionUID = 10001L;
	public static final String USER_TYPE_USER = "user";
	public static final String USER_TYPE_ORG = "org";
	public static final String USER_TYPE_ROLE = "role";
	public static final String USER_TYPE_POS = "pos";
	public static final String USER_TYPE_JOB = "job";
	public static final String USER_TYPE_USERGROUP = "group";
	private String type = "user";
	public String mainOrgName;
	private String executeId = "";
	private String executor = "";
	private int exactType = 0;

	public TaskExecutor() {
	}

	public TaskExecutor(String executeId) {
		Long userId = Long.valueOf(Long.parseLong(executeId));
		ISysUserService sysUserService = (ISysUserService) AppUtil.getBean(ISysUserService.class);
		ISysUser sysUser = sysUserService.getById(userId);
		this.executeId = executeId;
		this.executor = sysUser.getFullname();
	}

	public TaskExecutor(String type, String executeId, String name) {
		this.type = type;
		this.executeId = executeId;
		this.executor = name;
		if ("group".equalsIgnoreCase(type)) {
			this.exactType = 3;
		}

	}

	public static TaskExecutor getTaskUser(String executeId, String name) {
		return new TaskExecutor("user", executeId, name);
	}

	public static TaskExecutor getTaskOrg(String executeId, String name) {
		return new TaskExecutor("org", executeId, name);
	}

	public static TaskExecutor getTaskRole(String executeId, String name) {
		return new TaskExecutor("role", executeId, name);
	}

	public static TaskExecutor getTaskJob(String executeId, String name) {
		return new TaskExecutor("job", executeId, name);
	}

	public static TaskExecutor getTaskPos(String executeId, String name) {
		return new TaskExecutor("pos", executeId, name);
	}

	public static TaskExecutor getTaskUserGroup(String executeId, String name) {
		TaskExecutor ex = new TaskExecutor("group", executeId, name);
		ex.setExactType(3);
		return ex;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExecuteId() {
		return this.executeId;
	}

	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}

	public String getExecutor() {
		return this.executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public int getExactType() {
		return this.exactType;
	}

	public void setExactType(int exactType) {
		this.exactType = exactType;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof TaskExecutor)) {
			return false;
		} else {
			TaskExecutor tmp = (TaskExecutor) obj;
			return this.type.equals(tmp.getType()) && this.executeId.equals(tmp.getExecuteId());
		}
	}

	public int hashCode() {
		String tmp = this.type + this.executeId;
		return tmp.hashCode();
	}

	public Set<ISysUser> getSysUser() throws UnsatisfiedDependencyException {
		HashSet sysUsers = new HashSet();
		if (AppUtil.getContext() == null) {
			throw new UnsatisfiedDependencyException("Convert Executor to SysUser dependency ApplicationContext",
					"applicationContext", "", "Convert Executor to SysUser dependency ApplicationContext");
		} else {
			ISysUserService sysUserService = (ISysUserService) AppUtil.getBean(ISysUserService.class);
			if ("user".equals(this.type)) {
				ISysUser users = sysUserService.getById(Long.valueOf(this.executeId));
				sysUsers.add(users);
			} else if ("org".equals(this.type) && "pos".equals(this.type) && "role".equals(this.type)) {
				List users1 = sysUserService.getByGroup(Long.valueOf(this.executeId), this.type);
				sysUsers.addAll(users1);
			}

			return sysUsers;
		}
	}

	public String getMainOrgName() {
		return this.mainOrgName;
	}

	public void setMainOrgName(String mainOrgName) {
		this.mainOrgName = mainOrgName;
	}
}