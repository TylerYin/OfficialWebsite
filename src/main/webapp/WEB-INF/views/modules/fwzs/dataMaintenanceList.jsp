<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务计划管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/fwzs/dataMaintenance/scPlan/">生产维护列表</a></li>
		<%-- <shiro:hasPermission name="fwzs:scPlan:edit"><li><a href="${ctx}/fwzs/manager/scPlan/form">任务计划添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="scPlan" action="${ctx}/fwzs/dataMaintenance/scPlan/" method="post" class="breadcrumb form-search">
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
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>任务单号</th>
				<th>产品编号</th>
				<th>农药名称</th>
				<th>产品名称</th>
				<th>产品规格</th>
				<th>生产批号</th>
				<th>计划产量</th>
				<th>实际产量</th>
				<th>生产日期</th>
				<th>生产线</th>
                <th>创建人</th>
				<th>状态</th>
				<shiro:hasPermission name="fwzs:scPlan:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="scPlan">
			<c:if test="${not empty scPlan.bsProductList}">
				<c:set var="listSize" value="${fn:length(scPlan.bsProductList)}"/>
				<c:forEach items="${scPlan.bsProductList}" var="bsProduct" varStatus="status">
					<c:choose>
						<c:when test="${status.index eq 0}">
							<tr>
								<td rowspan="${listSize}">
									${scPlan.planNo}
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
								<td>
									${bsProduct.realNumber}
								</td>
								<td rowspan="${listSize}">
									<fmt:formatDate value="${scPlan.madeDate}" pattern="yyyy-MM-dd"/>
								</td>
								<td rowspan="${listSize}">
									${scPlan.scLines.lineName}
								</td>
                                <td rowspan="${listSize}">
                                    ${scPlan.createBy.id}
                                </td>
								<td rowspan="${listSize}">
									${fns:getDictLabel(scPlan.status, 'sc_status', '')}
								</td>
								<shiro:hasPermission name="fwzs:scPlan:edit"><td rowspan="${listSize}">
									<table class="operationTable">
										<tr>
											<td style="border-style:none;">
												<c:forEach items="${scPlan.bsProductList}" var="bsProduct" varStatus="status">
                                                    <a href="${ctx}/fwzs/fwmRelate/export?scPlan.id=${scPlan.id}&scPlan.bsProduct.id=${bsProduct.id}">导出</a><br>
												</c:forEach>
											</td>

											<td style="border-style:none;">
                                                <c:if test="${scPlan.status ne '6'}">
												    <a href="${ctx}/fwzs/dataMaintenance/scPlan/form?id=${scPlan.id}">修改&nbsp;&nbsp;</a>
                                                </c:if>
												<a href="${ctx}/fwzs/dataMaintenance/scPlan/deleteScPlan?id=${scPlan.id}&formTitle=planManagement" onclick="return confirmx('确认要删除该任务计划吗？', this.href)">删除</a>
											</td>

											<c:if test="${scPlan.status == '5' || scPlan.status == '6'}">
												<td style="border-style:none;">
													<a href="${ctx}/fwzs/scPlan/review?id=${scPlan.id}&formTitle=dataMaintenance">查看</a>
												</td>
											</c:if>
										</tr>
									</table>
								</td></shiro:hasPermission>
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
										${bsProduct.realNumber}
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
</body>
</html>