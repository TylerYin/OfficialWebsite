<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fwzs" tagdir="/WEB-INF/tags/fwzs"%>
<html>
<head>
	<title>入库计划管理</title>
	<meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/customized/sys.css" />
    <link rel="stylesheet" href="${ctxStatic}/colorbox/colorbox.css" />
    <link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
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
    <div id="submitPlanUrl" style="display:none">${ctx}/fwzs/inBound/delete/</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/fwzs/inBound/">入库列表</a></li>
	</ul>

	<form:form id="searchForm" modelAttribute="inBound" action="${ctx}/fwzs/inBound/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<label>生产任务单号：</label>
			<form:input path="scPlan.planNo" htmlEscape="false" maxlength="50" class="input-medium"/>

			<label>产品名称：</label>
            <form:input path="bsProduct.id" id="prodId" htmlEscape="false" maxlength="50" class="input-medium" cssStyle="display: none" />
            <c:choose>
                <c:when test="${not empty inBound.bsProduct.prodName}">
                    <form:input path="bsProduct.prodName" id="prodName" htmlEscape="false" readonly="true" maxlength="50" class="input-medium"/>
                </c:when>
                <c:otherwise>
                    <form:input path="bsProduct.prodName" id="prodName" htmlEscape="false" readonly="true" maxlength="50" class="input-medium" value="请选择"/>
                </c:otherwise>
            </c:choose>
		</div>
		<div class="marginTop">
			<label>日期范围：&nbsp;</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${inBound.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			&nbsp;&nbsp;--&nbsp;&nbsp;<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${inBound.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>生产任务单号</th>
				<th>产品名称</th>
				<th>产品批号</th>
				<th>包装比例</th>
				<th>仓库</th>
				<th>单品入库数量</th>
				<th>入库日期</th>
			</tr>
		</thead>
		<tbody id="contentBody">
        <c:forEach items="${page.list}" var="inBound">
            <tr>
                <c:choose>
                    <c:when test="${inBound.mergeScPlanRowCount gt 1}">
                        <td rowspan="${inBound.mergeScPlanRowCount}">
                            ${inBound.scPlan.planNo}
                        </td>
                        <td>
                            ${inBound.bsProduct.prodName}
                        </td>
                        <td>
                            ${inBound.bsProduct.batchNo}
                        </td>
                        <td>
                            ${inBound.bsProduct.packRate}
                        </td>
                        <td>
                            ${inBound.warehouse.name}
                        </td>
                        <td>
                            ${inBound.inBoundNumber}
                        </td>
                        <td rowspan="${inBound.mergeScPlanRowCount}">
                            <fmt:formatDate value="${inBound.inBoundDate}" pattern="yyyy-MM-dd"/>
                        </td>
                    </c:when>
                    <c:when test="${inBound.mergeScPlanRowCount eq 1}">
                        <td>
                            ${inBound.scPlan.planNo}
                        </td>
                        <td>
                            ${inBound.bsProduct.prodName}
                        </td>
                        <td>
                            ${inBound.bsProduct.batchNo}
                        </td>
                        <td>
                            ${inBound.bsProduct.packRate}
                        </td>
                        <td>
                            ${inBound.warehouse.name}
                        </td>
                        <td>
                            ${inBound.inBoundNumber}
                        </td>
                        <td>
                            <fmt:formatDate value="${inBound.inBoundDate}" pattern="yyyy-MM-dd"/>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${empty inBound.scPlan or empty inBound.scPlan.planNo}">
                            <td></td>
                        </c:if>
                        <td>
                            ${inBound.bsProduct.prodName}
                        </td>
                        <td>
                            ${inBound.bsProduct.batchNo}
                        </td>
                        <td>
                            ${inBound.bsProduct.packRate}
                        </td>
                        <td>
                            ${inBound.warehouse.name}
                        </td>
                        <td>
                            ${inBound.inBoundNumber}
                        </td>
                        <c:if test="${empty inBound.scPlan or empty inBound.scPlan.planNo}">
                            <td></td>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
		</tbody>
	</table>

	<div class="pagination">${page}</div>
    <fwzs:selectProduct bsProducts="${bsProducts}"/>
    <script src="${ctxStatic}/colorbox/jquery.colorbox.js"></script>
	<script src="${ctxStatic}/customized/inBoundList.js"></script>
    <script src="${ctxStatic}/customized/common.js"/>
</body>
</html>