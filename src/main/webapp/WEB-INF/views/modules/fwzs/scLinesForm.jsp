<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生产线管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
</head>
<body>
	<div class="hidden" id="ctx">${ctx}</div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/fwzs/scLines/">生产线列表</a></li>
		<li class="active"><a href="${ctx}/fwzs/scLines/form?id=${scLines.id}">生产线<shiro:hasPermission name="fwzs:scLines:edit">${not empty scLines.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fwzs:scLines:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="scLinesInputForm" modelAttribute="scLines" action="${ctx}/fwzs/scLines/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">生产线号：</label>
			<div class="controls">
				<form:input path="lineNo" id="lineNo" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline hidden"><font id="lineNoValidateError" class="hidden" color="red">生产线号有重复，请重新输入!</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生产线名称：</label>
			<div class="controls">
				<form:input path="lineName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="fwzs:scLines:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script src="${ctxStatic}/customized/scLinesForm.js"/>
</body>
</html>