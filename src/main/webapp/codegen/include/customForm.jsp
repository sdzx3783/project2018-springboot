<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<f:link href="web.css" ></f:link>
<f:link href="Aqua/css/ligerui-all.css" ></f:link>
<f:link href="tree/zTreeStyle.css" ></f:link>
<f:link href="form.css" ></f:link>
<f:js pre="static/platform/js/lang/common" ></f:js>
<f:js pre="static/platform/js/lang/js" ></f:js>
<script type="text/javascript" src="${ctx}/static/platform/js/dynamic.jsp"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/util/util.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/util/json2.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/util/form.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/tree/jquery.ztree.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/base.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/plugins/ligerComboBox.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/plugins/htDicCombo.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/plugins/ligerMenuBar.js" ></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/plugins/ligerMenu.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/plugins/ligerTextBox.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/plugins/ligerTip.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/plugins/ligerTab.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/plugins/ligerMessageBox.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/plugins/ligerMsg.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/plugins/ligerDrag.js" ></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/plugins/ligerDialog.js" ></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/plugins/ligerResizable.js" ></script>
<script type="text/javascript" src="${ctx}/static/platform/js/calendar/My97DatePicker/WdatePicker.js" ></script>
<link href="${ctx}/static/platform/styles/default/css/jquery.qtip.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/static/platform/js/jquery/plugins/jquery.qtip.js" ></script>

<%@include file="/commons/include/ueditor.jsp" %>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/system/SysDialog.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/form/rule.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/lg/util/DialogUtil.js" ></script>

<script type="text/javascript" src="${ctx}/static/platform/js/hotent/CustomValid.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/form/SelectorInit.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/system/FlexUploadDialog.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/system/HtmlUploadDialog.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/form/ReadOnlyQuery.js"></script>

<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/form/OfficePlugin.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/ntkoWebSign/NtkoAddSecSign.js"></script>


<link href="${ctx}/static/platform/js/pictureShow/css/jquery.fancybox.css" rel="stylesheet" />
<link href="${ctx}/static/platform/js/pictureShow/css/pictureShow.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/static/platform/js/pictureShow/jquery.fancybox.pack.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/pictureShow/PictureShowControl.js"></script>


<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/form/CommonDialog.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/form/FormMath.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/system/ShowExeInfo.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/system/SysAuditLink.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/form/Cascadequery.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/system/ImageQtip.js" ></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/form/RunAliasScript.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/form/OfficeControl.js"></script>

<script type="text/javascript" src="${ctx}/static/platform/codegen/js/PictureShowPlugin.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/codegen/js/NtkoWebSign.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/codegen/js/WebSignPlugin.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/codegen/js/FormUtil.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/codegen/js/CustomForm.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/codegen/js/AttachMent.js"></script>
<script type="text/javascript" src="${ctx}/static/platform/codegen/js/FormInit.js"></script>

<link href="${ctx}/static/platform/js/jquery/plugins/attach.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/static/platform/js/jquery/plugins/jquery.attach.js" ></script>
<script type="text/javascript" src="${ctx}/static/platform/js/hotent/platform/system/FlexUploadDialog.js" ></script>


