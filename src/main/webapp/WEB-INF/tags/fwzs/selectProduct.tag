<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="bsProducts" type="java.util.List" required="true" description="选择产品" %>

<div class="productInfoClass">
    <div class="colotBoxTitle" style="margin-top: 0px;background-color: #f9f9f9;line-height: 44px;height:44px; vertical-align: middle;">选择产品</div>
    <div style="margin-left: 20px;margin-top: 15px;margin-bottom: 5px;">
        <label>产品名称：</label>
        <input name="filterProdName" id="filterProdName" maxlength="150" type="text" class="input-medium"/>
        &nbsp;&nbsp;
        <label>产品规格：</label>
        <input name="filterProdSpec" id="filterProdSpec" maxlength="150" type="text" class="input-medium"/>
    </div>

    <hr style="height:1px;border:none;border-top:1px solid #555555;width: 98%;margin-left: 1%; margin-bottom:15px; margin-top: 0px;"/>

    <div id="productDiv">
        <table id="productItems" class="table table-bordered table-condensed" style="width: 1360px;margin-left: 20px;">
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
    </div>
</div>

<div id="findProductsURL" class="hidden">${ctx}/fwzs/bsProduct/findProducts</div>