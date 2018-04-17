package com.hotent.core.mybatis;

import com.hotent.core.mybatis.IbatisSql;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

public abstract class BaseMyBatisDao extends DaoSupport {
	protected final Log log = LogFactory.getLog(this.getClass());
	private SqlSessionFactory sqlSessionFactory;
	private SqlSessionTemplate sqlSessionTemplate;

	protected void checkDaoConfig() throws IllegalArgumentException {
		Assert.notNull(this.sqlSessionFactory, "sqlSessionFactory must be not null");
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return this.sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return this.sqlSessionTemplate;
	}

	public IbatisSql getIbatisSql(String id, Object parameterObject) {
		IbatisSql ibatisSql = new IbatisSql();
		Configuration configuration = this.sqlSessionFactory.getConfiguration();
		Collection coll = configuration.getMappedStatementNames();
		MappedStatement ms = configuration.getMappedStatement(id);
		BoundSql boundSql = ms.getBoundSql(parameterObject);
		TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
		List ResultMaps = ms.getResultMaps();
		if (ResultMaps != null && ResultMaps.size() > 0) {
			ResultMap parameterMappings = (ResultMap) ms.getResultMaps().get(0);
			ibatisSql.setResultClass(parameterMappings.getType());
		}

		ibatisSql.setSql(boundSql.getSql());
		List arg16 = boundSql.getParameterMappings();
		if (arg16 != null) {
			Object[] parameterArray = new Object[arg16.size()];
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);

			for (int i = 0; i < arg16.size(); ++i) {
				ParameterMapping parameterMapping = (ParameterMapping) arg16.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					String propertyName = parameterMapping.getProperty();
					Object value;
					if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}

					parameterArray[i] = value;
				}
			}

			ibatisSql.setParameters(parameterArray);
		}

		return ibatisSql;
	}
}