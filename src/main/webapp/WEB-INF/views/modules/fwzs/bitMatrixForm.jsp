<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>地址二维码生成</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/customized/customized.css"/>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/fwzs/bitMatrix/form">地址二维码生成</a></li>
</ul>

<form:form id="inputForm" modelAttribute="bitMatrix" action="${ctx}/fwzs/bitMatrix/form" method="post"
           class="breadcrumb form-search">
    <div style="padding-top: 25px;padding-left: 30px;font-size: 18px;font-weight:bold;">
        请输入URL地址&nbsp;&nbsp;
        <form:input id="url" path="url" style="width:280px" class="input-medium"></form:input> &nbsp;&nbsp;
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="生 成"/>

        <c:if test="${not empty bitMatrix.bitMatrixUrl}">
            <div id="bitMatrix" style="margin-top: 30px;">
                生成的二维码如下：</p>
                <img src="${bitMatrix.bitMatrixUrl}"/>
            </div>
        </c:if>
    </div>
</form:form>
<script src="${ctxStatic}/customized/bitMatrixForm.js"></script>
</body>
</html>