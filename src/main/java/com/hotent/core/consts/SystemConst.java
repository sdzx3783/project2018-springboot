package com.hotent.core.consts;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SystemConst {
	private static final String ROLE_SUPER = "ROLE_SUPER";
	private static final String ROLE_PUBLIC = "ROLE_PUBLIC";
	private static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
	public static final GrantedAuthority ROLE_GRANT_SUPER = new SimpleGrantedAuthority("ROLE_SUPER");
	public static final ConfigAttribute ROLE_CONFIG_PUBLIC = new SecurityConfig("ROLE_PUBLIC");
	public static final ConfigAttribute ROLE_CONFIG_ANONYMOUS = new SecurityConfig("ROLE_ANONYMOUS");
	private static final long serialVersionUID = 1L;
	public static final Long BEGIN_DEMID = Long.valueOf(1L);
	public static final Long BEGIN_ORGID = Long.valueOf(1L);
	public static final Integer BEGIN_DEPTH = Integer.valueOf(0);
	public static final String BEGIN_PATH = "1";
	public static final Short BEGIN_TYPE = Short.valueOf((short) 1);
	public static final Long BEGIN_ORGSUPID = Long.valueOf(-1L);
	public static final Short BEGIN_SN = Short.valueOf((short) 1);
	public static final Short BEGIN_FROMTYPE = Short.valueOf((short) 0);
	public static final String SEARCH_BY_ROL = "1";
	public static final String SEARCH_BY_ORG = "2";
	public static final String SEARCH_BY_POS = "3";
	public static final String SEARCH_BY_ONL = "4";
	public static final Short UN_LOCKED = Short.valueOf((short) 0);
	public static final Short LOCKED = Short.valueOf((short) 1);
	public static final Short UN_EXPIRED = Short.valueOf((short) 0);
	public static final Short EXPIRED = Short.valueOf((short) 1);
	public static final Short STATUS_OK = Short.valueOf((short) 1);
	public static final Short STATUS_NO = Short.valueOf((short) 0);
	public static final Short STATUS_Del = Short.valueOf((short) -1);
	public static final Short FROMTYPE_DB = Short.valueOf((short) 0);
	public static final Short FROMTYPE_AD = Short.valueOf((short) 1);
	public static final Short FROMTYPE_AD_SET = Short.valueOf((short) 2);
	public static final Long SYSTEMUSERID = Long.valueOf(0L);
	public static final String SYSTEMUSERNAME = "系统";
	public static final String CGLIB_PREFIX = "$cglib_prop_";
}