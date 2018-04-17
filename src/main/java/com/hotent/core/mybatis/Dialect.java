package com.hotent.core.mybatis;

import com.hotent.core.web.query.entity.FieldSort;
import java.util.Iterator;
import java.util.List;

public class Dialect {
	public boolean supportsLimit() {
		return false;
	}

	public boolean supportsLimitOffset() {
		return this.supportsLimit();
	}

	public String getLimitString(String sql, int offset, int limit) {
		return this.getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
	}

	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
		throw new UnsupportedOperationException("paged queries not supported");
	}

	public String getCountSql(String sql) {
		return "select count(1) from (" + sql + ") tmp_count";
	}

	public String getSortString(String sql, List<FieldSort> orders) {
		if (orders != null && !orders.isEmpty()) {
			StringBuffer buffer = (new StringBuffer("select * from (")).append(sql).append(") temp_order order by ");
			Iterator i$ = orders.iterator();

			while (i$.hasNext()) {
				FieldSort order = (FieldSort) i$.next();
				if (order != null) {
					buffer.append(order.toString()).append(", ");
				}
			}

			buffer.delete(buffer.length() - 2, buffer.length());
			return buffer.toString();
		} else {
			return sql;
		}
	}
}