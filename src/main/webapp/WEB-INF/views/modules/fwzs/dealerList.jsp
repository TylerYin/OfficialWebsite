<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>经销商管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
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
	<div id="areaMenuContent" class="menuContent" style="display:none; position: absolute; background: #c2ccd1">
		<ul id="areaTree" class="ztree" style="margin-top:0; width:220px;"></ul>
	</div>
	<div id="areaTreeMenuURL" style="display:none">${ctx}/fwzs/dealer/areaMenuData</div>

	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/fwzs/dealer">经销商列表</a></li>
		<shiro:hasPermission name="fwzs:dealer:edit"><li><a href="${ctx}/fwzs/dealer/form">经销商添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="dealer" action="${ctx}/fwzs/dealer/" method="post" class="breadcrumb form-search">
		<c:set var="dealerModel" value="${dealer}"/>
		<div id="ctxId" class="hidden">${ctx}</div>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>电话：</label>
				<form:input path="phone" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>区域：</label>
				<c:choose>
					<c:when test="${not empty dealer.salesArea.name}">
						<form:input path="salesArea.name" id="salesAreaName" htmlEscape="false" maxlength="50" onclick="showSalesAreaMenu()" readonly="true" class="input-medium"/>
					</c:when>
					<c:otherwise>
						<form:input path="salesArea.name" id="salesAreaName" htmlEscape="false" maxlength="50" onclick="showSalesAreaMenu()" readonly="true" class="input-medium" value="请选择"/>
					</c:otherwise>
				</c:choose>
				<form:input path="salesArea.id" id="salesArea" htmlEscape="false" maxlength="50" cssStyle="display:none;"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>经销商编号</th>
			<th>经销商帐号</th>
			<th>经销商名称</th>
			<th>上级经销商名称</th>
			<th>地址</th>
			<th>电话</th>
			<th>邮箱</th>
			<th>传真</th>
			<th>所属企业</th>
			<th>所在区域</th>
			<th>级别</th>
			<th>创建时间</th>
			<shiro:hasPermission name="fwzs:dealer:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dealer">
			<tr>
                <td>
                    <a href="${ctx}/fwzs/dealer/form?id=${dealer.id}">
                        ${dealer.dealerNo}
                    </a>
                </td>
				<td>
                    ${dealer.account}
				</td>
                <td>
					${dealer.name}
                </td>
				<td>
					<c:if test="${not empty dealer.parentDealer}">
						${dealer.parentDealer.name}
					</c:if>
				</td>
				<td>
					${dealer.address}
				</td>
				<td>
					${dealer.phone}
				</td>
				<td>
					${dealer.email}
				</td>
				<td>
					${dealer.fax}
				</td>
				<td>
					<c:if test="${not empty dealer.company}">
						${dealer.company.name}
					</c:if>
				</td>
				<td>
					<c:if test="${not empty dealer.salesArea}">
						${dealer.salesArea.name}
					</c:if>
				</td>
				<td>
                    <c:if test="${not empty dealer}" >
                        <c:choose>
                            <c:when test="${dealer.grade eq '1'}">
                                一级
                            </c:when>
                            <c:when test="${dealer.grade eq '2'}">
                                二级
                            </c:when>
                            <c:otherwise>
                                三级
                            </c:otherwise>
                        </c:choose>
                    </c:if>
				</td>
				<td>
					<fmt:formatDate value="${dealer.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fwzs:dealer:edit"><td>
					<a href="${ctx}/fwzs/dealer/form?id=${dealer.id}">修改</a>
					<a href="javascript:DealerList.Events.delete('${dealer.id},${dealerModel.name},${dealerModel.phone},${dealerModel.salesArea.id},${dealerModel.salesArea.name},${dealerModel.company.name}');" onclick="return confirmx('确认要删除该经销商吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script src="${ctxStatic}/customized/dealerList.js"></script>
	<script src="${ctxStatic}/customized/salesAreaTreeMenu.js"></script>
</body>
</html>