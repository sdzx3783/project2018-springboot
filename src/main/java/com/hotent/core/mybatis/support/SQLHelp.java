package com.hotent.core.mybatis.support;

import com.hotent.core.mybatis.Dialect;
import com.hotent.core.mybatis.support.DefaultParameterHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLHelp {
	private static Logger logger = LoggerFactory.getLogger(SQLHelp.class);

	public static int getCount(String sql, MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql,
			Dialect dialect) throws SQLException {
		String count_sql = dialect.getCountSql(sql);
		logger.debug("Total count SQL [{}] ", count_sql);
		logger.debug("Total count Parameters: {} ", parameterObject);
		Connection connection = null;
		PreparedStatement countStmt = null;
		ResultSet rs = null;

		int arg10;
		try {
			connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
			countStmt = connection.prepareStatement(count_sql);
			DefaultParameterHandler handler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
			handler.setParameters(countStmt);
			rs = countStmt.executeQuery();
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}

			logger.debug("Total count: {}", Integer.valueOf(count));
			arg10 = count;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} finally {
				try {
					if (countStmt != null) {
						countStmt.close();
					}
				} finally {
					if (connection != null && !connection.isClosed()) {
						connection.close();
					}

				}

			}

		}

		return arg10;
	}
}