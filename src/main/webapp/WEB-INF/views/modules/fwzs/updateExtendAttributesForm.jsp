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
		<li class="active"><a href="${ctx}/fwzs/bsProduct/updateExtendAttributesForm?id=${bsProduct.id}">产品<shiro:hasPermission name="fwzs:bsProduct:edit">扩展属性编辑</shiro:hasPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bsProduct" action="${ctx}/fwzs/bsProduct/updateExtendAttributes" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path=""/>
		<input type="hidden" id="_id"  value="${prodSpec.id }"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">产品编号：</label>
			<div class="controls">
				<form:input path="prodNo" htmlEscape="false" maxlength="20" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:input path="prodName" htmlEscape="false" maxlength="33" class="input-xlarge required" readonly="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

        <div class="control-group">
            <label class="control-label">产品参数：</label>
            <div class="controls">
                <form:textarea path="prodParameter" htmlEscape="false" rows="4" maxlength="166" class="input-xxlarge "/>
                <sys:ckeditor replace="prodParameter" uploadPath="/productInfo/prodParameter" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">产品特点：</label>
            <div class="controls">
                <form:textarea path="prodFeature" htmlEscape="false" rows="4" maxlength="166" class="input-xxlarge "/>
                <sys:ckeditor replace="prodFeature" uploadPath="/productInfo/prodFeature" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">注意事项：</label>
            <div class="controls">
                <form:textarea path="prodConsideration" htmlEscape="false" rows="4" maxlength="166" class="input-xxlarge "/>
                <sys:ckeditor replace="prodConsideration" uploadPath="/productInfo/prodConsideration" />
            </div>
        </div>

		<div class="form-actions">
			<shiro:hasPermission name="fwzs:bsProduct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>