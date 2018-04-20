package com.hotent.core.table;

import org.springframework.beans.factory.FactoryBean;

import com.hotent.core.mybatis.Dialect;
import com.hotent.core.mybatis.dialect.MySQLDialect;

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
		if (dbType.equals("mysql")) {
			dialect = new MySQLDialect();
		} else {
			throw new Exception("没有设置合适的数据库类型");
		}

		return (Dialect) dialect;
	}

	public static Dialect getDialectByDriverClassName(String driverClassName) throws Exception {
		Object dialect = null;
		if (driverClassName.equals("mysql")) {
			dialect = new MySQLDialect();
		}else {
			throw new Exception("没有设置合适的数据库类型");
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