package com.hotent.core.ldap;

public class LdapSettingModel {
	private String url = "";
	private String userDn = "";
	private String password = "";
	private String ldapBase = "";
	private String orgFields = "ou,distinguishedName,name";
	private String userFields = "sAMAccountName,distinguishedName,name";
	private String orgNames = "";

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOrgFields() {
		return this.orgFields;
	}

	public void setOrgFields(String orgFields) {
		this.orgFields = orgFields;
	}

	public String getUserFields() {
		return this.userFields;
	}

	public void setUserFields(String userFields) {
		this.userFields = userFields;
	}

	public String getOrgNames() {
		return this.orgNames;
	}

	public void setOrgNames(String orgNames) {
		this.orgNames = orgNames;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserDn() {
		return this.userDn;
	}

	public void setUserDn(String userDn) {
		this.userDn = userDn;
	}

	public String getLdapBase() {
		return this.ldapBase;
	}

	public void setLdapBase(String ldapBase) {
		this.ldapBase = ldapBase;
	}
}