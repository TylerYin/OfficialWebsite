<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
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
		<li class="active"><a href="${ctx}/fwzs/bsProduct/">产品列表</a></li>
		<shiro:hasPermission name="fwzs:bsProduct:edit"><li><a href="${ctx}/fwzs/bsProduct/form">产品添加</a></li></shiro:hasPermission>
		<shiro:hasPermission name="fwzs:bsProduct:edit"><li><a href="${ctx}/fwzs/bsProduct/multiProductForm">多规格产品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bsProduct" action="${ctx}/fwzs/bsProduct/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>产品编号：</label>
				<form:input path="prodNo" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>登记类别：</label>
				<form:select path="regType" class="input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('code_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>农药名：</label>
				<form:input path="pesticideName" htmlEscape="false" maxlength="150" class="input-medium"/>
			</li>
            <li><label>产品规格：</label>
                <form:select path="prodSpec.specCode" class="input-medium">
                    <form:option value="" label="请选择"/>
                    <c:forEach items="${fwmSpecs}" var="fwmSpec">
                        <form:option value="${fwmSpec.specCode}" label="${fwmSpec.specDesc}"/>
                    </c:forEach>
                </form:select>
            </li>
			<li><label>生产类型：</label>
				<form:select path="scType" class="input-medium">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sc_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>产品编号</th>
				<th>登记类别代码</th>
				<th>登记证号</th>
				<th>农药名</th>
				<th>产品名称</th>
				<th>证件所属公司</th>
				<th>生产企业</th>
				<th>产品规格</th>
				<th>包装比例</th>
				<th>生产类型</th>
				<th>单位</th>
				<th>添加时间</th>
				<shiro:hasPermission name="fwzs:bsProduct:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bsProduct">
			<tr>
				<td>
                    <a href="${ctx}/fwzs/bsProduct/form?id=${bsProduct.id}">${bsProduct.prodNo}</a>
                </td>
				<td>
					${fns:getDictLabel(bsProduct.regType, 'code_type', '')}
				</td>
				<td>
					${bsProduct.regCode}
				</td>
				<td>
					${bsProduct.pesticideName}
				</td>
				<td>
					${bsProduct.prodName}
				</td>
				<td>
					${bsProduct.regCrop}
				</td>
				<td>
					${bsProduct.company.name}
				</td>
				<td>
					${bsProduct.prodSpec.specDesc}
				</td>
				<td>
					${bsProduct.packRate}
				</td>
				<td>
					${fns:getDictLabel(bsProduct.scType, 'sc_type', '')}
				</td>
				<td>
				    ${fns:getDictLabel(bsProduct.prodUnit, 'prod_unit', '')}
				</td>
				<td>
					<fmt:formatDate value="${bsProduct.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fwzs:bsProduct:edit">
                    <td>
                        <a href="${ctx}/fwzs/bsProduct/form?id=${bsProduct.id}">修改</a>
                        <a href="${ctx}/fwzs/bsProduct/updateExtendAttributesForm?id=${bsProduct.id}">参数修改</a>
                        <a href="${ctx}/fwzs/bsProduct/delete?id=${bsProduct.id}" onclick="return confirmx('确认要删除该防伪查询记录表吗？', this.href)">删除</a>
                    </td>
                </shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>