<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!DOCTYPE >

<html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=yes" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="${ctxStatic}/demo/css/styles.css" rel="stylesheet" />

</head>
<body>
	<div class="wapper">
            <div class="banner">
                <img src="${ctxStatic}/demo/images/banner_history.jpg">
            </div>
        <div class="container">
            <div class="pagedescript">
                   <p>该数据由农药生产厂家提供，如有疑问，请向生产厂家咨询</p> 
            </div>
            <div class="contentlist spacenull">
                <div class="listtitle">
                    最近扫码记录
                </div>
                <ul>
                <c:forEach items="${fwmQueryHistorys}" var="history">
	                <li>
                        <span class="listtag">扫码时间</span>
                        <span class="listvalue">
                           <fmt:formatDate value="${history.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </span>
	                </li>
	                <li>
	                	<span class="listtag">扫码地址</span>
	                	<span class="listvalue">
	                    	${history.address}
	                    </span>
	                </li>
	                <br>
                </c:forEach>
				</ul>
            </div>
        </div>
        <div class="footer">

        </div>
    </div>
    <script src="${ctxStatic}/customized/removeFwmSessionStorage.js"></script>
</body>
</html>

