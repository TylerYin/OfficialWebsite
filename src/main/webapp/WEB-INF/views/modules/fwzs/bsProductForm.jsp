<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/fwzs/bsProduct/">产品列表</a></li>
		<li class="active"><a href="${ctx}/fwzs/bsProduct/form?id=${bsProduct.id}">产品<shiro:hasPermission name="fwzs:bsProduct:edit">${not empty bsProduct.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fwzs:bsProduct:edit">查看</shiro:lacksPermission></a></li>
        <c:if test="${empty bsProduct.id}">
            <li><a href="${ctx}/fwzs/bsProduct/multiProductForm?id=${bsProduct.id}">多规格产品添加</a></li>
        </c:if>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bsProduct" action="${ctx}/fwzs/bsProduct/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="_id"  value="${prodSpec.id }"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">产品编号：</label>
			<div class="controls">
				<form:input path="prodNo" htmlEscape="false" maxlength="20" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
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
				<form:input path="regCrop" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
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
			<label class="control-label">产品规格：</label>
			<div class="controls">
				<form:select id="ids" path="prodSpec.id" class="input-xlarge required" >
					<form:option value="" label="请选择"/>
					<form:options  items="${fwmSpecs}" itemLabel="specDesc" itemValue="id" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">包装比例：</label>
			<div class="controls">
				<form:input path="packRate" htmlEscape="false" class="input-xlarge"/>
				<span class="help-inline"><font color="red">* 示例：1:10,1:10:10</font> </span>
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
			<label class="control-label">单位：</label>
			<div class="controls"> 
			 	
			 	<form:select path="prodUnit" class="input-xlarge required">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('prod_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
		<div class="form-actions">
			<shiro:hasPermission name="fwzs:bsProduct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script src="${ctxStatic}/customized/bsProductForm.js"></script>
</body>
</html>