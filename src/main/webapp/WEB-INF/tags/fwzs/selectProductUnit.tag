<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="prodUnits" type="java.util.List" required="false" description="选择产品包装单位"%>

<div id="chooseProductUnit" class="hidden">
    <div class="colotBoxTitle colorBoxTitle" style="background-color: #f9f9f9;line-height: 44px;height:44px; margin-top: 0px; margin-bottom: 15px; vertical-align: middle;">选择产品包装单位</div>
    <table id="productItems" class="table table-striped table-bordered table-condensed " style="width: 640px;margin-left: 20px; margin-top: 0px;">
        <thead>
        <tr>
            <th>名称</th>
            <th>说明</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${prodUnits}" var="item" varStatus="status">
            <tr class="itemRow">
                <td>${item.label}</td>
                <td>${item.description}</td>
                <td class="hidden">${item.value}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>