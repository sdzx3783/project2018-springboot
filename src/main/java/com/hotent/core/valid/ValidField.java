package com.hotent.core.valid;

import com.hotent.core.valid.Rule;
import java.util.ArrayList;
import java.util.List;

public class ValidField {
	private String formName = "";
	private String displayName = "";
	private List<Rule> ruleList = new ArrayList();

	public String getFormName() {
		return this.formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<Rule> getRuleList() {
		return this.ruleList;
	}

	public void setRuleList(List<Rule> ruleList) {
		this.ruleList = ruleList;
	}

	public void addRule(Rule rule) {
		this.ruleList.add(rule);
	}
}