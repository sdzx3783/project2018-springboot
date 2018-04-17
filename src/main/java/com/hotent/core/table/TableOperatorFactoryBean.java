package com.hotent.core.table;

import com.hotent.core.mybatis.Dialect;
import com.hotent.core.table.ITableOperator;
import com.hotent.core.table.impl.Db2TableOperator;
import com.hotent.core.table.impl.DmTableOperator;
import com.hotent.core.table.impl.H2TableOperator;
import com.hotent.core.table.impl.MysqlTableOperator;
import com.hotent.core.table.impl.OracleTableOperator;
import com.hotent.core.table.impl.SqlserverTableOperator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.core.JdbcTemplate;

public class TableOperatorFactoryBean implements FactoryBean<ITableOperator> {
	private ITableOperator tableOperator;
	private String dbType = "mysql";
	private JdbcTemplate jdbcTemplate;
	private Dialect dialect;

	public ITableOperator getObject() throws Exception {
		if (this.dbType.equals("oracle")) {
			this.tableOperator = new OracleTableOperator();
		} else if (this.dbType.equals("mssql")) {
			this.tableOperator = new SqlserverTableOperator();
		} else if (this.dbType.equals("db2")) {
			this.tableOperator = new Db2TableOperator();
		} else if (this.dbType.equals("mysql")) {
			this.tableOperator = new MysqlTableOperator();
		} else if (this.dbType.equals("h2")) {
			this.tableOperator = new H2TableOperator();
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

	public Class<?> getObjectType() {
		return ITableOperator.class;
	}

	public boolean isSingleton() {
		return true;
	}
}