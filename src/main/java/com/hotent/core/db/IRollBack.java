package com.hotent.core.db;

import java.util.Map;

public interface IRollBack {
	Object execute(String arg0, Map<String, Object> arg1);
}