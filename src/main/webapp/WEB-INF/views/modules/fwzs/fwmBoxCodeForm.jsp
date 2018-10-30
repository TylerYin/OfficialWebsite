<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>箱码管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/fwzs/fwmBoxCode/">箱码列表</a></li>
		<li class="active"><a href="${ctx}/fwzs/fwmBoxCode/form?id=${fwmBoxCode.id}">箱码<shiro:hasPermission name="fwzs:fwmBoxCode:edit">${not empty fwmBoxCode.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fwzs:fwmBoxCode:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="fwmBoxCode" action="${ctx}/fwzs/fwmBoxCode/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">box_code：</label>
			<div class="controls">
				<form:input path="boxCode" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">plan_id：</label>
			<div class="controls">
				<form:input path="planId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">status：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('fwm_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">bigbox_code：</label>
			<div class="controls">
				<form:input path="bigboxCode" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="fwzs:fwmBoxCode:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script src="${ctxStatic}/customized/common.js"></script>
</body>
</html>