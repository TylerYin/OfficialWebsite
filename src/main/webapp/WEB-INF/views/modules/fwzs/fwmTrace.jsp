<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>物流及销售</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=yes" />
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <link href="${ctxStatic}/demo/css/styles.css" rel="stylesheet" />
</head>
<body>

<div class="wapper">
    <div class="header">
        <div class="productpic">
            <c:choose>
                <c:when test="${not empty fwmQrcode.bsProduct.imgUrl}">
                    <img src="${fwmQrcode.bsProduct.imgUrl}">
                </c:when>
                <c:otherwise>
                    <img src="${ctxStatic}/images/defaultProduct.jpg">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="productdedail">
            <ul>
                <li class="product-title">${fwmQrcode.bsProduct.prodName}</li>
                <li class="product-tag">金牌推荐</li>
                <li class="product-company">${fwmQrcode.bsProduct.company.name}</li>
            </ul>
        </div>
    </div>

    <div class="container">
        <c:choose>
            <c:when test="${empty errorMessage}">
                <c:if test="${not empty fwmTrace.outboundDate}">
                    <div style="margin-bottom: 5px;margin-top: 0px;" class="menulist">
                        <ul>
                            <li class="">
                                <b style="font-size: large">物流信息:</b>
                            </li>
                        </ul>
                    </div>

                    <div style="margin-top: 5px;margin-bottom: 5px;" class="menulist">
                        <ul>
                            <li class="menutitle"><b>企业名称：</b>
                                ${fwmTrace.companyName}
                            </li>
                                <li class="menutitle"><b>企业出库地址：</b>
                                <c:if test="${not empty fwmTrace.warehouseName}">
                                    ${fwmTrace.warehouseName} &nbsp;
                                </c:if>
                                <c:if test="${not empty fwmTrace.warehouseAddress}">
                                    ${fwmTrace.warehouseAddress}
                                </c:if>
                                </li>
                            </li>
                            <li><b>企业出库日期：</b>
                                <fmt:formatDate value="${fwmTrace.outboundDate}" pattern="yyyy-MM-dd"/>
                            </li>
                        </ul>
                    </div>
                </c:if>

                <c:if test="${not empty fwmTrace.receiveDate}">
                    <div style="margin-top: 5px;margin-bottom: 5px;" class="menulist">
                        <ul>
                            <li class="menutitle">
                                <div><b>经销商名称：</b>${fwmTrace.inDealerName}</div>
                            </li>
                            <li class="menutitle">
                                <div><b>经销商地址：</b>${fwmTrace.inDealerArea} ${fwmTrace.inDealerAddress}</div>
                            </li>
                            <li class="menutitle">
                                <b>收货时间：</b>
                                <fmt:formatDate value="${fwmTrace.receiveDate}" pattern="yyyy-MM-dd"/>
                            </li>
                        </ul>
                    </div>
                </c:if>

                <c:forEach items="${fwmDealerTraces}" var="fwmDealerTrace">
                    <c:if test="${not empty fwmDealerTrace.receiveDate}">
                        <div style="margin-top: 5px;margin-bottom: 5px;" class="menulist">
                            <ul>
                                <li class="menutitle">
                                    <b>经销商名称：</b>${fwmDealerTrace.outDealerName}
                                </li>
                                <li class="menutitle">
                                    <b>经销商地址：</b>${fwmDealerTrace.outDealerArea} ${fwmDealerTrace.outDealerAddress}
                                </li>
                                <li class="menutitle">
                                    <b>收货时间：</b>
                                    <fmt:formatDate value="${fwmDealerTrace.receiveDate}" pattern="yyyy-MM-dd"/>
                                </li>
                            </ul>
                        </div>
                    </c:if>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div style="margin-top: 5px;margin-bottom: 5px;" class="menulist">
                    <ul>
                        <li class="menutitle"><h4>${errorMessage}</h4></li>
                    </ul>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="footer">
        <span class="copyright"> Copyright © 2016-2017 防伪追溯系统</span>
    </div>
</div>
</body>
</html>
