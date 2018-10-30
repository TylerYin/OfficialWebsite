<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务计划管理</title>
	<meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/colorbox/colorbox.css" />
    <link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
	<script type="text/javascript">
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
	</script>
	<link rel="stylesheet" href="${ctxStatic}/colorbox/colorbox.css" />
</head>
<body>
	<ul class="nav nav-tabs">
        <c:choose>
            <c:when test="${scPlanType eq '5'}">
                <li class="active"><a href="${ctx}/fwzs/check/scPlan?scPlanType=5">未质检报告列表</a></li>
                <li><a href="${ctx}/fwzs/check/scPlan?scPlanType=6">已质检报告列表</a></li>
                <li><a href="${ctx}/fwzs/check/scPlan?scPlanType=7">质检未通过报告列表</a></li>
            </c:when>
            <c:when test="${scPlanType eq '6'}">
                <li><a href="${ctx}/fwzs/check/scPlan?scPlanType=5">未质检报告列表</a></li>
                <li class="active"><a href="${ctx}/fwzs/check/scPlan?scPlanType=6">已质检报告列表</a></li>
                <li><a href="${ctx}/fwzs/check/scPlan?scPlanType=7">质检未通过报告列表</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${ctx}/fwzs/check/scPlan?scPlanType=5">未质检报告列表</a></li>
                <li><a href="${ctx}/fwzs/check/scPlan?scPlanType=6">已质检报告列表</a></li>
                <li class="active"><a href="${ctx}/fwzs/check/scPlan?scPlanType=7">质检未通过报告列表</a></li>
            </c:otherwise>
        </c:choose>
	</ul>
    <div id="qcReason" class="hidden"></div>
    <div id="findQcNotPassURL" class="hidden">${ctx}/fwzs/check/scPlan/findQcNotPass</div>
	<form:form id="searchForm" modelAttribute="scPlan" action="${ctx}/fwzs/check/scPlan/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="scPlanType" name="scPlanType" type="hidden" value="${scPlanType}"/>
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
				<th>质检状态</th>
                <c:if test="${scPlanType eq '7'}"><th>质检未通过次数</th></c:if>
				<th>质检报告</th>
				<th>质检人</th>
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
										${fns:getDictLabel(scPlan.status, 'sc_status', '')}
									</td>

                                    <c:if test="${scPlanType eq '7'}">
                                        <td rowspan="${listSize}" class="showQcNotPassReason">
                                            ${scPlan.qcCount}
                                            <span class="hidden">${scPlan.id}</span>
                                        </td>
                                    </c:if>

									<td id="showVerifyResult">
										<img src="${bsProduct.checkPlanUrl}" style="width: 25px;height:25px;"/>
									</td>
									<td rowspan="${listSize}">
										${scPlan.qcBy.name}
									</td>
									<td rowspan="${listSize}">
										<shiro:hasPermission name="fwzs:scPlan:edit">
                                            <c:choose>
                                                <c:when test="${scPlan.status eq '6' or scPlan.status eq '7'}">
                                                    <a href="${ctx}/fwzs/check/scPlan/form?id=${scPlan.id}">查看</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="${ctx}/fwzs/check/scPlan/form?id=${scPlan.id}&scPlanType=${scPlanType}">质检</a>
                                                </c:otherwise>
                                            </c:choose>
										</shiro:hasPermission>
									</td>
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
									<td id="showVerifyResult">
										<img src="${bsProduct.checkPlanUrl}" style="width: 25px;height:25px;"/>
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
	<script src="${ctxStatic}/colorbox/jquery.colorbox.js"></script>
	<script src="${ctxStatic}/customized/scPlanListCheck.js"></script>
</body>
</html>