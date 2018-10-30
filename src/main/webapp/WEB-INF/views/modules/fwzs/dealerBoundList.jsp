<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fwzs" tagdir="/WEB-INF/tags/fwzs"%>
<html>
<head>
	<title>经销商发货计划管理</title>
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
    <div id="submitPlanUrl" style="display:none">${ctx}/fwzs/dealerOutBound/delete/</div>
	<ul class="nav nav-tabs">
        <c:choose>
            <c:when test="${dealerBound.status eq '0'}">
                <li class="active"><a href="${ctx}/fwzs/dealerOutBound?status=0">待出库计划列表</a></li>
                <li><a href="${ctx}/fwzs/dealerOutBound?status=1">已出库计划列表</a></li>
                <li><a href="${ctx}/fwzs/dealerOutBound?status=2">已收货计划列表</a></li>
            </c:when>
            <c:when test="${dealerBound.status eq '1'}">
                <li><a href="${ctx}/fwzs/dealerOutBound?status=0">待出库计划列表</a></li>
                <li class="active"><a href="${ctx}/fwzs/dealerOutBound?status=1">已出库计划列表</a></li>
                <li><a href="${ctx}/fwzs/dealerOutBound?status=2">已收货计划列表</a></li>
            </c:when>
            <c:when test="${dealerBound.status eq '2'}">
                <li><a href="${ctx}/fwzs/dealerOutBound?status=0">待出库计划列表</a></li>
                <li><a href="${ctx}/fwzs/dealerOutBound?status=1">已出库计划列表</a></li>
                <li class="active"><a href="${ctx}/fwzs/dealerOutBound?status=2">已收货计划列表</a></li>
            </c:when>
        </c:choose>
        <c:if test="${isDealer}">
            <shiro:hasPermission name="fwzs:dealer:edit"><li><a href="${ctx}/fwzs/dealerOutBound/form">经销商发货计划添加</a></li></shiro:hasPermission>
        </c:if>
	</ul>

    <div id="updateStatusURL" class="hidden">${ctx}/fwzs/dealerOutBound/updateStatus</div>
    <div id="validateRealNumberURL" class="hidden">${ctx}/fwzs/dealerOutBound/validateRealNumber</div>

    <div id="dealerTreeMenuURL" style="display:none">${ctx}/fwzs/dealer/dealerMenuData</div>
    <div id="inDealerListMenuContent" class="menuContent" style="display:none; position: absolute; background: #c2ccd1">
        <ul id="inDealerListTree" class="ztree" style="margin-top:0; width:180px;"></ul>
    </div>

	<form:form id="searchForm" modelAttribute="dealerBound" action="${ctx}/fwzs/dealerOutBound/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <input id="status" name="status" type="hidden" value="${dealerBound.status}"/>
		<div>
			<label>发货单号：</label>
			<form:input path="boundNo" htmlEscape="false" maxlength="50" class="input-medium"/>

			<label>产品名称：</label>
            <form:input path="bsProduct.id" id="prodId" htmlEscape="false" maxlength="50" class="input-medium" cssStyle="display: none" />
            <c:choose>
                <c:when test="${not empty dealerBound.bsProduct.prodName}">
                    <form:input path="bsProduct.prodName" id="prodName" htmlEscape="false" readonly="true" maxlength="50" class="input-medium"/>
                </c:when>
                <c:otherwise>
                    <form:input path="bsProduct.prodName" id="prodName" htmlEscape="false" readonly="true" maxlength="50" class="input-medium" value="请选择"/>
                </c:otherwise>
            </c:choose>

			<label>收货经销商：</label>
            <form:input path="dealer.id" id="inDealer" htmlEscape="false" maxlength="50" cssStyle="display:none;"/>
            <c:choose>
                <c:when test="${not empty dealerBound.dealer.name}">
                    <form:input path="dealer.name" id="inDealerName" htmlEscape="false" readonly="true" onclick="showDealerMenu('in')" maxlength="50" class="input-medium"/>
                </c:when>
                <c:otherwise>
                    <form:input path="dealer.name" id="inDealerName" htmlEscape="false" readonly="true" onclick="showDealerMenu('in')" maxlength="50" class="input-medium" value="请选择"/>
                </c:otherwise>
            </c:choose>
		</div>
		<div class="marginTop">
			<label>日期范围：&nbsp;</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${dealerBound.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			&nbsp;&nbsp;--&nbsp;&nbsp;<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${dealerBound.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
            <c:if test="${isDealer and dealerBound.status eq '0'}">
			    <input id="btnDelete" class="btn btn-primary" type="button" value="删除"/>
            </c:if>
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
                <th>
                    <c:if test="${isDealer and dealerBound.status eq '0'}">
                        <input type="checkbox" id="selectAllPlan" name="selectAllPlan" value="全选"/>全选
                    </c:if>
                    发货单号
                </th>
				<th>产品名称</th>
				<th>包装比例</th>
                <c:if test="${!isDealer}">
				    <th>发货经销商</th>
                </c:if>
				<th>收货经销商</th>
				<th>单品计划发货数量</th>
				<th>单品实际发货数量</th>
				<th>出库员</th>
				<th>计划日期</th>
                <c:if test="${dealerBound.status eq '1'}">
                    <th>发货日期</th>
                    <th>物流单号</th>
                    <th>物流名称</th>
                </c:if>
                <c:if test="${dealerBound.status eq '2'}">
                    <th>发货日期</th>
                    <th>收货日期</th>
                    <th>物流单号</th>
                    <th>物流名称</th>
                </c:if>
				<th>状态</th>
                <shiro:hasPermission name="fwzs:dealer:edit">
                    <c:if test="${isDealer and dealerBound.status eq '0'}">
                        <th>操作</th>
                    </c:if>
                </shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="contentBody">
        <c:forEach items="${page.list}" var="outBound">
            <c:if test="${not empty outBound.bsProductList}">
            <c:set var="listSize" value="${fn:length(outBound.bsProductList)}"/>
            <c:forEach items="${outBound.bsProductList}" var="bsProduct" varStatus="status">
                <c:choose>
                    <c:when test="${status.index eq 0}">
                        <tr>
                            <td rowspan="${listSize}">
                                <c:if test="${isDealer and outBound.status eq '0'}">
                                    <input class="itemCheck" type="checkbox"/>
                                </c:if>
                                <span style="display:none">${outBound.id}</span>
                                <span>${outBound.boundNo}</span>
                            </td>
                            <td>
                                ${bsProduct.prodName}
                            </td>
                            <td>
                                ${bsProduct.packRate}
                            </td>

                            <c:if test="${!isDealer}">
                                <td rowspan="${listSize}">
                                    <c:if test="${not empty outBound.inDealer}">
                                        ${outBound.inDealer.name}
                                    </c:if>
                                </td>
                            </c:if>

                            <td rowspan="${listSize}">
                                <c:if test="${not empty outBound.outDealer}">
                                    ${outBound.outDealer.name}
                                </c:if>
                            </td>

                            <td>
                                ${bsProduct.planNumber}
                            </td>
                            <td>
                                ${bsProduct.realNumber}
                            </td>
                            <td rowspan="${listSize}">
                                ${outBound.pdaUser.name}
                            </td>
                            <td rowspan="${listSize}">
                                <fmt:formatDate value="${outBound.createDate}" pattern="yyyy-MM-dd"/>
                            </td>
                            <c:if test="${outBound.status eq '1'}">
                                <td rowspan="${listSize}">
                                    <fmt:formatDate value="${outBound.boundDate}" pattern="yyyy-MM-dd"/>
                                </td>
                                <td rowspan="${listSize}">
                                        ${outBound.shipNo}
                                </td>
                                <td rowspan="${listSize}">
                                        ${outBound.shipName}
                                </td>
                            </c:if>
                            <c:if test="${outBound.status eq '2'}">
                                <td rowspan="${listSize}">
                                    <fmt:formatDate value="${outBound.boundDate}" pattern="yyyy-MM-dd"/>
                                </td>
                                <td rowspan="${listSize}">
                                    <fmt:formatDate value="${outBound.receiveDate}" pattern="yyyy-MM-dd"/>
                                </td>
                                <td rowspan="${listSize}">
                                        ${outBound.shipNo}
                                </td>
                                <td rowspan="${listSize}">
                                        ${outBound.shipName}
                                </td>
                            </c:if>
                            <td rowspan="${listSize}">
                                <c:choose>
                                    <c:when test="${outBound.status eq '0'}">
                                        待发货
                                    </c:when>
                                    <c:when test="${outBound.status eq '1'}">
                                        已发货
                                    </c:when>
                                    <c:otherwise>
                                        已收货
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <shiro:hasPermission name="fwzs:dealer:edit">
                                <c:if test="${isDealer and outBound.status eq '0'}">
                                    <td rowspan="${listSize}">
                                        <a href="javascript:DealerBoundList.Events.deliveryGoods('${outBound.id}');"
                                           onclick="return confirmx('确认要发货吗？', this.href)">发货</a>
                                    </td>
                                </c:if>
                            </shiro:hasPermission>
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

    <div id="shipMessageArea" class="hidden">
        <form action="${ctx}/fwzs/dealerOutBound/deliveryGoods" commandName="delivery" method="post" class="breadcrumb form-search">
            <div class="colotBoxTitle">物流信息</div>
            <table id="shipMessageTable" class="table table-striped table-bordered table-condensed" style="width: 540px;margin-left: 20px;">
                <tr>
                    <th>物流单号:</th>
                    <td>
                        <input name="shipNo" type="text" value="" maxlength="20"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        物流名称:
                    </th>
                    <td>
                        <input name="shipName" type="text" value="" maxlength="30"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div class="colotBoxTitle">
                            <input class="btn btn-primary" type="submit" value="保存"/>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

	<div class="pagination">${page}</div>
    <fwzs:selectProduct bsProducts="${bsProducts}"/>
    <script src="${ctxStatic}/colorbox/jquery.colorbox.js"></script>
	<script src="${ctxStatic}/customized/dealerBoundList.js"></script>
    <script src="${ctxStatic}/customized/dealerBoundListTreeMenu.js"></script>
    <script src="${ctxStatic}/customized/common.js"/>
</body>
</html>