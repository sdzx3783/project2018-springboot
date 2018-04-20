package com.hotent.core.util;

import com.hotent.core.util.AppUtil;
import com.hotent.core.util.FileUtil;
import com.makshi.framework.mainframe.config.properties.GenIdProperties;

import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;

public class UniqueIdUtil {
	private static long adjust = 1L;
	private static long nextId = 0L;
	private static long lastId = -1L;
	private static JdbcTemplate jdbcTemplate;
	private static boolean hasError = false;
	private static Logger logger = LoggerFactory.getLogger(UniqueIdUtil.class);

	private static void init() {
		try {
			jdbcTemplate = (JdbcTemplate) AppUtil.getBean("jdbcTemplateSn");
			GenIdProperties configproperties = (GenIdProperties) AppUtil.getBean("genIdProperties");
			
			String strAdjust = configproperties.getAdjust();
			if (strAdjust != null) {
				adjust = (long) Integer.parseInt(strAdjust);
			}
		} catch (Exception arg2) {
			adjust = 1L;
		}

	}

	private static void getNextIdBlock() {
		if (jdbcTemplate == null) {
			init();
		}

		Long bound = Long.valueOf(-1L);
		Integer incremental = Integer.valueOf(-1);
		String sql = "SELECT bound,incremental FROM SYS_DB_ID T WHERE T.ID=?";
		String upSql = "UPDATE SYS_DB_ID  SET BOUND=? WHERE ID=?";

		try {
			Map ex = jdbcTemplate.queryForMap(sql, new Object[]{Long.valueOf(adjust)});
			bound = Long.valueOf(Long.parseLong(ex.get("bound").toString()));
			incremental = Integer.valueOf(Integer.parseInt(ex.get("incremental").toString()));
			if (hasError) {
				lastId = nextId + (long) incremental.intValue();
			} else {
				nextId = bound.longValue();
				lastId = bound.longValue() + (long) incremental.intValue();
			}

			jdbcTemplate.update(upSql, new Object[]{Long.valueOf(lastId), Long.valueOf(adjust)});
			hasError = false;
		} catch (EmptyResultDataAccessException arg4) {
			insertNewComputer();
		} catch (Exception arg5) {
			hasError = true;
		}

	}

	private static void insertNewComputer() {
		try {
			lastId = 10000L;
			String e = "INSERT INTO SYS_DB_ID (id,incremental,bound) VALUES(" + adjust + ",10000," + lastId + ")";
			jdbcTemplate.update(e);
		} catch (Exception arg0) {
			arg0.printStackTrace();
		}

	}

	public static synchronized long genId() {
		if (lastId <= nextId) {
			getNextIdBlock();
		}

		int _nextId = (int) nextId++;
		return _nextId + adjust * 10000000000000L;
	}

	public static final String getGuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static void main(String[] args) throws Exception {
	}

	public static String getEnvir() {
		String run_env = "prd";

		try {
			run_env = System.getenv("RUN_ENV");
		} catch (NullPointerException arg1) {
			logger.info("没有配置环境变量RUN_ENV，默认使用生成机(prd)");
		} catch (SecurityException arg2) {
			logger.error("读取环境变量错误" + arg2.getMessage());
		}

		return run_env;
	}
}