package com.hotent.core.scheduler;

import com.hotent.core.api.log.ISysJobLog;
import com.hotent.core.api.log.ISysJobLogService;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.UniqueIdUtil;
import java.util.Date;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public abstract class BaseJob implements Job {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public abstract void executeJob(JobExecutionContext arg0) throws Exception;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		String jobName = context.getJobDetail().getKey().getName();
		String trigName = "directExec";
		Trigger trig = context.getTrigger();
		if (trig != null) {
			trigName = trig.getKey().getName();
		}

		Date strStartTime = new Date();
		long startTime = System.currentTimeMillis();

		try {
			this.executeJob(context);
			long ex1 = System.currentTimeMillis();
			Date strEndTime = new Date();
			long strEndTime2 = (ex1 - startTime) / 1000L;
			this.addLog(jobName, trigName, strStartTime, strEndTime, strEndTime2, "任务执行成功!", 1);
		} catch (Exception arg15) {
			Exception ex = arg15;
			long endTime = System.currentTimeMillis();
			Date strEndTime1 = new Date();
			long runTime = (endTime - startTime) / 1000L;

			try {
				this.addLog(jobName, trigName, strStartTime, strEndTime1, runTime, ex.toString(), 0);
			} catch (Exception arg14) {
				arg14.printStackTrace();
				this.log.error("执行任务出错:" + arg14.getMessage());
			}
		}

	}

	private void addLog(String jobName, String trigName, Date strStartTime, Date strEndTime, long runTime,
			String content, int state) throws Exception {
		ISysJobLogService logService = (ISysJobLogService) AppUtil.getBean(ISysJobLogService.class);
		ISysJobLog jobLog = logService.getJobLog(jobName, trigName, strStartTime, strEndTime, runTime, content, state);
		Long id = Long.valueOf(UniqueIdUtil.genId());
		jobLog.setLogId(id);
		logService.addLog(jobLog);
	}
}