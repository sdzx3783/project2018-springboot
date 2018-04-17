package com.hotent.core.mybatis;

import com.hotent.core.table.ITableOperator;
import com.hotent.core.util.AppUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IbatisSql {
	private String sql;
	private Object[] parameters;
	private Class resultClass;

	public Class getResultClass() {
		return this.resultClass;
	}

	public void setResultClass(Class resultClass) {
		this.resultClass = resultClass;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return this.sql;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public Object[] getParameters() {
		return this.parameters;
	}

	public String getCountSql() {
		String sqlCount = this.sql;
		ITableOperator tableOperator = (ITableOperator) AppUtil.getBean(ITableOperator.class);
		if (tableOperator.getDbType().equals("mssql")) {
			sqlCount = sqlCount.trim();
			Pattern pattern = Pattern.compile("^SELECT(\\s+(ALL|DISTINCT))?", 2);
			Matcher matcher = pattern.matcher(sqlCount);
			if (!matcher.find()) {
				throw new UnsupportedOperationException("SQL语句拼接出现错误！");
			}

			String matStr = matcher.group();
			sqlCount = sqlCount.toUpperCase().replaceFirst(matStr.toUpperCase(),
					matStr.toUpperCase() + " TOP 100 PERCENT");
		}

		sqlCount = "select count(*) amount from (" + sqlCount + ") A";
		return sqlCount;
	}
}