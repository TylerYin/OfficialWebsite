<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>${fns:getConfig('productName')} 登录</title>
    <meta name="decorator" content="blank"/>
    <link rel="stylesheet" href="${ctxStatic}/login/css/login.css" />
    <script type="text/javascript">
        // 如果在框架或在对话框中，则弹出提示并跳转到首页
        if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
            alert('未登录或登录超时。请重新登录，谢谢！');
            top.location = "${ctx}";
        }
    </script>
</head>
<body>
<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
<div id="container">
    <section class="asideL"><img src="${ctxStatic}/login/images/imgl.png"></section>
    <section class="asideR">
        <div class="signlink">
            <%--<a href="#">注册</a>--%>
        </div>
        <div class="main">
            <div class="formtitle"><img src="${ctxStatic}/login/images/title.png"></div>
            <div class="formbox">
                <form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
                    <ul>
                        <li class="li3">
                            <label>登录名</label>
                            <input id="username" name="username" type="text" class="inputText" style="width: 293px; height: 41px;padding: 0 0 0 5px;" value="${username}">
                            <c:if test="${message eq '用户名不正确'}">
                                <span for="username" class="error">${message}</span>
                            </c:if>
                        </li>
                        <li class="li3">
                            <label>密码</label>
                            <input id="password" name="password" type="password" class="inputText" style="width: 293px; height: 41px;padding: 0 0 0 5px;">
                            <c:if test="${message eq '密码不正确'}">
                                <span for="password" class="error">${message}</span>
                            </c:if>
                        </li>
                        <c:if test="${isValidateCodeLogin}">
                            <li id="dispalybox">
                                <label for="validateCode">验证码</label>
                                &nbsp;<sys:validateCodeForLogin name="validateCode" inputCssStyle="margin-bottom:10px;"/>
                            </li>
                        </c:if>
                        <li class="li33"><label>&nbsp;</label><input id="rememberMe" name="rememberMe" type="checkbox" ${rememberMe ? 'checked' : ''} class="checkb"><em>记住我（公共场所慎用）</em>
                            <%--<a href="#">忘记密码？</a>--%>
                        </li>
                        <li><input type="submit" value="登录" class="subbtton"></li>
                    </ul>
                </form>
            </div>
        </div>
    </section>
</div>
<div id="isValidateCodeLogin" style="display: none">${isValidateCodeLogin}</div>
<div id="validateCodeURL" style="display: none">${pageContext.request.contextPath}/servlet/validateCodeServlet</div>
<footer id="footer">Copyright &copy; 2015-${fns:getConfig('copyrightYear')} - Powered By 渭南臻诚科技有限责任公司 ${fns:getConfig('version')} </footer>
<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/customized/login.js" type="text/javascript"></script>
</body>
</html>