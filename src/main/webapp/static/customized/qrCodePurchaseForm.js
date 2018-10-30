//对应于防伪码购买qrCodePurchaseForm.jsp页面的js
var QrCodePurchaseForm = {};
QrCodePurchaseForm = {
    bindAll: function () {
        this.Events.validateInputForm();
        this.Events.changePackageType();
    },

    Events: {
        validateInputForm: function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    if (QrCodePurchaseForm.Events.validateForm()) {
                        form.submit();
                    } else {
                        alertx("请输入有效数据！")
                    }
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("请输入有效信息！");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        },

        validateForm: function () {
            var packageSize = $("#packageSize").val();
            var packageType = $("#packageType").val();
            var amount = $("#amount").val();
            var validateCode = $("#validateCode").val();

            var isFormValid = "" != packageSize && "" != packageType && "" != amount && "" != validateCode;
            if (!isFormValid) {
                return false;
            } else {
                return true;
            }
        },

        changePackageType: function () {
            $("#packageType").change(function () {
                var packageType = $("#packageType").val();
                if ("" != packageType) {
                    var amount;
                    if ("1" == packageType) {
                        amount = 100;
                    } else if ("2" == packageType) {
                        amount = 500 / 0.02;
                    } else {
                        amount = 1000 / 0.02;
                    }
                    $("#amount").val(amount);
                }
            });
        }
    }
};

$(document).ready(function () {
    QrCodePurchaseForm.bindAll();
});