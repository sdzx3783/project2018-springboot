package com.hotent.core.api.org.model;

public interface ISysOrg {
	Long BEGIN_DEMID = Long.valueOf(1L);
	Long BEGIN_ORGID = Long.valueOf(1L);
	Integer BEGIN_DEPTH = Integer.valueOf(0);
	String BEGIN_PATH = "1";
	Short BEGIN_TYPE = Short.valueOf(1);
	Long BEGIN_ORGSUPID = Long.valueOf(-1L);
	Short BEGIN_SN = Short.valueOf(1);
	Short BEGIN_FROMTYPE = Short.valueOf(0);
	Short FROMTYPE_AD = Short.valueOf(1);
	Short FROMTYPE_DB = Short.valueOf(0);
	Integer IS_LEAF_N = Integer.valueOf(1);
	Integer IS_LEAF_Y = Integer.valueOf(0);
	String IS_PARENT_N = "false";
	String IS_PARENT_Y = "true";

	void setOrgId(Long arg0);

	Long getOrgId();

	void setDemId(Long arg0);

	Long getDemId();

	String getDemName();

	void setDemName(String arg0);

	void setOrgName(String arg0);

	String getOrgName();

	void setOrgDesc(String arg0);

	String getOrgDesc();

	void setOrgPathname(String arg0);

	String getOrgPathname();

	void setOrgSupId(Long arg0);

	Long getOrgSupId();

	String getOrgSupName();

	void setOrgSupName(String arg0);

	void setPath(String arg0);

	String getPath();

	void setDepth(Integer arg0);

	Integer getDepth();

	void setOrgType(Long arg0);

	Long getOrgType();

	Short getFromType();

	void setFromType(Short arg0);
}