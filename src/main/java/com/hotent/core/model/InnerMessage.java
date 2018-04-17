package com.hotent.core.model;

import java.io.Serializable;
import java.util.Date;

public class InnerMessage implements Serializable {
	private String subject;
	private String from;
	private String fromName;
	private String to;
	private String toName;
	private Date sendDate;
	private String content;
	private short canReply;

	public short getCanReply() {
		return this.canReply;
	}

	public void setCanReply(short canReply) {
		this.canReply = canReply;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getFromName() {
		return this.fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return this.toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return this.to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}