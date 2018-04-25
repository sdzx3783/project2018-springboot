<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="hotent" uri="http://www.jee-soft.cn/paging" %>
<%@ taglib prefix="spr" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<f:link href="Aqua/css/ligerui-all.css"></f:link>
<f:link href="hotent/ats.css"></f:link>
<script type="text/javascript" src="${ctx}/static/platform/js/dynamic.jsp"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/jquery/jquery.js"></script>
<f:js pre="static/platform/js/lang/common" ></f:js>
<f:js pre="static/platform/js/lang/js" ></f:js>
<script type="text/javascript" src="${ctx}/static/platform/js/util/util.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/util/json2.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/ligerui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/displaytag.js" ></script>
<script type="text/javascript" src="${ctx}/static/platform/js/calendar/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/plugins/ligerResizable.js" ></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/util/DialogUtil.js" ></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/platform/styles/common/css/font-awesome/font-awesome.min.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/static/platform/styles/common/css/bootstrap/bootstrap.min.css"></link>
<f:link href="jqGrid/jquery-ui.css" ></f:link>
<f:link href="jqGrid/ui.jqgrid.css" ></f:link>
<link rel="stylesheet" type="text/css" href="${ctx}/static/platform/styles/common/css/fullcalendar/fullcalendar.min.css"></link>
<!--[if lte IE 8]>
	<script type="text/javascript" src="${ctx}/static/platform/js/bootstrap/html5shiv.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/platform/js/bootstrap/respond.min.js"></script>
<![endif]-->
<script type="text/javascript" src="${ctx}/static/platform/js/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/fullcalendar/fullcalendar.min.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lang/fullcalendar/zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/jqGrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/jqGrid/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/util/util.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/ats/AtsDialog.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/system/SysDialog.js"></script>