package com.hotent.core.web.query.script.impl;

import com.hotent.core.web.query.entity.JudgeScript;
import com.hotent.core.web.query.script.ISqlScript;

public class SqlScript implements ISqlScript {
	public String getSQL(JudgeScript judgeScript) {
		return judgeScript == null ? "" : judgeScript.getValue();
	}
}