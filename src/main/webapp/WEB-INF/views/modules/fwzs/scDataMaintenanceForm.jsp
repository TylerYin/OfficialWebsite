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
		<li><a href="${ctx}/fwzs/dataMaintenance/scPlan/">任务计划列表</a></li>
		<li class="active"><a href="${ctx}/fwzs/dataMaintenance/scPlan/form?id=${scPlan.id}">任务计划<shiro:hasPermission name="fwzs:scPlan:edit">${not empty scPlan.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fwzs:scPlan:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="scPlan" action="${ctx}/fwzs/dataMaintenance/scPlan/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div id="generateBatchNoUrl" class="hidden">${ctx}/fwzs/scPlan/generateBatchNo</div>
		<div id="findFwmFileByProductUrl" class="hidden">${ctx}/fwzs/dataMaintenance/scPlan/findFwmFileByProduct</div>
		<input id="deleteKey" name="deleteKey" type="hidden" value=""/>
		<input id="formTitle" name="formTitle" type="hidden" value="dataMaintenance"/>
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
                    <c:when test="${scPlan.status eq '6'}">
                        <input name="madeDate" id="madeDate" type="text" readonly="readonly" disabled="true" maxlength="20" class="input-medium Wdate required"
                               value="<fmt:formatDate value="${scPlan.madeDate}" pattern="yyyy-MM-dd"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                    </c:when>
                    <c:otherwise>
                        <input name="madeDate" id="madeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
                               value="<fmt:formatDate value="${scPlan.madeDate}" pattern="yyyy-MM-dd"/>"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                    </c:otherwise>
                </c:choose>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">生产线：</label>
			<div class="controls">
				<form:select path="scLines.id" disabled="true" class="input-xlarge required" >
					<form:option value="" label="请选择"/>
					<form:options items="${scLines}" itemLabel="lineName" itemValue="id" htmlEscape="false" />
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
            <label class="control-label">计划状态：</label>
            <div class="controls">
                <c:choose>
                    <c:when test="${scPlan.status eq '6'}">
                        <form:select path="status" class="input-xlarge" disabled="true">
                            <form:option value="" label="请选择"/>
                            <form:options items="${fns:getDictList('sc_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </c:when>
                    <c:otherwise>
                        <form:select path="status" class="input-xlarge">
                            <form:option value="" label="请选择"/>
                            <c:forEach items="${fns:getDictList('sc_status')}" var="scStatus">
                                <c:if test="${scStatus.value ge '5'}">
                                    <form:option value="${scStatus.value}" label="${scStatus.label}"/>
                                </c:if>
                            </c:forEach>
                        </form:select>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" maxlength="166" readonly="true" class="input-xlarge "/>
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
                        <th>仓库</th>
                        <c:if test="${scPlan.status eq '3'}">
                        <th>码文件</th>
                        </c:if>
						<th>备注</th>
                        <!--
						<shiro:hasPermission name="fwzs:scPlan:edit">
							<th>操作</th>
						</shiro:hasPermission>
						-->
					</tr>
				</thead>
				<tbody id="planContent">
				<c:if test="${not empty scPlan.bsProductList}">
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
										<form:input id="pesticideName" readonly="true" path="bsProductList[${status.index}].pesticideName" htmlEscape="false" maxlength="50" class="input-xlarge inputWidth165"/>
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div>
										<form:input id="prodName" readonly="true" path="bsProductList[${status.index}].prodName" htmlEscape="false" maxlength="33" class="input-xlarge inputWidth165"/>
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div>
										<form:input id="specDesc" readonly="true" path="bsProductList[${status.index}].prodSpec.specDesc" htmlEscape="false" maxlength="50" class="input-xlarge inputWidth115"/>
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div class="">
                                        <c:choose>
                                            <c:when test="${scPlan.status eq '6'}">
                                                <form:input path="bsProductList[${status.index}].batchNo" disabled="true" htmlEscape="false" maxlength="50" class="input-xlarge inputWidth115 required"/>
                                            </c:when>
                                            <c:otherwise>
                                                <form:input path="bsProductList[${status.index}].batchNo" htmlEscape="false" maxlength="50" class="input-xlarge inputWidth115 required"/>
                                            </c:otherwise>
                                        </c:choose>
									</div>
								</div>
							</td>
							<td>
								<div class="control-group">
									<div>
                                        <c:choose>
                                            <c:when test="${scPlan.status eq '6'}">
                                                <input name="bsProductList[${status.index}].indate" id="indate" disabled="true" type="text" maxlength="20" class="input-medium Wdate required"
                                                       value="<fmt:formatDate value="${bsProduct.indate}" pattern="yyyy-MM-dd"/>"
                                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                            </c:when>
                                            <c:otherwise>
                                                <input name="bsProductList[${status.index}].indate" id="indate" type="text" maxlength="20" class="input-medium Wdate required"
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
										<form:input path="bsProductList[${status.index}].planNumber" htmlEscape="false" maxlength="9" readonly="true" class="input-xlarge inputWidth115 required"/>
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
                            <c:if test="${scPlan.status eq '3'}">
                                <td>
                                    <div class="control-group">
                                        <div>
                                            <form:input id="fwmFileName" path="bsProductList[${status.index}].fwmFileName" htmlEscape="false" readonly="true" class="input-xlarge inputWidth115"/>
                                            <form:hidden id="fwmFileId" path="bsProductList[${status.index}].fwmFileId" />
                                        </div>
                                    </div>
                                </td>
                            </c:if>

							<td>
								<div class="control-group">
									<div>
										<form:input path="bsProductList[${status.index}].remark" readonly="true" htmlEscape="false" maxlength="166" class="input-xlarge inputWidth165"/>
									</div>
								</div>
							</td>
                            <!--
							<shiro:hasPermission name="fwzs:scPlan:edit">
								<td class="inputWidth60" id="SC${bsProduct.mappingId}">
									<span class="hidden" name="planId">${scPlan.id}</span>
									<span class="hidden" name="prodId">${bsProduct.mappingId}</span>
									<a href="javascript:SCDataMaintenanceForm.Events.deleteScPlan('${scPlan.id},${bsProduct.mappingId}');" onclick="return confirmx('确认要删除该任务计划吗？', this.href)">删除</a>
								</td>
							</shiro:hasPermission>
							-->
						</tr>
					</c:forEach>
				</c:if>
				</tbody>
			</table>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="fwzs:scPlan:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>

        <fwzs:selectFwm/>
	</form:form>
	<script src="${ctxStatic}/colorbox/jquery.colorbox.js"></script>
	<script src="${ctxStatic}/customized/scDataMaintenanceForm.js"></script>
</body>
</html>