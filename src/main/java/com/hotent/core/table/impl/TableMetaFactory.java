package com.hotent.core.table.impl;

import com.hotent.core.api.system.ISysDataSourceService;
import com.hotent.core.api.system.model.ISysDataSource;
import com.hotent.core.db.datasource.DbContextHolder;
import com.hotent.core.table.BaseTableMeta;
import com.hotent.core.table.IDbView;
import com.hotent.core.table.impl.Db2DbView;
import com.hotent.core.table.impl.Db2TableMeta;
import com.hotent.core.table.impl.DmDbView;
import com.hotent.core.table.impl.DmTableMeta;
import com.hotent.core.table.impl.H2DbView;
import com.hotent.core.table.impl.H2TableMeta;
import com.hotent.core.table.impl.MySqlTableMeta;
import com.hotent.core.table.impl.MysqlDbView;
import com.hotent.core.table.impl.OracleDbView;
import com.hotent.core.table.impl.OracleTableMeta;
import com.hotent.core.table.impl.SqlServerTableMeta;
import com.hotent.core.table.impl.SqlserverDbView;
import com.hotent.core.util.AppConfigUtil;
import com.hotent.core.util.AppUtil;

public class TableMetaFactory {
	public static BaseTableMeta getMetaData(String dsName) throws Exception {
		String dbType = getDbTypeBySysDataSourceAlias(dsName);
		BaseTableMeta meta = null;
		if (dbType.equals("oracle")) {
			meta = (BaseTableMeta) AppUtil.getBean(OracleTableMeta.class);
		} else if (dbType.equals("mysql")) {
			meta = (BaseTableMeta) AppUtil.getBean(MySqlTableMeta.class);
		} else if (dbType.equals("mssql")) {
			meta = (BaseTableMeta) AppUtil.getBean(SqlServerTableMeta.class);
		} else if (dbType.equals("db2")) {
			meta = (BaseTableMeta) AppUtil.getBean(Db2TableMeta.class);
		} else if (dbType.equals("h2")) {
			meta = (BaseTableMeta) AppUtil.getBean(H2TableMeta.class);
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
		if (dbType.equals("oracle")) {
			meta = (IDbView) AppUtil.getBean(OracleDbView.class);
		} else if (dbType.equals("mssql")) {
			meta = (IDbView) AppUtil.getBean(SqlserverDbView.class);
		} else if (dbType.equals("mysql")) {
			meta = (IDbView) AppUtil.getBean(MysqlDbView.class);
		} else if (dbType.equals("db2")) {
			meta = (IDbView) AppUtil.getBean(Db2DbView.class);
		} else if (dbType.equals("h2")) {
			meta = (IDbView) AppUtil.getBean(H2DbView.class);
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
		String dbType = AppConfigUtil.get("jdbc.dbType");
		if (sysDataSource != null) {
			dbType = sysDataSource.getDbType();
		}

		return dbType;
	}
}