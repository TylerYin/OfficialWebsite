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
    <div id="allmap" style="display:none"></div>
    <input type="hidden" id="qrCode" value="${qrCode}"/>
    <div id="insertQueryDataURL" style="display:none">${ctx}/phone/updateQueryData/</div>
    <header>
        <div class="headbg">
            <div class="container">
                <div class="row marpad">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 marpad">农药产品溯源查询系统</div>
                </div>
            </div>
        </div>
    </header>
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
        <!-- 轮播（Carousel）指标 -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>
        <!-- 轮播（Carousel）项目 -->
        <div class="carousel-inner">
            <div class="active item"><img src="${ctxStatic}/pesticide/images/banner1.jpg" alt="First slide"></div>
            <div class="item">
                <img src="${ctxStatic}/pesticide/images/banner1.jpg" alt="Second slide">
            </div>
            <div class="item">
                <img src="${ctxStatic}/pesticide/images/banner1.jpg" alt="Third slide">
            </div>
        </div>
        <!-- 轮播（Carousel）导航 -->
        <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true" ></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true" ></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
    <div class="container">
        <div class="row padding16">
            <fieldset>
                <legend>溯源码</legend>
                <div>${fwmQrcode.qrcode}</div>
                <c:choose>
                    <c:when test="${fwmQrcode.selectNum eq 1}">
                        <div>您查询的产品${fwmQrcode.bsProduct.prodName}已经查询1次</div>
                    </c:when>
                    <c:otherwise>
                        <div id="qrCodeDiv">
                            您查询的产品${fwmQrcode.bsProduct.prodName}已经查询<span id="qrCodeId">${fwmQrcode.selectNum}</span>次<span id="warningId"></span>
                        </div>
                    </c:otherwise>
                </c:choose>
            </fieldset>
        </div>
    </div>
    <div class="jianjunav"></div>
    <div class="container">
        <div class="row padding16" style="padding-top:5px" >
            <table class="tablebox">
                <thead>
                    <tr>
                        <th colspan="3" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <img src="${ctxStatic}/pesticide/images/img1.png" class="img-responsive" style="display:inline-block"/>产品信息
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="col-lg-4 col-md-4 col-sm-4 col-xs-4 td1">产品名称</td>
                        <td class="col-lg-7 col-md-7 col-sm-7 col-xs-7">${fwmQrcode.bsProduct.prodName}</td>
                        <td class="col-lg-1 col-md-1 col-sm-1 col-xs-1 padd0">
                            <a href="${ctx}/phone/product/extendAttribute?qrCode=${qrCode}">
                                <img src="${ctxStatic}/pesticide/images/imgjt.png" class="img-responsive" style="display:inline-block"/>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td class="col-lg-4 col-md-4 col-sm-4 col-xs-4 td1">型号</td>
                        <td class="col-lg-8 col-md-8 col-sm-8 col-xs-8" colspan="2">${fwmQrcode.bsProduct.prodSpec.specDesc}</td>
                    </tr>
                    <tr>
                        <td class="col-lg-4 col-md-4 col-sm-4 col-xs-4 td1">登记证号</td>
                        <td class="col-lg-8 col-md-8 col-sm-8 col-xs-8" colspan="2">${fwmQrcode.bsProduct.regCode}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="jianjunav"></div>
    <div class="container">
        <div class="row padding16" style="padding-top:5px" >
            <table class="tablebox">
                <thead>
                    <tr>
                        <th colspan="3" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <img src="${ctxStatic}/pesticide/images/img2.png" class="img-responsive" style="display:inline-block"/>生产信息
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="col-lg-4 col-md-4 col-sm-4 col-xs-4 td1">生产日期</td>
                        <td class="col-lg-7 col-md-7 col-sm-7 col-xs-7" ><fmt:formatDate value="${fwmQrcode.scPlan.updateDate}" pattern="yyyy-MM-dd"/></td>
                        <td class="col-lg-1 col-md-1 col-sm-1 col-xs-1 padd0"/>
                    </tr>
                    <tr>
                        <td class="col-lg-4 col-md-4 col-sm-4 col-xs-4 td1">生产批次</td>
                        <td class="col-lg-8 col-md-8 col-sm-8 col-xs-8" colspan="2">${fwmQrcode.scPlan.batchNo}</td>
                    </tr>
                    <tr>
                        <td class="col-lg-4 col-md-4 col-sm-4 col-xs-4 td1">登记企业</td>
                        <td class="col-lg-8 col-md-8 col-sm-8 col-xs-8" colspan="2">${fwmQrcode.bsProduct.regCrop}</td>
                    </tr>
                    <tr>
                        <td class="col-lg-4 col-md-4 col-sm-4 col-xs-4 td1">生产企业</td>
                        <td class="col-lg-8 col-md-8 col-sm-8 col-xs-8" colspan="2">${fwmQrcode.bsProduct.company.name}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="jianjunav"></div>
    <div class="container">
        <div class="row padding16" style="padding-top:5px">
            <table class="tablebox">
                <thead>
                    <tr>
                        <th colspan="3" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <img src="${ctxStatic}/pesticide/images/img3.png" class="img-responsive" style="display:inline-block"/>质检信息
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="col-lg-4 col-md-4 col-sm-4 col-xs-4 td1">质量检验</td>
                        <td class="col-lg-7 col-md-7 col-sm-7 col-xs-7">
                            <c:if test="${fwmQrcode.status eq '6'}">
                                合格
                            </c:if>
                        </td>
                        <td class="col-lg-1 col-md-1 col-sm-1 col-xs-1 padd0"/>
                    </tr>
                    <c:if test="${not empty fwmQrcode.bsProduct.checkPlanUrl}">
                        <tr>
                            <td class="col-lg-4 col-md-4 col-sm-4 col-xs-4 td1">质检报告</td>
                            <td class="col-lg-8 col-md-8 col-sm-8 col-xs-8" colspan="2">如下图</td>
                        </tr>
                        <tr>
                            <td class="col-lg-12 col-md-12 col-sm-12 col-xs-12 td1" colspan="3" style="border-bottom:none">
                                <img src="${fwmQrcode.bsProduct.checkPlanUrl}" class="img-responsive" style="display:inline-block"/>
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <fwzs:fwFooter qrCode="${qrCode}" isActiveTraceInfoLogo="true" isActiveCompanyInfoLogo="false" isActiveContactInfoLogo="false"/>
</body>
<script src="${ctxStatic}/demo/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=${baiduAPIKey}"></script>
<script src="${ctxStatic}/customized/traceInfo.js"></script>
</html>