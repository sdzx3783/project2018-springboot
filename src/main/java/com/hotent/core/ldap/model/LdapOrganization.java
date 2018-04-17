package com.hotent.core.ldap.model;

import java.util.Date;

public class LdapOrganization {
	public static String KEY_O = "o";
	public static String KEY_BUSINESSCATEGORY = "businessCategory";
	public static String KEY_DESCRIPTION = "description";
	public static String KEY_DISTINGUISHEDNAME = "distinguishedName";
	public static String KEY_FACSIMILETELEPHONENUMBER = "facsimileTelephoneNumber";
	public static String KEY_NAME = "name";
	public static String KEY_POSTALADDRESS = "postalAddress";
	public static String KEY_POSTALCODE = "postalCode";
	public static String KEY_REGISTEREDADDRESS = "registeredAddress";
	public static String KEY_ST = "st";
	public static String KEY_STREET = "street";
	public static String KEY_TELEPHONENUMBER = "telephoneNumber";
	public static String KEY_TELEXNUMBER = "telexNumber";
	public static String KEY_USNCHANGED = "uSNChanged";
	public static String KEY_USNCREATED = "uSNCreated";
	public static String KEY_WHENCHANGED = "whenChanged";
	public static String KEY_WHENCREATED = "whenCreated";
	public static String OBJECTCATEGORY = "organization";
	public static String OBJECTCLASS = "organization";
	private String o;
	private String businessCategory;
	private String description;
	private String distinguishedName;
	private String facsimileTelephoneNumber;
	private String name;
	private String postalAddress;
	private String postalCode;
	private String registeredAddress;
	private String st;
	private String street;
	private String telephoneNumber;
	private String telexNumber;
	private String uSNChanged;
	private String uSNCreated;
	private Date whenChanged;
	private Date whenCreated;

	public String getO() {
		return this.o;
	}

	public void setO(String o) {
		this.o = o;
	}

	public String getBusinessCategory() {
		return this.businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
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

	public String getFacsimileTelephoneNumber() {
		return this.facsimileTelephoneNumber;
	}

	public void setFacsimileTelephoneNumber(String facsimileTelephoneNumber) {
		this.facsimileTelephoneNumber = facsimileTelephoneNumber;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostalAddress() {
		return this.postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getRegisteredAddress() {
		return this.registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getSt() {
		return this.st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getTelephoneNumber() {
		return this.telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getTelexNumber() {
		return this.telexNumber;
	}

	public void setTelexNumber(String telexNumber) {
		this.telexNumber = telexNumber;
	}

	public String getuSNChanged() {
		return this.uSNChanged;
	}

	public void setuSNChanged(String uSNChanged) {
		this.uSNChanged = uSNChanged;
	}

	public String getuSNCreated() {
		return this.uSNCreated;
	}

	public void setuSNCreated(String uSNCreated) {
		this.uSNCreated = uSNCreated;
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

	public String toString() {
		return "Organization [o=" + this.o + ", businessCategory=" + this.businessCategory + ", description="
				+ this.description + ", distinguishedName=" + this.distinguishedName + ", facsimileTelephoneNumber="
				+ this.facsimileTelephoneNumber + ", name=" + this.name + ", postalAddress=" + this.postalAddress
				+ ", postalCode=" + this.postalCode + ", registeredAddress=" + this.registeredAddress + ", st="
				+ this.st + ", street=" + this.street + ", telephoneNumber=" + this.telephoneNumber + ", telexNumber="
				+ this.telexNumber + ", uSNChanged=" + this.uSNChanged + ", uSNCreated=" + this.uSNCreated
				+ ", whenChanged=" + this.whenChanged + ", whenCreated=" + this.whenCreated + "]";
	}
}