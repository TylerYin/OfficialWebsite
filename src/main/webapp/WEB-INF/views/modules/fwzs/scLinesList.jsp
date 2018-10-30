<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生产线管理</title>
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
		<li class="active"><a href="${ctx}/fwzs/scLines/">生产线列表</a></li>
		<shiro:hasPermission name="fwzs:scLines:edit"><li><a href="${ctx}/fwzs/scLines/form">生产线添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="scLines" action="${ctx}/fwzs/scLines/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>生产线号：</label>
				<form:input path="lineNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>生产线名称：</label>
				<form:input path="lineName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>生产线号</th>
				<th>生产线名称</th>
				<!-- <th>创建人</th> -->
				<th>创建时间</th>
				<shiro:hasPermission name="fwzs:scLines:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="scLines">
			<tr>
				<td><a href="${ctx}/fwzs/scLines/form?id=${scLines.id}">
					${scLines.lineNo}
				</a></td>
				<td>
					${scLines.lineName}
				</td>
				<%-- <td>
					${scLines.createBy.id}
				</td> --%>
				<td>
					<fmt:formatDate value="${scLines.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fwzs:scLines:edit"><td>
    				<a onclick="javascript:loadUrl('${ctx}/fwzs/scLines/form?id=${scLines.id}','${scLines.createBy.id}')" href="#">修改</a>
					<a href="javascript:loadUrl('${ctx}/fwzs/scLines/delete?id=${scLines.id}','${scLines.createBy.id}')" onclick="return confirmx('确认要删除该生产线吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
	function loadUrl(url,createBy) {
		if ('${fns:getUser().id}'==createBy) {
			window.location.href = url;
		}else{
			showTip("无操作权限！");
		}
			
		
	}
</script>
</body>
</html>