package com.hotent.core.table.impl;

import com.hotent.core.api.system.ISysDataSourceService;
import com.hotent.core.api.system.model.ISysDataSource;
import com.hotent.core.db.datasource.DbContextHolder;
import com.hotent.core.table.BaseTableMeta;
import com.hotent.core.table.IDbView;
import com.hotent.core.table.impl.DmDbView;
import com.hotent.core.table.impl.DmTableMeta;
import com.hotent.core.table.impl.MySqlTableMeta;
import com.hotent.core.table.impl.MysqlDbView;
import com.hotent.core.util.AppUtil;
import com.makshi.framework.mainframe.config.properties.HotentCoreProperties;

public class TableMetaFactory {
	public static BaseTableMeta getMetaData(String dsName) throws Exception {
		String dbType = getDbTypeBySysDataSourceAlias(dsName);
		BaseTableMeta meta = null;
		if (dbType.equals("mysql")) {
			meta = (BaseTableMeta) AppUtil.getBean(MySqlTableMeta.class);
		} else {
			if (!dbType.equals("dm")) {
				throw new Exception("未知的数据库类型");
			}

			meta = (BaseTableMeta) AppUtil.getBean(DmTableMeta.class);
		}

		DbContextHolder.setDataSource(dsName);
		return meta;
	}

	public static IDbView getDbView(String dsName) throws Exception {
		String dbType = getDbTypeBySysDataSourceAlias(dsName);
		IDbView meta = null;
		if (dbType.equals("mysql")) {
			meta = (IDbView) AppUtil.getBean(MysqlDbView.class);
		} else {
			if (!dbType.equals("dm")) {
				throw new Exception("未知的数据库类型");
			}

			meta = (IDbView) AppUtil.getBean(DmDbView.class);
		}

		DbContextHolder.setDataSource(dsName);
		return meta;
	}

	private static String getDbTypeBySysDataSourceAlias(String alias) {
		ISysDataSource sysDataSource = null;
		ISysDataSourceService sysDataSourceService = (ISysDataSourceService) AppUtil
				.getBean(ISysDataSourceService.class);
		sysDataSource = sysDataSourceService.getByAlias(alias);
		HotentCoreProperties bean = AppUtil.getBean(HotentCoreProperties.class);
		String dbType = bean.getJdbc().getDbType();
		if (sysDataSource != null) {
			dbType = sysDataSource.getDbType();
		}

		return dbType;
	}
}