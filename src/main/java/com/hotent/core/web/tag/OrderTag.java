package com.hotent.core.web.tag;

import java.net.URLEncoder;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class OrderTag extends BodyTagSupport {
	private static final String Asc = "ASC";
	private static final String Desc = "DESC";
	private String field = "";
	private String order = "DESC";
	private String ascImg = "/themes/img/commons/asc.png";
	private String descImg = "/themes/img/commons/desc.png";
	private String[] aryAvoid = new String[]{"sortField", "orderSeq"};

	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int doStartTag() throws JspTagException {
		return 2;
	}

	private String getOutput(HttpServletRequest request) throws Exception {
		String body = this.getBodyContent().getString();
		if (this.field != null && !this.field.equals("")) {
			String img = "";
			String orderSeq = request.getParameter("orderSeq");
			String sortField = request.getParameter("sortField");
			if (orderSeq != null && sortField.equals(this.field)) {
				if (orderSeq.equals("DESC")) {
					orderSeq = "ASC";
				} else {
					orderSeq = "DESC";
				}
			} else {
				orderSeq = this.order;
			}

			if (orderSeq.equals("DESC")) {
				img = request.getContextPath() + this.descImg;
			} else {
				img = request.getContextPath() + this.ascImg;
			}

			String url = this.getUrl(request);
			String para = "sortField=" + this.field + "&orderSeq=" + orderSeq;
			if (url.indexOf("?") > -1) {
				url = url + "&" + para;
			} else {
				url = url + "?" + para;
			}

			StringBuffer sb = new StringBuffer();
			sb.append("<th  >");
			sb.append("<a href=\'" + url + "\'>" + body
					+ "<span style=\'vertical-align:middle;\'><img border=\'0\' src=\'" + img + "\'/></span></a>");
			sb.append("</th>");
			return sb.toString();
		} else {
			return "<th>" + body + "</th>";
		}
	}

	private String getUrl(HttpServletRequest request) throws Exception {
		StringBuffer urlThisPage = new StringBuffer();
		String url = request.getAttribute("currentPath").toString();
		if (url == null) {
			throw new Exception("请在控制器中设置currentPath(当前路径)!");
		} else {
			urlThisPage.append(url);
			Enumeration e = request.getParameterNames();
			String para = "";
			String values = "";
			urlThisPage.append("?");

			while (e.hasMoreElements()) {
				para = (String) e.nextElement();
				boolean rtn = this.isExists(para);
				if (!rtn) {
					values = URLEncoder.encode(this.getValueByKey(request, para), "utf-8");
					urlThisPage.append(para);
					urlThisPage.append("=");
					urlThisPage.append(values);
					urlThisPage.append("&");
				}
			}

			return urlThisPage.substring(0, urlThisPage.length() - 1);
		}
	}

	private boolean isExists(String key) {
		String[] arr$ = this.aryAvoid;
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			String str = arr$[i$];
			if (key.equals(str)) {
				return true;
			}
		}

		return false;
	}

	private String getValueByKey(HttpServletRequest request, String key) {
		String rtn = "";
		String[] values = request.getParameterValues(key);
		String[] arr$ = values;
		int len$ = values.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			String str = arr$[i$];
			if (str != null && !str.trim().equals("")) {
				rtn = rtn + str + ",";
			}
		}

		if (rtn.length() > 0) {
			rtn = rtn.substring(0, rtn.length() - 1);
		}

		return rtn;
	}

	public int doEndTag() throws JspTagException {
		String body = this.getBodyContent().getString();
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();

		try {
			JspWriter e = this.pageContext.getOut();
			String str = this.getOutput(request);
			this.pageContext.getOut().print(str);
			return 0;
		} catch (Exception arg4) {
			throw new JspTagException(arg4.getMessage());
		}
	}
}