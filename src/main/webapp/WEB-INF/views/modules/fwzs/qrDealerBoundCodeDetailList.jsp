<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>单品码详情</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/customized/sys.css" />
    <link rel="stylesheet" href="${ctxStatic}/colorbox/colorbox.css" />
    <link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
    <script type="text/javascript">
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>

<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/fwzs/dealerOutBound/qrcodeDetailList?id=${dealerBound.id}">发货单明细</a></li>
</ul><br/>

<form:form id="searchForm" modelAttribute="dealerBound" action="${ctx}/fwzs/dealerOutBound/qrcodeDetailList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <form:hidden path="id" htmlEscape="false" maxlength="50" class="input-medium" />
    <form:hidden path="bsProduct.id" htmlEscape="false" maxlength="50" class="input-medium" readonly="true"/>

    <div>
        <label>发货单号：</label>
        <form:input path="boundNo" htmlEscape="false" maxlength="50" class="input-medium" readonly="true"/>

        <c:if test="${not empty bsProduct and not empty bsProduct.prodName}">
            <label>产品名称：</label>
            <input type="text" htmlEscape="false" maxlength="50" class="input-medium" value="${bsProduct.prodName}" readonly="true"/>
        </c:if>
        &nbsp;&nbsp;<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
        <tr>
            <th style="width:200px;">一级单品码</th>
            <th style="width:200px;">二级包装码</th>
            <th style="width:200px;">三级包装码</th>
        </tr>
    </thead>
    <tbody id="contentBody">
        <c:forEach items="${page.list}" var="qrcode2BoxcodeMapping">
            <tr>
                <td>${qrcode2BoxcodeMapping.qrCode}</td>
                <td>${qrcode2BoxcodeMapping.boxCode}</td>
                <td>${qrcode2BoxcodeMapping.bigBoxCode}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>