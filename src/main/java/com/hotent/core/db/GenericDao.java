package com.hotent.core.db;

import com.hotent.core.db.IDbSetModel;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.model.BaseModel;
import com.hotent.core.mybatis.BaseMyBatisDao;
import com.hotent.core.mybatis.IbatisSql;
import com.hotent.core.page.PageBean;
import com.hotent.core.page.PageList;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class GenericDao<E, PK extends Serializable> extends BaseMyBatisDao implements IEntityDao<E, PK> {
	@Resource
	protected JdbcTemplate jdbcTemplate;
	@Resource
	Properties configproperties;

	public abstract Class getEntityClass();

	protected String getDbType() {
		return this.configproperties.getProperty("jdbc.dbType");
	}

	public E getById(PK primaryKey) {
		String getStatement = this.getIbatisMapperNamespace() + ".getById";
		Object object = this.getSqlSessionTemplate().selectOne(getStatement, primaryKey);
		return object;
	}

	public E getUnique(String sqlKey, Object params) {
		String getStatement = this.getIbatisMapperNamespace() + "." + sqlKey;
		Object object = this.getSqlSessionTemplate().selectOne(getStatement, params);
		return object;
	}

	public Object getOne(String sqlKey, Object params) {
		String statement = this.getIbatisMapperNamespace() + "." + sqlKey;
		Object object = this.getSqlSessionTemplate().selectOne(statement, params);
		return object;
	}

	public List<E> getBySqlKey(String sqlKey, Object params, PageBean pageBean) {
		String statement = this.getIbatisMapperNamespace() + "." + sqlKey;
		return this.getList(statement, params, pageBean);
	}

	public List<E> getBySqlKey(String sqlKey, QueryFilter queryFilter) {
		new ArrayList();
		List list;
		if (queryFilter.getPageBean() == null) {
			list = this.getBySqlKey(sqlKey, (Object) queryFilter.getFilters());
		} else {
			list = this.getBySqlKey(sqlKey, queryFilter.getFilters(), queryFilter.getPageBean());
		}

		queryFilter.setForWeb();
		return list;
	}

	public List getBySqlKeyGenericity(String sqlKey, Object params) {
		String statement = this.getIbatisMapperNamespace() + "." + sqlKey;
		return this.getSqlSessionTemplate().selectList(statement, params);
	}

	public List<E> getBySqlKey(String sqlKey, Object params) {
		String statement = this.getIbatisMapperNamespace() + "." + sqlKey;
		return this.getSqlSessionTemplate().selectList(statement, params);
	}

	@Deprecated
	public List<?> getListBySqlKey(String sqlKey, Object params) {
		String statement = this.getIbatisMapperNamespace() + "." + sqlKey;
		return this.getSqlSessionTemplate().selectList(statement, params);
	}

	public List<E> getBySqlKey(String sqlKey) {
		String statement = this.getIbatisMapperNamespace() + "." + sqlKey;
		List list = this.getSqlSessionTemplate().selectList(statement);
		return list;
	}

	public int delById(PK id) {
		String delStatement = this.getIbatisMapperNamespace() + ".delById";
		int affectCount = this.getSqlSessionTemplate().delete(delStatement, id);
		return affectCount;
	}

	public int delBySqlKey(String sqlKey, Object params) {
		String delStatement = this.getIbatisMapperNamespace() + "." + sqlKey;
		int affectCount = this.getSqlSessionTemplate().delete(delStatement, params);
		return affectCount;
	}

	public void add(E entity) {
		String addStatement = this.getIbatisMapperNamespace() + ".add";
		if (entity instanceof BaseModel) {
			BaseModel baseModel = (BaseModel) entity;
			if (baseModel.getCreatetime() == null) {
				baseModel.setCreatetime(new Date());
			}

			try {
				IDbSetModel ex = (IDbSetModel) AppUtil.getBean(IDbSetModel.class);
				if (ex != null) {
					ex.setAdd(baseModel);
				}
			} catch (NoSuchBeanDefinitionException arg4) {
				this.logger.debug("add:NoSuchBeanDefinitionException" + arg4.getMessage());
			}
		}

		this.getSqlSessionTemplate().insert(addStatement, entity);
	}

	public int update(E entity) {
		String updStatement = this.getIbatisMapperNamespace() + ".update";
		if (entity instanceof BaseModel) {
			BaseModel affectCount = (BaseModel) entity;
			affectCount.setUpdatetime(new Date());

			try {
				IDbSetModel ex = (IDbSetModel) AppUtil.getBean(IDbSetModel.class);
				if (ex != null) {
					ex.setUpd(affectCount);
				}
			} catch (NoSuchBeanDefinitionException arg4) {
				this.logger.debug("UPDATE:NoSuchBeanDefinitionException" + arg4.getMessage());
			}
		}

		int affectCount1 = this.getSqlSessionTemplate().update(updStatement, entity);
		return affectCount1;
	}

	public int update(String sqlKey, Object params) {
		String updStatement = this.getIbatisMapperNamespace() + "." + sqlKey;
		int affectCount = this.getSqlSessionTemplate().update(updStatement, params);
		return affectCount;
	}

	public String getIbatisMapperNamespace() {
		return this.getEntityClass().getName();
	}

	public List<E> getList(String statementName, Object params, PageBean pageBean) {
		if (pageBean == null) {
			return this.getList(statementName, params);
		} else {
			HashMap filters = new HashMap();
			if (params != null) {
				if (params instanceof Map) {
					filters.putAll((Map) params);
				} else {
					Map rowBounds = BeanUtils.describe(params);
					filters.putAll(rowBounds);
				}
			}

			if (pageBean.isShowTotal()) {
				IbatisSql rowBounds1 = this.getIbatisSql(statementName, filters);
				this.log.info(rowBounds1.getSql());
				int list = this.jdbcTemplate.queryForInt(rowBounds1.getCountSql(), rowBounds1.getParameters());
				pageBean.setTotalCount(list);
			}

			RowBounds rowBounds2 = new RowBounds(pageBean.getFirst(), pageBean.getPageSize());
			List list1 = this.getSqlSessionTemplate().selectList(statementName, filters, rowBounds2);
			PageList pageList = new PageList();
			pageList.addAll(list1);
			pageList.setPageBean(pageBean);
			return pageList;
		}
	}

	public List<E> getList(String statementName, Object params) {
		HashMap filters = new HashMap();
		if (params != null) {
			if (params instanceof Map) {
				filters.putAll((Map) params);
			} else {
				Map ibatisSql = BeanUtils.describe(params);
				filters.putAll(ibatisSql);
			}
		}

		if (this.log.isDebugEnabled()) {
			IbatisSql ibatisSql1 = this.getIbatisSql(statementName, filters);
			this.log.debug(ibatisSql1.getSql());
		}

		return this.getSqlSessionTemplate().selectList(statementName, filters);
	}

	public List<E> getList(String statementName, QueryFilter queryFilter) {
		List list = null;
		PageBean pageBean = queryFilter.getPageBean();
		Map filters = queryFilter.getFilters();
		if (pageBean != null) {
			list = this.getList(statementName, queryFilter.getFilters(), pageBean);
		} else {
			list = this.getList(statementName, (Object) filters);
		}

		return list;
	}

	public List<E> getAll() {
		String statementName = this.getIbatisMapperNamespace() + ".getAll";
		return this.getSqlSessionTemplate().selectList(statementName, (Object) null);
	}

	public List<E> getAll(QueryFilter queryFilter) {
		String statementName = this.getIbatisMapperNamespace() + ".getAll";
		List list = this.getList(statementName, queryFilter);
		queryFilter.setForWeb();
		return list;
	}

	public int insert(String sqlKey, Object params) {
		String updStatement = this.getIbatisMapperNamespace() + "." + sqlKey;
		int affectCount = this.getSqlSessionTemplate().insert(updStatement, params);
		return affectCount;
	}
}