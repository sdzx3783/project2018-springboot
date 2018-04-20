package com.hotent.core.table;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hotent.core.mybatis.Dialect;
import com.hotent.core.table.impl.MysqlTableOperator;

public class TableOperatorFactoryBean implements FactoryBean<ITableOperator> {
	private ITableOperator tableOperator;
	private String dbType = "mysql";
	private JdbcTemplate jdbcTemplate;
	private Dialect dialect;

	public ITableOperator getObject() throws Exception {
		if (this.dbType.equals("mysql")) {
			this.tableOperator = new MysqlTableOperator();
		} else {
			throw new Exception("没有设置合适的数据库类型");
		}

		this.tableOperator.setDbType(this.dbType);
		this.tableOperator.setJdbcTemplate(this.jdbcTemplate);
		this.tableOperator.setDialect(this.dialect);
		return this.tableOperator;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public Class<?> getObjectType() {
		return ITableOperator.class;
	}

	public boolean isSingleton() {
		return true;
	}
}