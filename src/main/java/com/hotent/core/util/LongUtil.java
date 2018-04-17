package com.hotent.core.util;

import com.hotent.core.util.StringUtil;

public class LongUtil {
	public static Long[] transStrsToLongs(String[] strs) {
		Long[] longs = new Long[strs.length];

		for (int i = 0; i < strs.length; ++i) {
			longs[i] = Long.valueOf(Long.parseLong(strs[i]));
		}

		return longs;
	}

	public static Long[] transStrsToLongs(String str) {
		return StringUtil.isNotEmpty(str) ? transStrsToLongs(str.split(",")) : null;
	}
}