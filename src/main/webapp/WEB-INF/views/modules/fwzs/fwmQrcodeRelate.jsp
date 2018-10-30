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
		<li class="active"><a href="${ctx}/fwzs/fwmRelate/">生产任务关联码</a></li>
		<%-- <shiro:hasPermission name="fwzs:fwmQrcode:edit"><li><a href="${ctx}/fwzs/fwmQrcode/form">防伪码添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="fwmRelate" action="${ctx}/fwzs/fwmRelate/relate" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="pageSize" name="scPlan.id" type="hidden" value="${fwmRelate1.scPlan.id}"/>
		<input id="pageSize" name="scPlan.bsProduct.id" type="hidden" value="${fwmRelate1.scPlan.bsProduct.id}"/>
		<ul class="ul-form">
			<li><label>单品码：</label>
				<form:input path="fwmQrcode.qrcode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>一级包装码：</label>
				<form:input path="fwmBoxCode.boxCode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			 <li><label>任务单号：</label>
				<form:input path="scPlan.planNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="fwmQrcode.status" class="input-medium">
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
			<li class="btns"><input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>单品码</th>
				<th>单品码采集时间</th>
				<th>一级包装码</th>
				<th>一级包装时间</th>
				<th>二级包装码</th>
				<th>二级包装时间</th>
				
				<shiro:hasPermission name="fwzs:fwmQrcode:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fwmRelate">
			<tr>
				<td>
					${fwmRelate.fwmQrcode.qrcode}
				</td>
				<td>
				<fmt:formatDate value="${fwmRelate.fwmQrcode.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					
				</td>
				<td>
					${fwmRelate.fwmBoxCode.boxCode}
				</td>
				<td>
				<fmt:formatDate value="${fwmRelate.fwmBoxCode.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				
				</td>
				<td>
					${fwmRelate.bigboxCode.bigBoxCode}
				</td>
				<td>
				<fmt:formatDate value="${fwmRelate.bigboxCode.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					
				</td>
				
			
				<shiro:hasPermission name="fwzs:fwmQrcode:edit"><td>
    				<%-- <a href="${ctx}/fwzs/fwmQrcode/form?id=${fwmQrcode.id}">修改</a> --%>
					<a href="${ctx}/fwzs/fwmRelate/delete?fwmQrcode.id=${fwmRelate.fwmQrcode.id}&scPlan.id=${fwmRelate1.scPlan.id}&scPlan.bsProduct.id=${fwmRelate1.scPlan.bsProduct.id}" onclick="return confirmx('确认要删除该防伪码吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>