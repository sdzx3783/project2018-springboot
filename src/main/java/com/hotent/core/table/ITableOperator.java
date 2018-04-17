package com.hotent.core.table;

import com.hotent.core.model.TableIndex;
import com.hotent.core.mybatis.Dialect;
import com.hotent.core.page.PageBean;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract interface ITableOperator
{
  public abstract String getDbType();
  
  public abstract void setJdbcTemplate(JdbcTemplate paramJdbcTemplate);
  
  public abstract void createTable(TableModel paramTableModel)
    throws SQLException;
  
  public abstract void dropTable(String paramString);
  
  public abstract void updateTableComment(String paramString1, String paramString2)
    throws SQLException;
  
  public abstract void addColumn(String paramString, ColumnModel paramColumnModel)
    throws SQLException;
  
  public abstract void updateColumn(String paramString1, String paramString2, ColumnModel paramColumnModel)
    throws SQLException;
  
  public abstract void addForeignKey(String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract void createIndex(String paramString1, String paramString2);
  
  public abstract void dropForeignKey(String paramString1, String paramString2);
  
  public abstract void createIndex(TableIndex paramTableIndex)
    throws SQLException;
  
  public abstract void dropIndex(String paramString1, String paramString2);
  
  public abstract TableIndex getIndex(String paramString1, String paramString2);
  
  public abstract List<TableIndex> getIndexesByTable(String paramString);
  
  public abstract List<TableIndex> getIndexesByFuzzyMatching(String paramString1, String paramString2, Boolean paramBoolean);
  
  public abstract List<TableIndex> getIndexesByFuzzyMatching(String paramString1, String paramString2, Boolean paramBoolean, PageBean paramPageBean);
  
  public abstract void rebuildIndex(String paramString1, String paramString2);
  
  public abstract List<String> getPKColumns(String paramString)
    throws SQLException;
  
  public abstract Map<String, List<String>> getPKColumns(List<String> paramList)
    throws SQLException;
  
  public abstract void setDialect(Dialect paramDialect);
  
  public abstract void setDbType(String paramString);
  
  public abstract boolean isTableExist(String paramString);
}


/* Location:           C:\Users\sun\Desktop\反编译class\hotentcore-1.3.6.8.jar
 * Qualified Name:     com.hotent.core.table.ITableOperator
 * JD-Core Version:    0.7.0.1
 */