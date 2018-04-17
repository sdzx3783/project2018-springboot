package com.hotent.core.db;

import com.hotent.core.mybatis.Dialect;
import com.hotent.core.page.PageBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcDao {
	private JdbcTemplate jdbcTemplate;
	private Dialect dialect;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public List getPage(int currentPage, int pageSize, String sql, Map paraMap, PageBean pageBean) throws Exception {
		int offset = (currentPage - 1) * pageSize;
		String pageSql = this.dialect.getLimitString(sql, offset, pageSize);
		String totalSql = this.dialect.getCountSql(sql);
		List list = this.queryForList(pageSql, paraMap);
		int total = this.queryForInt(totalSql, paraMap);
		pageBean.setTotalCount(total);
		return list;
	}

	public List getPage(PageBean pageBean, String sql, RowMapper rowMap) throws Exception {
		int pageSize = pageBean.getPageSize();
		int offset = pageBean.getFirst();
		HashMap map = new HashMap();
		String pageSql = this.dialect.getLimitString(sql, offset, pageSize);
		String totalSql = this.dialect.getCountSql(sql);
		List list = this.queryForList(pageSql, map, rowMap);
		int total = this.queryForInt(totalSql, map);
		pageBean.setTotalCount(total);
		return list;
	}

	public <T> T getPage(String sql, ResultSetExtractor<T> rse, PageBean pageBean) throws Exception {
		int pageSize = pageBean.getPageSize();
		int offset = pageBean.getFirst();
		String pageSql = this.dialect.getLimitString(sql, offset, pageSize);
		String totalSql = this.dialect.getCountSql(sql);
		Object result = this.jdbcTemplate.query(pageSql, rse);
		int total = this.jdbcTemplate.queryForInt(totalSql);
		pageBean.setTotalCount(total);
		return result;
	}

	public List queryForList(String sql, Map parameter, RowMapper rowMap) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
		return template.query(sql, parameter, rowMap);
	}

	public List queryForList(String sql, Map parameter) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
		return template.queryForList(sql, parameter);
	}

	public int queryForInt(String sql, Map parameter) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
		return template.queryForInt(sql, parameter);
	}

	public int upd(String sql) {
		return this.jdbcTemplate.update(sql);
	}
}