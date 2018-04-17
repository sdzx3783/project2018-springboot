package com.hotent.core.datakey.impl;

import com.hotent.core.datakey.IKeyGenerator;
import com.hotent.core.util.UniqueIdUtil;

public class TimeGenerator implements IKeyGenerator {
	public Object nextId() throws Exception {
		return Long.valueOf(UniqueIdUtil.genId());
	}

	public void setAlias(String alias) {
	}
}