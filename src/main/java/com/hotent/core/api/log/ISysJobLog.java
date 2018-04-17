package com.hotent.core.api.log;

import java.util.Date;

public interface ISysJobLog {
	void setLogId(Long arg0);

	Long getLogId();

	void setJobName(String arg0);

	String getJobName();

	void setTrigName(String arg0);

	String getTrigName();

	void setStartTime(Date arg0);

	Date getStartTime();

	void setEndTime(Date arg0);

	Date getEndTime();

	void setContent(String arg0);

	String getContent();

	void setState(int arg0);

	int getState();

	void setRunTime(Long arg0);

	Long getRunTime();
}