<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fwzs" tagdir="/WEB-INF/tags/fwzs"%>
<html>
<head>
	<title>出库计划添加</title>
	<meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/colorbox/colorbox.css" />
	<link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
</head>
<body>
	<div class="hidden" id="ctx">${ctx}</div>
	<div class="hidden" id="dealerLevel"></div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/fwzs/outBound/">出库计划列表</a></li>
		<li class="active"><a href="${ctx}/fwzs/outBound/form?id=${outBound.id}">出库计划<shiro:hasPermission name="fwzs:dealer:edit">${not empty scLines.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fwzs:dealer:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>

    <div id="dealerTreeMenuURL" class="hidden">${ctx}/fwzs/dealer/dealerMenuData</div>
    <div id="inDealerListMenuContent" class="menuContent" style="width: 280px">
        <ul id="inDealerListTree" class="ztree menuTree"></ul>
    </div>

    <div id="warehouseTreeMenuURL" class="hidden">${ctx}/fwzs/warehouse/warehouseMenuData</div>
    <div id="warehouseListMenuContent" class="menuContent">
        <ul id="warehouseListTree" class="ztree menuTree"></ul>
    </div>

    <div id="getLatestStockLevelURL" class="hidden">${ctx}/fwzs/outBound/getLatestStockLevel</div>

	<form:form id="inputForm" modelAttribute="outBound" action="${ctx}/fwzs/outBound/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">出库单号：</label>
			<div class="controls">
				<form:input path="outBoundNo" id="outBoundNo" htmlEscape="false" readonly="true" maxlength="20" class="input-xlarge required"/>
			</div>
		</div>

        <div class="control-group">
            <label class="control-label">出库员：</label>
            <div class="controls">
                <form:select path="pdaUser.id" id="pdaUser" class="input-xlarge required" >
                    <form:option value="" label="请选择"/>
                    <form:options  items="${pdaUsers}" itemLabel="name" itemValue="id" htmlEscape="false" />
                </form:select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">收货经销商：</label>
            <div class="controls">
                <form:input id="inDealerName" path="dealer.name" htmlEscape="false" readonly="true" value="请选择" onclick="showDealerMenu('in')" class="input-xlarge "/>
            </div>
            <div class="controls hidden">
                <form:input id="inDealer" path="dealer.id" class="input-xlarge"/>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">备注：</label>
            <div class="controls">
                <form:textarea path="remarks" htmlEscape="false" maxlength="166" class="input-xlarge "/>
            </div>
        </div>

        <input id="btnAddNewProduct" class="btn btn-primary" type="button" value="添 加"/><p>

        <div>
            <table id="contentTable" class="table table-striped table-bordered table-condensed">
                <thead>
                    <tr>
                        <th>产品名称</th>
                        <th>仓库</th>
                        <th>单品计划出库数量</th>
                        <th>备注</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody id="planContent">
                    <c:if test="${not empty outBound.bsProductList}">
                        <c:forEach items="${outBound.bsProductList}" var="bsProduct">
                            <tr>
                                <td>
                                    <div class="control-group">
                                        <div>
                                            <form:input id="prodName" readonly="true" path="bsProductList[0].prodName" htmlEscape="false" maxlength="64" value="请选择" class="input-xlarge inputWidth165"/>
                                        </div>
                                        <div class="controls hidden">
                                            <form:input id="prodId0" path="bsProductList[0].id" class="input-xlarge" readonly="true"/>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="control-group">
                                        <div>
                                            <form:input id="warehouseName0" path="bsProductList[0].warehouse.name" readonly="true" value="请选择" onclick="showWarehouseMenu('warehouse0,warehouseName0')" htmlEscape="false" class="input-xlarge inputWidth115 "/>
                                        </div>
                                        <div class="controls hidden">
                                            <form:input id="warehouse0" path="bsProductList[0].warehouse.id" class="input-xlarge"/>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="control-group">
                                        <div>
                                           <form:input id="planNumber0" path="bsProductList[0].planNumber" htmlEscape="false" onKeyPress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" maxlength="11" class="input-xlarge inputWidth115 "/>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="control-group">
                                        <div>
                                            <form:input path="bsProductList[0].remarks" htmlEscape="false" maxlength="166" class="input-xlarge inputWidth115 "/>
                                        </div>
                                    </div>
                                </td>
                                <td>

                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
        </div>

		<div class="form-actions">
			<shiro:hasPermission name="fwzs:dealer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
    <fwzs:selectProduct bsProducts="${bsProducts}"/>
    <script src="${ctxStatic}/colorbox/jquery.colorbox.js"></script>
    <script src="${ctxStatic}/customized/dealerBoundFormTreeMenu.js"></script>
    <script src="${ctxStatic}/customized/warehouseTreeMenuForOutBoundForm.js"></script>
	<script src="${ctxStatic}/customized/outBoundForm.js"/>
</body>
</html>