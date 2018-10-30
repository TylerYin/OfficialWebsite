<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!DOCTYPE >

<html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <link href="${ctxStatic}/demo/css/styles.css" rel="stylesheet" />
    <title>产品真伪鉴别</title>
</head>
<body>
<div id="allmap" style="display:none"></div>
<div id="zsURL" style="display:none">${ctx}/phone/zs</div>
<div id="fwURL" style="display:none">${ctx}/phone/history</div>
<div id="insertQueryDataURL" style="display:none">${ctx}/phone/updateQueryData/</div>
<form action="">
    <input type="hidden" id="qrCode" name="qrCode" value="${fwmQrcode.qrcode}">
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
            <div class="menulist spacenull">
                <ul>
                    <li>
                        <span class="menutitle">产品名称：</span>
                        <span class="menuvalue">${fwmQrcode.bsProduct.prodName}</span>
                    </li>
                    <li>
                        <span class="menutitle">生产企业：</span>
                        <span class="menuvalue">${fwmQrcode.bsProduct.company.name}</span>
                    </li>
                    <li>
                        <span class="menutitle">识别代码：</span>
                        <span class="menuvalue">${fwmQrcode.qrcode }</span>
                    </li>
                    <li id="locateToZSURL">
                        <span class="menutitle">追溯网址：</span>
                        <span class="menuvalue"><a href="#">http://www.wnzc.ltd/fwzs</a></span>
                        <span class="icon-chevron-right"></span>
                    </li>
                    <li id="locateToFWURL">
                        <span class="menutitle">扫描次数: </span>
                        <span class="icon-chevron-right"></span>
                        <span class="menuvalue fr scan-value">${fwmQrcode.selectNum}次</span>
                    </li>
                </ul>
            </div>
        </div>
        <div class="footer">

        </div>
    </div>
</form>
<script src="${ctxStatic}/demo/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=${baiduAPIKey}"></script>
<script src="${ctxStatic}/customized/query_fw.js"></script>
</body>
</html>