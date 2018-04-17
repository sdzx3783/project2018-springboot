package com.hotent.core.db;

import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.logicalcobwebs.proxool.ProxoolDataSource;

public class ProxoolDataSourceFactory implements DataSourceFactory {
	private static ProxoolDataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public static DataSource createDataSource(Map map) {
		dataSource = new ProxoolDataSource();
		String driver = "";
		String driverUrl = "";
		String user = "";
		String alias = "";
		if (map.containsKey("driver")) {
			driver = (String) map.get("driver");
		} else {
			driver = (String) map.get("driverClassName");
		}

		dataSource.setDriver(driver);
		if (map.containsKey("driverUrl")) {
			driverUrl = (String) map.get("driverUrl");
		} else {
			driverUrl = (String) map.get("url");
		}

		dataSource.setDriverUrl(driverUrl);
		if (map.containsKey("user")) {
			user = (String) map.get("user");
		} else {
			user = (String) map.get("username");
		}

		dataSource.setUser(user);
		dataSource.setPassword((String) map.get("password"));
		if (map.containsKey("alias")) {
			alias = (String) map.get("alias");
		} else {
			(new StringBuilder()).append(driverUrl).append(user).toString();
		}

		dataSource.setAlias(driverUrl);
		if (map.containsKey("house-keeping-sleep-time")) {
			dataSource.setHouseKeepingSleepTime(Integer.parseInt(map.get("house-keeping-sleep-time").toString()));
		}

		if (map.containsKey("house-keeping-test-sql")) {
			dataSource.setHouseKeepingTestSql(map.get("house-keeping-test-sql").toString());
		} else {
			dataSource.setHouseKeepingTestSql("select 1 from SYS_ACCEPT_IP");
		}

		if (map.containsKey("maximum-active-time")) {
			dataSource.setMaximumActiveTime((long) Integer.parseInt(map.get("maximum-active-time").toString()));
		}

		if (map.containsKey("maximum-connection-count")) {
			dataSource.setMaximumConnectionCount(Integer.parseInt(map.get("maximum-connection-count").toString()));
		}

		if (map.containsKey("maximum-connection-lifetime")) {
			dataSource
					.setMaximumConnectionLifetime(Integer.parseInt(map.get("maximum-connection-lifetime").toString()));
		}

		if (map.containsKey("minimum-connection-count")) {
			dataSource.setMaximumConnectionLifetime(Integer.parseInt(map.get("minimum-connection-count").toString()));
		}

		if (map.containsKey("overload-without-refusal-lifetime")) {
			dataSource.setMaximumConnectionLifetime(
					Integer.parseInt(map.get("overload-without-refusal-lifetime").toString()));
		}

		return dataSource;
	}

	public void setProperties(Properties paramProperties) {
	}
}