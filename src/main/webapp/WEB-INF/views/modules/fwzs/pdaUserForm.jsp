<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>PDA用户管理</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
</head>
<body>

<ul class="nav nav-tabs">
    <li><a href="${ctx}/fwzs/pda/">PDA用户列表</a></li>
    <li class="active"><a href="${ctx}/fwzs/pda/form?id=${pdaUser.id}">PDA用户<shiro:hasPermission
            name="fwzs:dealer:edit">${not empty pdaUser.id?'修改':'添加'}</shiro:hasPermission></a></li>
</ul>
<br/>

<div id="validateUserAccountUrl" class="hidden">${ctx}/fwzs/pda/isUserAccountValidate?originLoginName=${pda.loginName}</div>

<form:form id="inputForm" modelAttribute="pdaUser" action="${ctx}/fwzs/pda/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">用户编号：</label>
        <div class="controls">
            <form:input path="no" htmlEscape="false" maxlength="50" class="input-xlarge required" readonly="true"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">用户名称：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">联系电话：</label>
        <div class="controls">
            <form:input path="mobile" htmlEscape="false" maxlength="13" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">登录帐号：</label>
        <div class="controls">
            <c:choose>
                <c:when test="${not empty pdaUser.loginName}">
                    <form:input path="loginName" htmlEscape="false" maxlength="20" disabled="true" class="input-xlarge required"/>
                </c:when>
                <c:otherwise>
                    <form:input path="loginName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <c:if test="${empty pdaUser.password}">
        <div class="control-group">
            <label class="control-label">登录密码：</label>
            <div class="controls">
                <form:password path="password" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </c:if>

    <div class="control-group">
        <label class="control-label">是否启用：</label>
        <div class="controls">
            <form:radiobuttons path="loginFlag" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">备注：</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="166" class="input-xxlarge "/>
        </div>
    </div>

    <div class="form-actions">
        <shiro:hasPermission name="fwzs:dealer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
<script src="${ctxStatic}/customized/pdaUserForm.js"></script>
</body>
</html>