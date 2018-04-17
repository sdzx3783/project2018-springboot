package com.hotent.core.page;

import com.hotent.core.page.PageBean;
import com.hotent.core.page.PageUtils;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class PageNav {
	private String strUrl;
	private PageBean _pv;
	private String _strPara = "";
	private static long i = 0L;
	private HttpServletRequest _request;
	private int count = 8;

	public PageNav(HttpServletRequest request, PageBean pageBean) {
		this._request = request;
		this._pv = pageBean;
		this.strUrl = request.getRequestURL().toString();
		this._strPara = this.getPara(request);
	}

	private String getPara(HttpServletRequest request) {
		String params = "";
		Enumeration e = request.getParameterNames();
		String key = "";
		String value = "";

		while (e.hasMoreElements()) {
			key = (String) e.nextElement();
			if (!key.equals(this._pv.getPageName())) {
				value = request.getParameter(key);
				if (value != null && !value.equals("")) {
					params = params + "&" + key + "=" + this.getValueByParameter(key);
				}
			}
		}

		return params;
	}

	private String getValueByParameter(String parameter) {
		String[] values = this._request.getParameterValues(parameter);
		String value = "";
		if (values.length == 1) {
			value = this._request.getParameter(parameter);
			if (value == null) {
				return "";
			}

			value = value.trim();
		} else {
			int k = values.length;

			for (int i = 0; i < values.length; ++i) {
				if (i == k - 1) {
					value = value + values[i].trim();
				} else {
					value = value + values[i].trim() + ",";
				}
			}
		}

		return value;
	}

	public String getNumber() {
		int currentPage = this._pv.getCurrentPage();
		List list = PageUtils.getPageNumbers(this._pv.getCurrentPage(), this._pv.getTotalPage(), this.count);
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\'pageNav\'>");
		String url = this.strUrl + "?page=%1$s" + this._strPara;
		String curTmplate = "<span class=\'spanCurPage\'><a href=\'" + url + "\'>%1$s</a></span>";
		String tmplate = "<span class=\'spanPage\'><a href=\'" + url + "\'>%1$s</a></span>";
		Iterator i$ = list.iterator();

		while (i$.hasNext()) {
			Integer it = (Integer) i$.next();
			if (currentPage == it.intValue()) {
				sb.append(String.format(curTmplate, new Object[]{Integer.valueOf(it.intValue())}));
			} else {
				sb.append(String.format(tmplate, new Object[]{Integer.valueOf(it.intValue())}));
			}
		}

		sb.append("</div>");
		return sb.toString();
	}

	public String getPage(boolean allRecord, boolean pagesInfo, boolean pageNav, boolean jumpPage) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\'pageNav\'>");
		if (allRecord) {
			sb.append(this.showTotalRecord());
		}

		if (pagesInfo) {
			sb.append(this.showPageInfo());
		}

		if (pageNav) {
			sb.append(this.showPageNav());
		}

		if (jumpPage) {
			sb.append(this.showJumpPage());
		}

		sb.append("</div>");
		return sb.toString();
	}

	public String getPage() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\'pageNav\'>");
		sb.append(this.showTotalRecord());
		sb.append(this.showPageInfo());
		sb.append(this.showPageNav());
		sb.append(this.showJumpPage());
		sb.append("</div>");
		return sb.toString();
	}

	public String getShortPage() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\'pageNav\'>");
		sb.append(this.showPageNav());
		sb.append("</div>");
		return sb.toString();
	}

	private String showJumpPage() throws Exception {
		StringBuffer sb = new StringBuffer();
		++i;
		if (i > 100L) {
			i = 0L;
		}

		sb.append("<span class=\'jumpPage\'>");
		sb.append(" 转到第<input type=\"text\" name=\"page" + i
				+ "\" size=\"1\" class=\"input\" />页 <input type=\"button\" class=\"btnjump\" value=\"跳转\" onclick=\"goToPage"
				+ i + "()\" /> ");
		sb.append("<script language=\"javascript\">");
		sb.append("function goToPage" + i + "(){");
		sb.append("value=document.all.page" + i + ".value;");
		sb.append("if(value.indexOf(\".\")==-1 && value.indexOf(\"-\")==-1 && value!==\"\" && !isNaN(value) && value<"
				+ (this._pv.getTotalPage() + 1) + "){");
		sb.append("location.assign(\'" + this.strUrl + "?page=\'" + "+value+\'" + this._strPara + "\')");
		sb.append("}");
		sb.append("}");
		sb.append("</script>");
		sb.append("</span>");
		return sb.toString();
	}

	private String showPageInfo() throws Exception {
		return "<span class=\'pageInfo\'> 第" + this._pv.getCurrentPage() + "/" + this._pv.getTotalPage() + "页 </span>";
	}

	private String showPageNav() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<span class=\'spanNav\'>");
		if (this._pv.getTotalPage() > 1) {
			sb.append("<a href=\"" + this.strUrl + "?page=1" + this._strPara + "\">第一页</a>");
		} else {
			sb.append("<a disabled=\'true\' >第一页</a>");
		}

		if (this._pv.isHasPreviousPage()) {
			sb.append(
					"<a href=\"" + this.strUrl + "?page=" + this._pv.getPreviousPage() + this._strPara + "\">上一页</a>");
		} else {
			sb.append("<a disabled=\'true\' >上一页</a>");
		}

		if (this._pv.isHasNextPage()) {
			sb.append("<a href=\"" + this.strUrl + "?page=" + this._pv.getNextPage() + this._strPara + "\">下一页</a>");
		} else {
			sb.append("<a disabled=\'true\' >下一页</a>");
		}

		if (this._pv.getTotalPage() > 1) {
			sb.append("<a href=\"" + this.strUrl + "?page=" + this._pv.getTotalPage() + this._strPara + "\">最末页</a>");
		} else {
			sb.append("<a disabled=\'true\' >最末页</a>");
		}

		sb.append("</span>");
		return sb.toString();
	}

	private String showTotalRecord() throws Exception {
		return "<span class=\'totalRecord\'>共" + this._pv.getTotalCount() + "条记录 </span>";
	}
}