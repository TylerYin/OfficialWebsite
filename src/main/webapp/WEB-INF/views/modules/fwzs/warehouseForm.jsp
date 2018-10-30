<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>仓库管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
</head>
<body>

<div id="warehouseMenuContent" class="menuContent">
	<ul id="warehouseTree" class="ztree menuTree"></ul>
</div>

<div id="areaMenuContent" class="menuContent">
	<ul id="areaTree" class="ztree menuTree" style="margin-top:0; width:220px;"></ul>
</div>

<div id="warehouseTreeMenuURL" class="hidden">${ctx}/fwzs/warehouse/warehouseMenuData</div>
<div id="areaTreeMenuURL" class="hidden">${ctx}/fwzs/warehouse/areaMenuData</div>

<ul class="nav nav-tabs">
	<li><a href="${ctx}/fwzs/warehouse">仓库列表</a></li>
	<li class="active"><shiro:hasPermission name="fwzs:dealer:edit"><a href="${ctx}/fwzs/warehouse/form">仓库${not empty warehouse.id?'修改':'添加'}</a></shiro:hasPermission></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="warehouse" action="${ctx}/fwzs/warehouse/save" method="post" class="form-horizontal">
	<sys:message content="${message}"/>
	<form:input path="id" htmlEscape="false" maxlength="50" cssStyle="display: none"/>
    <div class="control-group">
        <label class="control-label">仓库编号:</label>
        <div class="controls">
            <form:input path="warehouseNo" htmlEscape="false" maxlength="50" class="required" readonly="true"/>
        </div>
    </div>
	<div class="control-group">
		<label class="control-label">仓库名称:</label>
		<div class="controls">
			<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">地址:</label>
		<div class="controls">
			<form:input path="address" htmlEscape="false" maxlength="66" class="required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">电话:</label>
		<div class="controls">
			<form:input path="phone" htmlEscape="false" maxlength="13" class="required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">负责人:</label>
		<div class="controls">
			<form:input path="leader" htmlEscape="false" maxlength="50" class="required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">级别:</label>
		<div class="controls">
			<form:select path="grade" id="grade" onchange="clearParentWarehouse()" class="input-medium required">
				<form:option value="" label="请选择"/>
				<form:option value="1" label="一级"/>
				<form:option value="2" label="二级"/>
				<form:option value="3" label="三级"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">上级仓库:</label>
		<div class="controls">
            <c:choose>
                <c:when test="${warehouse.grade eq '1'}">
                    <form:input path="parentWarehouse.name" id="parentWarehouseName" htmlEscape="false" onclick="showWarehouseMenu()" readonly="true" maxlength="50"/>
                </c:when>
                <c:otherwise>
                    <form:input path="parentWarehouse.name" id="parentWarehouseName" htmlEscape="false" onclick="showWarehouseMenu()" required="true" readonly="true" maxlength="50"/>
                </c:otherwise>
            </c:choose>
            <form:input path="parentWarehouse.id" id="parentWarehouse" htmlEscape="false" maxlength="50" cssStyle="display:none;"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">所在区域:</label>
		<div class="controls">
			<form:input path="salesArea.name" id="salesAreaName" readonly="true" htmlEscape="false" onclick="showSalesAreaMenu()" maxlength="50" class="required"/>
			<form:input path="salesArea.id" id="salesArea" htmlEscape="false" maxlength="50" cssStyle="display:none;"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
    <div class="control-group">
        <label class="control-label">面积(平方米):</label>
        <div class="controls">
            <form:input path="size" id="size" htmlEscape="false" maxlength="9" onKeyPress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" />
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">容量:</label>
        <div class="controls">
            <form:input path="volume" id="volume" htmlEscape="false" maxlength="9" onKeyPress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" />
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
	<div class="control-group">
		<label class="control-label">描述:</label>
		<div class="controls">
			<form:textarea path="warehouseInfo" htmlEscape="false" rows="3" maxlength="166" class="input-xlarge"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">备注:</label>
		<div class="controls">
			<form:textarea path="remark" htmlEscape="false" rows="3" maxlength="166" class="input-xlarge"/>
		</div>
	</div>
	<div class="form-actions">
		<shiro:hasPermission name="fwzs:dealer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>

<div style="padding-left: 30px;padding-top: 30px;">
    <b>提示：</b>
    <div>
        <label class="control-label">1. 一级仓库是最高级别，不能为其指定上级。</label>
    </div>
    <div>
        <label class="control-label">2. 二级仓库的上级只能选择一级，三级仓库的上级只能选择二级。</label>
    </div>
</div>

<script src="${ctxStatic}/customized/warehouseForm.js"></script>
<script src="${ctxStatic}/customized/salesAreaTreeMenu.js"></script>
<script src="${ctxStatic}/customized/warehouseTreeMenu.js"></script>
</body>
</html>