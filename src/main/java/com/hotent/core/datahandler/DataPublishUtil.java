package com.hotent.core.datahandler;

import com.hotent.core.datahandler.DataModel;
import com.hotent.core.datahandler.UpdDataEvent;
import com.hotent.core.util.AppUtil;

public class DataPublishUtil {
	public static void publishData(DataModel model) {
		AppUtil.publishEvent(new UpdDataEvent(model));
	}

	public static void publishData(String tableName, String pk) {
		DataModel model = new DataModel();
		model.setPk(pk);
		model.setTableName(tableName);
		AppUtil.publishEvent(new UpdDataEvent(model));
	}
}