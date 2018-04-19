package com.hotent.core.web.query.script.impl;

import com.hotent.core.web.query.entity.JudgeScope;
import com.hotent.core.web.query.script.IScopeScript;
import com.hotent.core.web.query.script.ISingleScript;
import com.hotent.core.web.query.script.impl.SingleScriptFactory;

public class ScopeScript implements IScopeScript {
	public String getSQL(JudgeScope judgeScope) {
		StringBuilder sb = new StringBuilder();
		ISingleScript queryScript = SingleScriptFactory.getQueryScript(judgeScope.getOptType());
		String scriptBegin = queryScript.getSQL(judgeScope.getBeginJudge());
		String scriptEnd = queryScript.getSQL(judgeScope.getEndJudge());
		sb.append(" (").append(scriptBegin).append(" ").append(judgeScope.getRelation()).append(" ").append(scriptEnd)
				.append(") ");
		return sb.toString();
	}
}