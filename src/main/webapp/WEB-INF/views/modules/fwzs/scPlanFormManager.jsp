<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>任务计划管理</title>
<meta name="decorator" content="default" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/fwzs/manager/scPlan/">任务计划列表</a></li>
		<li class="active"><a
			href="${ctx}/fwzs/manager/scPlan/form?id=${scPlan.id}">任务计划<shiro:hasPermission
					name="fwzs:scPlan:edit">${not empty scPlan.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="fwzs:scPlan:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="scPlan"
		action="${ctx}/fwzs/manager/scPlan/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">任务单号：</label>
			<div class="controls">
				<form:input path="planNo" htmlEscape="false" maxlength="50"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">选择产品：</label>
			<div class="controls">
				<form:select path="bsProduct.id" class="input-xlarge required">
					<form:option value="" label="请选择" />
					<form:options items="${bsProducts}" itemLabel="prodNo"
						itemValue="id" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">农药名：</label>
			<div class="controls">
				<form:input id="pesticideName" readonly="true"
					path="bsProduct.pesticideName" htmlEscape="false" maxlength="20"
					class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:input id="prodName" readonly="true" path="bsProduct.prodName"
					htmlEscape="false" maxlength="64" class="input-xlarge " />
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
			<label class="control-label">生产批号：</label>
			<div class="controls">
				<form:input path="batchNo" htmlEscape="false" maxlength="50"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生产日期：</label>
			<div class="controls">
				<input name="madeDate" type="text" readonly="readonly"
					maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${scPlan.madeDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效期：</label>
			<div class="controls">
				<input name="indate" type="text" readonly="readonly" maxlength="20"
					class="input-medium Wdate required"
					value="<fmt:formatDate value="${scPlan.indate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计划产量：</label>
			<div class="controls">
				<form:input path="planNumber" htmlEscape="false" maxlength="11"
					class="input-xlarge required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生产线：</label>
			<div class="controls">
				<form:select path="scLines.id" class="input-xlarge required">
					<form:option value="" label="请选择" />
					<form:options items="${scLines}" itemLabel="lineName"
						itemValue="id" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">实际生产量：</label>
			<div class="controls">
				<form:input path="realNumber" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		 --%>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="500"
					class="input-xlarge " />
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">1草稿2确认3审核(未生产)4生产中5完工：</label>
			<div class="controls">
				<form:input path="status" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">操作员：</label>
			<div class="controls">
				<form:input path="operateBy" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">质检员：</label>
			<div class="controls">
				<form:input path="qcBy" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div> --%>
		<%-- 	<div class="control-group">
			<label class="control-label">检验报告单：</label>
			<div class="controls">
				<form:hidden id="checkPlanUrl" path="checkPlanUrl" htmlEscape="false" maxlength="150" class="input-xlarge"/>
				<sys:ckfinder input="checkPlanUrl" type="files" uploadPath="/fwzs/manager/scPlan" selectMultiple="true"/>
			</div>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="fwzs:scPlan:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
	<script src="${ctxStatic}/customized/common.js"></script>
</body>
</html>