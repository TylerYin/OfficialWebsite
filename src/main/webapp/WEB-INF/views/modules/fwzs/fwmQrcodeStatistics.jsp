<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fwzs" tagdir="/WEB-INF/tags/fwzs"%>
<html>
<head>
    <title>生码统计</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/colorbox/colorbox.css" />
    <link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
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
    <li class="active"><a href="${ctx}/fwzs/fwmQrcodeStatistics/">生码统计</a></li>
</ul>
<form:form id="searchForm" modelAttribute="fwmFileStatistics" action="${ctx}/fwzs/fwmQrcodeStatistics/" method="post" class="breadcrumb form-search">
    <div id="ctxId" class="hidden">${ctx}</div>
    <div id="isSystemManager" class="hidden">${isSystemManager}</div>
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>选择产品：</label>
            <form:input path="bsProduct.id" id="prodId" htmlEscape="false" maxlength="50" value="${fwmFileStatistics.bsProduct.id}" readonly="true" style="display:none"/>
            <c:choose>
                <c:when test="${not empty fwmFileStatistics.bsProduct.prodName}">
                    <form:input path="bsProduct.prodName" id="prodName" htmlEscape="false" maxlength="50" value="${fwmFileStatistics.bsProduct.prodName}" readonly="true" class="input-medium"/>
                </c:when>
                <c:otherwise>
                    <form:input path="bsProduct.prodName" id="prodName" htmlEscape="false" maxlength="50" value="请选择" readonly="true" class="input-medium"/>
                </c:otherwise>
            </c:choose>
        </li>
        <li>
            <label>日期范围：</label>
            <input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${fwmFileStatistics.beginDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> ---
            <input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${fwmFileStatistics.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>

<div style="text-align:right;margin-right:50px;margin-top:20px;margin-bottom:20px;">
    <b>生码总量：<fmt:formatNumber type="number" value="${totalCount}"/></b>
</div>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
        <tr>
            <c:if test="${isSystemManager}">
                <th>企业名称</th>
            </c:if>
            <c:if test="${!isSystemManager}">
                <th>产品名称</th>
            </c:if>
            <th>生码数量</th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${isSystemManager}">
                <c:forEach items="${page.list}" var="fwmFileStatistics">
                    <tr>
                        <td>
                            ${fwmFileStatistics.company.name}
                        </td>
                        <td>
                            <fmt:formatNumber type="number" value="${fwmFileStatistics.codeCount}"/>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:forEach items="${page.list}" var="fwmFileStatistics">
                    <tr>
                        <td>
                            ${fwmFileStatistics.productName}
                        </td>
                        <td>
                            <fmt:formatNumber type="number" value="${fwmFileStatistics.codeCount}"/>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </tbody>
</table>
<div class="pagination">${page}</div>

<fwzs:selectProduct bsProducts="${bsProducts}"/>

<c:if test="${!isSystemManager}">
    <div id="pieChart" style="width:600px;height:400px;margin: auto;"></div>
</c:if>

<script src="${ctxStatic}/echarts/echarts.min.js"></script>
<script src="${ctxStatic}/colorbox/jquery.colorbox.js"></script>
<script src="${ctxStatic}/customized/fwmFileList.js"></script>
</body>
</html>