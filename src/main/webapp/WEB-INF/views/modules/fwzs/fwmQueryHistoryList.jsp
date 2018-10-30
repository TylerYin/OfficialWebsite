<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>防伪码查询管理</title>
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
		<li class="active"><a href="${ctx}/fwzs/fwmQueryHistory/">防伪码查询列表</a></li>
		<%-- <shiro:hasPermission name="sys:fwmQueryHistory:edit"><li><a href="${ctx}/sys/fwmQueryHistory/form">防伪码查询添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="fwmQueryHistory" action="${ctx}/fwzs/fwmQueryHistory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>防伪码：</label>
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
				<th>防伪码</th>
				<th>查询时间</th>
				<th>查看方式</th>
				<th>查询Ip</th>
				<th>查询地址</th>
				<th>查询结果</th>
				<shiro:hasPermission name="sys:fwmQueryHistory:edit"><th>操作</th></shiro:hasPermission>
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
                    <c:choose>
                        <c:when test="${fwmQueryHistory.queryType==1}">
                            扫码方式
                        </c:when>
                        <c:when test="${fwmQueryHistory.queryType==2}">
                            平台查询
                        </c:when>
                        <c:otherwise>
                            其它方式
                        </c:otherwise>
                    </c:choose>
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
				<shiro:hasPermission name="sys:fwmQueryHistory:edit"><td>
    				<%-- <a href="${ctx}/sys/fwmQueryHistory/form?id=${fwmQueryHistory.id}">修改</a> --%>
					<a href="${ctx}/sys/fwmQueryHistory/delete?id=${fwmQueryHistory.id}" onclick="return confirmx('确认要删除该防伪码查询吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>