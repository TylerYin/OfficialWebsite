<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>生码规则设置管理</title>
<meta name="decorator" content="default" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/bsCodeRule/">批量生码</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="bsCodeRule"
		action="${ctx}/sys/bsCodeRule/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<%-- <div class="control-group">
			<label class="control-label">code_pre：</label>
			<div class="controls">
				<form:input path="codePre" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">登记类别代码：</label>
			<div class="controls">
				<form:select path="codeType" class="input-xlarge required">
					<form:option value="" label="请选择" />
					<form:options items="${fns:getDictList('code_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记证后六位：</label>
			<div class="controls">
				<form:input path="djzLast" htmlEscape="false" maxlength="6"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生产类型：</label>
			<div class="controls">
				<form:select path="scType" class="input-xlarge required">
					<form:option value="" label="请选择" />
					<form:options items="${fns:getDictList('sc_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品规格码：</label>
			<div class="controls">
				<form:input path="codeSpec" htmlEscape="false" maxlength="3"
					class="input-xlarge required" />
				<span class="help-inline"> <font color="red">*</font>
					请输入3位规格码（企业自行定制）
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数量：</label>
			<div class="controls">
				<form:input path="codeRadom" htmlEscape="false" maxlength="7"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font>(单次建议10万条以内) </span>
				 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否启用：</label>
			<div class="controls">
				<form:radiobuttons path="status"
					items="${fns:getDictList('yes_no')}" itemLabel="label"
					itemValue="value" htmlEscape="false" class="required" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:bsCodeRule:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="批量生成" />&nbsp;</shiro:hasPermission>
			<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
		</div>
	</form:form>
	<script src="${ctxStatic}/customized/common.js"></script>
</body>
</html>