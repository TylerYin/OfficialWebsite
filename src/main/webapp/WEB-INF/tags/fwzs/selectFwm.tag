<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<div class="productInfoClass">
    <div class="colotBoxTitle" style="background-color: #f9f9f9;line-height: 44px;height:44px; margin-top: 0px; vertical-align: middle;">选择码文件</div>
    <table id="fwmFileItems" class="table table-striped table-bordered table-condensed" style="margin-top: 15px;width: 1360px;margin-left: 20px;margin-bottom: 20px;">
        <thead>
            <tr>
                <th>文件号：</th>
                <th>产品编码：</th>
                <th>产品名称：</th>
                <th>产品规格：</th>
                <th>生码数量：</th>
                <th>生码日期：</th>
            </tr>
        </thead>
        <tbody id="fwmFileListBody">

        </tbody>
    </table>
</div>