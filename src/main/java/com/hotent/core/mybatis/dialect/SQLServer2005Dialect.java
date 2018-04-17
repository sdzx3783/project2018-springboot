package com.hotent.core.mybatis.dialect;

import com.hotent.core.mybatis.Dialect;
import com.hotent.core.util.AppUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class SQLServer2005Dialect extends Dialect {
	public boolean supportsLimit() {
		return true;
	}

	public boolean supportsLimitOffset() {
		return true;
	}

	public String getLimitString(String querySqlString, int offset, String offsetPlaceholder, int limit,
			String limitPlaceholder) {
		int start = offset + 1;
		StringBuffer pagingBuilder = new StringBuffer();
		querySqlString = querySqlString.toLowerCase().trim();
		String orderby = getOrderByPart(querySqlString);
		String distinctStr = "";
		String sqlPartString = querySqlString;
		if (querySqlString.startsWith("select")) {
			if (querySqlString.indexOf("distinct") != -1) {
				distinctStr = " DISTINCT ";
				sqlPartString = querySqlString.replaceFirst("^select(\\s+(ALL|distinct))?", "");
			} else {
				sqlPartString = querySqlString.replaceFirst("^select", "");
			}
		}

		pagingBuilder.append(sqlPartString);
		String fields = getFields(querySqlString);
		if (orderby == null || orderby.length() == 0) {
			orderby = "ORDER BY CURRENT_TIMESTAMP";
		}

		StringBuffer result = new StringBuffer();
		result.append("WITH query AS (SELECT ").append("TOP 100 PERCENT ").append(" ROW_NUMBER() OVER (")
				.append(orderby).append(") as __row_number__, ").append(pagingBuilder)
				.append(") SELECT  " + distinctStr + fields + " FROM query WHERE __row_number__ BETWEEN ").append(start)
				.append(" AND ").append(offset + limit);
		return result.toString();
	}

	static String getOrderByPart(String sql) {
		String loweredString = sql.toLowerCase();
		loweredString = loweredString.replaceAll("(?i)order\\s*by", "order by");
		int orderByIndex = loweredString.indexOf("order by");
		return orderByIndex != -1 ? sql.substring(orderByIndex) : "";
	}

	private static int getParameterCount(String sql) {
		Pattern regex = Pattern.compile("\\?", 66);
		int i = 0;

		for (Matcher regexMatcher = regex.matcher(sql); regexMatcher.find(); ++i) {
			;
		}

		return i;
	}

	private static String getFields(String sql) {
		Connection conn = null;
		JdbcTemplate jdbcTemplate = null;

		String ps;
		try {
			sql = sql.replaceAll("where", "where 1=0 and ");
			int ex = getParameterCount(sql);
			jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
			conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			PreparedStatement arg13 = conn.prepareStatement(sql);

			for (int rs = 1; rs <= ex; ++rs) {
				arg13.setObject(1, (Object) null);
			}

			ResultSetMetaData arg14 = arg13.getMetaData();
			int colCount = arg14.getColumnCount();
			String str = "";

			for (int i = 1; i <= colCount; ++i) {
				if (i == 1) {
					str = arg14.getColumnName(i);
				} else {
					str = str + "," + arg14.getColumnName(i);
				}
			}

			String arg15 = str;
			return arg15;
		} catch (Exception arg11) {
			ps = " * ";
		} finally {
			DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
		}

		return ps;
	}

	public String getCountSql(String sql) {
		String sqlCount = sql.trim();
		Pattern pattern = Pattern.compile("^SELECT(\\s+(ALL|DISTINCT))?", 2);
		Matcher matcher = pattern.matcher(sqlCount);
		if (matcher.find()) {
			String matStr = matcher.group();
			sqlCount = sqlCount.replaceFirst(matStr, matStr + " TOP 100 PERCENT");
			sqlCount = "select count(*) amount from (" + sqlCount + ") A";
			return sqlCount;
		} else {
			throw new UnsupportedOperationException("SQL语句拼接出现错误！");
		}
	}
}