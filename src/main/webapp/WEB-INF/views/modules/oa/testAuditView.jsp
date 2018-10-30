<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>审批管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/testAudit/">审批列表</a></li>
		<li class="active"><a href="${ctx}/oa/testAudit/form/?procInsId=${testAudit.procInsId}">审批详情</a></li>
	</ul>
	<form:form class="form-horizontal">
		<sys:message content="${message}"/>
		<fieldset>
			<legend>审批详情</legend>
			<table class="table-form">
				<tr>
					<td class="tit">姓名</td><td>${testAudit.user.name}</td>
					<td class="tit">部门</td><td>${testAudit.office.name}</td>
					<td class="tit">岗位职级</td><td>${testAudit.post}</td>
				</tr>
				<tr>
					<td class="tit">调整原因</td>
					<td colspan="5">${testAudit.content}</td>
				</tr>
				<tr>
					<td class="tit" rowspan="3">调整原因</td>
					<td class="tit">薪酬档级</td>
					<td>${testAudit.olda}</td>
					<td class="tit" rowspan="3">拟调整标准</td>
					<td class="tit">薪酬档级</td>
					<td>${testAudit.newa}</td>
				</tr>
				<tr>
					<td class="tit">月工资额</td>
					<td>${testAudit.oldb}</td>
					<td class="tit">月工资额</td>
					<td>${testAudit.newb}</td>
				</tr>
				<tr>
					<td class="tit">年薪金额</td>
					<td>${testAudit.oldc}</td>
					<td class="tit">年薪金额</td>
					<td>${testAudit.newc}</td>
				</tr>
				<tr>
					<td class="tit">月增资</td>
					<td colspan="2">${testAudit.addNum}</td>
					<td class="tit">执行时间</td>
					<td colspan="2">${testAudit.exeDate}</td>
				</tr>
				<tr>
					<td class="tit">人力资源部意见</td>
					<td colspan="5">
						${testAudit.hrText}
					</td>
				</tr>
				<tr>
					<td class="tit">分管领导意见</td>
					<td colspan="5">
						${testAudit.leadText}
					</td>
				</tr>
				<tr>
					<td class="tit">集团主要领导意见</td>
					<td colspan="5">
						${testAudit.mainLeadText}
					</td>
				</tr>
			</table>
		</fieldset>
		<act:histoicFlow procInsId="${testAudit.act.procInsId}" />
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script src="${ctxStatic}/customized/common.js"></script>
</body>
</html>