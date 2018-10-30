<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务计划管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/fwzs/check/scPlan/">检验报告列表</a></li>
		<li class="active"><a href="${ctx}/fwzs/check/scPlan/form?id=${scPlan.id}">检验报告<shiro:hasPermission name="fwzs:scPlan:edit">${not empty scPlan.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fwzs:scPlan:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="scPlan" action="${ctx}/fwzs/check/scPlan/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input id="scPlanType" name="scPlanType" type="hidden" value="${scPlanType}"/>
		<input id="newPlan" name="newPlan" type="hidden" value="${newPlan}"/>
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
            <label class="control-label">质检结果：</label>
            <div class="controls">
                <c:choose>
                    <c:when test="${scPlan.status eq '6' or scPlan.status eq '7'}">
                        <form:select path="status" disabled="true" class="input-medium required">
                            <form:option value="" label="请选择"/>
                            <form:option value="6" label="质检通过"/>
                            <form:option value="7" label="质检未通过"/>
                        </form:select>
                    </c:when>
                    <c:otherwise>
                        <form:select path="status" id="scPlanStatus" class="input-medium required">
                            <form:option value="" label="请选择"/>
                            <form:option value="6" label="质检通过"/>
                            <form:option value="7" label="质检未通过"/>
                        </form:select>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <c:if test="${scPlan.status ne '6'}">
            <c:choose>
                <c:when test="${scPlan.status eq '7'}">
                    <div class="control-group" id="qcNotPass">
                </c:when>
                <c:otherwise>
                    <div class="control-group" id="qcNotPass" style="display: none">
                </c:otherwise>
            </c:choose>
                <label class="control-label">质检没有通过原因：</label>
                <div class="controls">
                    <c:choose>
                        <c:when test="${scPlan.status eq '7'}">
                            <form:textarea path="qcNotPassReason" htmlEscape="false" readonly="true" maxlength="166" class="input-xlarge "/>
                        </c:when>
                        <c:otherwise>
                            <form:textarea path="qcNotPassReason" htmlEscape="false" maxlength="166" class="input-xlarge "/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:if>

		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
                <form:textarea path="remark" readonly="true" htmlEscape="false" maxlength="166" class="input-xlarge "/>
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
					<th>备注</th>
					<th>检验报告单</th>
				</tr>
				</thead>
				<tbody id="planContent">
					<c:if test="${not empty scPlan.bsProductList}">
						<c:forEach items="${scPlan.bsProductList}" var="bsProduct" varStatus="status">
							<tr>
								<td>
									<div class="control-group">
										<div>
											<c:choose>
												<c:when test="${not empty bsProduct.prodNo}">
													<form:input id="prodNo" path="bsProductList[${status.index}].prodNo" class="input-xlarge inputWidth115 required" value="${scPlan.bsProduct.prodNo}" readonly="true" />
												</c:when>
												<c:otherwise>
													<form:input id="prodNo" path="bsProductList[${status.index}].prodNo" class="input-xlarge inputWidth115 required " value="请选择" readonly="true" />
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
											<form:input id="prodName" readonly="true" path="bsProductList[${status.index}].prodName" htmlEscape="false" maxlength="33" class="input-xlarge inputWidth165"/>
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
											<form:input readonly="true" path="bsProductList[${status.index}].batchNo" htmlEscape="false" maxlength="50" class="input-xlarge inputWidth115 required"/>
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
										<input name="bsProductList[${status.index}].indate" type="text" readonly="readonly" disabled="disabled" maxlength="20" class="input-medium Wdate required"
											   value="<fmt:formatDate value="${bsProduct.indate}" pattern="yyyy-MM-dd"/>"
											   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
									</div>
								</td>
								<td>
									<div class="control-group">
										<form:input path="bsProductList[${status.index}].planNumber" htmlEscape="false" maxlength="9" readonly="true" class="input-xlarge inputWidth115 required"/>
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
											<form:input path="bsProductList[${status.index}].remark" readonly="true" htmlEscape="false" maxlength="166" class="input-xlarge inputWidth165"/>
										</div>
									</div>
								</td>
								<td>
									<div class="control-group">
                                        <c:choose>
                                            <c:when test="${scPlan.status eq '6' or scPlan.status eq '7'}">
                                                <img src="${bsProduct.checkPlanUrl}" style="width: 25px;height:25px;"/>
                                            </c:when>
                                            <c:otherwise>
										        <form:hidden id="checkPlanUrl${status.index}" path="bsProductList[${status.index}].checkPlanUrl" htmlEscape="false" maxlength="150" class="input-xlarge"/>
                                                <sys:ckfinder input="checkPlanUrl${status.index}" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="30" maxHeight="30"/>
                                            </c:otherwise>
                                        </c:choose>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>

		<div class="form-actions">
            <c:if test="${scPlan.status eq '5'}">
			    <shiro:hasPermission name="fwzs:scPlan:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
            </c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
    <script src="${ctxStatic}/customized/scPlanFormCheck.js"></script>
</body>
</html>