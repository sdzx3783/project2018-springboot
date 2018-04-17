package com.hotent.core.web.tag;

import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.page.PageBean;
import com.hotent.core.util.AppUtil;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.collections.map.HashedMap;
import org.displaytag.util.ParamEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageTag extends TagSupport {
	private static Logger logger = LoggerFactory.getLogger(PageTag.class);
	private String tableId;
	private boolean showExplain = true;
	private boolean showPageSize = true;

	public String getTableId() {
		return this.tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public boolean isShowExplain() {
		return this.showExplain;
	}

	public void setShowExplain(boolean showExplain) {
		this.showExplain = showExplain;
	}

	public boolean isShowPageSize() {
		return this.showPageSize;
	}

	public void setShowPageSize(boolean showPageSize) {
		this.showPageSize = showPageSize;
	}

	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();

		try {
			FreemarkEngine ex = (FreemarkEngine) AppUtil.getBean("freemarkEngine");
			HashedMap model = new HashedMap();
			PageBean pb = null;
			logger.debug("table id:" + this.tableId);
			String url = null;
			if (this.tableId != null) {
				pb = (PageBean) request.getAttribute("pageBean" + this.tableId);
				url = (String) request.getAttribute("requestURI" + this.tableId);
				ParamEncoder params = new ParamEncoder(this.tableId);
				model.put("tableIdCode", params.encodeParameterName(""));
			} else {
				pb = (PageBean) request.getAttribute("pageBean");
				url = url + request.getRequestURI();
				model.put("tableIdCode", "");
			}

			if (pb == null) {
				throw new RuntimeException("pagingBean can\'t no be null");
			}

			model.put("pageBean", pb);
			String params1 = this.getQueryParameters(request);
			if (url.indexOf("?") > 0) {
				if (!"".equals(params1)) {
					url = url + "&" + params1;
				} else {
					url = url + "?" + params1;
				}
			} else if (!"".equals(params1)) {
				url = url + "?" + params1;
			}

			logger.info("current url:" + url);
			model.put("showExplain", Boolean.valueOf(this.showExplain));
			model.put("showPageSize", Boolean.valueOf(this.showPageSize));
			model.put("baseHref", url);
			String html = ex.mergeTemplateIntoString("page.ftl", model);
			out.println(html);
		} catch (Exception arg8) {
			arg8.printStackTrace();
		}

		return 0;
	}

	private String getQueryParameters(HttpServletRequest request) {
		Enumeration names = request.getParameterNames();
		StringBuffer sb = new StringBuffer();
		int i = 0;

		while (names.hasMoreElements()) {
			if (i++ > 0) {
				sb.append("&");
			}

			String name = (String) names.nextElement();
			String value = request.getParameter(name);
			sb.append(name).append("=").append(value);
		}

		return sb.toString();
	}
}