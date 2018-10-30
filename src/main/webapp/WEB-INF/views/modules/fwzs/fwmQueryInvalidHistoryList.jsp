<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>无效码查询列表</title>
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
		<li class="active"><a href="${ctx}/fwzs/fwmQueryHistory/invalidList">无效码查询列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="fwmQueryHistory" action="${ctx}/fwzs/fwmQueryHistory/invalidList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>无效码：</label>
				<form:input path="qrcode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>查询时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${fwmQueryHistory.createDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="删除"/></li> -->
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>无效码</th>
				<th>查询时间</th>
				<th>查看方式</th>
				<th>查询Ip</th>
				<th>查询地址</th>
				<th>查询结果</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fwmQueryHistory">
			<tr>
				<td><a href="#">
					${fwmQueryHistory.qrcode}
				</a></td>
				<td>
					<fmt:formatDate value="${fwmQueryHistory.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
				<c:if test="${fwmQueryHistory.queryType==1}">扫码方式</c:if>
					
				</td>
				<td>
					${fwmQueryHistory.querySource}
				</td>
				<td>
					${fwmQueryHistory.address}
				</td>
				<td>
				<c:if test="${fwmQueryHistory.queryResult==0}">真</c:if>
					<c:if test="${fwmQueryHistory.queryResult==1}">假</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>