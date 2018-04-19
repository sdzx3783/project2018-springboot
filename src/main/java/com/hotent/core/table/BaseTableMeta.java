package com.hotent.core.table;

import com.hotent.core.mybatis.Dialect;
import com.hotent.core.page.PageBean;
import com.hotent.core.table.TableModel;
import com.hotent.core.util.DialectUtil;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseTableMeta {
	@Resource
	private JdbcTemplate jdbcTemplate;

	public abstract TableModel getTableByName(String arg0);

	public abstract Map<String, String> getTablesByName(String arg0);

	public abstract List<TableModel> getTablesByName(String arg0, PageBean arg1) throws Exception;

	public abstract Map<String, String> getTablesByName(List<String> arg0);

	public abstract String getAllTableSql();

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