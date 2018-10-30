<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fwzs" tagdir="/WEB-INF/tags/fwzs"%>

<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta name="applicable-device" content="pc,mobie">
    <title>农药防伪信息查询</title>
    <script src="${ctxStatic}/pesticide/js/jquery.min.js"></script>
    <script src="${ctxStatic}/pesticide/js/bootstrap.min.js" ></script>
    <link href="${ctxStatic}/pesticide/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctxStatic}/pesticide/css/basic.css" rel="stylesheet"/>
</head>
<body>
<header>
    <div class="headbg">
        <div class="container">
            <div class="row marpad">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 marpad">农药产品溯源查询系统</div>
            </div>
        </div>
    </div>
</header>

<div class="jianjunav"></div>
<div class="container">
    <div class="row padding16 content" style="padding-top:5px">
        ${office.summary}
    </div>
</div>

<fwzs:fwFooter qrCode="${qrCode}" isActiveTraceInfoLogo="false" isActiveCompanyInfoLogo="true" isActiveContactInfoLogo="false"/>

</body>
<script src="${ctxStatic}/customized/removeFwmSessionStorage.js"></script>
</html>
