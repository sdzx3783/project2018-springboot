package com.hotent.core.service;

import com.hotent.core.db.WfBaseDao;
import com.hotent.core.page.PageList;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.query.QueryFilter;

public abstract class WfBaseService<E> extends BaseService<E> {
	public PageList getMyDraft(Long userId, QueryFilter queryFilter) {
		return ((WfBaseDao) this.getEntityDao()).getDraftByUser(userId, queryFilter);
	}

	public PageList getMyEnd(Long userId, QueryFilter queryFilter) {
		return ((WfBaseDao) this.getEntityDao()).getEndByUser(userId, queryFilter);
	}

	public PageList getMyTodoTask(Long userId, QueryFilter queryFilter) {
		return ((WfBaseDao) this.getEntityDao()).getMyTodoTask(userId, queryFilter);
	}
}