<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<%@include file="/commons/include/get.jsp" %>
	<title>友情提示</title>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 180px;
	margin-right: 0px;
	margin-bottom: 0px;
	height: auto;
}body,td,th {
	font-size: 12px;
}
.STYLE1 {
	color: #354D79;
	line-height: 18px;
	font-size:16px;
	color:#666;
	text-align: center
}

</style>

</head>
<body>
<table align="center"  height="182" width="457" border="0" cellpadding="0" cellspacing="0">
  <tbody><tr valign="middle" >
    <td height="111" valign="middle" width="480" background="${ctx}/static/platform/styles/default/static/images/warm.gif"><table height="89" width="449" border="0" cellpadding="0" cellspacing="0">
      <tbody><tr>
        <td height="12" width="67">&nbsp;</td>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td rowspan="2" height="45">&nbsp;</td>
        <td height="44" valign="top" width="390" >&nbsp;</td>
        <td valign="middle" width="50">&nbsp;</td>
      </tr>
      <tr>
        <td height="30" valign="top"  class="STYLE1" text-align="center">${content}</td>
        <td valign="bottom" width="50"><a href="javascript:;"></a></td>
      </tr>
    </tbody></table></td>
  </tr>
</tbody></table>
</body>
</html>
