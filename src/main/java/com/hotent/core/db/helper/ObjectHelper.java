package com.hotent.core.db.helper;

import com.hotent.core.annotion.ClassDescription;
import com.hotent.core.annotion.FieldDescription;
import com.hotent.core.db.helper.ColumnModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectHelper<T> {
	private T obj;

	public void setModel(T obj) {
		this.obj = obj;
	}

	public String getTableName() {
		Class cls = this.obj.getClass();
		ClassDescription clsDesc = (ClassDescription) cls.getAnnotation(ClassDescription.class);
		return clsDesc == null ? cls.getSimpleName() : clsDesc.tableName();
	}

	public List<ColumnModel> getColumns() {
		ArrayList list = new ArrayList();
		Class cls = this.obj.getClass();
		Field[] fields = cls.getDeclaredFields();

		for (int i = 0; i < fields.length; ++i) {
			Field fld = fields[i];
			ColumnModel column = new ColumnModel();
			column.setPropery(fld.getName());
			FieldDescription fldDesc = (FieldDescription) fld.getAnnotation(FieldDescription.class);
			if (fldDesc == null) {
				column.setColumnName(fld.getName());
				column.setPk(false);
			} else {
				column.setColumnName(fldDesc.columnName());
				column.setPk(fldDesc.pk());
				column.setCanUpd(fldDesc.canUpd());
			}

			list.add(column);
		}

		return list;
	}

	public ColumnModel getPk(List<ColumnModel> list) {
		Object columnModel = null;
		int len = list.size();

		for (int i = 0; i < len; ++i) {
			ColumnModel model = (ColumnModel) list.get(i);
			if (model.getPk()) {
				return model;
			}
		}

		return (ColumnModel) columnModel;
	}

	private List<ColumnModel> getCommonCols(List<ColumnModel> list) {
		ArrayList cols = new ArrayList();
		int len = list.size();

		for (int i = 0; i < len; ++i) {
			ColumnModel model = (ColumnModel) list.get(i);
			if (!model.getPk()) {
				cols.add(model);
			}
		}

		return cols;
	}

	private String[] getInsertColumns() {
		List list = this.getColumns();
		String cols = "";
		String vals = "";
		int len = list.size();
		String[] aryStr = new String[2];

		for (int i = 0; i < len; ++i) {
			ColumnModel column = (ColumnModel) list.get(i);
			if (i < len - 1) {
				cols = cols + column.getColumnName() + ",";
				vals = vals + ":" + column.getPropery() + ",";
			} else {
				cols = cols + column.getColumnName();
				vals = vals + ":" + column.getPropery();
			}
		}

		aryStr[0] = cols;
		aryStr[1] = vals;
		return aryStr;
	}

	public String getUpdSql() {
		List list = this.getColumns();
		List commonList = this.getCommonCols(list);
		ColumnModel pk = this.getPk(list);
		String tableName = this.getTableName();
		String sql = "update ";
		sql = sql + tableName + " set ";
		String tmp = "";
		int len = commonList.size();

		for (int i = 0; i < len; ++i) {
			ColumnModel model = (ColumnModel) list.get(i);
			if (model.getCanUpd()) {
				tmp = tmp + model.getColumnName() + "=:" + model.getPropery() + ",";
			}
		}

		if (tmp.length() > 0) {
			tmp = tmp.substring(0, tmp.length() - 1);
		}

		sql = sql + tmp;
		sql = sql + " where " + pk.getColumnName() + "=:" + pk.getPropery();
		return sql;
	}

	public String getDelSql() {
		List list = this.getColumns();
		String tableName = this.getTableName();
		ColumnModel column = this.getPk(list);
		String sql = "delete from " + tableName + " where " + column.getColumnName() + "=:" + column.getPropery();
		return sql;
	}

	public String getDetailSql() {
		List list = this.getColumns();
		String tableName = this.getTableName();
		ColumnModel column = this.getPk(list);
		String sql = "select a.* from " + tableName + " a where " + column.getColumnName() + "=:" + column.getPropery();
		return sql;
	}

	public String getAddSql() {
		String tableName = this.getTableName();
		String[] aryCol = this.getInsertColumns();
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append(tableName);
		sb.append("(");
		sb.append(aryCol[0]);
		sb.append(")");
		sb.append(" values (");
		sb.append(aryCol[1]);
		sb.append(")");
		return sb.toString();
	}
}