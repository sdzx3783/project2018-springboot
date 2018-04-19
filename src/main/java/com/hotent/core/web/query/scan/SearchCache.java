package com.hotent.core.web.query.scan;

import com.hotent.core.annotion.query.Table;
import com.hotent.core.web.query.scan.TableEntity;
import com.hotent.core.web.query.scan.TableField;
import com.hotent.core.web.query.scan.TableScaner;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

public class SearchCache implements InitializingBean {
	public Logger logger = LoggerFactory.getLogger(SearchCache.class);
	private static Map<String, TableEntity> tableEntityMap = new HashMap();
	private static Map<String, TableEntity> displayTagIdMap = new HashMap();
	private static Map<String, TableEntity> tableVarMap = new HashMap();
	private Resource[] basePackage;

	public void constractSearchTableList() throws IOException, ClassNotFoundException {
		this.logger.debug("开始扫描元数据..");
		Long start = Long.valueOf(System.currentTimeMillis());
		List tableList = TableScaner.findTableScan(this.basePackage);
		this.logger.debug("扫描结束,共耗时:" + (System.currentTimeMillis() - start.longValue()) + "毫秒");
		Iterator list = tableList.iterator();

		while (list.hasNext()) {
			Class i$ = (Class) list.next();
			Table sub = (Table) i$.getAnnotation(Table.class);
			TableEntity mainTable = new TableEntity(i$.getSimpleName(), sub);
			Field[] primaryTable = i$.getDeclaredFields();
			Field[] arr$ = primaryTable;
			int len$ = primaryTable.length;

			for (int i$1 = 0; i$1 < len$; ++i$1) {
				Field field = arr$[i$1];
				com.hotent.core.annotion.query.Field qField = (com.hotent.core.annotion.query.Field) field
						.getAnnotation(com.hotent.core.annotion.query.Field.class);
				if (qField != null) {
					TableField tableField = new TableField(field, qField);
					mainTable.getTableFieldList().add(tableField);
				}
			}

			tableEntityMap.put(mainTable.getTableName(), mainTable);
			if (StringUtils.isNotEmpty(mainTable.getDisplayTagId())) {
				displayTagIdMap.put(mainTable.getDisplayTagId(), mainTable);
			}

			if (StringUtils.isNotEmpty(mainTable.getVar())) {
				tableVarMap.put(mainTable.getVar(), mainTable);
			}
		}

		Collection arg13 = tableEntityMap.values();
		Iterator arg14 = arg13.iterator();

		while (arg14.hasNext()) {
			TableEntity arg15 = (TableEntity) arg14.next();
			if (!arg15.isPrimary()) {
				String arg16 = arg15.getPrimaryTable();
				if (StringUtils.isNotEmpty(arg16)) {
					TableEntity arg17 = (TableEntity) tableEntityMap.get(arg16);
					arg17.addSubTable(arg15);
				}
			}
		}

	}

	public void afterPropertiesSet() throws Exception {
		this.constractSearchTableList();
	}

	public Resource[] getBasePackage() {
		return this.basePackage;
	}

	public void setBasePackage(Resource[] basePackage) {
		this.basePackage = basePackage;
	}

	public static Map<String, TableEntity> getTableEntityMap() {
		return tableEntityMap;
	}

	public static Map<String, TableEntity> getDisplayTagIdMap() {
		return displayTagIdMap;
	}

	public static Map<String, TableEntity> getTableVarMap() {
		return tableVarMap;
	}
}