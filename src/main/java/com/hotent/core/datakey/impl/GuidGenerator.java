package com.hotent.core.datakey.impl;

import com.hotent.core.datakey.IKeyGenerator;
import com.hotent.core.util.UniqueIdUtil;

public class GuidGenerator implements IKeyGenerator {
	public Object nextId() throws Exception {
		return UniqueIdUtil.getGuid();
	}

	public void setAlias(String alias) {
	}
}