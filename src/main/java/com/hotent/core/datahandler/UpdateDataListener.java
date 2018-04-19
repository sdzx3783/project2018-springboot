package com.hotent.core.datahandler;

import com.hotent.core.datahandler.DataModel;
import com.hotent.core.datahandler.UpdDataEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;

public class UpdateDataListener implements ApplicationListener {
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	private Map<String, List<String>> sqlMap = new HashMap();

	public void onApplicationEvent(ApplicationEvent event) {
		DataModel dataModel = (DataModel) event.getSource();
		String id = dataModel.getPk();
		String tableName = dataModel.getTableName().toLowerCase();
		List sqlList = (List) this.sqlMap.get(tableName);
		Iterator i$ = sqlList.iterator();

		while (i$.hasNext()) {
			String sql = (String) i$.next();
			this.jdbcTemplate.update(sql, new Object[]{id});
		}

	}

	public void setSqlMap(Map<String, List<String>> map) {
		Iterator i$ = map.keySet().iterator();

		while (i$.hasNext()) {
			String key = (String) i$.next();
			this.sqlMap.put(key.toLowerCase(), map.get(key));
		}

	}

}