<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>防伪码订单列表</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="#">防伪码订单列表</a></li>
</ul>
<form id="searchForm" modelAttribute="scLines" action="" method="post" class="breadcrumb form-search">
    <ul class="ul-form">
        <li><label>产品：</label>
            <input name="productNo" htmlEscape="false" maxlength="20" class="input-medium"/>
        </li>
        <li><label>批次：</label>
            <input name="batchNo" htmlEscape="false" maxlength="100" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>订单号</th>
        <th>数量</th>
        <th>类别</th>
        <th>交易时间</th>
        <th>交易金额</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
        <tr>
            <td>2018061118293211235</td>
            <td>25000</td>
            <td>中包</td>
            <td>2018-06-11 18:29:32</td>
            <td>500</td>
            <td>已付款</td>
            <td>
                <a href="${ctx}/fwzs/qrCode/downloadQrcodeFile">下载二维码</a>
            </td>
        </tr>
        <tr>
            <td>2018061118123211235</td>
            <td>50000</td>
            <td>大包</td>
            <td>2018-06-11 18:12:32</td>
            <td>1000</td>
            <td>已付款</td>
            <td>
                <a href="${ctx}/fwzs/qrCode/downloadQrcodeFile">下载二维码</a>
            </td>
        </tr>
        <tr>
            <td>2018061115293211235</td>
            <td>25000</td>
            <td>中包</td>
            <td>2018-06-12 15:29:32</td>
            <td>500</td>
            <td>已付款</td>
            <td>
                <a href="${ctx}/fwzs/qrCode/downloadQrcodeFile">下载二维码</a>
            </td>
        </tr>
        <tr>
            <td>2018061213123211235</td>
            <td>25000</td>
            <td>中包</td>
            <td>2018-06-12 13:12:32</td>
            <td>500</td>
            <td>待付款</td>
            <td>
                <a href="${ctx}/fwzs/qrCode/orderPayment?packageType=2">付款</a>
            </td>
        </tr>
        <tr>
            <td>2018061318293211235</td>
            <td>50000</td>
            <td>大包</td>
            <td>2018-06-13 18:29:32</td>
            <td>1000</td>
            <td>待付款</td>
            <td>
                <a href="${ctx}/fwzs/qrCode/orderPayment?packageType=3">付款</a>
            </td>
        </tr>
        <tr>
            <td>2018061314323211235</td>
            <td>50000</td>
            <td>大包</td>
            <td>2018-06-13 14:32:32</td>
            <td>1000</td>
            <td>已付款</td>
            <td>
                <a href="${ctx}/fwzs/qrCode/downloadQrcodeFile">下载二维码</a>
            </td>
        </tr>
    </tbody>
</table>

<div class="pagination"><ul>
    <li class="disabled"><a href="javascript:">« 上一页</a></li>
    <li class="active"><a href="javascript:">1</a></li>
    <li><a href="javascript:" onclick="page(2,2,'');">2</a></li>
    <li><a href="javascript:" onclick="page(3,2,'');">3</a></li>
    <li><a href="javascript:" onclick="page(4,2,'');">4</a></li>
    <li><a href="javascript:" onclick="page(5,2,'');">5</a></li>
    <li><a href="javascript:" onclick="page(2,2,'');">下一页 »</a></li>
    <li class="disabled controls"><a href="javascript:">共 6 页</a></li>
</ul><div style="clear:both;"></div></div>

</body>
</html>
