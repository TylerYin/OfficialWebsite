//对应于warehouseList.jsp页面的js
var WarehouseList = {
    bindAll : function () {
        this.Events.validateForm();
    },

    Events : {
        validateForm: function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("请输入有效信息！");
                    if (element.is(":checkbox")
                        || element.is(":radio")
                        || element.parent().is(
                            ".input-append")) {
                        error.appendTo(element.parent()
                            .parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        },

        delete: function (deleteKeys) {
            var id = deleteKeys.split(",")[0];
            var name = deleteKeys.split(",")[1];
            var phone = deleteKeys.split(",")[2];
            var areaId = deleteKeys.split(",")[3];
            var areaName = deleteKeys.split(",")[4];
            window.location.href = $("#ctxId").text() + "/fwzs/warehouse/delete?id=" + id + "&warehouse.name=" + name
                + "&warehouse.phone=" + phone + "&warehouse.salesArea.id=" + areaId + "&warehouse.salesArea.name=" + areaName;
        },
    }
};

$(document).ready(function() {
    WarehouseList.bindAll();
});