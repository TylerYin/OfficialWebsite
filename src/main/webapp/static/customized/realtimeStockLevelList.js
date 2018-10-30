//对应于realtimeStockLevelList.jsp页面的js
var RealtimeStockLevelList = {
    bindAll: function () {
        InputForm.formValidate.Events.changeProduct();
    },

    Events: {
        delete: function (deleteKeys) {
            var id = deleteKeys.split(",")[0];
            var warehouseName = deleteKeys.split(",")[1];
            var prodName = deleteKeys.split(",")[2];
            window.location.href = $("#ctxId").text() + "/fwzs/realtimeStockQuery/delete?id=" + id + "&realtimeStockLevel.id="
                + phone + "&warehouse.name=" + warehouseName + "&warehouse.bsProduct.prodName=" + prodName;
        }
    }
};

$(document).ready(function () {
    RealtimeStockLevelList.bindAll();
});