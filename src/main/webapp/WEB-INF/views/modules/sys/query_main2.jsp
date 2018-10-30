<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!DOCTYPE >

<html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=yes" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://tjsys.china315net.com/Admin/Comm/css/1/red.css" />
<link href="${ctxStatic}/demo/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/customized/sys.css" />
<script src="${ctxStatic}/demo/js/jquery-2.1.1.min.js"></script>
<script src="${ctxStatic}/demo/js/bootstrap.min.js"></script>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}
</style>
</head>
<body class="bodyPosition">
	<form method="post" action="index.aspx" id="form1">
		<div class="aspNetHidden">
			<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE"
				value="/wEPDwUKMTUyNzYxOTU3NmRk7vHIMyClhNyCty8IZ4lMv/8I//loeF7j6n/E0BgixnE=" />
		</div>

		<div class="aspNetHidden">
			<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION"
				value="/wEWAgKwh4TBDgLb9dPMBmLoVwtWGh4w0Y2LrLDH0abzjzg6N8SAsGMiQ22NxIOK" />
		</div>
		<div id="myCarousel" class="carousel slide">
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
			</ol>
			<div class="carousel-inner">
				<div class="item active topslideimg">
					<img class="qr-logo" src="${ctxStatic}/demo/images/top.png" />
				</div>
			</div>
			<a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
			<a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
		</div>

		<div class="container ">
			<div id="jcdj" class="chanel-title">精彩点击</div>
			<input type="hidden" id="qrCode" name="qrCode" value="${qrCode}">
			<div class="row mokuairow ">
				<div class="col-xs-4 tjmoduleicon">
					<div class="tjmoduleiconsingle">
						<a Target="_self" href="javascript:loadUrl('${ctx}/phone/fw');"><img
							src="${ctxStatic}/demo/images/fw.jpg"></a>
						<!-- <div class="classicon">
							<span>真伪鉴别</span>
						</div> -->
					</div>
				</div>
				<div class="tjmoduleicon col-xs-4">
					<div class="tjmoduleiconsingle">
						<a Target="_self" href="#"><img
							src="${ctxStatic}/demo/images/ch.jpg"></a>
						<!-- <div class="classicon">
							<span>窜货查询</span>
						</div> -->
					</div>
				</div>
				<div class="tjmoduleicon col-xs-4">
					<div class="tjmoduleiconsingle">
						<a Target="_self" href="javascript:loadUrl('${ctx}/phone/zs')"><img
							src="${ctxStatic}/demo/images/sy.jpg"></a>
						<!-- <div class="classicon">
							<span>产品溯源</span>
						</div> -->
					</div>
				</div>
			</div>
			<div class="row mokuairow ">
				<div class="col-xs-4 tjmoduleicon">
					<div class="tjmoduleiconsingle">
						<a Target="_self" href="#"><img
							src="${ctxStatic}/demo/images/lxwm.jpg"></a>
						<!-- <div class="classicon">
							<span>联系我们</span>
						</div> -->
					</div>
				</div>
				<div class="tjmoduleicon col-xs-4">
					<div class="tjmoduleiconsingle">
						<a Target="_self" href="#"><img
							src="${ctxStatic}/demo/images/qyjs.jpg"></a>
						<!-- <div class="classicon">
							<span>企业介绍</span>
						</div> -->
					</div>
				</div>
				<div class="tjmoduleicon col-xs-4">
					<div class="tjmoduleiconsingle">
						<a Target="_self" href="#"><img
							src="${ctxStatic}/demo/images/fwzs.jpg"></a>
						<!-- <div class="classicon">
							<span>防伪知识</span>
						</div> -->
					</div>
				</div>
			</div>
		</div>

		<input name="TCompID" type="text" id="TCompID" class="tComp" />
	</form>
	<script type="text/javascript">
		window.onload = function() {
			var bhght = $("body").height(), whght = $(window).height();
			$(".carousel").carousel({
				interval : 2000
			});
			if (bhght < whght) {
				$("body").css({
					"height" : whght
				});
			}
		}
		function loadUrl(url) {
			var qrCode = $('#qrCode').val();
			window.location.href = url + "?qrCode=" + qrCode;
		}
	</script>
</body>
</html>