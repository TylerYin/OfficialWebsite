<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!DOCTYPE >

<html>
<head>
    <link href="${ctxStatic}/customized/fwmQrcodeNotFound.css" rel="stylesheet" />
    <title>无效的防伪码</title>
</head>
<body>
    <div id="allmap" style="display:none"></div>
    <div id="insertInvalidQueryDataURL" style="display:none">${ctx}/phone/invalid/insertInvalidFWMData/</div>
    <div class="main">
        <div class="header">
            <span>扫描结果</span>
        </div>
        <div class="content">
            <img src="${ctxStatic}/images/tip.jpg" alt="">
        </div>
        <div class="footer">
            <span class="tip">非臻诚科技合作二维码 <p></span>
            <span class="result">您扫描的有效内容：<br>
                <span id="qrCode">${qrCode}</span>
            </span>
        </div>
    </div>
</body>
<script src="${ctxStatic}/demo/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=${baiduAPIKey}"></script>
<script src="${ctxStatic}/customized/query_fw_qrcode_invalid.js"></script>
</html>