<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>防窜货参数设置管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
</head>
<body>
	<div class="hidden" id="ctx">${ctx}</div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/fwzs/antiRegionalParaSetting/">防窜货参数设置列表</a></li>
		<li class="active"><a href="${ctx}/fwzs/antiRegionalParaSetting/form?id=${antiRegionalParaSetting.id}">防窜货参数设置${not empty antiRegionalParaSetting.id?'修改':'添加'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="antiRegionalParaSetting" action="${ctx}/fwzs/antiRegionalParaSetting/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">窜货数量阈值(个)：</label>
            <div class="controls">
                <form:input path="antiRegionalThreshold" id="antiRegionalThreshold" htmlEscape="false" maxlength="3" onKeyPress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" />
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script src="${ctxStatic}/customized/antiRegionalParaSettingForm.js"/>
</body>
</html>