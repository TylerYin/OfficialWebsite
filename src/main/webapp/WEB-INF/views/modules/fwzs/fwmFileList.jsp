<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fwzs" tagdir="/WEB-INF/tags/fwzs"%>
<html>
<head>
	<title>防伪码文件列表管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/colorbox/colorbox.css" />
	<link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/fwzs/fwmFile/">防伪码文件列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="fwmFile" action="${ctx}/fwzs/fwmFile/" method="post" class="breadcrumb form-search">
		<div id="ctxId" class="hidden">${ctx}</div>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>文件号：</label>
				<form:input path="fileName" id="fileName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>选择产品：</label>
				<c:choose>
					<c:when test="${empty fwmFile.bsProduct.prodName}">
						<form:input path="bsProduct.prodName" id="prodName" htmlEscape="false" maxlength="50" value="请选择" readonly="true" class="input-medium"/>
					</c:when>
					<c:otherwise>
						<form:input path="bsProduct.prodName" id="prodName" htmlEscape="false" maxlength="50" readonly="true" class="input-medium"/>
					</c:otherwise>
				</c:choose>
				<form:input path="bsProduct.id" id="prodId" htmlEscape="false" maxlength="50" class="input-medium" style="display:none"/>
			</li>
			<li><label>创建时间：</label>
				<input id="createDate" name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${fwmFile.createDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>文件号</th>
				<th>产品编码</th>
				<th>登记证号</th>
				<th>农药名称</th>
				<th>产品名称</th>
				<th>产品规格</th>
				<th>生码数量</th>
				<!-- <th>创建人</th> -->
				<th>创建时间</th>
				<shiro:hasPermission name="fwzs:fwmFile:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fwmFile">
			<tr>
				<td><a href="${ctx}/fwzs/fwmQrcode/list?fwmFile.id=${fwmFile.id}">
					${fwmFile.fileName}
				</a></td>
				<td>
					${fwmFile.bsProduct.prodNo}
				</td>
				<td>
					${fwmFile.bsProduct.regCode}
				</td>
				<td>
					${fwmFile.bsProduct.pesticideName}
				</td>
				<td>
					${fwmFile.bsProduct.prodName}
				</td>
				<td>
					${fwmFile.bsProduct.prodSpec.specDesc}
				</td>
				<td>
					${fwmFile.codeNumber}
				</td>
				
				<%-- <td>
					${fwmFile.createBy.id}
				</td>  --%>
				<td>
					<fmt:formatDate value="${fwmFile.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fwzs:fwmFile:edit"><td>
    				<a href="${ctx}/fwzs/fwmFile/download?id=${fwmFile.id}">下载</a>
					<a href="javascript:FWMFileList.selectProduct.Events.delete('${fwmFile.id}');" onclick="return confirmx('确认要删除该防伪码文件列表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

	<fwzs:selectProduct bsProducts="${bsProducts}"/>

	<script src="${ctxStatic}/colorbox/jquery.colorbox.js"></script>
	<script src="${ctxStatic}/customized/fwmFileList.js"></script>
</body>
</html>