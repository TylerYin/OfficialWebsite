<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公司赋码查询</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/fwzs/fwmQrcode/query">公司赋码查询</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="fwmQuery" action="${ctx}/fwzs/fwmQrcode/query" method="post" class="breadcrumb form-search">
		<div style="margin-top: 20px;margin-left: 20px;line-height: 30px;font-size: larger;">
			<div>
				1. 用户在生码后，可以用 “公司赋码查询” 功能查验本会员生成的防伪码相关信息
			</div>
			<div>
				2. 用户只能查验自己的防伪码
			</div>
		</div>
		<div style="padding-top: 25px;padding-left: 30px;font-size: 18px;font-weight:bold;">
			请输入防伪码&nbsp;&nbsp;
			<form:input path="qrCode" htmlEscape="false" maxlength="50" style="width:280px" class="input-medium"/> &nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查 询"/>
		</div>

		<p>
		<div id="queryResult">
			<c:choose>
				<c:when test="${empty fwmQuery.status or empty fwmQuery.createDate}">
					<c:if test="${not empty fwmQuery.qrCode}">
						<div style="padding-top: 25px;padding-left: 30px;font-size: 18px;">
							您查询的防伪码信息不存在或非自己生成的防伪码
						</div>
					</c:if>
				</c:when>
				<c:otherwise>
					<div style="padding-top: 25px;padding-left: 30px;font-size: 18px;">
						您查询的防伪码已赋码，信息如下：<p>
						<div style="margin-top: 10px;margin-bottom:10px; width: 1200px;border-bottom: 1px solid #000000;"></div>
					</div>
					<div style="padding-left: 30px">
						<table style="border: none;font-size: 14px;line-height: 2">
							<tr>
								<td>单品码：</td>
								<td style="width: 280px">${fwmQuery.qrCode}</td>
								<td>状态：</td>
								<td style="width: 280px">
									<c:choose>
										<c:when test="${fwmQuery.status eq 1}">
											激活
										</c:when>
										<c:otherwise>
											非激活
										</c:otherwise>
									</c:choose>
								</td>
								<td>查询次数：</td>
								<td style="width: 280px">${fwmQuery.selectNum}</td>
							</tr>

							<tr>
								<td>产品编号：</td>
								<td>${fwmQuery.prodNo}</td>
								<td>农药名称：</td>
								<td>${fwmQuery.pesticideName}</td>
								<td>产品名称：</td>
								<td>${fwmQuery.prodName}</td>
							</tr>

							<tr>
								<td>产品规格：</td>
								<td>${fwmQuery.specDesc}</td>
								<td>包装比例：</td>
								<td>${fwmQuery.packRate}</td>
								<td>登记公司：</td>
								<td>${fwmQuery.regCrop}</td>
							</tr>

							<tr>
								<td>生产企业：</td>
								<td>${fwmQuery.corpName}</td>
								<td>生码文件：</td>
								<td>${fwmQuery.fileName}</td>
								<td>生码时间：</td>
								<td>
									<c:if test="${not empty fwmQuery.createDate}">
										<fmt:formatDate value="${fwmQuery.createDate}" pattern="yyyy-MM-dd"/>
									</c:if>
								</td>
							</tr>
						</table>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</form:form>
    <script src="${ctxStatic}/customized/fwmQueryForm.js"></script>
</body>
</html>