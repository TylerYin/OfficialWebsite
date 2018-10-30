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

    <div class="container">
        <div class="row padding16" >
            <img src="${ctxStatic}/pesticide/images/about.jpg" class="img-responsive center-block"/>
        </div>
    </div>
    <div class="jianjunav"></div>

    <div class="container">
        <div class="row padding16" style="padding-top:5px" >
            <table class="tablebox">
                <tbody>
                    <tr>
                        <td class="col-lg-2 col-md-2 col-sm-2 col-xs-2 td1" >电话</td>
                        <td class="col-lg-10 col-md-10 col-sm-10 col-xs-10 " >${office.phone}</td>
                    </tr>
                    <tr>
                        <td class="col-lg-2 col-md-2 col-sm-2 col-xs-2 td1" >传真</td>
                        <td class="col-lg-10 col-md-10 col-sm-10 col-xs-10" colspan="2">${office.fax}</td>
                    </tr>
                    <tr>
                        <td class="col-lg-2 col-md-2 col-sm-2 col-xs-2 td1" >邮箱</td>
                        <td class="col-lg-10 col-md-10 col-sm-10 col-xs-10" colspan="2">${office.email}</td>
                    </tr>
                    <tr>
                        <td class="col-lg-2 col-md-2 col-sm-2 col-xs-2 td1" >网址</td>
                        <td class="col-lg-10 col-md-10 col-sm-10 col-xs-10" colspan="2">
                            <a href="${office.website}">${office.website}</a>
                        </td>
                    </tr>
                    <tr>
                        <td class="col-lg-2 col-md-2 col-sm-2 col-xs-2 td1" >地址</td>
                        <td class="col-lg-10 col-md-10 col-sm-10 col-xs-10" colspan="2">
                            <c:if test="${not empty office.area and not empty office.area.name}">
                                ${office.area.name}
                            </c:if>
                            ${office.address}
                        </td>
                    </tr>
                    <tr>
                        <td class="col-lg-12 col-md-12 col-sm-12 col-xs-12 td1" colspan="3" style="border-bottom:none; padding:0px; ">
                            <a href="${office.website}">
                                <input type="button" value="访问官网" class="button1"/>
                            </a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <fwzs:fwFooter qrCode="${qrCode}" isActiveTraceInfoLogo="false" isActiveCompanyInfoLogo="false" isActiveContactInfoLogo="true"/>
</body>
<script src="${ctxStatic}/customized/removeFwmSessionStorage.js"></script>
</html>