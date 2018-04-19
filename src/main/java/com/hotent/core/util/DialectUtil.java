package com.hotent.core.util;

import com.hotent.core.api.system.ISysDataSourceService;
import com.hotent.core.api.system.model.ISysDataSource;
import com.hotent.core.db.datasource.DbContextHolder;
import com.hotent.core.mybatis.Dialect;
import com.hotent.core.mybatis.dialect.DmDialect;
import com.hotent.core.mybatis.dialect.MySQLDialect;
import com.hotent.core.util.AppConfigUtil;
import com.hotent.core.util.AppUtil;

public class DialectUtil {
	public static Dialect getDialect(String dbType) throws Exception {
		Object dialect;
		if (dbType.equals("mysql")) {
			dialect = new MySQLDialect();
		} else {
			if (!dbType.equals("dm")) {
				throw new Exception("没有设置合适的数据库类型");
			}

			dialect = new DmDialect();
		}

		return (Dialect) dialect;
	}

	public static Dialect getCurrentDialect() throws Exception {
		return getDialect(DbContextHolder.getDbType());
	}

	public static Dialect getDialect(ISysDataSource sysDataSource) throws Exception {
		return getDialect(sysDataSource.getDbType());
	}

	public static Dialect getDialectByDataSourceAlias(String alias) throws Exception {
		ISysDataSourceService sysDataSourceService = (ISysDataSourceService) AppUtil
				.getBean(ISysDataSourceService.class);
		ISysDataSource sysDataSource = sysDataSourceService.getByAlias(alias);
		return sysDataSource == null ? getDialect(AppConfigUtil.get("jdbc.dbType")) : getDialect(sysDataSource);
	}
}