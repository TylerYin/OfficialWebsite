<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>追溯展示设置管理</title>
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
		<li class="active"><a href="${ctx}/fwzs/fwmRetrospectSet/">追溯展示设置列表</a></li>
		<shiro:hasPermission name="fwzs:fwmRetrospectSet:edit"><li><a href="${ctx}/fwzs/fwmRetrospectSet/form">追溯展示设置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="fwmRetrospectSet" action="${ctx}/fwzs/fwmRetrospectSet/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>update_date</th>
				<shiro:hasPermission name="fwzs:fwmRetrospectSet:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fwmRetrospectSet">
			<tr>
				<td><a href="${ctx}/fwzs/fwmRetrospectSet/form?id=${fwmRetrospectSet.id}">
					<fmt:formatDate value="${fwmRetrospectSet.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</a></td>
				<shiro:hasPermission name="fwzs:fwmRetrospectSet:edit"><td>
    				<a href="${ctx}/fwzs/fwmRetrospectSet/form?id=${fwmRetrospectSet.id}">修改</a>
					<a href="${ctx}/fwzs/fwmRetrospectSet/delete?id=${fwmRetrospectSet.id}" onclick="return confirmx('确认要删除该追溯展示设置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>