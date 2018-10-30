<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务计划管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<div id="submitPlanUrl" style="display:none">${ctx}/fwzs/scPlan/updateStatus/</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/fwzs/scPlan/">任务计划列表</a></li>
		<shiro:hasPermission name="fwzs:scPlan:edit"><li><a href="${ctx}/fwzs/scPlan/form">任务计划添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="scPlan" action="${ctx}/fwzs/scPlan/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>任务单号：</label>
				<form:input path="planNo" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>生产批号：</label>
				<form:input path="batchNo" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnSubmitPlans" class="btn btn-primary" type="button" value="提交"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input type="checkbox" id="selectAllProduct" name="selectAllProduct" value="全选"/></th>
				<th>任务单号</th>
				<th>产品编号</th>
				<th>农药名称</th>
				<th>产品名称</th>
				<th>产品规格</th>
				<th>生产批号</th>
				<th>计划产量</th>
				<th>生产日期</th>
				<th>有效期至</th>
				<th>生产线</th>
				<th>创建日期</th>
				<th>创建人</th>
				<shiro:hasPermission name="fwzs:scPlan:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="contentBody">
			<c:forEach items="${page.list}" var="scPlan">
				<c:if test="${not empty scPlan.bsProductList}">
					<c:set var="listSize" value="${fn:length(scPlan.bsProductList)}"/>
					<c:forEach items="${scPlan.bsProductList}" var="bsProduct" varStatus="status">
						<c:choose>
							<c:when test="${status.index eq 0}">
								<tr>
									<td rowspan="${listSize}">
										<input class="itemCheck" type="checkbox" name="selectProduct"/>
										<span style="display:none">${scPlan.id}</span>
									</td>
                                    <td rowspan="${listSize}">
                                        <a href="${ctx}/fwzs/scPlan/form?id=${scPlan.id}&formTitle=planManagement"> ${scPlan.planNo}</a>
                                    </td>
									<td>
											${bsProduct.prodNo}
									</td>

									<td>
											${bsProduct.pesticideName}
									</td>
									<td>
											${bsProduct.prodName}
									</td>
									<td>
											${bsProduct.prodSpec.specDesc}
									</td>
									<td>
											${bsProduct.batchNo}
									</td>
									<td>
											${bsProduct.planNumber}
									</td>

									<td rowspan="${listSize}">
										<fmt:formatDate value="${scPlan.madeDate}" pattern="yyyy-MM-dd"/>
									</td>
									<td>
										<fmt:formatDate value="${bsProduct.indate}" pattern="yyyy-MM-dd"/>
									</td>
									<td rowspan="${listSize}">
										${scPlan.scLines.lineName}
									</td>
									<td rowspan="${listSize}">
										<fmt:formatDate value="${scPlan.createDate}" pattern="yyyy-MM-dd"/>
									</td>
									<td rowspan="${listSize}">
										${scPlan.createBy.id}
									</td>
									<shiro:hasPermission name="fwzs:scPlan:edit">
										<td rowspan="${listSize}">
											<a href="${ctx}/fwzs/scPlan/form?id=${scPlan.id}&formTitle=planManagement">修改</a>
											<a href="${ctx}/fwzs/scPlan/deleteScPlan?id=${scPlan.id}&formTitle=planManagement" onclick="return confirmx('确认要删除该任务计划吗？', this.href)">删除</a>
										</td>
									</shiro:hasPermission>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td>
											${bsProduct.prodNo}
									</td>
									<td>
											${bsProduct.pesticideName}
									</td>
									<td>
											${bsProduct.prodName}
									</td>
									<td>
											${bsProduct.prodSpec.specDesc}
									</td>
									<td>
											${bsProduct.batchNo}
									</td>
									<td>
											${bsProduct.planNumber}
									</td>
									<td>
										<fmt:formatDate value="${bsProduct.indate}" pattern="yyyy-MM-dd"/>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script src="${ctxStatic}/customized/scPlanList.js"></script>
</body>
</html>