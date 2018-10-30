<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>剁码管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/fwzs/fwmBigboxCode/">剁码列表</a></li>
		<li class="active"><a href="${ctx}/fwzs/fwmBigboxCode/form?id=${fwmBigboxCode.id}">剁码<shiro:hasPermission name="fwzs:fwmBigboxCode:edit">${not empty fwmBigboxCode.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fwzs:fwmBigboxCode:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="fwmBigboxCode" action="${ctx}/fwzs/fwmBigboxCode/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">big_box_code：</label>
			<div class="controls">
				<form:input path="bigBoxCode" htmlEscape="false" maxlength="50" class="input-xlarge "/>
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
		<div class="form-actions">
			<shiro:hasPermission name="fwzs:fwmBigboxCode:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script src="${ctxStatic}/customized/common.js"></script>
</body>
</html>