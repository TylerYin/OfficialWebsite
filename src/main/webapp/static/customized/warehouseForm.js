//对应于warehouseForm.jsp页面的js
var WarehouseForm = {
    bindAll : function () {
        this.Events.validateForm();
    },

    Events : {
        validateForm: function () {
            $.validator.addMethod('phone' ,function(value, element){
                var phoneReg = /^1\d{10}$|^0\d{2,3}-?\d{7,8}$/;
                return this.optional(element) || (phoneReg.test(value));
            }, '请输入正确的电话');

            $("#inputForm").validate({
                submitHandler: function (form) {
                    if (WarehouseForm.Events.customizedValidteForm()) {
                        if (""!=$("#id").val() && $("#parentWarehouse").val() == $("#id").val()) {
                            alertx("当前仓库和上级仓库不能相同！")
                        } else {
                            loading('正在提交，请稍等...');
                            form.submit();
                        }
                    } else {
                        alertx("请选择上级仓库！")
                    }
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
                },
                rules : {
                    "phone" :{
                        required :true,
                        "phone" : true
                    },
                    size :{
                        required: true,
                        number :true,
                        range : [1,1000000000]
                    },
                    volume :{
                        required: true,
                        number :true,
                        range : [1,1000000000]
                    }
                }
            });
        },

        customizedValidteForm: function () {
            var grade = $("#grade").val();
            var parentWarehouse = $("#parentWarehouseName").val();
            if (grade != "1" && parentWarehouse == "") {
                return false;
            }
            return true;
        }
    }
};

$(document).ready(function() {
    WarehouseForm.bindAll();
});