package com.hotent.core.web.query;

import com.hotent.core.page.PageBean;
import com.hotent.core.page.PageUtils;
import com.hotent.core.table.TableModel;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.util.RequestUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.displaytag.util.ParamEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryFilter {
	private Logger logger;
	private Map<String, Object> filters;
	private String sortColumns;
	private ParamEncoder paramEncoder;
	public static final String ORDER_ASC = "1";
	public static final String ORDER_DESC = "2";
	private String tableId;
	private HttpServletRequest request;
	private PageBean pageBean;

	public QueryFilter(HttpServletRequest request, String tableId) {
		this(request, tableId, true);
	}

	public QueryFilter(HttpServletRequest request, Boolean isLg, String tableId) {
		this.logger = LoggerFactory.getLogger(QueryFilter.class);
		this.filters = new HashMap();
		this.sortColumns = "";
		this.tableId = "";
		this.pageBean = null;
		this.tableId = tableId;
		this.request = request;
		this.paramEncoder = new ParamEncoder(tableId);

		try {
			String ex = RequestUtil.getString(request, "sortname");
			String orderSeq = RequestUtil.getString(request, "sortorder");
			Map map = RequestUtil.getQueryMap(request);
			if (StringUtil.isNotEmpty(ex)) {
				ex = ex.equals("id") ? ex : TableModel.CUSTOMER_COLUMN_PREFIX + ex;
				map.put("orderField", ex);
				map.put("orderSeq", orderSeq);
			}

			this.filters = map;
			if (isLg.booleanValue()) {
				Integer page = Integer.valueOf(RequestUtil.getInt(request, "page", 1));
				Integer pageSize = Integer
						.valueOf(RequestUtil.getInt(request, "pagesize", PageBean.DEFAULT_PAGE_SIZE.intValue()));
				this.pageBean = new PageBean(page.intValue(), pageSize.intValue());
				this.pageBean.setShowTotal(true);
			}
		} catch (Exception arg8) {
			this.logger.error(arg8.getMessage());
		}

	}

	public QueryFilter(HttpServletRequest request) {
		this(request, true);
	}

	public QueryFilter(HttpServletRequest request, String tableId, boolean needPage) {
		this.logger = LoggerFactory.getLogger(QueryFilter.class);
		this.filters = new HashMap();
		this.sortColumns = "";
		this.tableId = "";
		this.pageBean = null;
		this.tableId = tableId;
		this.request = request;
		this.paramEncoder = new ParamEncoder(tableId);
		String tableIdCode = this.paramEncoder.encodeParameterName("");

		try {
			String ex = request.getParameter(tableIdCode + "s");
			String orderSeqNum = request.getParameter(tableIdCode + "o");
			String orderSeq = "desc";
			if (orderSeqNum != null && "1".equals(orderSeqNum)) {
				orderSeq = "asc";
			}

			Map map = RequestUtil.getQueryMap(request);
			if (ex != null) {
				map.put("orderField", ex);
				map.put("orderSeq", orderSeq);
			}

			this.filters = map;
			if (BeanUtils.isNotEmpty(map.get(tableIdCode + "e")) && "1".equals(map.get("exportAll"))) {
				needPage = false;
			}

			if (needPage) {
				int page = RequestUtil.getInt(request, tableIdCode + "p", 1);
				int pageSize = RequestUtil.getInt(request, tableIdCode + "z", PageBean.DEFAULT_PAGE_SIZE.intValue());
				int oldPageSize = RequestUtil.getInt(request, tableIdCode + "oz",
						PageBean.DEFAULT_PAGE_SIZE.intValue());
				if (pageSize != oldPageSize) {
					int first = PageUtils.getFirstNumber(page, oldPageSize);
					page = first / pageSize + 1;
				}

				this.pageBean = new PageBean(page, pageSize);
			}
		} catch (Exception arg12) {
			this.logger.error(arg12.getMessage());
		}

	}

	public QueryFilter(HttpServletRequest request, String tableId, String showTotal) {
		this(request, tableId, true);
		if ("false".equals(showTotal)) {
			this.pageBean.setShowTotal(false);
		}

	}

	public QueryFilter(HttpServletRequest request, boolean needPage) {
		this.logger = LoggerFactory.getLogger(QueryFilter.class);
		this.filters = new HashMap();
		this.sortColumns = "";
		this.tableId = "";
		this.pageBean = null;
		this.request = request;

		try {
			if (needPage) {
				int ex = RequestUtil.getInt(request, "page", 1);
				int pageSize = RequestUtil.getInt(request, "pageSize", 15);
				this.pageBean = new PageBean(ex, pageSize);
			}

			Map ex1 = RequestUtil.getQueryMap(request);
			this.filters = ex1;
		} catch (Exception arg4) {
			this.logger.error(arg4.getMessage());
		}

	}

	public QueryFilter(HttpServletRequest request, String tableId, int pageSize) {
		this(request, tableId, true, pageSize);
	}

	public QueryFilter(HttpServletRequest request, String tableId, boolean needPage, int pageSize) {
		this.logger = LoggerFactory.getLogger(QueryFilter.class);
		this.filters = new HashMap();
		this.sortColumns = "";
		this.tableId = "";
		this.pageBean = null;
		this.tableId = tableId;
		this.request = request;
		this.paramEncoder = new ParamEncoder(tableId);
		String tableIdCode = this.paramEncoder.encodeParameterName("");

		try {
			String ex = request.getParameter(tableIdCode + "s");
			String orderSeqNum = request.getParameter(tableIdCode + "o");
			String orderSeq = "desc";
			if (orderSeqNum != null && "1".equals(orderSeqNum)) {
				orderSeq = "asc";
			}

			Map map = RequestUtil.getQueryMap(request);
			if (ex != null) {
				map.put("orderField", ex);
				map.put("orderSeq", orderSeq);
			}

			this.filters = map;
			if (needPage) {
				int page = RequestUtil.getInt(request, tableIdCode + "p", 1);
				if (page <= 0) {
					page = 1;
				}

				int size = RequestUtil.getInt(request, tableIdCode + "z", 0);
				if (size > 0) {
					pageSize = size;
				}

				int oldPageSize = RequestUtil.getInt(request, tableIdCode + "oz",
						PageBean.DEFAULT_PAGE_SIZE.intValue());
				if (pageSize != oldPageSize) {
					int first = PageUtils.getFirstNumber(page, oldPageSize);
					page = first / pageSize + 1;
				}

				this.pageBean = new PageBean(page, pageSize);
			}
		} catch (Exception arg13) {
			this.logger.error(arg13.getMessage());
		}

	}

	/*public QueryFilter(JSONObject json) {
		this.logger = LoggerFactory.getLogger(QueryFilter.class);
		this.filters = new HashMap();
		this.sortColumns = "";
		this.tableId = "";
		this.pageBean = null;

		try {
			String ex = json.get("currentPage") != null ? json.getString("currentPage") : "";
			String pageSizeStr = json.get("pageSize") != null ? json.getString("pageSize") : "";
			Integer pageSize = Integer.valueOf(StringUtil.isNotEmpty(pageSizeStr)
					? Integer.parseInt(pageSizeStr)
					: PageBean.DEFAULT_PAGE_SIZE.intValue());
			Integer currentPage = Integer.valueOf(StringUtil.isNotEmpty(ex) ? Integer.parseInt(ex) : 1);
			if (StringUtil.isNotEmpty(ex)) {
				this.pageBean = new PageBean(currentPage.intValue(), pageSize.intValue());
			}

			Map map = JSONObjectUtil.getQueryMap(json);
			this.filters = map;
		} catch (Exception arg6) {
			arg6.printStackTrace();
			this.logger.error(arg6.getMessage());
		}

	}*/

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public PageBean getPageBean() {
		return this.pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public void setForWeb() {
		String pbName = "pageBean";
		String href = "requestURI";
		if (this.tableId != null) {
			pbName = pbName + this.tableId;
			href = href + this.tableId;
		}

		if (this.request != null) {
			this.request.setAttribute(href, this.request.getRequestURI());
			this.request.setAttribute(pbName, this.pageBean);
		}
	}

	public String encodeParameter(String parameterName) {
		if (this.paramEncoder == null) {
			this.paramEncoder = new ParamEncoder(this.tableId);
		}

		return this.paramEncoder.encodeParameterName(parameterName);
	}

	public Map<String, Object> getFilters() {
		return this.filters;
	}

	public void addFilter(String filterName, Object params) {
		this.filters.put(filterName, params);
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public String getSortColumns() {
		return this.sortColumns;
	}

	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public String getTableId() {
		return this.tableId;
	}

	public ParamEncoder getParamEncoder() {
		return this.paramEncoder;
	}
}