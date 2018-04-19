package com.hotent.core.table;

import com.hotent.core.table.BaseTableMeta;
import com.hotent.core.table.impl.TableMetaFactory;
import org.springframework.beans.factory.FactoryBean;

public class TableMetaFactoryBean implements FactoryBean {
	private BaseTableMeta tableMeta;
	private String dbType = "mysql";

	@Override
	public Object getObject() throws Exception {
		this.tableMeta = TableMetaFactory.getMetaData("dataSource_Default");
		return this.tableMeta;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	@Override
	public Class<?> getObjectType() {
		return BaseTableMeta.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}