package com.hotent.core.model;

import com.hotent.core.api.org.model.ISysUser;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class MessageModel implements Serializable {
	private ISysUser receiveUser;
	private ISysUser sendUser;
	private String informType;
	private Map<String, String> templateMap;
	private String content;
	private String subject;
	private String opinion;
	private Date sendDate;
	private Long extId;
	private boolean isTask;
	private Map vars;
	private String[] to;
	private String[] cc;
	private String[] bcc;

	public MessageModel(String informType) {
		this.informType = informType;
	}

	public boolean getIsTask() {
		return this.isTask;
	}

	public void setIsTask(boolean isTask) {
		this.isTask = isTask;
	}

	public Date getSendDate() {
		return this.sendDate == null ? new Date() : this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Map<String, String> getTemplateMap() {
		return this.templateMap;
	}

	public void setTemplateMap(Map<String, String> templateMap) {
		this.templateMap = templateMap;
	}

	public ISysUser getReceiveUser() {
		return this.receiveUser;
	}

	public void setReceiveUser(ISysUser receiveUser) {
		this.receiveUser = receiveUser;
	}

	public ISysUser getSendUser() {
		return this.sendUser;
	}

	public void setSendUser(ISysUser sendUser) {
		this.sendUser = sendUser;
	}

	public String getInformType() {
		return this.informType;
	}

	public void setInformType(String informType) {
		this.informType = informType;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getOpinion() {
		return this.opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Long getExtId() {
		return this.extId;
	}

	public void setExtId(Long extId) {
		this.extId = extId;
	}

	public Map getVars() {
		return this.vars;
	}

	public void setVars(Map vars) {
		this.vars = vars;
	}

	public String[] getTo() {
		return this.to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String[] getCc() {
		return this.cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String[] getBcc() {
		return this.bcc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}