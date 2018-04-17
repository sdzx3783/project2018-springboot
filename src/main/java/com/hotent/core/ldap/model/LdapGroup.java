package com.hotent.core.ldap.model;

import java.util.Date;

public class LdapGroup {
	public static final String KEY_ADMINCOUNT = "adminCount";
	public static final String KEY_CN = "cn";
	public static final String KEY_DESCRIPTIONT = "description";
	public static final String KEY_DISTINGUISHEDNAME = "distinguishedName";
	public static final String KEY_INFO = "info";
	public static final String KEY_MAIL = "mail";
	public static final String KEY_MEMBER = "member";
	public static final String KEY_NAME = "name";
	public static final String KEY_SAMACCOUNTNAME = "sAMAccountName";
	public static final String KEY_SAMACCOUNTTYPE = "sAMAccountType";
	public static final String KEY_WHENCHANGED = "whenChanged";
	public static final String KEY_WHENCREATED = "whenCreated";
	public static String OBJECTCATEGORY = "group";
	public static String OBJECTCLASS = "group";
	private String adminCount;
	private String cn;
	private String description;
	private String distinguishedName;
	private String info;
	private String mail;
	private String[] members;
	private String name;
	private String sAMAccountName;
	private String sAMAccountType;
	private Date whenChanged;
	private Date whenCreated;

	public String getAdminCount() {
		return this.adminCount;
	}

	public void setAdminCount(String adminCount) {
		this.adminCount = adminCount;
	}

	public String getCn() {
		return this.cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDistinguishedName() {
		return this.distinguishedName;
	}

	public void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String[] getMembers() {
		return this.members;
	}

	public void setMembers(String[] members) {
		this.members = members;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getsAMAccountName() {
		return this.sAMAccountName;
	}

	public void setsAMAccountName(String sAMAccountName) {
		this.sAMAccountName = sAMAccountName;
	}

	public String getsAMAccountType() {
		return this.sAMAccountType;
	}

	public void setsAMAccountType(String sAMAccountType) {
		this.sAMAccountType = sAMAccountType;
	}

	public Date getWhenChanged() {
		return this.whenChanged;
	}

	public void setWhenChanged(Date whenChanged) {
		this.whenChanged = whenChanged;
	}

	public Date getWhenCreated() {
		return this.whenCreated;
	}

	public void setWhenCreated(Date whenCreated) {
		this.whenCreated = whenCreated;
	}
}