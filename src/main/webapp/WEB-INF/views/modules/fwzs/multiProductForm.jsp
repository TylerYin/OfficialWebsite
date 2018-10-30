<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fwzs" tagdir="/WEB-INF/tags/fwzs"%>

<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/colorbox/colorbox.css" />
    <link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/fwzs/bsProduct/">产品列表</a></li>
        <li><a href="${ctx}/fwzs/bsProduct/form?id=${bsProduct.id}">产品<shiro:hasPermission name="fwzs:bsProduct:edit">${not empty bsProduct.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fwzs:bsProduct:edit">查看</shiro:lacksPermission></a></li>
		<li class="active"><a href="${ctx}/fwzs/bsProduct/multiProductForm?id=${bsProduct.id}">多规格产品添加</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bsProduct" action="${ctx}/fwzs/bsProduct/multiSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
        <div id="generateProdNoURL" class="hidden">${ctx}/fwzs/bsProduct/generateProdNo</div>
        <div id="isDuplicateProductURL" class="hidden">${ctx}/fwzs/bsProduct/isDuplicateProduct</div>
		<input type="hidden" id="_id"  value="${prodSpec.id }"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">登记类别代码：</label>
			<div class="controls">
				<form:select path="regType" class="input-xlarge required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('code_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记证号：</label>
			<div class="controls">
				<form:input path="regCode" htmlEscape="false" maxlength="6" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>	 
		<div class="control-group">
			<label class="control-label">农药名：</label>
			<div class="controls">
				<form:input path="pesticideName" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:input path="prodName" htmlEscape="false" maxlength="33" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件所属公司：</label>
			<div class="controls">
				<form:input path="regCrop" htmlEscape="false" maxlength="150" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生产企业：</label>
			<div class="controls">
				 <sys:treeselect id="company" name="company.id" value="${bsProduct.company.id}" labelName="company.name" labelValue="${bsProduct.company.name}"
					title="公司" url="/sys/office/treeData?type=1" cssClass="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生产类型：</label>
			<div class="controls">
				<form:select path="scType" class="input-xlarge required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sc_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品经理：</label>
			<div class="controls">
				<form:input path="prodManager" htmlEscape="false" maxlength="33" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否启用：</label>
			<div class="controls">
				<form:radiobuttons path="isuser" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片路径：</label>
			<div class="controls">
				<form:hidden id="imgUrl" path="imgUrl" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<sys:ckfinder input="imgUrl" type="files" uploadPath="/fwzs/bsProduct" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" rows="4" maxlength="166" class="input-xxlarge "/>
			</div>
		</div>

        <div style="padding-left: 80px;">
            <input id="btnAddProductSpec" class="btn btn-primary" type="button" value="添加规格"/><p>

            <div>
                <b>提示：</b>
                1. 产品规格的填写示例：1:10,1:10:10。
                <p>
            </div>

            <div>
                <table id="contentTable" class="table table-striped table-bordered table-condensed" style="width: 1200px;">
                    <thead>
                        <tr>
                            <th>产品编号</th>
                            <th>产品规格</th>
                            <th>单位</th>
                            <th>包装比例</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody id="productSpecContent">
                        <c:forEach items="${bsProduct.productSpecs}" var="productSpec" varStatus="status">
                            <tr>
                                <td>
                                    <form:input path="productSpecs[${status.index}].prodNo" htmlEscape="false" class="input-xlarge" readonly="true"/>
                                </td>
                                <td>
                                    <form:input id="prodSpecDesc" path="productSpecs[${status.index}].prodSpec.specDesc" class="input-xlarge" value="请选择" readonly="true"/>
                                    <form:input id="prodSpecId" path="productSpecs[${status.index}].prodSpec.id" class="input-xlarge" cssStyle="display: none;"/>
                                </td>
                                <td>
                                    <form:input id="prodUnit" path="productSpecs[${status.index}].prodUnit" class="input-xlarge" value="请选择" readonly="true"/>
                                    <form:input id="prodUnitValue" path="productSpecs[${status.index}].prodUnitValue" class="input-xlarge" cssStyle="display: none;"/>
                                </td>
                                <td>
                                    <form:input id="packRate" path="productSpecs[${status.index}].packRate" htmlEscape="false" class="input-xlarge"/>
                                </td>
                                <td></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

		<div class="form-actions">
			<shiro:hasPermission name="fwzs:bsProduct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>

        <fwzs:selectProductSpec fwmSpecs="${fwmSpecs}"/>
        <fwzs:selectProductUnit prodUnits="${prodUnits}"/>
	</form:form>
    <script src="${ctxStatic}/colorbox/jquery.colorbox.js"></script>
	<script src="${ctxStatic}/customized/bsProductFormForMultiSpec.js"></script>
</body>
</html>