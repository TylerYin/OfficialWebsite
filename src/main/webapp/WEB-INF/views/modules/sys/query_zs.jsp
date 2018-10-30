<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!DOCTYPE >

<html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=yes" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="http://tjsys.china315net.com/Admin/Comm/css/1/red.css" />
<link href="${ctxStatic}/demo/css/TJpublic.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/customized/sys.css" />
<script src="${ctxStatic}/demo/js/jquery-2.1.1.min.js"></script>
<script src="${ctxStatic}/demo/js/bootstrap.min.js"></script>

<!-- <style type="text/css">
.li-left {
	display: inline-block;
	text-align: right;
	width: 35%;
	font-size: 13px;
	margin: 5px 0;
}

.li-right {
	display: inline-block;
	text-align: left;
	width: 55%;
	font-size: 13px;
	color: #1411f6;
	margin: 5px 0;
}
</style> -->

</head>
<body class="f_s8">
	<form method="post" action="" id="form1">
		<!-- 新 Bootstrap 核心 CSS 文件 -->
		<div class="header">
			<span class="nav_left"><a
				class="glyphicon glyphicon-arrow-left"
				href="javascript:history.go(-1)"
				class="fontStyle"></a></span>
			<h1>产品溯源</h1>
		</div>
		<div id="myCarousel" class="carousel slide myCarousel">
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
			</ol>
			<div class="carousel-inner">
				<div class="item active">
					<img src="${ctxStatic}/demo/images/lb1.jpg" class="imageStyle">
				</div>
				 <div class="item">
					<img src="${ctxStatic}/demo/images/lb2.jpg" class="imageStyle">
				</div>
				<div class="item">
					<img src="${ctxStatic}/demo/images/lb3.jpg" class="imageStyle">
				</div> 
			</div>
			<%-- <div class="carousel-inner">
				<div class="item active topslideimg">
					<img class="qr-logo" src="${ctxStatic}/demo/images/lb1.jpg" />
					<%-- <img class="qr-logo" src="${ctxStatic}/demo/images/lb2.jpg" />
					<img class="qr-logo" src="${ctxStatic}/demo/images/lb3.jpg" /> --%>
			</div>

		<!-- <a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
			<a class="carousel-control right" href="#myCarousel"
				data-slide="next">&rsaquo;</a> -->
		
		<div class="container">
			<div class="qr-container">
				<div class="qrContainer">您所查询的产品信息如下(示例)：</div>
				<c:if test="${map.size()==0 }">
					<div class="span01 spanStyle">您好，您所查询的防伪码信息不存在！谨防假冒！</div>
				</c:if>
				<c:if test="${map.size()!=0 }">
					<div class="qr_labelimage" align="left">
						<ul>
							<c:forEach items="${map }" var="mymap">
								<c:if test="${mymap.key=='单元识别代码' }">
									<li class="liStyle1">&nbsp;&nbsp;<c:out value="${mymap.key}"></c:out>：<br/><font class="spanStyle1">&nbsp;&nbsp;<c:out value="${mymap.value}" /></font></li>
								</c:if>
								<c:if test="${mymap.key!='单元识别代码' }">
									<li class="liStyle1">&nbsp;&nbsp;<c:out value="${mymap.key}"></c:out>：<font class="liStyle"><c:out value="${mymap.value}" /></font></li>
								</c:if>
							</c:forEach>

							<%-- <li class="li-left">品种名称：</li>
					<li class="li-right">${pzmc }</li>
					<li class="li-left">生产经营者：</li>
					<li id="cxcs" class="li-right">${scjyz }</li>
					<li class="li-left">识别代码：</li>
					<li id="cxcs" class="li-right">${fwmQrcode.qrcode}</li>
					<li class="li-left">技术服务电话：</li>
					<li id="cxcs" class="li-right">${jsfwdh }</li>
					<li class="li-left">联系电话：</li>
					<li id="cxcs" class="li-right">${lxdh }</li>
					<li class="li-left">企业传真：</li>
					<li id="cxcs" class="li-right">${qycz }</li> --%>
						</ul>
					</div>
				</c:if>
			</div>

			<%-- <div style="color: #F60;margin-top: 10px;">您所查询的产品信息如下(示例)：</div>
		
			<table style="font-size: 14px;" border="0" >
			<c:if test="${fwmQrcode==null }"><tr class="jfhd-tr1"><td class="span01" >您好，您所查询的防伪码信息不存在！谨防假冒！</td></tr></c:if>
			<c:if test="${fwmQrcode!=null }">
				<tr class="jfhd-tr1"><td class="tdL">品种名称：${pzmc }</td></tr>
				<tr class="jfhd-tr1"><td class="tdL">生产经营者：${scjyz }</td></tr>
				<tr class="jfhd-tr1"><td class="tdL">识别代码：${fwmQrcode.qrcode}</td></tr>
				<!-- <tr class="jfhd-tr1"><td class="tdL">追溯网址：http://47.94.235.121/fwzs/a/login</td></tr> -->
				<tr class="jfhd-tr1"><td class="tdL">技术服务电话：${jsfwdh }</td></tr>
				<tr class="jfhd-tr1"><td class="tdL">联系电话：${lxdh }</td></tr>
				<tr class="jfhd-tr1"><td class="tdL">企业传真：${qycz }</td></tr>
				
			</table>
			</c:if>
		</div> --%>


			<!-- <div class="duihuan_footer home-bg " style="margin-top: 20px">
			<p class="copyright ">

				<b id="XYFT" style="display: none; line-height: 32px; color: White;"
					class="cpjjXYfooter"></b>
			</p>
		</div> -->
	</form>
	<script type="text/javascript">
		$(".carousel").carousel({
			interval : 2000
		});
	</script>
</body>
</html>

