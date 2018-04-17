package com.hotent.core.api.log;

import com.hotent.core.api.log.ISysJobLog;
import java.util.Date;

public interface ISysJobLogService {
	void addLog(ISysJobLog arg0);

	ISysJobLog getJobLog(String arg0, String arg1, Date arg2, Date arg3, long arg4, String arg6, int arg7);
}