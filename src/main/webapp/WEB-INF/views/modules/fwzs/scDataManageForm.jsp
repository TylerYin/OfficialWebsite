<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fwzs" tagdir="/WEB-INF/tags/fwzs"%>
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
		<div id="generateBatchNoUrl" class="hidden">${ctx}/fwzs/scPlan/generateBatchNo</div>
		<input id="formTitle" name="formTitle" type="hidden" value="dataManagement"/>
		<input id="newPlan" name="newPlan" type="hidden" value="${newPlan}"/>
		<sys:message content="${message}"/>
		<c:set var="statusFlagIsAuditing" value="${not empty scPlan.status and scPlan.status eq '3'}"/>
		<c:set var="statusFlagIsCompleted" value="${not empty scPlan.status and (scPlan.status eq '5' or scPlan.status eq '6')}"/>
		<c:set var="isReadOnly" value="${statusFlagIsAuditing or statusFlagIsCompleted}"/>
		<div class="control-group">
			<label class="control-label">任务单号：</label>
			<div class="controls">
				 <form:input path="planNo" htmlEscape="false" maxlength="50" class="input-xlarge required" readonly="true"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">生产日期：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${cPlan.status == '4' || scPlan.status == '5' || scPlan.status == '6'}">
						<input id="madeDate" name="madeDate" type="text" readonly="readonly" disabled="disabled" maxlength="20" class="input-medium Wdate required"
							value="<fmt:formatDate value="${scPlan.madeDate}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</c:when>
					<c:otherwise>
						<input id="madeDate" name="madeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
							value="<fmt:formatDate value="${scPlan.madeDate}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</c:otherwise>
				</c:choose>
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
			<c:choose>
				<c:when test="${statusFlagIsCompleted}">
					<div class="controls">
						<form:textarea path="remark" htmlEscape="false" maxlength="166" readonly="true" class="input-xlarge "/>
					</div>
				</c:when>
				<c:otherwise>
					<div class="controls">
						<form:textarea path="remark" htmlEscape="false" maxlength="166" class="input-xlarge "/>
					</div>
				</c:otherwise>
			</c:choose>
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
                        <th>仓库</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody id="planContent">
				<c:choose>
					<c:when test="${not empty scPlan.bsProductList}">
						<c:forEach items="${scPlan.bsProductList}" var="bsProduct" varStatus="status">
							<tr>
								<td>
									<div class="control-group">
										<div>
											<c:choose>
												<c:when test="${not empty bsProduct.prodNo}">
													<form:input path="bsProductList[${status.index}].prodNo" class="input-xlarge inputWidth115 required" value="${scPlan.bsProduct.prodNo}" readonly="true" />
												</c:when>
												<c:otherwise>
													<form:input path="bsProductList[${status.index}].prodNo" class="input-xlarge inputWidth115 required " value="请选择" readonly="true" />
												</c:otherwise>
											</c:choose>
										</div>

										<div class="controls hidden">
											<form:input id="prodId" path="bsProductList[${status.index}].id" class="input-xlarge required" value="${bsProduct.id}" readonly="true" />
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
										<div>
											<form:input id="pesticideName" readonly="true" path="bsProductList[${status.index}].pesticideName" htmlEscape="false" maxlength="50" class="input-xlarge inputWidth165"/>
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
										<div>
											<form:input id="prodName" readonly="true" path="bsProductList[${status.index}].prodName" htmlEscape="false" maxlength="50" class="input-xlarge inputWidth165"/>
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
										<div class="">
                                            <c:choose>
                                                <c:when test="${statusFlagIsAuditing}">
                                                    <form:input path="bsProductList[${status.index}].batchNo" htmlEscape="false" maxlength="50" class="input-xlarge inputWidth115 required"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <form:input path="bsProductList[${status.index}].batchNo" htmlEscape="false" readonly="true" maxlength="50" class="input-xlarge inputWidth115 required"/>
                                                </c:otherwise>
                                            </c:choose>
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
										<div>
											<c:choose>
												<c:when test="${cPlan.status == '4' || scPlan.status == '5' || scPlan.status == '6'}">
													<input name="bsProductList[${status.index}].indate" id="indate" type="text" readonly="readonly" disabled="disabled" maxlength="20" class="input-medium Wdate required"
														   value="<fmt:formatDate value="${bsProduct.indate}" pattern="yyyy-MM-dd"/>"
														   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
												</c:when>
												<c:otherwise>
													<input name="bsProductList[${status.index}].indate" id="indate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
														   value="<fmt:formatDate value="${bsProduct.indate}" pattern="yyyy-MM-dd"/>"
														   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
										<div>
											<c:choose>
												<c:when test="${isReadOnly}">
													<form:input path="bsProductList[${status.index}].planNumber" htmlEscape="false" maxlength="9" readonly="true" class="input-xlarge inputWidth115 required"/>
												</c:when>
												<c:otherwise>
													<form:input path="bsProductList[${status.index}].planNumber" htmlEscape="false" maxlength="9" class="input-xlarge inputWidth115 required"/>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</td>
                                <td>
                                    <div class="control-group">
                                        <div>
                                            <form:input id="warehouseName${status.index}" path="bsProductList[${status.index}].warehouse.name" readonly="true" htmlEscape="false" class="input-xlarge inputWidth115 "/>
                                        </div>
                                        <div class="controls hidden">
                                            <form:input id="warehouse${status.index}" path="bsProductList[${status.index}].warehouse.id" class="input-xlarge"/>
                                        </div>
                                    </div>
                                </td>
								<td>
									<div class="control-group">
										<div>
											<c:choose>
												<c:when test="${scPlan.status == '5' || scPlan.status == '6'}">
													<form:input path="bsProductList[${status.index}].remark" readonly="true" htmlEscape="false" maxlength="166" class="input-xlarge inputWidth165"/>
												</c:when>
												<c:otherwise>
													<form:input path="bsProductList[${status.index}].remark" htmlEscape="false" maxlength="166" class="input-xlarge inputWidth165"/>
												</c:otherwise>
											</c:choose>
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
										<c:choose>
											<c:when test="${not empty scPlan.bsProductList[0].prodNo}">
												<form:input id="prodNo" path="bsProductList[0].prodNo" class="input-xlarge inputWidth115 required" value="${scPlan.bsProduct.prodNo}" readonly="true" />
											</c:when>
											<c:otherwise>
												<form:input id="prodNo" path="bsProductList[0].prodNo" class="input-xlarge inputWidth115 required " value="请选择" readonly="true" />
											</c:otherwise>
										</c:choose>
									</div>

									<div class="controls" class="hidden">
										<form:input id="prodId" path="bsProductList[0].id" class="input-xlarge required" value="${scPlan.bsProductList[0].id}" readonly="true" />
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div>
										<form:input id="pesticideName" readonly="true" path="bsProductList[0].pesticideName" htmlEscape="false" maxlength="50" class="input-xlarge inputWidth165"/>
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div>
										<form:input id="prodName" readonly="true" path="bsProductList[0].prodName" htmlEscape="false" maxlength="33" class="input-xlarge inputWidth165"/>
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
                                        <c:choose>
                                            <c:when test="${statusFlagIsAuditing}">
										        <form:input path="bsProductList[0].batchNo" htmlEscape="false" maxlength="50" class="input-xlarge inputWidth115 required"/>
                                            </c:when>
                                            <c:otherwise>
                                                <form:input path="bsProductList[0].batchNo" htmlEscape="false" readonly="true" maxlength="50" class="input-xlarge inputWidth115 required"/>
                                            </c:otherwise>
                                        </c:choose>
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div>
										<c:choose>
											<c:when test="${statusFlagIsCompleted}">
												<input name="bsProductList[0].indate" type="text" readonly="readonly" disabled="disabled" maxlength="20" class="input-medium Wdate required"
													   value="<fmt:formatDate value="${scPlan.bsProductList[0].indate}" pattern="yyyy-MM-dd"/>"
													   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
											</c:when>
											<c:otherwise>
												<input name="bsProductList[0].indate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
													   value="<fmt:formatDate value="${scPlan.bsProductList[0].indate}" pattern="yyyy-MM-dd"/>"
													   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div>
										<c:choose>
											<c:when test="${isReadOnly}">
												<form:input path="bsProductList[0].planNumber" htmlEscape="false" maxlength="9" readonly="true" class="input-xlarge inputWidth115 required"/>
											</c:when>
											<c:otherwise>
												<form:input path="bsProductList[0].planNumber" htmlEscape="false" maxlength="9" class="input-xlarge inputWidth115 required"/>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div>
										<form:input path="bsProductList[0].remark" htmlEscape="false" maxlength="166" class="input-xlarge inputWidth165 required"/>
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
			<c:if test="${!statusFlagIsCompleted}"> 
				<shiro:hasPermission name="fwzs:scPlan:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>

		<fwzs:selectProduct bsProducts="${bsProducts}"/>
	</form:form>
	<script src="${ctxStatic}/colorbox/jquery.colorbox.js"></script>
	<script src="${ctxStatic}/customized/scPlanForm.js"></script>
</body>
</html>