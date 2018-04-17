package com.hotent.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class MailModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String subject;
	private String from;
	private String[] to;
	private String[] cc;
	private String[] bcc;
	private Date sendDate;
	private String content;
	private String mailTemplate;
	private Map mailData = null;

	public String getSubject() {
		return this.subject;
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

	public String getMailTemplate() {
		return this.mailTemplate;
	}

	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
	}

	public Map getMailData() {
		return this.mailData;
	}

	public void setMailData(Map mailData) {
		this.mailData = mailData;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}