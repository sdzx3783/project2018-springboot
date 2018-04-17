package com.hotent.core.page;

import com.hotent.core.page.PageUtils;
import com.hotent.core.util.StringUtil;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class PageBean implements Serializable {
	public static Integer SHOW_PAGES = Integer.valueOf(6);
	public static final Integer DEFAULT_PAGE_SIZE = Integer.valueOf(20);
	private int pageSize;
	private int currentPage;
	private int totalCount;
	private String pageName;
	private String pageSizeName;
	private boolean showTotal;

	public PageBean() {
		this.pageSize = DEFAULT_PAGE_SIZE.intValue();
		this.currentPage = 1;
		this.totalCount = 0;
		this.pageName = "page";
		this.pageSizeName = "pageSize";
		this.showTotal = true;
	}

	public PageBean(int currentPage, int pageSize) {
		this.pageSize = DEFAULT_PAGE_SIZE.intValue();
		this.currentPage = 1;
		this.totalCount = 0;
		this.pageName = "page";
		this.pageSizeName = "pageSize";
		this.showTotal = true;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setPagesize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageName() {
		return StringUtil.isEmpty(this.pageName) ? "page" : this.pageName;
	}

	public String getPageSizeName() {
		return StringUtil.isEmpty(this.pageSizeName) ? "pageSize" : this.pageSizeName;
	}

	public boolean isFirstPage() {
		return this.getCurrentPage() == 1;
	}

	public boolean isLastPage() {
		return this.getCurrentPage() >= this.getLastPage();
	}

	public boolean isHasNextPage() {
		return this.getLastPage() > this.getCurrentPage();
	}

	public boolean isHasPreviousPage() {
		return this.getCurrentPage() > 1;
	}

	public int getLastPage() {
		return PageUtils.getTotalPage(this.totalCount, this.pageSize);
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int count) {
		this.totalCount = count;
	}

	public int getTotalPage() {
		int page = this.totalCount / this.pageSize;
		int tmp = this.totalCount % this.pageSize;
		return page + (tmp == 0 ? 0 : 1);
	}

	public int getThisPageFirstElementNumber() {
		return (this.getCurrentPage() - 1) * this.getPageSize() + 1;
	}

	public int getThisPageLastElementNumber() {
		int fullPage = this.getThisPageFirstElementNumber() + this.getPageSize() - 1;
		return this.getTotalCount() < fullPage ? this.getTotalCount() : fullPage;
	}

	public int getNextPage() {
		return this.getCurrentPage() + 1;
	}

	public int getPreviousPage() {
		return this.getCurrentPage() - 1;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public boolean isShowTotal() {
		return this.showTotal;
	}

	public void setShowTotal(boolean showTotal) {
		this.showTotal = showTotal;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public List<Integer> getLinkPageNumbers() {
		return PageUtils.getPageNumbers(this.getCurrentPage(), this.getLastPage(), 10);
	}

	public int getFirst() {
		return PageUtils.getFirstNumber(this.currentPage, this.pageSize);
	}

	public int getLast() {
		return PageUtils.getLastNumber(this.currentPage, this.pageSize, this.totalCount);
	}

	public Integer[] getPageArr() {
		int totalPages = this.getTotalPage();
		int minPage = 1;
		int maxPage;
		if (this.currentPage == 1) {
			maxPage = SHOW_PAGES.intValue();
		} else {
			minPage = this.currentPage - (SHOW_PAGES.intValue() / 2 + SHOW_PAGES.intValue() % 2);
			if (minPage <= 0) {
				minPage = 1;
			}

			maxPage = minPage + SHOW_PAGES.intValue() - 1;
		}

		if (maxPage > totalPages) {
			maxPage = totalPages;
		}

		Integer[] arrs = new Integer[maxPage - minPage + 1];

		for (int i = 0; i < arrs.length; ++i) {
			arrs[i] = Integer.valueOf(minPage + i);
		}

		return arrs;
	}
}