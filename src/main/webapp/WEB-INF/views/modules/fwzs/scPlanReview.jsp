<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务计划管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/colorbox/colorbox.css" />
	<link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/fwzs/scPlan/">任务计划列表</a></li>
		<li class="active"><a href="${ctx}/fwzs/scPlan/form?id=${scPlan.id}">任务计划<shiro:hasPermission name="fwzs:scPlan:edit">${not empty scPlan.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fwzs:scPlan:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="scPlan" action="${ctx}/fwzs/scPlan/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">任务单号：</label>
			<div class="controls">
				<form:input path="planNo" htmlEscape="false" maxlength="50" class="input-xlarge required" readonly="true"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">生产日期：</label>
			<div class="controls">
				<input name="madeDate" type="text" readonly="readonly" disabled="disabled" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${scPlan.madeDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">生产线：</label>
			<div class="controls">
				<form:select path="scLines.id" disabled="true" class="input-xlarge required" >
					<form:option value="" label="请选择"/>
					<form:options  items="${scLines}" itemLabel="lineName" itemValue="id" htmlEscape="false" />
				</form:select>
			</div>
		</div>

        <div class="control-group">
            <label class="control-label">质检人：</label>
            <div class="controls">
                <form:select path="qcBy.id" id="checkPerson" disabled="true" class="input-xlarge required" >
                    <form:option value="" label="请选择"/>
                    <form:options items="${users}" itemLabel="name" itemValue="id" htmlEscape="false" />
                </form:select>
            </div>
        </div>

		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" maxlength="500" readonly="true" class="input-xlarge "/>
			</div>
		</div>

		<div>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>产品编号</th>
						<th>农药名</th>
						<th>产品名称</th>
						<th>产品规格</th>
						<th>生产批号</th>
						<th>有效期</th>
						<th>计划产量</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${not empty scPlan.bsProductList}">
						<c:forEach items="${scPlan.bsProductList}" var="bsProduct" varStatus="status">
							<tr>
								<td>
									<div class="control-group">
										<div>
											<form:input id="prodNo" path="bsProductList[${status.index}].prodNo" class="input-xlarge inputWidth115 required" value="${scPlan.bsProduct.prodNo}" readonly="true" />
										</div>

										<div class="controls hidden">
											<form:input id="prodId" path="bsProductList[${status.index}].id" class="input-xlarge required" value="${bsProduct.id}" readonly="true" />
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
										<div>
											<form:input id="pesticideName" readonly="true" path="bsProductList[${status.index}].pesticideName" htmlEscape="false" maxlength="20" class="input-xlarge inputWidth165"/>
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
										<div>
											<form:input id="prodName" readonly="true" path="bsProductList[${status.index}].prodName" htmlEscape="false" maxlength="64" class="input-xlarge inputWidth165"/>
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
										<div>
											<form:input id="specDesc" readonly="true" path="bsProductList[${status.index}].prodSpec.specDesc" htmlEscape="false" maxlength="64" class="input-xlarge inputWidth115"/>
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
										<div>
											<form:input path="bsProductList[${status.index}].batchNo" htmlEscape="false" readonly="true" maxlength="50" class="input-xlarge inputWidth115 required"/>
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
										<div>
											<input name="bsProductList[${status.index}].indate" type="text" readonly="readonly" disabled="disabled" maxlength="20" class="input-medium Wdate required"
												   value="<fmt:formatDate value="${bsProduct.indate}" pattern="yyyy-MM-dd"/>"
												   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
										<div>
											<form:input path="bsProductList[${status.index}].planNumber" htmlEscape="false" maxlength="11" readonly="true" class="input-xlarge inputWidth115 required"/>
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
										<div>
											<form:input path="bsProductList[${status.index}].remark" readonly="true" htmlEscape="false" maxlength="11" class="input-xlarge inputWidth165 required"/>
										</div>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td>
								<div class="control-group">
									<div>
										<form:input id="prodNo" path="bsProductList[0].prodNo" class="input-xlarge inputWidth115 required" value="${scPlan.bsProduct.prodNo}" readonly="true" />
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div>
										<form:input id="pesticideName" readonly="true" path="bsProductList[0].pesticideName" htmlEscape="false" maxlength="20" class="input-xlarge inputWidth165"/>
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div>
										<form:input id="prodName" readonly="true" path="bsProductList[0].prodName" htmlEscape="false" maxlength="64" class="input-xlarge inputWidth165"/>
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div>
										<form:input id="specDesc" readonly="true" path="bsProductList[0].prodSpec.specDesc" htmlEscape="false" maxlength="64" class="input-xlarge inputWidth115"/>
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div class="">
										<form:input path="bsProductList[0].batchNo" readonly="true" htmlEscape="false" maxlength="50" class="input-xlarge inputWidth115 required"/>
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div>
										<input name="bsProductList[0].indate" type="text" readonly="readonly" disabled="disabled" maxlength="20" class="input-medium Wdate required"
											   value="<fmt:formatDate value="${scPlan.bsProductList[0].indate}" pattern="yyyy-MM-dd"/>"
											   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div>
										<form:input path="bsProductList[0].planNumber" htmlEscape="false" maxlength="11" readonly="true" class="input-xlarge inputWidth115 required"/>
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div>
										<form:input path="bsProductList[0].remark" htmlEscape="false" maxlength="11" readonly="true" class="input-xlarge inputWidth165 required"/>
									</div>
								</div>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</div>

		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>