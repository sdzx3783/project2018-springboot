package com.hotent.core.table;

import com.hotent.core.table.BaseTableMeta;
import com.hotent.core.table.impl.TableMetaFactory;
import org.springframework.beans.factory.FactoryBean;

public class TableMetaFactoryBean implements FactoryBean<BaseTableMeta> {
	private BaseTableMeta tableMeta;
	private String dbType = "mysql";

	public BaseTableMeta getObject() throws Exception {
		this.tableMeta = TableMetaFactory.getMetaData("dataSource_Default");
		return this.tableMeta;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public Class<?> getObjectType() {
		return BaseTableMeta.class;
	}

	public boolean isSingleton() {
		return true;
	}
}