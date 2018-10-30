<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>防窜货参数设置管理</title>
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
    <li class="active"><a href="${ctx}/fwzs/antiRegionalParaSetting/">防窜货参数设置列表</a></li>
    <c:if test="${empty antiRegionalParaSettings and !isSystemManager}">
        <li><a href="${ctx}/fwzs/antiRegionalParaSetting/form">防窜货参数设置添加</a></li>
    </c:if>
</ul>
<c:if test="${isSystemManager}">
    <form:form id="searchForm" modelAttribute="antiRegionalParaSetting" action="${ctx}/fwzs/antiRegionalParaSetting/" method="post" class="breadcrumb form-search">
        <ul class="ul-form">
            <li><label>企业名称：</label>
                <sys:treeselect id="company" name="company.id" value="${antiRegionalParaSetting.company.id}"
                                labelName="company.name" labelValue="${antiRegionalParaSetting.company.name}"
                                title="公司" url="/sys/office/treeData?type=1" cssClass="required"/>
            </li>
            <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li class="clearfix"></li>
        </ul>
    </form:form>
</c:if>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <c:if test="${isSystemManager}">
            <th>公司名称</th>
            <th>经销商名称</th>
        </c:if>
        <th>防窜货数量阈值(个)</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${antiRegionalParaSettings}" var="antiRegionalParaSetting">
        <tr>
            <c:if test="${isSystemManager}">
                <td>
                    <a href="${ctx}/fwzs/antiRegionalParaSetting/form?id=${antiRegionalParaSetting.id}">${antiRegionalParaSetting.company.name}</a>
                </td>
                <td>
                    ${antiRegionalParaSetting.dealer.name}
                </td>
            </c:if>
            <td>
                ${antiRegionalParaSetting.antiRegionalThreshold}
            </td>
            <td>
                <a href="${ctx}/fwzs/antiRegionalParaSetting/form?id=${antiRegionalParaSetting.id}">修改</a>
                <a href="${ctx}/fwzs/antiRegionalParaSetting/delete?id=${antiRegionalParaSetting.id}" onclick="return confirmx('确认要删除该设置吗？', this.href)">删除</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>