<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!DOCTYPE >

<html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>产品追溯</title>
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
				<li class="product-title">${fwmQrcode.bsProduct.prodName}</li>
				<li class="product-tag">金牌推荐</li>
				<li class="product-company">${fwmQrcode.bsProduct.company.name}</li>
			</div>
		</div>
		<div class="container">
			<div class="menulist">
				<ul>
					<li><span class="menutitle">产品性能：</span> <span
						class="menuvalue"><p>${fwmQrcode.bsProduct.remark}</p></span>
					</li>
				</ul>
			</div>
			<div class="menulist">
				<ul>
					<li><span class="menutitle">生产批次：</span> <span
						class="menuvalue fr scan-value">${fwmQrcode.scPlan.batchNo}</span></li>
					<li><span class="menutitle">质检人员：</span> <span
						class="menuvalue fr scan-value">
                        <c:if test="${fwmQrcode.status eq '6'}">
                            ${fwmQrcode.scPlan.qcBy.name}
                        </c:if>
                        </span></li>
					<li><span class="menutitle">质量检验：</span> <span
						class="menuvalue fr scan-value">
                        <c:if test="${fwmQrcode.status eq '6'}">
                            合格
                        </c:if>
                    </span></li>
                    <li onclick="javascript:window.location.href='${ctx}/phone/trace?qrCode=${fwmQrcode.qrcode}'">
                        <span class="menutitle">物流及销售：</span>
                        <span class="menuvalue"></span>
                        <span class="icon-chevron-right"></span>
                    </li>
				</ul>
			</div>
		</div>
		<div class="footer">
			<span class="copyright"> Copyright © 2016-2017 防伪追溯系统</span>
		</div>
	</div>
    <script src="${ctxStatic}/customized/removeFwmSessionStorage.js"></script>
</body>
</html>

