<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!DOCTYPE >
<html>
<head>
<meta charset="utf-8">
<title>${fns:getConfig('productName')}</title>
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="${ctxStatic}/jingle/css/Jingle.css">
<link rel="stylesheet" href="${ctxStatic}/jingle/css/app.css">
</head>
<body>
	<div id="aside_container"></div>
	<div id="section_container">
		<section id="login_section" class="active">
			<header>
				<h1 class="title">臻诚智能防伪追溯系统</h1>
				<!-- <nav class="right">
                <a data-target="section" data-icon="info" href="#about_section"></a>
            </nav> -->
			</header>
			<article data-scroll="true" id="login_article">
				<div class="indented">
					<label for="username">产品二维码</label>${fwmQrcode.qrcode}</br>
					<label for="username">加工批次</label>${fwmQrcode.batch}</br>
					<label for="username">经销商</label>${fwmQrcode.dealer}</br>
					<label for="username">销售区域</label>${fwmQrcode.saleArea}</br>
					<label for="username">服务电话</label>${fwmQrcode.servicePhone}</br>
				</div>
			</article>
		</section>
	</div>
	<!--<script type="text/javascript" src="${ctxStatic}/jingle/js/lib/cordova.js"></script>-->
	<!-- lib -->
	<script type="text/javascript"
		src="${ctxStatic}/jingle/js/lib/zepto.js"></script>
	<script type="text/javascript"
		src="${ctxStatic}/jingle/js/lib/iscroll.js"></script>
	<%-- <script type="text/javascript" src="${ctxStatic}/jingle/js/lib/template.min.js"></script> --%>
	<script type="text/javascript"
		src="${ctxStatic}/jingle/js/lib/Jingle.debug.js"></script>
	<script type="text/javascript"
		src="${ctxStatic}/jingle/js/lib/zepto.touch2mouse.js"></script>
	<%-- <script type="text/javascript" src="${ctxStatic}/jingle/js/lib/JChart.debug.js"></script> --%>
	<!--- app --->
	<script type="text/javascript">
		var ctx = '${ctx}';
	</script>
	<script type="text/javascript" src="${ctxStatic}/jingle/js/app/app.js"></script>
	<!--<script src="http://192.168.2.153:8080/target/target-script-min.js#anonymous"></script>-->
	<script type="text/javascript">
		var sessionid = '${not empty fns:getPrincipal() ? fns:getPrincipal().sessionid : ""}';

		$('body').delegate('#login_section', 'pageshow', function() {
			if (sessionid != '') {
				var targetHash = location.hash;
				if (targetHash == '#login_section') {
					//J.showToast('你已经登录！', 'success');
					J.Router.goTo('#index_section?index');
				}
			} else {
				$('#login_article').addClass('active');
			}
		});
		function loadUrl(url) {
			var qrCode=$('#qrCode').val();
			window.location.href = url+"?qrCode="+qrCode;
		}
	</script>
</body>
</html>