<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
	<title><sitemesh:title default="欢迎光临"/> - ${site.title} - Powered By 臻诚科技</title>

	<%@include file="/WEB-INF/views/modules/cms/front/include/head.jsp" %>
	<script>var _hmt=_hmt||[];(function(){var hm=document.createElement("script");hm.src="//hm.baidu.com/hm.js?82116c626a8d504a5c0675073362ef6f";var s=document.getElementsByTagName("script")[0];s.parentNode.insertBefore(hm,s);})();</script>
	<sitemesh:head/>
    <link href="${ctxStatic}/frontpage/css/basic.css" rel="stylesheet"/>
    <style>
        html,body{background: url(${ctxStatic}/frontpage/images/bg1.jpg) fixed center center no-repeat;background-size: cover;width: 100%;padding:0;}
    </style>
</head>
<body>
    <div id="header" class="color1">
        <div class="warp">
            <div id="logo">
                <a href="#">
                    <img src="${ctxStatic}/frontpage/images/logo.png" alt="臻城科技">
                </a>
            </div>
            <div class="nav">
                <ul>
                    <li style="width:117px"><a href="${ctx}/index-1${fns:getUrlSuffix()}"><span>${site.id eq '1'?'首　 页':'返回主站'}</span></a></li>
                    <c:forEach items="${fnc:getMainNavList(site.id)}" var="category" varStatus="status">
                        <c:set var="menuCategoryId" value=",${category.id},"/>
                        <li style="width:117px"><a href="${category.url}" target="${category.target}"><span>${category.name}</span></a></li>
                    </c:forEach>
                </ul>
            </div>
            <span class="menu-toggle show-xs-block">
                菜单
                <em></em>
            </span>
        </div>
    </div>

	<div id="container" class="contentbg">
        <sitemesh:body/>
    </div>
    <div class="foot">${site.copyright}</div>
</body>
</html>