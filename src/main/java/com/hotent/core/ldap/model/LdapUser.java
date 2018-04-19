package com.hotent.core.ldap.model;

import java.util.Date;

public class LdapUser implements Cloneable {
	public static String KEY_CN = "cn";
	public static String KEY_C = "c";
	public static String KEY_CO = "co";
	public static String KEY_COMPANY = "company";
	public static String KEY_COUNTRYCODE = "countryCode";
	public static String KEY_DEPARTMENT = "department";
	public static String KEY_DESCRIPTION = "description";
	public static String KEY_DISPLAYNAME = "displayName";
	public static String KEY_DISTINGUISHEDNAME = "distinguishedName";
	public static String KEY_FACSIMILETELEPHONENUMBER = "facsimileTelephoneNumber";
	public static String KEY_HOMEPHONE = "homePhone";
	public static String KEY_INITIALS = "initials";
	public static String KEY_L = "l";
	public static String KEY_MAIL = "mail";
	public static String KEY_NAME = "name";
	public static String KEY_POSTALADDRESS = "postalAddress";
	public static String KEY_POSTALCODE = "postalCode";
	public static String KEY_POSTOFFICEBOX = "postOfficeBox";
	public static String KEY_SAMACCOUNTNAME = "sAMAccountName";
	public static String KEY_SN = "sn";
	public static String KEY_ST = "st";
	public static String KEY_STREETADDRESS = "streetAddress";
	public static String KEY_TELEPHONENUMBER = "telephoneNumber";
	public static String KEY_TITLE = "title";
	public static String KEY_USERPRINCIPALNAME = "userPrincipalName";
	public static String KEY_WHENCHANGED = "whenChanged";
	public static String KEY_WHENCREATED = "whenCreated";
	public static String KEY_WWWHOMEPAGE = "wWWHomePage";
	public static String KEY_USERACCOUNTCONTROL = "userAccountControl";
	public static String KEY_GIVENNAME = "givenName";
	public static String OBJECTCATEGORY = "organizationalPerson";
	public static String OBJECTCLASS = "organizationalPerson";
	private String cn;
	private String c;
	private String co;
	private String company;
	private String countryCode;
	private String department;
	private String description;
	private String displayName;
	private String distinguishedName;
	private String facsimileTelephoneNumber;
	private String homePhone;
	private String initials;
	private String l;
	private String mail;
	private String name;
	private String givenName;
	private String postalAddress;
	private String postalCode;
	private String postOfficeBox;
	private String sAMAccountName;
	private String sn;
	private String st;
	private String streetAddress;
	private String telephoneNumber;
	private String title;
	private String userPrincipalName;
	private Date whenChanged;
	private Date whenCreated;
	private String wWWHomePage;
	private String userAccountControl;

	public static String getKEY_CN() {
		return KEY_CN;
	}

	public static void setKEY_CN(String kEY_CN) {
		KEY_CN = kEY_CN;
	}

	public static String getKEY_C() {
		return KEY_C;
	}

	public static void setKEY_C(String kEY_C) {
		KEY_C = kEY_C;
	}

	public static String getKEY_CO() {
		return KEY_CO;
	}

	public static void setKEY_CO(String kEY_CO) {
		KEY_CO = kEY_CO;
	}

	public static String getKEY_COMPANY() {
		return KEY_COMPANY;
	}

	public static void setKEY_COMPANY(String kEY_COMPANY) {
		KEY_COMPANY = kEY_COMPANY;
	}

	public static String getKEY_COUNTRYCODE() {
		return KEY_COUNTRYCODE;
	}

	public static void setKEY_COUNTRYCODE(String kEY_COUNTRYCODE) {
		KEY_COUNTRYCODE = kEY_COUNTRYCODE;
	}

	public static String getKEY_DEPARTMENT() {
		return KEY_DEPARTMENT;
	}

	public static void setKEY_DEPARTMENT(String kEY_DEPARTMENT) {
		KEY_DEPARTMENT = kEY_DEPARTMENT;
	}

	public static String getKEY_DESCRIPTION() {
		return KEY_DESCRIPTION;
	}

	public static void setKEY_DESCRIPTION(String kEY_DESCRIPTION) {
		KEY_DESCRIPTION = kEY_DESCRIPTION;
	}

	public static String getKEY_DISPLAYNAME() {
		return KEY_DISPLAYNAME;
	}

	public static void setKEY_DISPLAYNAME(String kEY_DISPLAYNAME) {
		KEY_DISPLAYNAME = kEY_DISPLAYNAME;
	}

