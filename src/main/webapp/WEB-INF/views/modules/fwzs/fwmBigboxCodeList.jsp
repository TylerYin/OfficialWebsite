<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>剁码管理</title>
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
		<li class="active"><a href="${ctx}/fwzs/fwmBigboxCode/">剁码列表</a></li>
		<shiro:hasPermission name="fwzs:fwmBigboxCode:edit"><li><a href="${ctx}/fwzs/fwmBigboxCode/form">剁码添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="fwmBigboxCode" action="${ctx}/fwzs/fwmBigboxCode/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>big_box_code：</label>
				<form:input path="bigBoxCode" htmlEscape="false" maxlength="50" class="input-medium"/>
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
				<th>big_box_code</th>
				<th>plan_id</th>
				<th>status</th>
				<th>create_by</th>
				<th>update_date</th>
				<shiro:hasPermission name="fwzs:fwmBigboxCode:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fwmBigboxCode">
			<tr>
				<td><a href="${ctx}/fwzs/fwmBigboxCode/form?id=${fwmBigboxCode.id}">
					${fwmBigboxCode.bigBoxCode}
				</a></td>
				<td>
					${fwmBigboxCode.planId}
				</td>
				<td>
					${fns:getDictLabel(fwmBigboxCode.status, 'fwm_status', '')}
				</td>
				<td>
					${fwmBigboxCode.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${fwmBigboxCode.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fwzs:fwmBigboxCode:edit"><td>
    				<a href="${ctx}/fwzs/fwmBigboxCode/form?id=${fwmBigboxCode.id}">修改</a>
					<a href="${ctx}/fwzs/fwmBigboxCode/delete?id=${fwmBigboxCode.id}" onclick="return confirmx('确认要删除该剁码吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>