package com.hotent.core.mybatis.dialect;

import com.hotent.core.mybatis.Dialect;

public class H2Dialect extends Dialect {
	public boolean supportsLimit() {
		return true;
	}

	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
		return (new StringBuffer(sql.length() + 40)).append(sql)
				.append(offset > 0
						? " limit " + limitPlaceholder + " offset " + offsetPlaceholder
						: " limit " + limitPlaceholder)
				.toString();
	}

	public boolean supportsLimitOffset() {
		return true;
	}
}