package com.hotent.core.db;

import com.hotent.core.db.BaseDao;
import com.hotent.core.page.PageList;
import com.hotent.core.web.query.QueryFilter;

public abstract class WfBaseDao<E> extends BaseDao<E> {
	public PageList getMyTodoTask(Long userId, QueryFilter queryFilter) {
		queryFilter.addFilter("userId", userId);
		return (PageList) this.getBySqlKey("getMyTodoTask", queryFilter);
	}

	public PageList getDraftByUser(Long userId, QueryFilter queryFilter) {
		queryFilter.addFilter("userId", userId);
		return (PageList) this.getBySqlKey("getDraftByUser", queryFilter);
	}

	public PageList getEndByUser(Long userId, QueryFilter queryFilter) {
		queryFilter.addFilter("userId", userId);
		return (PageList) this.getBySqlKey("getEndByUser", queryFilter);
	}
}