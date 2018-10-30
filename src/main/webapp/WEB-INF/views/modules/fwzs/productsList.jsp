<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<table id="productItems" class="table table-striped table-bordered table-condensed" style="width: 1360px;margin-left: 20px;">
    <thead>
    <tr>
        <th>产品编码</th>
        <th>农药名称</th>
        <th>产品名称</th>
        <th>产品规格</th>
        <th>单位</th>
        <th>证件所属公司全称</th>
        <th>生产企业全称</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${bsProducts}" var="item" varStatus="status">
        <tr class="itemRow">
            <td>${item.prodNo}</td>
            <td>${item.pesticideName}</td>
            <td>${item.prodName}</td>
            <td>${item.prodSpec.specDesc}</td>
            <td>${fns:getDictLabel(item.prodUnit, 'prod_unit', '')}</td>
            <td>${item.regCrop}</td>
            <td>${item.company.name}</td>
            <td class="hidden">${item.id}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
