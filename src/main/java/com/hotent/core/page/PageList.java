package com.hotent.core.page;

import com.hotent.core.page.PageBean;
import java.util.ArrayList;

public class PageList<E> extends ArrayList<E> {
	private PageBean pageBean = new PageBean();

	public PageBean getPageBean() {
		return this.pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public int getTotalPage() {
		return this.pageBean.getTotalPage();
	}

	public int getTotalCount() {
		return this.pageBean.getTotalCount();
	}
}