	public static String getKEY_DISTINGUISHEDNAME() {
		return KEY_DISTINGUISHEDNAME;
	}

	public static void setKEY_DISTINGUISHEDNAME(String kEY_DISTINGUISHEDNAME) {
		KEY_DISTINGUISHEDNAME = kEY_DISTINGUISHEDNAME;
	}

	public static String getKEY_FACSIMILETELEPHONENUMBER() {
		return KEY_FACSIMILETELEPHONENUMBER;
	}

	public static void setKEY_FACSIMILETELEPHONENUMBER(String kEY_FACSIMILETELEPHONENUMBER) {
		KEY_FACSIMILETELEPHONENUMBER = kEY_FACSIMILETELEPHONENUMBER;
	}

	public static String getKEY_HOMEPHONE() {
		return KEY_HOMEPHONE;
	}

	public static void setKEY_HOMEPHONE(String kEY_HOMEPHONE) {
		KEY_HOMEPHONE = kEY_HOMEPHONE;
	}

	public static String getKEY_INITIALS() {
		return KEY_INITIALS;
	}

	public static void setKEY_INITIALS(String kEY_INITIALS) {
		KEY_INITIALS = kEY_INITIALS;
	}

	public static String getKEY_L() {
		return KEY_L;
	}

	public static void setKEY_L(String kEY_L) {
		KEY_L = kEY_L;
	}

	public static String getKEY_MAIL() {
		return KEY_MAIL;
	}

	public static void setKEY_MAIL(String kEY_MAIL) {
		KEY_MAIL = kEY_MAIL;
	}

	public static String getKEY_NAME() {
		return KEY_NAME;
	}

	public static void setKEY_NAME(String kEY_NAME) {
		KEY_NAME = kEY_NAME;
	}

	public static String getKEY_POSTALADDRESS() {
		return KEY_POSTALADDRESS;
	}

	public static void setKEY_POSTALADDRESS(String kEY_POSTALADDRESS) {
		KEY_POSTALADDRESS = kEY_POSTALADDRESS;
	}

	public static String getKEY_POSTALCODE() {
		return KEY_POSTALCODE;
	}

	public static void setKEY_POSTALCODE(String kEY_POSTALCODE) {
		KEY_POSTALCODE = kEY_POSTALCODE;
	}

	public static String getKEY_POSTOFFICEBOX() {
		return KEY_POSTOFFICEBOX;
	}

	public static void setKEY_POSTOFFICEBOX(String kEY_POSTOFFICEBOX) {
		KEY_POSTOFFICEBOX = kEY_POSTOFFICEBOX;
	}

	public static String getKEY_SAMACCOUNTNAME() {
		return KEY_SAMACCOUNTNAME;
	}

	public static void setKEY_SAMACCOUNTNAME(String kEY_SAMACCOUNTNAME) {
		KEY_SAMACCOUNTNAME = kEY_SAMACCOUNTNAME;
	}

	public static String getKEY_SN() {
		return KEY_SN;
	}

	public static void setKEY_SN(String kEY_SN) {
		KEY_SN = kEY_SN;
	}

	public static String getKEY_ST() {
		return KEY_ST;
	}

	public static void setKEY_ST(String kEY_ST) {
		KEY_ST = kEY_ST;
	}

	public static String getKEY_STREETADDRESS() {
		return KEY_STREETADDRESS;
	}

	public static void setKEY_STREETADDRESS(String kEY_STREETADDRESS) {
		KEY_STREETADDRESS = kEY_STREETADDRESS;
	}

	public static String getKEY_TELEPHONENUMBER() {
		return KEY_TELEPHONENUMBER;
	}

	public static void setKEY_TELEPHONENUMBER(String kEY_TELEPHONENUMBER) {
		KEY_TELEPHONENUMBER = kEY_TELEPHONENUMBER;
	}

	public static String getKEY_TITLE() {
		return KEY_TITLE;
	}

	public static void setKEY_TITLE(String kEY_TITLE) {
		KEY_TITLE = kEY_TITLE;
	}

	public static String getKEY_USERPRINCIPALNAME() {
		return KEY_USERPRINCIPALNAME;
	}

	public static void setKEY_USERPRINCIPALNAME(String kEY_USERPRINCIPALNAME) {
		KEY_USERPRINCIPALNAME = kEY_USERPRINCIPALNAME;
	}

	public static String getKEY_WHENCHANGED() {
		return KEY_WHENCHANGED;
	}

