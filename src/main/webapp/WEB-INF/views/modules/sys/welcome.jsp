<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>welecome</title>
	<meta name="decorator" content="default"/>

<style type="text/css">
.mydiv{   
   width:300px;  
   height:200px;  
   position:absolute;  
   left:50%;  
   top:50%;  
   margin:-100px 0 0 -150px   
}  
</style>
</head>
<body > 
	<div class="mydiv">
	<font style="font-size: 18px;">
	欢迎使用渭南臻诚官网管理后台
	</font>
	</div>
	
	
	<%-- <form>
	34534
		<div class="form-actions">
			<shiro:hasPermission name="sys:area:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form> --%>
</body>
</html>