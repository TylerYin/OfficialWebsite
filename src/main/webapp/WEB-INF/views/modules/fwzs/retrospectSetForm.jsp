<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>防伪码信息管理</title>
	<meta name="decorator" content="default" />
	<link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
</head>
<body>
	<div id="saveURL" class="hidden">${ctx}/fwzs/retrospectSet/save</div>
	<div id="selectProductURL" class="hidden">${ctx}/fwzs/retrospectSet/selectProductProperty</div>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/fwmQrcode/">追溯展示设置</a></li>
	</ul>
	<br />
	<form id="inputForm" modelAttribute="fwmQrcode"
		action="" method="post"
		class="form-horizontal">
		<input type="hidden" id="id" name="id" value="">
		<div class="controls">
			<select id="productId" name="productId" class="product">
				<option value="">请选择</option>
				<c:forEach items="${fwmProduces}" var="d">
					<option value="${d.id }">${d.pesticideName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="control-group"></div>
		<div>
			<p id="p" class="p">
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:fwmQrcodeFiles:edit">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="设置" />&nbsp;</shiro:hasPermission>
		</div>
	</form>
	<script src="${ctxStatic}/customized/retrospectSetForm.js"></script>
</body>
</html>