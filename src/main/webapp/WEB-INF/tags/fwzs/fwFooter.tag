<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="qrCode" type="java.lang.String" required="true" description="产品防伪码" %>
<%@ attribute name="isActiveTraceInfoLogo" type="java.lang.Boolean" required="true" description="溯源信息" %>
<%@ attribute name="isActiveCompanyInfoLogo" type="java.lang.Boolean" required="true" description="公司简介" %>
<%@ attribute name="isActiveContactInfoLogo" type="java.lang.Boolean" required="true" description="联系我们" %>

<footer class="navbar-fixed-bottom">
    <div class="container">
        <div class="row">
            <div class="linknavfoot text-center">
                <ul>
                    <li class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <c:choose>
                            <c:when test="${isActiveTraceInfoLogo}">
                                <a href="${ctx}/phone/traceInfo?qrCode=${qrCode}" class="green">
                                    <img src="${ctxStatic}/pesticide/images/syxx_g.svg" class="img-responsive center-block"/>溯源信息
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="${ctx}/phone/traceInfo?qrCode=${qrCode}">
                                    <img src="${ctxStatic}/pesticide/images/syxx.svg" class="img-responsive center-block"/>溯源信息
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <c:choose>
                            <c:when test="${isActiveCompanyInfoLogo}">
                                <a href="${ctx}/phone/company/introduction?qrCode=${qrCode}" class="green">
                                    <img src="${ctxStatic}/pesticide/images/gsjj_g.svg" class="img-responsive center-block"/>公司简介
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="${ctx}/phone/company/introduction?qrCode=${qrCode}">
                                    <img src="${ctxStatic}/pesticide/images/gsjj.svg" class="img-responsive center-block"/>公司简介
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <c:choose>
                            <c:when test="${isActiveContactInfoLogo}">
                                <a href="${ctx}/phone/company/contactUs?qrCode=${qrCode}" class="green">
                                    <img src="${ctxStatic}/pesticide/images/lxwm_g.svg" class="img-responsive center-block"/>联系我们
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="${ctx}/phone/company/contactUs?qrCode=${qrCode}">
                                    <img src="${ctxStatic}/pesticide/images/lxwm.svg" class="img-responsive center-block"/>联系我们
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</footer>