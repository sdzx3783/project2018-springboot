package com.hotent.core.db.helper;

import javax.sql.DataSource;

public interface JdbcCommand {
	void execute(DataSource arg0) throws Exception;
}