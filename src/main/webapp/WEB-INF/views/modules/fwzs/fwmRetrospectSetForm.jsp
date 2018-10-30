<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>追溯展示设置管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/fwzs/fwmRetrospectSet/">追溯展示设置列表</a></li>
		<li class="active"><a href="${ctx}/fwzs/fwmRetrospectSet/form?id=${fwmRetrospectSet.id}">追溯展示设置<shiro:hasPermission name="fwzs:fwmRetrospectSet:edit">${not empty fwmRetrospectSet.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fwzs:fwmRetrospectSet:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="fwmRetrospectSet" action="${ctx}/fwzs/fwmRetrospectSet/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">产品id：</label>
			<div class="controls">
				<form:input path="prodId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展示属性：</label>
			<div class="controls">
				<form:input path="property" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="fwzs:fwmRetrospectSet:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script src="${ctxStatic}/customized/common.js"></script>
</body>
</html>