	public static void setKEY_WHENCHANGED(String kEY_WHENCHANGED) {
		KEY_WHENCHANGED = kEY_WHENCHANGED;
	}

	public static String getKEY_WHENCREATED() {
		return KEY_WHENCREATED;
	}

	public static void setKEY_WHENCREATED(String kEY_WHENCREATED) {
		KEY_WHENCREATED = kEY_WHENCREATED;
	}

	public static String getKEY_WWWHOMEPAGE() {
		return KEY_WWWHOMEPAGE;
	}

	public static void setKEY_WWWHOMEPAGE(String kEY_WWWHOMEPAGE) {
		KEY_WWWHOMEPAGE = kEY_WWWHOMEPAGE;
	}

	public static String getOBJECTCATEGORY() {
		return OBJECTCATEGORY;
	}

	public static void setOBJECTCATEGORY(String oBJECTCATEGORY) {
		OBJECTCATEGORY = oBJECTCATEGORY;
	}

	public static String getOBJECTCLASS() {
		return OBJECTCLASS;
	}

	public static void setOBJECTCLASS(String oBJECTCLASS) {
		OBJECTCLASS = oBJECTCLASS;
	}

	public String getCn() {
		return this.cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getC() {
		return this.c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getCo() {
		return this.co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public String getHomePhone() {
		return this.homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getInitials() {
		return this.initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getL() {
		return this.l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return this.name;
	}

	public String getGivenName() {
		return this.givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
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

	public String getPostOfficeBox() {
		return this.postOfficeBox;
	}

	public void setPostOfficeBox(String postOfficeBox) {
		this.postOfficeBox = postOfficeBox;
	}

	public String getsAMAccountName() {
		return this.sAMAccountName;
	}

	public void setsAMAccountName(String sAMAccountName) {
		this.sAMAccountName = sAMAccountName;
	}

	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getSt() {
		return this.st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getStreetAddress() {
		return this.streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getTelephoneNumber() {
		return this.telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserPrincipalName() {
		return this.userPrincipalName;
	}

	public void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
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

	public String getwWWHomePage() {
		return this.wWWHomePage;
	}

	public void setwWWHomePage(String wWWHomePage) {
		this.wWWHomePage = wWWHomePage;
	}

	public String getUserAccountControl() {
		return this.userAccountControl;
	}

	public void setUserAccountControl(String userAccountControl) {
		this.userAccountControl = userAccountControl;
	}

	public String toString() {
		return "LdapUser [cn=" + this.cn + ", c=" + this.c + ", co=" + this.co + ", company=" + this.company
				+ ", countryCode=" + this.countryCode + ", department=" + this.department + ", description="
				+ this.description + ", displayName=" + this.displayName + ", distinguishedName="
				+ this.distinguishedName + ", facsimileTelephoneNumber=" + this.facsimileTelephoneNumber
				+ ", homePhone=" + this.homePhone + ", initials=" + this.initials + ", l=" + this.l + ", mail="
				+ this.mail + ", name=" + this.name + ", postalAddress=" + this.postalAddress + ", postalCode="
				+ this.postalCode + ", postOfficeBox=" + this.postOfficeBox + ", sAMAccountName=" + this.sAMAccountName
				+ ", sn=" + this.sn + ", st=" + this.st + ", streetAddress=" + this.streetAddress + ", telephoneNumber="
				+ this.telephoneNumber + ", title=" + this.title + ", userPrincipalName=" + this.userPrincipalName
				+ ", whenChanged=" + this.whenChanged + ", whenCreated=" + this.whenCreated + ", wWWHomePage="
				+ this.wWWHomePage + "]";
	}

	public boolean isAccountDisable() {
		int accountControl = Integer.parseInt(this.userAccountControl);
		int num = this.getDigitHex(accountControl, 0);
		num -= 2;
		boolean result = num == 9;
		result = result || num == 8;
		result = result || num == 1;
		result = result || num == 0;
		return result;
	}

	private int getDigitHex(int value, int offset) {
		char[] arr = Integer.toHexString(value).toCharArray();
		offset = arr.length - 1 - offset;
		int intValue = Integer.parseInt(String.valueOf(arr[offset]));
		return intValue;
	}

	public Object clone() throws CloneNotSupportedException {
		LdapUser obj = null;

		try {
			obj = (LdapUser) super.clone();
		} catch (CloneNotSupportedException arg2) {
			arg2.printStackTrace();
		}

		return obj;
	}
}