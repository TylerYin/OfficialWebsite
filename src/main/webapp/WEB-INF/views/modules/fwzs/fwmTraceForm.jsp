<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>产品追溯</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/customized/customized.css"/>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/fwzs/fwmTrace/form">产品追溯</a></li>
</ul>

<form:form id="searchForm" modelAttribute="fwmTrace" action="${ctx}/fwzs/fwmTrace/form" method="post" class="breadcrumb form-search">
    <div style="padding-top: 25px;padding-left: 30px;font-size: 18px;font-weight:bold;">
        请输入防伪码&nbsp;&nbsp;
        <form:input path="qrCode" id="qrCode" htmlEscape="false" maxlength="50" style="width:280px" class="input-medium"/> &nbsp;&nbsp;
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查 询"/>
    </div>

    <c:choose>
        <c:when test="${not empty fwmTrace.prodName}">
            <div style="margin-top: 10px;margin-bottom:10px; width: 1200px;border-bottom: 1px solid #000000;"></div>
            <div style="margin-left: 30px;">
                <div style="margin-top: 40px;">
                    <h4>基本信息</h4>
                    <table class="fwmTrace">
                        <tr>
                            <td><span><b>产品名称：</b>${fwmTrace.prodName}</span></td>
                            <td><span><b>产品规格：</b>${fwmTrace.prodSpec}</span></td>
                            <td><span><b>所属公司：</b>${fwmTrace.companyName}</span></td>
                        </tr>
                        <tr>
                            <td><span><b>生产计划编号：</b>${fwmTrace.planNo}</span></td>
                            <td><span><b>生产批号：</b>${fwmTrace.batchNo}</span></td>
                            <td><span></span></td>
                        </tr>
                    </table>
                </div>
            </div>

            <c:choose>
                <c:when test="${not empty fwmTrace.outboundNo}">
                    <div style="margin-top: 20px;margin-bottom: 20px;margin-left: 30px;">
                        <h4>物流信息</h4>
                        <table class="fwmTrace">
                            <tr>
                                <td><span><b>企业出库计划编号：</b>${fwmTrace.outboundNo}</span></td>
                                <td>
                                <span><b>企业出库计划日期：</b>
                                    <c:if test="${not empty fwmTrace.outboundPlanDate}">
                                        <fmt:formatDate value="${fwmTrace.outboundPlanDate}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </c:if>
                                </span>
                                </td>
                                <td>
                                <span><b>企业实际出库日期：</b>
                                    <c:if test="${not empty fwmTrace.outboundDate}">
                                        <fmt:formatDate value="${fwmTrace.outboundDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </c:if>
                                </span>
                                </td>
                            </tr>

                            <tr>
                                <td><span><b>收货经销商编号：</b>${fwmTrace.inDealerNo}</span></td>
                                <td><span><b>收货经销商名称：</b>${fwmTrace.inDealerName}</span></td>
                                <td><span><b>收货经销商地址：</b>${fwmTrace.inDealerAddress}</span></td>
                            </tr>
                        </table>
                    </div>

                    <c:forEach items="${fwmDealerTraces}" var="fwmDealerTrace">
                        <div style="margin-top: 20px;margin-bottom: 20px; margin-left: 30px;">
                            <table class="fwmTrace">
                                <tr>
                                    <td><span><b>发货经销商编号：</b>${fwmDealerTrace.inDealerNo}</span></td>
                                    <td><span><b>发货经销商名称：</b>${fwmDealerTrace.inDealerName}</span></td>
                                    <td><span><b>发货经销商地址：</b>${fwmDealerTrace.inDealerAddress}</span></td>
                                    <td><span><b>发货时间：</b>
                                <c:if test="${not empty fwmDealerTrace.outboundDate}">
                                    <fmt:formatDate value="${fwmDealerTrace.outboundDate}"
                                                    pattern="yyyy-MM-dd HH:mm:ss"/>
                                </c:if>
                                </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td><span><b>收货经销商编号：</b>${fwmDealerTrace.outDealerNo}</span></td>
                                    <td><span><b>收货经销商名称：</b>${fwmDealerTrace.outDealerName}</span></td>
                                    <td><span><b>收货经销商地址：</b>${fwmDealerTrace.outDealerAddress}</span></td>
                                    <td><span><b>收货时间：</b>
                                <c:if test="${not empty fwmDealerTrace.receiveDate}">
                                    <fmt:formatDate value="${fwmDealerTrace.receiveDate}"
                                                    pattern="yyyy-MM-dd HH:mm:ss"/>
                                </c:if>
                                </span>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div style="margin-top: 20px;margin-bottom: 20px; margin-left: 30px;"><h4>${errorMessage}</h4></div>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty fwmTrace.qrCode}">
                <div style="margin-top: 20px;margin-bottom: 20px; margin-left: 30px;"><h4>${errorMessage}</h4></div>
            </c:if>
        </c:otherwise>
    </c:choose>
</form:form>

<script src="${ctxStatic}/customized/fwmTraceForm.js"></script>
</body>
</html>
