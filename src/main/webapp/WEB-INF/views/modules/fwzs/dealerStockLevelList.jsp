<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fwzs" tagdir="/WEB-INF/tags/fwzs"%>
<html>
<head>
	<title>经销商实时库存管理</title>
	<meta name="decorator" content="default"/>
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/fwzs/stocklevelquery/dealer">经销商实时库存列表</a></li>
	</ul>

	<div id="warehouseTreeMenuURL" style="display:none">${ctx}/fwzs/warehouse/warehouseMenuData</div>
	<div id="warehouseListMenuContent" class="menuContent" style="display:none; position: absolute; background: #c2ccd1">
		<ul id="warehouseListTree" class="ztree" style="margin-top:0; width:160px;"></ul>
	</div>

	<form:form id="searchForm" modelAttribute="realtimeStockLevel" action="${ctx}/fwzs/stocklevelquery/dealer" method="post" class="breadcrumb form-search">
		<c:set var="realtimeStockLevelModel" value="${realtimeStockLevel}"/>
		<div id="ctxId" class="hidden">${ctx}</div>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>产品名称：</label>
				<form:input path="bsProduct.id" id="prodId" htmlEscape="false" maxlength="50" class="input-medium" cssStyle="display: none" />
				<c:choose>
					<c:when test="${not empty realtimeStockLevel.bsProduct.prodName}">
						<form:input path="bsProduct.prodName" id="prodName" htmlEscape="false" readonly="true" maxlength="50" class="input-medium"/>
					</c:when>
					<c:otherwise>
						<form:input path="bsProduct.prodName" id="prodName" htmlEscape="false" readonly="true" maxlength="50" class="input-medium" value="请选择"/>
					</c:otherwise>
				</c:choose>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
            <tr>
                <c:if test="${!isDealer}">
                    <th>经销商编号</th>
                    <th>经销商名称</th>
                </c:if>
                <th>产品名称</th>
                <th>包装比例</th>
                <th>一级单品码量</th>
                <th>二级包装码量</th>
                <th>三级包装码量</th>
                <th>更新时间</th>
            </tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="realtimeStock">
			<tr>
                <c:if test="${!isDealer}">
                    <td>
                        ${realtimeStock.dealer.dealerNo}
                    </td>
                    <td>
                        ${realtimeStock.dealer.name}
                    </td>
                </c:if>
				<td>
                    <c:if test="${not empty realtimeStock.bsProduct}">
					    ${realtimeStock.bsProduct.prodName}
                    </c:if>
				</td>
				<td>
                    <c:if test="${not empty realtimeStock.bsProduct}">
					    ${realtimeStock.bsProduct.packRate}
                    </c:if>
				</td>
				<td>
					${realtimeStock.stockLevel}
				</td>
                <td>
                    ${realtimeStock.boxNum}
                </td>
                <td>
                    ${realtimeStock.bigBoxNum}
                </td>
				<td>
					<fmt:formatDate value="${realtimeStock.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<div class="pagination">${page}</div>
	<fwzs:selectProduct bsProducts="${bsProducts}"/>
	<script src="${ctxStatic}/colorbox/jquery.colorbox.js"></script>
	<script src="${ctxStatic}/customized/warehouseListTreeMenu.js"></script>
	<script src="${ctxStatic}/customized/realtimeStockLevelList.js"></script>
    <script src="${ctxStatic}/customized/common.js"/>
</body>
</html>