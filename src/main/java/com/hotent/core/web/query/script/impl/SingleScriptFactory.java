package com.hotent.core.web.query.script.impl;

import com.hotent.core.web.query.script.ISingleScript;
import com.hotent.core.web.query.script.impl.InScript;
import com.hotent.core.web.query.script.impl.NumberScript;
import com.hotent.core.web.query.script.impl.StringScript;

public class SingleScriptFactory {
	public static final int OPT_TYPE_NUMBER = 1;
	public static final int OPT_TYPE_STRING = 2;
	public static final int OPT_TYPE_DATE = 3;
	public static final int OPT_TYPE_DIC = 4;
	public static final int OPT_TYPE_SELECTOR = 5;

	public static ISingleScript getQueryScript(Integer optType) {
		switch (optType.intValue()) {
			case 1 :
			case 3 :
				return new NumberScript();
			case 2 :
			case 4 :
				return new StringScript();
			case 5 :
				return new InScript();
			default :
				return new NumberScript();
		}
	}
}