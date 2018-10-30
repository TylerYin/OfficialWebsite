<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>PDA用户管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/fwzs/pda/">PDA用户列表</a></li>
    <shiro:hasPermission name="fwzs:dealer:edit">
        <li><a href="${ctx}/fwzs/pda/form">PDA用户添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="pdaUser" action="${ctx}/fwzs/pda/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>用户编号：</label>
            <form:input path="no" htmlEscape="false" maxlength="20" class="input-medium"/>
        </li>
        <li><label>用户名称：</label>
            <form:input path="name" htmlEscape="false" maxlength="150" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>用户编号</th>
        <th>用户名称</th>
        <th>联系电话</th>
        <th>登录帐号</th>
        <th>所属企业或经销商</th>
        <th>是否启用</th>
        <th>创建时间</th>
        <shiro:hasPermission name="fwzs:dealer:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="pda">
        <tr>
            <td><a href="${ctx}/fwzs/pda/form?id=${pda.id}">${pda.no}</a></td>
            <td>${pda.name}</td>
            <td>${pda.mobile}</td>
            <td>${pda.loginName}</td>
            <td>
                <c:choose>
                    <c:when test="${empty pda.dealer}">
                        ${pda.company.name}
                    </c:when>
                    <c:otherwise>
                        ${pda.dealer.name}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${pda.loginFlag eq '1'}">
                        启用
                    </c:when>
                    <c:otherwise>
                        未启用
                    </c:otherwise>
                </c:choose>
            </td>
            <td><fmt:formatDate value="${pda.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <shiro:hasPermission name="fwzs:dealer:edit">
                <td>
                    <a href="${ctx}/fwzs/pda/form?id=${pda.id}">修改</a>
                    <a href="${ctx}/fwzs/pda/delete?id=${pda.id}&no=${pdaUser.no}&name=${pdaUser.name}" onclick="return confirmx('确认要删除该PDA用户吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
<script src="${ctxStatic}/customized/pdaUserList.js"></script>
</html>