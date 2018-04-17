package com.hotent.core.page;

import java.util.ArrayList;
import java.util.List;

public class PageUtils {
	public static int getFirstNumber(int currentPage, int pageSize) {
		if (pageSize <= 0) {
			throw new IllegalArgumentException("[pageSize] must great than zero");
		} else {
			return (currentPage - 1) * pageSize;
		}
	}

	public static int getLastNumber(int currentPage, int pageSize, int totalCount) {
		int last = currentPage * pageSize;
		return last > totalCount ? totalCount : last;
	}

	public static List<Integer> getPageNumbers(int currentPage, int totalPage, int count) {
		int avg = count / 2;
		int startPage = currentPage - avg;
		if (startPage <= 0) {
			startPage = 1;
		}

		int endPage = startPage + count - 1;
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		if (endPage - startPage < count) {
			startPage = endPage - count;
			if (startPage <= 0) {
				startPage = 1;
			}
		}

		ArrayList result = new ArrayList();

		for (int i = startPage; i <= endPage; ++i) {
			result.add(new Integer(i));
		}

		return result;
	}

	public static int getTotalPage(int totalCount, int pageSize) {
		int result = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
		if (result <= 1) {
			result = 1;
		}

		return result;
	}

	public static int getPageNumber(int currentPage, int pageSize, int totalCount) {
		return currentPage <= 1
				? 1
				: (Integer.MAX_VALUE != currentPage && currentPage <= getTotalPage(totalCount, pageSize)
						? currentPage
						: getTotalPage(totalCount, pageSize));
	}
}