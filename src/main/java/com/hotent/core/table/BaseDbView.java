package com.hotent.core.table;

import com.hotent.core.mybatis.Dialect;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.ColumnModel;
import com.hotent.core.table.TableModel;
import com.hotent.core.util.DialectUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseDbView {
	@Resource
	private JdbcTemplate jdbcTemplate;
	protected String currentDb;

	public abstract String getType(String arg0);

	public TableModel getModelByViewName(String viewName) throws SQLException {
		Connection conn = this.jdbcTemplate.getDataSource().getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		TableModel tableModel = new TableModel();
		tableModel.setName(viewName);
		tableModel.setComment(viewName);

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from " + viewName);
			ResultSetMetaData e = rs.getMetaData();
			int count = e.getColumnCount();

			for (int i = 1; i <= count; ++i) {
				ColumnModel columnModel = new ColumnModel();
				String columnName = e.getColumnName(i);
				String typeName = e.getColumnTypeName(i);
				String dataType = this.getType(typeName);
				columnModel.setName(columnName);
				columnModel.setColumnType(dataType);
				columnModel.setComment(columnName);
				tableModel.addColumnModel(columnModel);
			}
		} catch (SQLException arg20) {
			arg20.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (stmt != null) {
					stmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException arg19) {
				arg19.printStackTrace();
			}

		}

		return tableModel;
	}

	protected <T> List<T> getForList(String sql, PageBean pageBean, Class<T> elementType, String dbType)
			throws Exception {
		if (pageBean != null) {
			int pageSize = pageBean.getPageSize();
			int offset = pageBean.getFirst();
			Dialect dialect = DialectUtil.getDialect(dbType);
			String pageSql = dialect.getLimitString(sql, offset, pageSize);
			String totalSql = dialect.getCountSql(sql);
			List list = this.jdbcTemplate.queryForList(pageSql, elementType);
			int total = this.jdbcTemplate.queryForObject(totalSql,Integer.class);
			pageBean.setTotalCount(total);
			return list;
		} else {
			return this.jdbcTemplate.queryForList(sql, elementType);
		}
	}

	protected <T> List<T> getForList(String sql, PageBean pageBean, RowMapper<T> rowMapper, String dbType)
			throws Exception {
		if (pageBean != null) {
			int pageSize = pageBean.getPageSize();
			int offset = pageBean.getFirst();
			Dialect dialect = DialectUtil.getDialect(dbType);
			String pageSql = dialect.getLimitString(sql, offset, pageSize);
			String totalSql = dialect.getCountSql(sql);
			List list = this.jdbcTemplate.query(pageSql, rowMapper);
			int total = this.jdbcTemplate.queryForObject(totalSql,Integer.class);
			pageBean.setTotalCount(total);
			return list;
		} else {
			return this.jdbcTemplate.query(sql, rowMapper);
		}
	}
}