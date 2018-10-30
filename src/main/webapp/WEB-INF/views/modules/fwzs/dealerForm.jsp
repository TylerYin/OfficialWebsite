<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>经销商管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
</head>
<body>

<div id="dealerMenuContent" class="menuContent">
	<ul id="dealerTree" class="ztree menuTree"></ul>
</div>

<div id="areaMenuContent" class="menuContent">
	<ul id="areaTree" class="ztree menuTree" style="margin-top:0; width:220px;"></ul>
</div>

<div id="dealerTreeMenuURL" class="hidden">${ctx}/fwzs/dealer/dealerMenuData</div>
<div id="areaTreeMenuURL" class="hidden">${ctx}/fwzs/dealer/areaMenuData</div>

<ul class="nav nav-tabs">
	<li><a href="${ctx}/fwzs/dealer">经销商列表</a></li>
	<li class="active"><shiro:hasPermission name="fwzs:dealer:edit"><a href="${ctx}/fwzs/dealer/form">经销商${not empty dealer.id?'修改':'添加'}</a></shiro:hasPermission></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="dealer" action="${ctx}/fwzs/dealer/save" method="post" class="form-horizontal">
	<sys:message content="${message}"/>
    <div id="validateUserAccountUrl" class="hidden">${ctx}/fwzs/dealer/isUserAccountValidate?originAccount=${dealer.account}</div>
    <div id="currentUserIsDealer" class="hidden">${currentUserIsDealer}</div>
	<form:input path="id" htmlEscape="false" maxlength="50" cssStyle="display: none"/>
    <div class="control-group">
        <label class="control-label">经销商编号:</label>
        <div class="controls">
            <form:input path="dealerNo" htmlEscape="false" maxlength="50" class="required" readonly="true"/>
        </div>
    </div>
	<div class="control-group">
		<label class="control-label">经销商名称:</label>
		<div class="controls">
            <c:choose>
                <c:when test="${not empty dealer.id}">
                    <form:input path="name" htmlEscape="false" maxlength="50" readonly="true" class="required"/>
                </c:when>
                <c:otherwise>
                    <form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
                </c:otherwise>
            </c:choose>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
    <div class="control-group">
        <label class="control-label">级别:</label>
        <div class="controls">
            <form:select path="grade" id="grade" onchange="clearParentDealer()" class="input-medium required">
                <form:option value="" label="请选择"/>
                <c:choose>
                    <c:when test="${currentUserIsDealer}">
                        <c:choose>
                            <c:when test="${currentDealer.grade eq '1'}">
                                <form:option value="2" label="二级"/>
                                <form:option value="3" label="三级"/>
                            </c:when>
                            <c:when test="${currentDealer.grade eq '2'}">
                                <form:option value="3" label="三级"/>
                            </c:when>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <form:option value="1" label="一级"/>
                        <form:option value="2" label="二级"/>
                        <form:option value="3" label="三级"/>
                    </c:otherwise>
                </c:choose>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">上级经销商:</label>
        <div class="controls">
            <c:choose>
                <c:when test="${dealer.grade eq '1'}">
                    <c:if test="${dealer.parentDealer.delFlag eq '1'}">
                        <form:input path="parentDealer.name" id="parentDealerName" htmlEscape="false" onclick="showDealerMenu()" readonly="true" maxlength="50" value="DEL"/>
                    </c:if>
                    <c:if test="${dealer.parentDealer.delFlag eq '0'}">
                        <form:input path="parentDealer.name" id="parentDealerName" htmlEscape="false" onclick="showDealerMenu()" readonly="true" maxlength="50"/>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <c:if test="${dealer.parentDealer.delFlag eq '1'}">
                        <form:input path="parentDealer.name" id="parentDealerName" htmlEscape="false" onclick="showDealerMenu()" required="true" readonly="true" maxlength="50" value="DEL"/>
                    </c:if>
                    <c:if test="${dealer.parentDealer.delFlag eq '0'}">
                        <form:input path="parentDealer.name" id="parentDealerName" htmlEscape="false" onclick="showDealerMenu()" required="true" readonly="true" maxlength="50"/>
                    </c:if>
                </c:otherwise>
            </c:choose>

            <c:if test="${empty dealer.parentDealer}">
                <form:input path="parentDealer.name" id="parentDealerName" htmlEscape="false" onclick="showDealerMenu()" readonly="true" maxlength="50"/>
            </c:if>
            <form:input path="parentDealer.id" id="parentDealer" htmlEscape="false" maxlength="50" cssStyle="display:none;"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">授权证书:</label>
        <div class="controls">
            <form:hidden id="certificateUrl" path="certificateUrl" htmlEscape="false" maxlength="150" class="input-xlarge"/>
            <sys:ckfinder input="certificateUrl" type="images" uploadPath="/certificate" selectMultiple="false" maxWidth="100" maxHeight="100"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">登录帐号:</label>
        <div class="controls">
            <input id="oldAccount" name="oldAccount" type="hidden" value="${dealer.account}">
            <c:choose>
                <c:when test="${empty dealer.account}">
                    <form:input path="account" htmlEscape="false" maxlength="20" class="required" value="${dealer.account}"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </c:when>
                <c:otherwise>
                    <form:input path="account" htmlEscape="false" maxlength="20" readonly="true" class="required"/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <c:if test="${empty dealer.password}">
        <div class="control-group">
            <label class="control-label">登录密码:</label>
            <div class="controls">
                <form:password path="password" htmlEscape="false" maxlength="10" class="required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </c:if>
    <div class="control-group">
        <label class="control-label">所在区域:</label>
        <div class="controls">
            <form:input path="salesArea.name" id="salesAreaName" readonly="true" htmlEscape="false" onclick="showSalesAreaMenu()" maxlength="50" class="required"/>
            <form:input path="salesArea.id" id="salesArea" htmlEscape="false" maxlength="50" cssStyle="display:none;"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">销售区域:</label>
        <div class="controls">
            <sys:treeselectForDealer id="areaIds" name="areaIds" areaIds="${dealer.areaIds}" isDealerForm="true" value="${dealer.areaIds}" labelName="area.name"
                labelValue="${dealer.areaNames}" checked="true" title="区域" url="/sys/area/treeData" cssClass="required"/>
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
		<label class="control-label">邮件:</label>
		<div class="controls">
			<form:input path="email" htmlEscape="false" maxlength="50" class="required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">传真:</label>
		<div class="controls">
			<form:input path="fax" htmlEscape="false" maxlength="50"/>
		</div>
	</div>
    <div class="control-group">
        <label class="control-label">QQ:</label>
        <div class="controls">
            <form:input path="qq" htmlEscape="false" minlength="3" maxlength="15" onKeyPress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">微信号:</label>
        <div class="controls">
            <form:input path="wechat" htmlEscape="false" maxlength="30"/>
        </div>
    </div>
	<div class="control-group">
		<label class="control-label">描述:</label>
		<div class="controls">
			<form:textarea path="dealerInfo" htmlEscape="false" rows="3" maxlength="166" class="input-xlarge"/>
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
        <label class="control-label">1. 一级经销商是最高级别，不能为其指定上级。</label>
    </div>
    <div>
        <label class="control-label">2. 二级经销商的上级只能选择一级，三级经销商的上级只能选择二级。</label>
    </div>
</div>

<script src="${ctxStatic}/customized/dealerForm.js"></script>
<script src="${ctxStatic}/customized/salesAreaTreeMenu.js"></script>
<script src="${ctxStatic}/customized/dealerTreeMenu.js"></script>
</body>
</html>