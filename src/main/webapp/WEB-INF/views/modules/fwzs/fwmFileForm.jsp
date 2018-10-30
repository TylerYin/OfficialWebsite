<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fwzs" tagdir="/WEB-INF/tags/fwzs"%>
<html>
<head>
<title>防伪码文件列表管理</title>
	<meta name="decorator" content="default" />
	<link rel="stylesheet" href="${ctxStatic}/colorbox/colorbox.css" />
	<link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a
			href="${ctx}/fwzs/fwmFile/form?id=${fwmFile.id}">批量生码<shiro:hasPermission
					name="fwzs:fwmFile:edit"></shiro:hasPermission> <shiro:lacksPermission
					name="fwzs:fwmFile:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="fwmFile"
		action="${ctx}/fwzs/fwmFile/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">文件名：</label>
			<div class="controls">
				<form:input path="fileName" htmlEscape="false" maxlength="50"
					class="input-xlarge " readonly="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选择产品：</label>
			<div class="controls">
				<form:input id="prodNo" path=""
					class="input-xlarge" value="请选择" readonly="true" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="hidden">
			<div class="controls">
				<form:input id="prodId" path="bsProduct.id"
							class="input-xlarge" value="请选择" readonly="true" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">农药名：</label>
			<div class="controls">
				<form:input id="pesticideName" readonly="true"
					path="bsProduct.pesticideName" htmlEscape="false" maxlength="50"
					class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:input id="prodName" readonly="true" path="bsProduct.prodName"
					htmlEscape="false" maxlength="33" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品规格：</label>
			<div class="controls">
				<form:input id="specDesc" readonly="true"
					path="bsProduct.prodSpec.specDesc" htmlEscape="false"
					maxlength="64" class="input-xlarge " />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">生码数量：</label>
			<div class="controls">
				<form:input path="codeNumber" htmlEscape="false" onKeyPress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" maxlength="6" class="input-xlarge" />
				<span class="help-inline"><font color="red">* 建议码数在50万以内</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="fwzs:fwmFile:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>

		<fwzs:selectProduct bsProducts="${bsProducts}"/>
	</form:form>
	<script src="${ctxStatic}/colorbox/jquery.colorbox.js"></script>
	<script src="${ctxStatic}/customized/fwmFileForm.js"></script>
</body>
</html>