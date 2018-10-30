<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fwzs" tagdir="/WEB-INF/tags/fwzs"%>
<html>
<head>
	<title>经销商收货管理</title>
	<meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/customized/sys.css" />
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
</head>
<body>
	<ul class="nav nav-tabs">
        <c:choose>
            <c:when test="${dealerInBound.status eq '1'}">
                <li class="active"><a href="${ctx}/fwzs/dealerInBound?status=1">经销商待收货管理</a></li>
                <li><a href="${ctx}/fwzs/dealerInBound?status=2">经销商已收货管理</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${ctx}/fwzs/dealerInBound?status=1">经销商待收货管理</a></li>
                <li class="active"><a href="${ctx}/fwzs/dealerInBound?status=2">经销商已收货管理</a></li>
            </c:otherwise>
        </c:choose>
	</ul>

	<form:form id="searchForm" modelAttribute="dealerInBound" action="${ctx}/fwzs/dealerInBound/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <input id="status" name="status" type="hidden" value="${dealerInBound.status}"/>
		<div>
			<label>发货单号：</label>
			<form:input path="boundNo" htmlEscape="false" maxlength="50" class="input-medium"/>

			<label>产品名称：</label>
            <form:input path="bsProduct.id" id="prodId" htmlEscape="false" maxlength="50" class="input-medium" cssStyle="display: none" />
            <c:choose>
                <c:when test="${not empty dealerInBound.bsProduct.prodName}">
                    <form:input path="bsProduct.prodName" id="prodName" htmlEscape="false" readonly="true" maxlength="50" class="input-medium"/>
                </c:when>
                <c:otherwise>
                    <form:input path="bsProduct.prodName" id="prodName" htmlEscape="false" readonly="true" maxlength="50" class="input-medium" value="请选择"/>
                </c:otherwise>
            </c:choose>
		</div>
		<div class="marginTop">
			<label>日期范围：&nbsp;</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${dealerInBound.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			&nbsp;&nbsp;--&nbsp;&nbsp;<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${dealerInBound.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>发货单号</th>
				<th>发货方</th>
				<th>产品名称</th>
				<th>包装比例</th>
				<th>单品实际发货数量</th>
				<th>下单日期</th>
				<th>发货日期</th>
                <c:if test="${dealerInBound.status eq '2'}">
				    <th>收货日期</th>
                </c:if>
				<th>物流单号</th>
				<th>物流名称</th>
				<th>状态</th>
                <c:if test="${dealerInBound.status eq '1'}">
                    <th>操作</th>
                </c:if>
			</tr>
		</thead>
		<tbody id="contentBody">
        <c:forEach items="${page.list}" var="inBound">
            <c:set var="boundNo" value="${inBound.boundNo}"/>
            <c:set var="listSize" value="${fn:length(inBound.bsProductList)}"/>
            <c:forEach items="${inBound.bsProductList}" var="bsProduct" varStatus="status">
                <c:choose>
                    <c:when test="${status.index eq 0}">
                        <tr>
                            <td rowspan="${listSize}">
                                ${inBound.boundNo}
                            </td>
                            <td rowspan="${listSize}">
                                ${inBound.shipper}
                            </td>
                            <td>
                                ${bsProduct.prodName}
                            </td>
                            <td>
                                ${bsProduct.packRate}
                            </td>
                            <td>
                                ${bsProduct.realNumber}
                            </td>
                            <td rowspan="${listSize}">
                                <fmt:formatDate value="${inBound.createDate}" pattern="yyyy-MM-dd"/>
                            </td>
                            <td rowspan="${listSize}">
                                <fmt:formatDate value="${inBound.boundDate}" pattern="yyyy-MM-dd"/>
                            </td>
                            <c:if test="${dealerInBound.status eq '2'}">
                                <td rowspan="${listSize}">
                                    <fmt:formatDate value="${inBound.receiveDate}" pattern="yyyy-MM-dd"/>
                                </td>
                            </c:if>
                            <td rowspan="${listSize}">
                                ${inBound.shipNo}
                            </td>
                            <td rowspan="${listSize}">
                                ${inBound.shipName}
                            </td>
                            <td rowspan="${listSize}">
                                <c:choose>
                                    <c:when test="${inBound.status eq '1'}">
                                        待收货
                                    </c:when>
                                    <c:otherwise>
                                        已收货
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <c:if test="${inBound.status eq '1'}">
                                <c:choose>
                                    <c:when test="${fn:contains(boundNo, 'JXSCKD')}">
                                        <td rowspan="${listSize}">
                                            <a href="${ctx}/fwzs/dealerInBound/updateStatus?dealerBoundId=${inBound.id}">确认收货</a>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td rowspan="${listSize}">
                                            <a href="${ctx}/fwzs/dealerInBound/updateStatus?boundId=${inBound.id}">确认收货</a>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>
                                ${bsProduct.prodName}
                            </td>
                            <td>
                                ${bsProduct.packRate}
                            </td>
                            <td>
                                ${bsProduct.realNumber}
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:forEach>
		</tbody>
	</table>

	<div class="pagination">${page}</div>
    <fwzs:selectProduct bsProducts="${bsProducts}"/>
    <script src="${ctxStatic}/colorbox/jquery.colorbox.js"></script>
    <script src="${ctxStatic}/customized/dealerBoundList.js"></script>
    <script src="${ctxStatic}/customized/common.js"/>
</body>
</html>