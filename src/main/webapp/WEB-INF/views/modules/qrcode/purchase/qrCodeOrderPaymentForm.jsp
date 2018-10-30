<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>防伪码购买</title>
    <meta name="decorator" content="default"/>
</head>
<ul class="nav nav-tabs">
    <li class="active"><a href="#">防伪码购买</a></li>
</ul><br/>
<form id="inputForm" action="${ctx}/fwzs/qrCode/orderPayment" method="post" class="form-horizontal">
    <div>
        <span>
            <c:choose>
                <c:when test="${packageType eq '2'}">
                    您选择的是500元套餐，共计生成防伪码25000个，您需要支付5000元整
                </c:when>
                <c:when test="${packageType eq '3'}">
                    您选择的是1000元套餐，共计生成防伪码50000个，您需要支付1000元整
                </c:when>
            </c:choose>
        </span>
    </div>

    <div>
        <hr style="height:1px;border:none;border-top:1px solid #555555;width: 98%;margin-bottom:15px; margin-top: 0px;"/>
        <input type="radio" name="paymentType" checked="checked">微信支付 <br>
        <input type="radio" name="paymentType">支付宝支付
        <input type="hidden" name="packageType" value="${packageType}">
    </div>

    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="支  付"/>
    </div>
</form>
</body>
</html>
