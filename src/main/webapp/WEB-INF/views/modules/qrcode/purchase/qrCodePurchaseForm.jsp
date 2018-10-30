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
<form id="inputForm" action="${ctx}/fwzs/qrCode/purchase" method="post" class="form-horizontal">
    <div class="control-group">
        <label class="control-label">类别：</label>
        <div class="controls">
            <select name="packageSize" id="packageSize" class="input-xlarge required" >
                <option value="">请选择</option>
                <option value="1">小包</option>
                <option value="2">中包</option>
                <option value="3">大包</option>
            </select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">模式：</label>
        <div class="controls">
            <select name="packageType" id="packageType" class="input-xlarge required" >
                <option value="">请选择</option>
                <option value="1">0元体验套餐</option>
                <option value="2">500元套餐</option>
                <option value="3">1000元套餐</option>
            </select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">生成数量：</label>
        <div class="controls">
            <input name="amount" id="amount" htmlEscape="false" readonly="true"/>
        </div>
    </div>

    <div class="control-group" style="margin-left: 110px;">
        <div class="input-row">
            <label class="input-label mid" for="validateCode">验证码：</label>&nbsp;&nbsp;&nbsp;&nbsp;
            <sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;" imageCssStyle="padding-top:7px;"/>
        </div>
    </div>

    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="下一步"/>
    </div>
</form>
<script src="${ctxStatic}/customized/qrCodePurchaseForm.js"></script>
</body>
</html>
