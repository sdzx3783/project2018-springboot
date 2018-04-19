package com.hotent.core.table;

import com.hotent.core.mybatis.Dialect;
import com.hotent.core.table.ITableOperator;
import com.hotent.core.table.impl.DmTableOperator;
import com.hotent.core.table.impl.MysqlTableOperator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.core.JdbcTemplate;

public class TableOperatorFactoryBean implements FactoryBean {
	private ITableOperator tableOperator;
	private String dbType = "mysql";
	private JdbcTemplate jdbcTemplate;
	private Dialect dialect;

	@Override
	public Object getObject() throws Exception {
		if (this.dbType.equals("mysql")) {
			this.tableOperator = new MysqlTableOperator();
		} else {
			if (!this.dbType.equals("dm")) {
				throw new Exception("没有设置合适的数据库类型");
			}

			this.tableOperator = new DmTableOperator();
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

	@Override
	public Class<?> getObjectType() {
		return ITableOperator.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}