package com.hotent.core.web.query.script.impl;

import com.hotent.core.web.query.entity.JudgeSingle;
import com.hotent.core.web.query.script.ISingleScript;

public class InScript implements ISingleScript {
	public String getSQL(JudgeSingle judgeSingle) {
		StringBuilder sb = new StringBuilder();
		if ("1".equals(judgeSingle.getCompare())) {
			sb.append(judgeSingle.getFixFieldName()).append(" in (").append("").append(judgeSingle.getValue())
					.append(")");
		} else if ("2".equals(judgeSingle.getCompare())) {
			sb.append(judgeSingle.getFixFieldName()).append(" not in (").append("").append(judgeSingle.getValue())
					.append(")");
		} else if ("3".equals(judgeSingle.getCompare())) {
			sb.append(judgeSingle.getFixFieldName()).append(" = ").append("").append(judgeSingle.getValue());
		} else if ("4".equals(judgeSingle.getCompare())) {
			sb.append(judgeSingle.getFixFieldName()).append(" !=").append("").append(judgeSingle.getValue());
		}

		return sb.toString();
	}
}