package com.hotent.core.db.datasource;

import com.hotent.core.bpm.util.BpmConst;
import com.hotent.core.db.RollbackJdbcTemplate;
import com.hotent.core.db.datasource.DataSourceUtil;
import com.hotent.core.db.datasource.JdbcTemplateUtil.1;
import com.hotent.core.mybatis.Dialect;
import com.hotent.core.page.PageBean;
import com.hotent.core.page.PageList;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.DialectUtil;
import com.hotent.core.util.StringUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcTemplateUtil {
	public static void execute(String sql) {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		jdbcTemplate.execute(sql);
	}

	public static void execute(String dsName, String sql) throws Exception {
		DataSource dataSource = DataSourceUtil.getDataSourceByAlias(dsName);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute(sql);
	}

	public static NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
		return new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
	}

	public static NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(String alias) {
		try {
			return new NamedParameterJdbcTemplate(DataSourceUtil.getDataSourceByAlias(alias));
		} catch (Exception arg1) {
			return null;
		}
	}

	public static PageList getPage(String alias, PageBean pageBean, String sql, RowMapper rowMap) {
		int pageSize = pageBean.getPageSize();
		int offset = pageBean.getFirst();
		HashMap map = new HashMap();
		Dialect dialect = null;

		try {
			dialect = DialectUtil.getDialectByDataSourceAlias(alias);
		} catch (Exception arg13) {
			return null;
		}

		String pageSql = dialect.getLimitString(sql, offset, pageSize);
		String totalSql = dialect.getCountSql(sql);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
		namedParameterJdbcTemplate = getNamedParameterJdbcTemplate(alias);
		List list = namedParameterJdbcTemplate.query(pageSql, map, rowMap);
		int total = namedParameterJdbcTemplate.queryForInt(totalSql, map);
		pageBean.setTotalCount(total);
		PageList pageList = new PageList();
		pageList.setPageBean(pageBean);
		pageList.addAll(list);
		return pageList;
	}

	public static <T> T getPage(String alias, String sql, ResultSetExtractor<T> rse, PageBean pageBean,
			Map<String, Object> params) {
		Object result = null;
		NamedParameterJdbcTemplate template = null;
		template = getNamedParameterJdbcTemplate(alias);
		if (pageBean != null) {
			int pageSize = pageBean.getPageSize();
			int offset = pageBean.getFirst();
			Dialect dialect = null;

			try {
				dialect = DialectUtil.getDialectByDataSourceAlias(alias);
			} catch (Exception arg12) {
				return null;
			}

			String pageSql = dialect.getLimitString(sql, offset, pageSize);
			String totalSql = dialect.getCountSql(sql);
			result = template.query(pageSql, params, rse);
			int total = template.queryForInt(totalSql, params);
			pageBean.setTotalCount(total);
		} else {
			result = template.query(sql, params, rse);
		}

		return result;
	}

	public static PageList getPage(String alias, int currentPage, int pageSize, String sql, Map paraMap) {
		int offset = (currentPage - 1) * pageSize;
		Dialect dialect = null;

		try {
			dialect = DialectUtil.getDialectByDataSourceAlias(alias);
		} catch (Exception arg13) {
			return null;
		}

		String pageSql = dialect.getLimitString(sql, offset, pageSize);
		String totalSql = dialect.getCountSql(sql);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
		namedParameterJdbcTemplate = getNamedParameterJdbcTemplate(alias);
		List list = namedParameterJdbcTemplate.queryForList(pageSql, paraMap);
		int total = namedParameterJdbcTemplate.queryForInt(totalSql, paraMap);
		PageBean pageBean = new PageBean(currentPage, pageSize);
		pageBean.setTotalCount(total);
		PageList pageList = new PageList();
		pageList.addAll(list);
		pageList.setPageBean(pageBean);
		return pageList;
	}

	public static List getPage(String alias, String sql, Map<?, ?> paraMap, PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		return getPage(alias, currentPage, pageSize, sql, paraMap);
	}

	public static JdbcTemplate getNewJdbcTemplate(String alias) throws Exception {
		if (!StringUtil.isEmpty(alias) && !alias.equals("dataSource_Default")
				&& !alias.equals(BpmConst.LOCAL_DATASOURCE)) {
			Map map = DataSourceUtil.getDataSources();
			DataSource ds = (DataSource) map.get(alias);
			return new JdbcTemplate(ds);
		} else {
			return (JdbcTemplate) AppUtil.getBean("jdbcTemplate");
		}
	}

	public static Boolean executeSql(String sql, boolean rollback) {
      JdbcTemplate jdbcTemplate = (JdbcTemplate)AppUtil.getBean("jdbcTemplate");
      RollbackJdbcTemplate rollbackJdbcTemplate = (RollbackJdbcTemplate)AppUtil.getBean("rollbackJdbcTemplate");
      if(!rollback) {
         jdbcTemplate.execute(sql);
         return Boolean.valueOf(true);
      } else {
         HashMap param = new HashMap();
         Boolean b = (Boolean)rollbackJdbcTemplate.executeRollBack(new 1(jdbcTemplate), sql, param);
         return b;
      }
   }
}