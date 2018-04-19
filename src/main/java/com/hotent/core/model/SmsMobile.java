package com.hotent.core.model;

import java.io.Serializable;
import java.util.Date;

public class SmsMobile implements Serializable {
	public static final Short STATUS_SENDED = Short.valueOf(1);
	public static final Short STATUS_NOT_SENDED = Short.valueOf(0);
	protected Long smsId;
	protected Date sendTime;
	protected String recipients;
	protected String phoneNumber;
	protected Long userId;
	protected String userName;
	protected String smsContent;
	protected Short status;
	protected String fromUser;

	public SmsMobile() {
	}

	public SmsMobile(Long in_smsId) {
		this.setSmsId(in_smsId);
	}

	public Long getSmsId() {
		return this.smsId;
	}

	public void setSmsId(Long aValue) {
		this.smsId = aValue;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date aValue) {
		this.sendTime = aValue;
	}

	public String getRecipients() {
		return this.recipients;
	}

	public void setRecipients(String aValue) {
		this.recipients = aValue;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String aValue) {
		this.phoneNumber = aValue;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long aValue) {
		this.userId = aValue;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String aValue) {
		this.userName = aValue;
	}

	public String getSmsContent() {
		return this.smsContent;
	}

	public void setSmsContent(String aValue) {
		this.smsContent = aValue;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short aValue) {
		this.status = aValue;
	}

	public String getFromUser() {
		return this.fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
}