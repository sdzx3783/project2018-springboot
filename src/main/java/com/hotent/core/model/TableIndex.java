package com.hotent.core.model;

import com.hotent.core.model.BaseModel;
import java.util.List;

public class TableIndex extends BaseModel implements Cloneable {
	private static final long serialVersionUID = 1L;
	public static String INDEX_TYPE_BITMAP = "BITMAP";
	public static String INDEX_TYPE_BTREE = "BTREE";
	public static String INDEX_TYPE_FUNCTION = "FUNCTION";
	public static String INDEX_TYPE_HEAP = "HEAP";
	public static String INDEX_TYPE_CLUSTERED = "CLUSTERED";
	public static String INDEX_TYPE_NONCLUSTERED = "NONCLUSTERED";
	public static String INDEX_TYPE_XML = "XML";
	public static String INDEX_TYPE_SPATIAL = "SPATIAL";
	public static String INDEX_TYPE_REG = "REGULAR";
	public static String INDEX_TYPE_DIM = "DIMENSIONBLOCK";
	public static String INDEX_TYPE_BLOK = "BLOCK";
	public static String TABLE_TYPE_TABLE = "TABLE";
	public static String TABLE_TYPE_VIEW = "VIEW";
	public static String INDEX_STATUS_VALIDATE = "VALIDATE";
	public static String INDEX_STATUS_INVALIDATE = "INVALIDATE";
	private String indexTable;
	private String tableType;
	private String indexName;
	private String indexType;
	private String indexStatus;
	private List<String> indexFields;
	private boolean unique;
	private String indexDdl;
	private String indexComment;
	private boolean pkIndex;

	public String getIndexName() {
		return this.indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexType() {
		return this.indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public List<String> getIndexFields() {
		return this.indexFields;
	}

	public void setIndexFields(List<String> indexFields) {
		this.indexFields = indexFields;
	}

	public String getIndexComment() {
		return this.indexComment;
	}

	public void setIndexComment(String indexComment) {
		this.indexComment = indexComment;
	}

	public String getIndexTable() {
		return this.indexTable;
	}

	public void setIndexTable(String indexTable) {
		this.indexTable = indexTable;
	}

	public String getIndexStatus() {
		return this.indexStatus;
	}

	public void setIndexStatus(String indexStatus) {
		this.indexStatus = indexStatus;
	}

	public String getTableType() {
		return this.tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getIndexDdl() {
		return this.indexDdl;
	}

	public void setIndexDdl(String indexDdl) {
		this.indexDdl = indexDdl;
	}

	public boolean isUnique() {
		return this.unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public boolean isPkIndex() {
		return this.pkIndex;
	}

	public void setPkIndex(boolean pkIndex) {
		this.pkIndex = pkIndex;
	}

	public String toString() {
		return "BpmFormTableIndex [indexTable=" + this.indexTable + ", tableType=" + this.tableType + ", indexName="
				+ this.indexName + ", indexType=" + this.indexType + ", indexStatus=" + this.indexStatus
				+ ", indexFields=" + this.indexFields + ", unique=" + this.unique + ", indexDdl=" + this.indexDdl
				+ ", indexComment=" + this.indexComment + ", pkIndex=" + this.pkIndex + "]";
	}
}