package com.hotent.core.model;

public class CurrentUser {
	private Long userId = Long.valueOf(0L);
	private String account = "";
	private String name = "";
	private Long orgId = Long.valueOf(0L);
	private Long posId = Long.valueOf(0L);

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getPosId() {
		return this.posId;
	}

	public void setPosId(Long posId) {
		this.posId = posId;
	}
}