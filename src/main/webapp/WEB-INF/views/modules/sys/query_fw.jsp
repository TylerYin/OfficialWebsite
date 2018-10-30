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
<link rel="stylesheet" type="text/css" href="${ctxStatic}/customized/sys.css" />
<link href="${ctxStatic}/demo/css/TJpublic.css" rel="stylesheet" />
<script src="${ctxStatic}/demo/js/jquery-2.1.1.min.js"></script>
<script src="${ctxStatic}/demo/js/bootstrap.min.js"></script>

<!-- <style type="text/css">
.li-left {
	display: inline-block;
	text-align: left;
	width: 30%;
	font-size: 13px;
	margin: 5px 0;
}

.li-right {
	display: inline-block;
	text-align: left;
	width: 40%;
	font-size: 13px;
	color: #1411f6;
	margin: 5px 0;
}
</style>
 -->

</head>
<body>
	<!-- <div id="myCarousel" class="carousel slide">
	轮播（Carousel）指标
	<ol class="carousel-indicators">
		<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
		<li data-target="#myCarousel" data-slide-to="1"></li>
		<li data-target="#myCarousel" data-slide-to="2"></li>
	</ol>   
	轮播（Carousel）项目
	<div class="carousel-inner">
		<div class="item active">
			<img src="/wp-content/uploads/2014/07/slide1.png" alt="First slide">
		</div>
		<div class="item">
			<img src="/wp-content/uploads/2014/07/slide2.png" alt="Second slide">
		</div>
		<div class="item">
			<img src="/wp-content/uploads/2014/07/slide3.png" alt="Third slide">
		</div>
	</div>
	轮播（Carousel）导航
	<a class="carousel-control left" href="#myCarousel" 
	   data-slide="prev">&lsaquo;</a>
	<a class="carousel-control right" href="#myCarousel" 
	   data-slide="next">&rsaquo;</a>
</div>  -->
	<form method="post" action="" id="form1">
		<div class="header">
			<span class="nav_left"><a
				class="glyphicon glyphicon-arrow-left"
				href="javascript:history.go(-1)"
				class="fontStyle"></a></span>
			<h1>真伪鉴别</h1>
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

		<div class=" qr-container">
			<div class=" qr_labelimage">
				<div>
					<img class="labelImage" src="${codeImage }" />
				</div>
				<div class=" qr-caption">
					<span id="Label5" class="span01">${fwInfo}</span>
					<!-- <span id="S1" class="span01">图片信息一致，相符并用手触摸有明显凹凸感,则为真品,否则为假冒.</span> -->
				</div>
			</div>
		</div>
		<div class="qr_labelimage">
			<ul class="ulStyle">
				<li class="li-left">&nbsp;&nbsp;产品二维码：<font class="liStyle">${fwmQrcode.qrcode}</font></li>
				<li class="li-right" ></li>
				<li class="li-left">&nbsp;&nbsp;经销商：<font class="liStyle">${fwmQrcode.dealer}</font></li>
				<li class="li-right"></li>
				<li class="li-left">&nbsp;&nbsp;服务电话：<font class="liStyle">${fwmQrcode.servicePhone}</font></li>
				<li id="cxcs" class="li-right">&nbsp;&nbsp;</li>
			</ul>
		</div>
		</div>
	</form>
</body>
</html>