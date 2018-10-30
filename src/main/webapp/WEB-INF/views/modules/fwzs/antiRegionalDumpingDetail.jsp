<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib prefix="fwzs" tagdir="/WEB-INF/tags/fwzs"%>
<html>
<head>
    <title>窜货列表详情</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/colorbox/colorbox.css" />
    <link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
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
    <li class="active"><a href="${ctx}/fwzs/antiRegionalParaSetting/">窜货列表详情</a></li>
</ul>
<form:form id="searchForm" modelAttribute="antiRegionalDumping" action="${ctx}/fwzs/antiRegionalDumping/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>产品：</label>
            <form:input path="bsProduct.prodName" htmlEscape="false" maxlength="50" readonly="true" class="input-medium"/>
        </li>
        <li><label>经销商：</label>
            <form:input path="dealer.name" htmlEscape="false" maxlength="50" readonly="true" class="input-medium"/>
        </li>
        <li class="btns"><input class="btn btn-primary" type="button" onclick="history.go(-1);" value="返回"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>防伪码</th>
        <c:if test="${isSystemManager}">
            <th>公司名称</th>
        </c:if>
        <th>查询次数</th>
        <th>第一次查询地址</th>
        <th>第一次查询时间</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="antiRegionalDumping">
        <tr>
            <td>${antiRegionalDumping.qrCode}</td>
            <c:if test="${isSystemManager}">
                <td>${antiRegionalDumping.company.name}</td>
            </c:if>
            <td>${antiRegionalDumping.queryTimes}</td>
            <td>${antiRegionalDumping.fwmQueryHistory.address}</td>
            <td>
                <fmt:formatDate value="${antiRegionalDumping.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
<fwzs:selectProduct bsProducts="${bsProducts}"/>

<script src="${ctxStatic}/colorbox/jquery.colorbox.js"></script>
<script src="${ctxStatic}/customized/antiRegionalDumpingList.js"></script>
</body>
</html>