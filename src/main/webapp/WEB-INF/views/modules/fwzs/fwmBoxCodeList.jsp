<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>箱码管理</title>
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
		<li class="active"><a href="${ctx}/fwzs/fwmBoxCode/">箱码列表</a></li>
		<shiro:hasPermission name="fwzs:fwmBoxCode:edit"><li><a href="${ctx}/fwzs/fwmBoxCode/form">箱码添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="fwmBoxCode" action="${ctx}/fwzs/fwmBoxCode/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>box_code：</label>
				<form:input path="boxCode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>plan_id：</label>
				<form:input path="planId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>status：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('fwm_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>box_code</th>
				<th>plan_id</th>
				<th>status</th>
				<th>create_by</th>
				<th>update_date</th>
				<shiro:hasPermission name="fwzs:fwmBoxCode:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fwmBoxCode">
			<tr>
				<td><a href="${ctx}/fwzs/fwmBoxCode/form?id=${fwmBoxCode.id}">
					${fwmBoxCode.boxCode}
				</a></td>
				<td>
					${fwmBoxCode.planId}
				</td>
				<td>
					${fns:getDictLabel(fwmBoxCode.status, 'fwm_status', '')}
				</td>
				<td>
					${fwmBoxCode.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${fwmBoxCode.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fwzs:fwmBoxCode:edit"><td>
    				<a href="${ctx}/fwzs/fwmBoxCode/form?id=${fwmBoxCode.id}">修改</a>
					<a href="${ctx}/fwzs/fwmBoxCode/delete?id=${fwmBoxCode.id}" onclick="return confirmx('确认要删除该箱码吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>