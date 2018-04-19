package com.hotent.core.table;

import com.hotent.core.page.PageBean;
import com.hotent.core.table.TableModel;
import java.sql.SQLException;
import java.util.List;

public interface IDbView {
	void createOrRep(String arg0, String arg1) throws Exception;

	List<String> getViews(String arg0) throws SQLException;

	List<String> getViews(String arg0, PageBean arg1) throws SQLException, Exception;

	TableModel getModelByViewName(String arg0) throws SQLException;

	List<TableModel> getViewsByName(String arg0, PageBean arg1) throws Exception;
}