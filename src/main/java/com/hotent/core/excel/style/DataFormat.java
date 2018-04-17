package com.hotent.core.excel.style;

public interface DataFormat {
	String TEXT = "@";
	String GENERAL = "General";
	String NUMBER_1 = "0";
	String NUMBER_2 = "0.00";
	String NUMBER_THOUSANDS = "#,##0";
	String NUMBER_THOUSANDS1 = "#,##0.00";
	String NUMBER_5 = "=$#,##0_);=$#,##0)";
	String NUMBER_6 = "=$#,##0_);[Red]=$#,##0)";
	String NUMBER_7 = "=$#,##0.00);=$#,##0.00)";
	String NUMBER_8 = "=$#,##0.00_);[Red]=$#,##0.00)";
	String NUMBER_9 = "0%";
	String NUMBER_10 = "0.00%";
	String NUMBER_11 = "0.00E+00";
	String DATETIME = "yyyy-MM-dd HH:mm:ss";
	String DATE = "yyyy-MM-dd";
	String DATETIME_NOSECOND = "yyyy-MM-dd HH:mm";
	String TIME = "HH:mm:ss";
	String TIME_NOSECOND = "HH:mm";
	String TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";
}