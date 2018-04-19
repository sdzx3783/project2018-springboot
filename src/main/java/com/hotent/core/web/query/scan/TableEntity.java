package com.hotent.core.web.query.scan;

import com.hotent.core.annotion.query.Table;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.query.scan.SearchCache;
import com.hotent.core.web.query.scan.TableField;
import com.hotent.core.web.query.scan.TableEntity.1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TableEntity {
	private String tableName;
	private String var;
	private String displayTagId;
	private String pk;
	private String comment;
	private boolean isPrimary = true;
	private String relation = "";
	private String primaryTable = "";
	private List<TableField> tableFieldList = new ArrayList();
	private List<TableEntity> subTableList = new ArrayList();
	private static ThreadLocal<Map<String, Object>> queryFilterLocal = new ThreadLocal();

	public TableEntity() {
	}

	public TableEntity(String claName, Table table) {
		this.tableName = table.name();
		this.var = StringUtils.isNotEmpty(table.var()) ? table.var() : StringUtil.makeFirstLetterLowerCase(claName);
		this.displayTagId = StringUtils.isNotEmpty(table.displayTagId()) ? table.displayTagId() : table.var() + "Item";
		this.pk = table.pk();
		this.comment = table.comment();
		this.isPrimary = table.isPrimary();
		this.relation = table.relation();
		this.primaryTable = table.primaryTable();
	}

	public String getTableName() {
		return this.tableName;
	}

	public String getVar() {
		return this.var;
	}

	public String getDisplayTagId() {
		return this.displayTagId;
	}

	public String getPk() {
		return this.pk;
	}

	public String getComment() {
		return this.comment;
	}

	public List<TableField> getTableFieldList() {
		return this.tableFieldList;
	}

	public void setTableFieldList(List<TableField> tableFieldList) {
		this.tableFieldList = tableFieldList;
	}

	public static void setQueryFilterLocal(Map<String, Object> map) {
		queryFilterLocal.set(map);
	}

	public static Map<String, Object> getQueryFilterLocal() {
		return (Map) queryFilterLocal.get();
	}

	public static void removeQueryFilterLocal() {
		queryFilterLocal.remove();
	}

	public static List<TableEntity> getAll(QueryFilter queryFilter) {
		ArrayList tableEntitylist = new ArrayList(SearchCache.getTableEntityMap().values());
		Object queryList = new ArrayList(tableEntitylist);
		if (BeanUtils.isNotEmpty(queryFilter.getFilters())) {
			List queryList1 = getQueryList((List) queryList, tableEntitylist, queryFilter);
			queryList = getSortList(queryList1, queryFilter);
		}

		PageBean pageBean = queryFilter.getPageBean();
		int total = ((List) queryList).size();
		int pageSize = pageBean.getPageSize();
		int currentPage = pageBean.getCurrentPage();
		int fromIndex = pageSize * (currentPage - 1);
		int toIndex = pageSize * currentPage > total ? total : pageSize * currentPage;
		List list = ((List) queryList).subList(fromIndex, toIndex);
		pageBean.setTotalCount(total);
		queryFilter.setForWeb();
		return list;
	}

	private static List<TableEntity> getSortList(List<TableEntity> queryList, QueryFilter queryFilter) {
      Object orderField = queryFilter.getFilters().get("orderField");
      Object orderSeq = queryFilter.getFilters().get("orderSeq");
      if(!BeanUtils.isEmpty(orderField) && !BeanUtils.isEmpty(orderSeq)) {
         setQueryFilterLocal(queryFilter.getFilters());
         1 comparator = new 1();
         Collections.sort(queryList, comparator);
         removeQueryFilterLocal();
         return queryList;
      } else {
         return queryList;
      }
   }

	private static List<TableEntity> getQueryList(List<TableEntity> queryList, List<TableEntity> list,
			QueryFilter queryFilter) {
		Object tableName = queryFilter.getFilters().get("tableName");
		Object description = queryFilter.getFilters().get("comment");
		if (BeanUtils.isEmpty(tableName) && BeanUtils.isEmpty(description)) {
			return queryList;
		} else {
			ArrayList queryList1 = new ArrayList();
			int type = getQueryType(tableName, description);
			Iterator i$ = list.iterator();

			while (i$.hasNext()) {
				TableEntity tableEntity = (TableEntity) i$.next();
				boolean flag = isContainsQuery(tableEntity, tableName, description, type);
				if (flag) {
					queryList1.add(tableEntity);
				}
			}

			return queryList1;
		}
	}

	private static boolean isContainsQuery(TableEntity tableEntity, Object tableName, Object description, int type) {
		switch (type) {
			case 1 :
				return isContainsQuery(tableEntity, tableName, description);
			case 2 :
				return StringUtils.containsIgnoreCase(tableEntity.getTableName(), tableName.toString());
			case 3 :
				return StringUtils.containsIgnoreCase(tableEntity.getComment(), description.toString());
			default :
				return false;
		}
	}

	private static boolean isContainsQuery(TableEntity tableEntity, Object tableName, Object description) {
		return isContainsQuery(tableEntity, tableName, Integer.valueOf(2))
				&& isContainsQuery(tableEntity, description, Integer.valueOf(3));
	}

	private static int getQueryType(Object tableName, Object description) {
		byte type = 0;
		if (BeanUtils.isNotEmpty(tableName) && BeanUtils.isNotEmpty(description)) {
			type = 1;
		} else if (BeanUtils.isNotEmpty(tableName) && BeanUtils.isEmpty(description)) {
			type = 2;
		} else if (BeanUtils.isEmpty(tableName) && BeanUtils.isNotEmpty(description)) {
			type = 3;
		}

		return type;
	}

	public boolean isPrimary() {
		return this.isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getRelation() {
		return this.relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getPrimaryTable() {
		return this.primaryTable;
	}

	public void setPrimaryTable(String primaryTable) {
		this.primaryTable = primaryTable;
	}

	public List<TableEntity> getSubTableList() {
		return this.subTableList;
	}

	public void setSubTableList(List<TableEntity> subTableList) {
		this.subTableList = subTableList;
	}

	public void addSubTable(TableEntity tableEnt) {
		this.subTableList.add(tableEnt);
	}

	public static Map<String, TableEntity> getSubTableMap(TableEntity tableEntity) {
		List subTableList = tableEntity.getSubTableList();
		HashMap map = new HashMap();
		if (BeanUtils.isEmpty(subTableList)) {
			return map;
		} else {
			Iterator i$ = subTableList.iterator();

			while (i$.hasNext()) {
				TableEntity table = (TableEntity) i$.next();
				map.put(table.getTableName(), table);
			}

			return map;
		}
	}

	public String toString() {
		return (new ToStringBuilder(this)).append("tableName", this.tableName).append("pk", this.pk)
				.append("comment", this.comment).append("tableFieldList.size", this.tableFieldList.size()).toString();
	}
}