package com.hotent.core.db.datasource;

import com.hotent.core.api.system.ISysDataSourceService;
import com.hotent.core.api.system.model.ISysDataSource;
import com.hotent.core.util.AppConfigUtil;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.StringUtil;

public class DbContextHolder {
	private static final ThreadLocal<String> contextHolderAlias = new ThreadLocal();
	private static final ThreadLocal<String> contextHolderDbType = new ThreadLocal();

	public static void setDataSource(String dbAlias, String dbType) {
		contextHolderAlias.set(dbAlias);
		contextHolderDbType.set(dbType);
	}

	public static void setDefaultDataSource() {
		String dbType = AppConfigUtil.get("jdbc.dbType");
		contextHolderAlias.set("dataSource_Default");
		contextHolderDbType.set(dbType);
	}

	public static String getDataSource() {
		String str = (String) contextHolderAlias.get();
		return str;
	}

	public static String getDbType() {
		String str = (String) contextHolderDbType.get();
		return str;
	}

	public static void clearDataSource() {
		contextHolderAlias.remove();
		contextHolderDbType.remove();
	}

	public static void setDataSource(String alias) {
		if (!StringUtil.isEmpty(alias)) {
			ISysDataSourceService sourceService = (ISysDataSourceService) AppUtil.getBean(ISysDataSourceService.class);
			ISysDataSource sysDataSource = sourceService.getByAlias(alias);
			if (sysDataSource != null) {
				setDataSource(alias, sysDataSource.getDbType());
			}
		}
	}
}