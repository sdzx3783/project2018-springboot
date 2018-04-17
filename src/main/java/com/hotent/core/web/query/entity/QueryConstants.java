package com.hotent.core.web.query.entity;

public interface QueryConstants {
	String QUERY_AND = "AND";
	String QUERY_OR = "OR";
	String QUERY_NOT = "NOT";
	String SQL_RELATION_EQUAL = "=";
	String SQL_RELATION_GT = ">";
	String SQL_RELATION_GT_EQUAL = ">=";
	String SQL_RELATION_LT = "<";
	String SQL_RELATION_LT_EQUAL = "<=";
	String SQL_RELATION_LIKE = " LIKE ";
	String SORT_ASC = "ASC";
	String SORT_DESC = "DESC";
	int JUDGE_SINGLE = 1;
	int JUDGE_SCOPE = 2;
	int JUDGE_SCRIPT = 3;
	String FILTER_KEY = "__FILTERKEY__";
	String FILTER_FLAG = "__FILTER_FLAG__";
	String IS_QUERY = "__IS_QUERY__";
	short IS_QUERY_YES = 0;
	short IS_QUERY_NOT = 1;
}