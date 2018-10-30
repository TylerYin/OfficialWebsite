<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib prefix="fwzs" tagdir="/WEB-INF/tags/fwzs"%>
<html>
<head>
    <title>窜货列表管理</title>
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
    <li class="active"><a href="${ctx}/fwzs/antiRegionalParaSetting/">窜货列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="antiRegionalDumping" action="${ctx}/fwzs/antiRegionalDumping/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <div class="hidden" id="refreshDateInterval">${refreshDateInterval}</div>
    <ul class="ul-form">
        <c:if test="${isSystemManager}">
            <li><label>企业名称：</label>
                <sys:treeselect id="company" name="company.id" value="${antiRegionalDumping.company.id}"
                                labelName="company.name" labelValue="${antiRegionalDumping.company.name}"
                                title="公司" url="/sys/office/treeData?type=1" cssClass="required"/>
            </li>
        </c:if>
        <li><label>选择产品：</label>
            <form:input path="bsProduct.id" id="prodId" htmlEscape="false" maxlength="50" value="${antiRegionalDumping.bsProduct.id}" readonly="true" style="display:none"/>
            <c:choose>
                <c:when test="${not empty antiRegionalDumping.bsProduct.prodName}">
                    <form:input path="bsProduct.prodName" id="prodName" htmlEscape="false" maxlength="50" value="${antiRegionalDumping.bsProduct.prodName}" readonly="true" class="input-medium"/>
                </c:when>
                <c:otherwise>
                    <form:input path="bsProduct.prodName" id="prodName" htmlEscape="false" maxlength="50" value="请选择" readonly="true" class="input-medium"/>
                </c:otherwise>
            </c:choose>
        </li>
        <li>
            <label>日期范围：</label>
            <input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${antiRegionalDumping.beginDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> ---
            <input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${antiRegionalDumping.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>产品编号</th>
        <th>产品名称</th>
        <th>产品规格</th>
        <th>经销商编号</th>
        <th>经销商名称</th>
        <th>经销商地址</th>
        <th>窜货数量</th>
        <c:if test="${isSystemManager}"><th>公司名称</th></c:if>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="regionalDumping">
        <tr>
            <td>${regionalDumping.bsProduct.prodNo}</td>
            <td>${regionalDumping.bsProduct.prodName}</td>
            <td>${regionalDumping.bsProduct.prodSpec.specDesc}</td>
            <td>${regionalDumping.dealer.dealerNo}</td>
            <td>${regionalDumping.dealer.name}</td>
            <td>${regionalDumping.dealer.salesArea.name}${regionalDumping.dealer.address}</td>
            <td>${regionalDumping.antiCount}</td>
            <c:if test="${isSystemManager}"><td>${regionalDumping.company.name}</td></c:if>
            <td>
                <a href="${ctx}/fwzs/antiRegionalDumping/listDetail?bsProduct.id=${regionalDumping.bsProduct.id}&dealer.id=${regionalDumping.dealer.id}&beginDate=<fmt:formatDate value="${antiRegionalDumping.beginDate}" pattern="yyyy-MM-dd"/>&endDate=<fmt:formatDate value="${antiRegionalDumping.endDate}" pattern="yyyy-MM-dd"/>">详情</a>
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