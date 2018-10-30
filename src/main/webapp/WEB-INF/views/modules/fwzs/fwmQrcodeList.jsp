<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>防伪码管理</title>
	<meta name="decorator" content="default"/>
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
		<li class="active"><a href="${ctx}/fwzs/fwmQrcode/">防伪码列表</a></li>
		<%-- <shiro:hasPermission name="fwzs:fwmQrcode:edit"><li><a href="${ctx}/fwzs/fwmQrcode/form">防伪码添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="fwmQrcode" action="${ctx}/fwzs/fwmQrcode/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fwmFile.id" name="fwmFile.id" type="hidden" value="${fwmQrcode.fwmFile.id}"/>
		<ul class="ul-form">
			<li><label>防伪码：</label>
				<form:input path="qrcode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>箱码：</label>
				<form:input path="fwmBoxCode.boxCode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>任务号：</label>
				<form:input path="scPlan.planNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('fwm_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%-- <li><label>产品：</label>
				<form:select path="bsProduct.id" class="input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>防伪码文件：</label>
				<form:select path="fwmFile.id" class="input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>防伪码</th>
				<th>箱码</th>
				<th>任务号</th>
				<th>状态</th>
				<th>查询数</th>
				<th>产品</th>
				<th>防伪码文件</th>
				<!-- <th>创建人</th> -->
				<th>创建时间</th>
				<shiro:hasPermission name="fwzs:fwmQrcode:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fwmQrcode">
			<tr>
				<td>
					${fwmQrcode.qrcode}
				</td>
				<td>
					${fwmQrcode.fwmBoxCode.boxCode}
				</td>
				<td>
					${fwmQrcode.scPlan.planNo}
				</td>
				<td>
					${fns:getDictLabel(fwmQrcode.status, 'fwm_status', '')}
				</td>
				<td>
					${fwmQrcode.selectNum}
				</td>
				<td>
					${fwmQrcode.bsProduct.prodName}
				</td>
				<td>
					${fwmQrcode.fwmFile.fileName}
				</td>
				<%-- <td>
					${fwmQrcode.createBy.id}
				</td> --%>
				<td>
					<fmt:formatDate value="${fwmQrcode.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fwzs:fwmQrcode:edit"><td>
    				<%-- <a href="${ctx}/fwzs/fwmQrcode/form?id=${fwmQrcode.id}">修改</a> --%>
					<a href="${ctx}/fwzs/fwmQrcode/delete?id=${fwmQrcode.id}" onclick="return confirmx('确认要删除该防伪码吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>