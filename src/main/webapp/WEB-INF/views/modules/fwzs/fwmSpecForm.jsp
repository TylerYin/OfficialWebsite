<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品规格管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
</head>
<body>
	<div class="hidden" id="ctx">${ctx}</div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/fwzs/fwmSpec/">产品规格列表</a></li>
		<li class="active"><a href="${ctx}/fwzs/fwmSpec/form?id=${fwmSpec.id}">产品规格<shiro:hasPermission name="fwzs:fwmSpec:edit">${not empty fwmSpec.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fwzs:fwmSpec:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="fwmSpecInputForm" modelAttribute="fwmSpec" action="${ctx}/fwzs/fwmSpec/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">规格码：</label>
			<div class="controls">
				<form:input path="specCode" id="specCode" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span class="help-inline"><font color="red" id="specCodeValidateError" class="hidden">规格码有重复，请重新输入!</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格说明：</label>
			<div class="controls">
				<form:input path="specDesc" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="fwzs:fwmSpec:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script src="${ctxStatic}/customized/fwmSpecForm.js"/>
</body>
</html>