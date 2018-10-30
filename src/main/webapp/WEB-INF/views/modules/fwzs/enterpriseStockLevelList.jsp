<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fwzs" tagdir="/WEB-INF/tags/fwzs"%>
<html>
<head>
	<title>企业实时库存管理</title>
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
		<li class="active"><a href="${ctx}/fwzs/stocklevelquery/enterprise">企业实时库存列表</a></li>
	</ul>

	<div id="warehouseTreeMenuURL" style="display:none">${ctx}/fwzs/warehouse/warehouseMenuData</div>
	<div id="warehouseListMenuContent" class="menuContent" style="display:none; position: absolute; background: #c2ccd1">
		<ul id="warehouseListTree" class="ztree" style="margin-top:0; width:160px;"></ul>
	</div>

	<form:form id="searchForm" modelAttribute="realtimeStockLevel" action="${ctx}/fwzs/stocklevelquery/enterprise" method="post" class="breadcrumb form-search">
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

            <li>
                <label>产品规格：</label>
                <form:select path="bsProduct.prodSpec.specCode" class="input-medium">
                    <form:option value="" label="请选择"/>
                    <c:forEach items="${fwmSpecs}" var="fwmSpec">
                        <form:option value="${fwmSpec.specCode}" label="${fwmSpec.specDesc}"/>
                    </c:forEach>
                </form:select>
            </li>

            <li><label>所属仓库：</label>
                <c:choose>
                    <c:when test="${not empty realtimeStockLevel.warehouse.name}">
                        <form:input path="warehouse.name" id="warehouseName" htmlEscape="false" readonly="true" onclick="showWarehouseMenu()" maxlength="50"/>
                    </c:when>
                    <c:otherwise>
                        <form:input path="warehouse.name" id="warehouseName" htmlEscape="false" readonly="true" onclick="showWarehouseMenu()" maxlength="50" value="请选择"/>
                    </c:otherwise>
                </c:choose>
                <form:input path="warehouse.id" id="warehouse" htmlEscape="false" maxlength="50" cssStyle="display:none;"/>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
            <tr>
                <th>产品名称</th>
                <th>包装比例</th>
                <th>一级单品码量</th>
                <th>二级包装码量</th>
                <th>三级包装码量</th>
                <th>仓库名称</th>
                <th>仓库地址</th>
                <th>仓库电话</th>
                <th>负责人</th>
                <th>更新时间</th>
            </tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="realtimeStock">
			<tr>
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
                    <c:if test="${not empty realtimeStock.warehouse}">
                        ${realtimeStock.warehouse.name}
                    </c:if>
                </td>
                <td>
                    <c:if test="${not empty realtimeStock.warehouse}">
                        ${realtimeStock.warehouse.address}
                    </c:if>
                </td>
                <td>
                    <c:if test="${not empty realtimeStock.warehouse}">
                        ${realtimeStock.warehouse.phone}
                    </c:if>
                </td>
                <td>
                    <c:if test="${not empty realtimeStock.warehouse}">
                        ${realtimeStock.warehouse.leader}
                    </c:if>
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