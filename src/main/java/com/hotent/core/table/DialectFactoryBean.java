package com.hotent.core.table;

import com.hotent.core.mybatis.Dialect;
import com.hotent.core.mybatis.dialect.DB2Dialect;
import com.hotent.core.mybatis.dialect.DmDialect;
import com.hotent.core.mybatis.dialect.H2Dialect;
import com.hotent.core.mybatis.dialect.MySQLDialect;
import com.hotent.core.mybatis.dialect.OracleDialect;
import com.hotent.core.mybatis.dialect.SQLServer2005Dialect;
import org.springframework.beans.factory.FactoryBean;

public class DialectFactoryBean implements FactoryBean<Dialect> {
	private Dialect dialect;
	private String dbType = "mysql";

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public Dialect getObject() throws Exception {
		this.dialect = getDialect(this.dbType);
		return this.dialect;
	}

	public static Dialect getDialect(String dbType) throws Exception {
		Object dialect = null;
		if (dbType.equals("oracle")) {
			dialect = new OracleDialect();
		} else if (dbType.equals("mssql")) {
			dialect = new SQLServer2005Dialect();
		} else if (dbType.equals("db2")) {
			dialect = new DB2Dialect();
		} else if (dbType.equals("mysql")) {
			dialect = new MySQLDialect();
		} else if (dbType.equals("h2")) {
			dialect = new H2Dialect();
		} else {
			if (!dbType.equals("dm")) {
				throw new Exception("没有设置合适的数据库类型");
			}

			dialect = new DmDialect();
		}

		return (Dialect) dialect;
	}

	public static Dialect getDialectByDriverClassName(String driverClassName) throws Exception {
		Object dialect = null;
		if (driverClassName.contains("oracle")) {
			dialect = new OracleDialect();
		} else if (driverClassName.equals("sqlserver")) {
			dialect = new SQLServer2005Dialect();
		} else if (driverClassName.equals("db2")) {
			dialect = new DB2Dialect();
		} else if (driverClassName.equals("mysql")) {
			dialect = new MySQLDialect();
		} else if (driverClassName.equals("h2")) {
			dialect = new H2Dialect();
		} else {
			if (!driverClassName.equals("dm")) {
				throw new Exception("没有设置合适的数据库类型");
			}

			dialect = new DmDialect();
		}

		return (Dialect) dialect;
	}

	public Class<?> getObjectType() {
		return Dialect.class;
	}

	public boolean isSingleton() {
		return true;
	}
}