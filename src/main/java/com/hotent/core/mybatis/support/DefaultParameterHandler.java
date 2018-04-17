package com.hotent.core.mybatis.support;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

public class DefaultParameterHandler implements ParameterHandler {
	private final TypeHandlerRegistry typeHandlerRegistry;
	private final MappedStatement mappedStatement;
	private final Object parameterObject;
	private BoundSql boundSql;
	private Configuration configuration;

	public DefaultParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
		this.mappedStatement = mappedStatement;
		this.configuration = mappedStatement.getConfiguration();
		this.typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
		this.parameterObject = parameterObject;
		this.boundSql = boundSql;
	}

	public Object getParameterObject() {
		return this.parameterObject;
	}

	public void setParameters(PreparedStatement ps) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(this.mappedStatement.getParameterMap().getId());
		List parameterMappings = this.boundSql.getParameterMappings();
		if (parameterMappings != null) {
			MetaObject metaObject = this.parameterObject == null
					? null
					: this.configuration.newMetaObject(this.parameterObject);

			for (int i = 0; i < parameterMappings.size(); ++i) {
				ParameterMapping parameterMapping = (ParameterMapping) parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					String propertyName = parameterMapping.getProperty();
					Object value;
					if (this.boundSql.hasAdditionalParameter(propertyName)) {
						value = this.boundSql.getAdditionalParameter(propertyName);
					} else if (this.parameterObject == null) {
						value = null;
					} else if (this.typeHandlerRegistry.hasTypeHandler(this.parameterObject.getClass())) {
						value = this.parameterObject;
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}

					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName
								+ " of statement " + this.mappedStatement.getId());
					}

					JdbcType jdbcType = parameterMapping.getJdbcType();
					if (value == null && jdbcType == null) {
						jdbcType = this.configuration.getJdbcTypeForNull();
					}

					typeHandler.setParameter(ps, i + 1, value, jdbcType);
				}
			}
		}

	}
}