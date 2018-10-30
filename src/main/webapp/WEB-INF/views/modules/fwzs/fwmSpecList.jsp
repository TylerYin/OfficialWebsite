<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>产品规格管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/fwzs/fwmSpec/">产品规格列表</a></li>
		<shiro:hasPermission name="fwzs:fwmSpec:edit">
			<li><a href="${ctx}/fwzs/fwmSpec/form">产品规格添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="fwmSpec"
		action="${ctx}/fwzs/fwmSpec/" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>规格码：</label> <form:input path="specCode"
					htmlEscape="false" maxlength="64" class="input-medium" /></li>
			<li><label>规格说明：</label> <form:input path="specDesc"
					htmlEscape="false" maxlength="100" class="input-medium" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>规格码</th>
				<th>规格说明</th>
				<!-- <th>创建人</th> -->
				<th>创建时间</th>
				<shiro:hasPermission name="fwzs:fwmSpec:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="fwmSpec">
				<tr>
					<td><a href="${ctx}/fwzs/fwmSpec/form?id=${fwmSpec.id}">
							${fwmSpec.specCode} </a></td>
					<td>${fwmSpec.specDesc}</td>
					<%-- <td>
					${fwmSpec.createBy.id}
				</td> --%>
					<td><fmt:formatDate value="${fwmSpec.updateDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<shiro:hasPermission name="fwzs:fwmSpec:edit">
						<td><a      
							onclick="javascript:loadUrl('${ctx}/fwzs/fwmSpec/form?id=${fwmSpec.id}','${fwmSpec.createBy.id}')"
							href="#">修改</a> <a
							href="javascript:loadUrl('${ctx}/fwzs/fwmSpec/delete?id=${fwmSpec.id}','${fwmSpec.createBy.id}')"
							onclick="return confirmx('确认要删除该产品规格吗？', this.href)">删除</a></td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		function loadUrl(url, createBy) {
			if ('${fns:getUser().id}' == createBy) {
				window.location.href = url;
			} else {
				showTip("无操作权限！");
			}

		}
	</script>
</body>

</html>
