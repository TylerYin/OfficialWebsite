<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>防伪追溯模板管理</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/customized/customized.css" />
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/fwzs/systemSetting/retrospectTemplate">防伪追溯模板管理</a></li>
</ul><br/>
<form id="retrospectTemplateForm" action="${ctx}/fwzs/systemSetting/saveRetrospectTemplate" method="post" class="form-horizontal">
    <div class="control-group">
        <div class="controls">
            <c:forEach items="${retrospectTemplateList}" var="retrospectTemplate">
                <table style="float: left;margin-left: 20px;">
                    <tr>
                        <td><img src="${ctxStatic}/retrospectTemplate/image/${retrospectTemplate}.png" style="width: 330px; height: 630px;"></td>
                    </tr>
                    <c:choose>
                        <c:when test="${retrospectTemplate eq defaultRetrospectTemplate}">
                            <tr><td style="text-align: center"><input type="radio" name="retrospectTemplate" checked="checked" value="${retrospectTemplate}"></td></tr>
                        </c:when>
                        <c:otherwise>
                            <tr><td style="text-align: center"><input type="radio" name="retrospectTemplate" value="${retrospectTemplate}"></td></tr>
                        </c:otherwise>
                    </c:choose>
                </table>
            </c:forEach>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>
    </div>
</form>
<script src="${ctxStatic}/customized/retrospectTemplateForm.js"></script>
</body>
</html>