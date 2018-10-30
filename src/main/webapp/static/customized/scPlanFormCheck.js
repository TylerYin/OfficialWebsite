//对应于scPlanListCheck.jsp页面的js
var SCPlanFormCheck = {};
SCPlanFormCheck = {
    bindAll: function () {
        this.Events.validateForm();
        $(document).on('change', '#scPlanStatus', this.Events.changeScPlanStatus);
    },

    Events: {
        validateForm: function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    var result = SCPlanFormCheck.Events.customizedValidteForm();
                    if ("notChooseReport" == result) {
                        alertx("请上传有效的检验报告单！");
                    } else if ("reasonEmpty" == result) {
                        alertx("请输入检验没有通过的原因！");
                    } else {
                        loading('正在提交，请稍等...');
                        form.submit();
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
                }
            });
        },

        customizedValidteForm: function () {
            var verifyResult = "valid";
            if ("6" == $("#scPlanStatus").val()) {
                $("#planContent").find("tr").each(function (i, row) {
                    $(row).find("input").each(function (k, input) {
                        var attr = $(input).attr("id");
                        if (undefined != attr && -1 != attr.indexOf("checkPlanUrl")) {
                            if ("" == $(input).val().trim()) {
                                verifyResult = "notChooseReport";
                            }
                        }
                    });
                });
            } else {
                if ("" == $("#qcNotPassReason").val().trim()) {
                    $("#qcNotPassReason").css("border-color", "red");
                    verifyResult = "reasonEmpty";
                } else {
                    $("#qcNotPassReason").css("border-color", "#ccc");
                }
            }
            return verifyResult;
        },

        changeScPlanStatus: function () {
            var qcResult = $(this).val();
            if ("7" == qcResult) {
                $("#qcNotPassReason").text("");
                $("#qcNotPass").css("display", "block");
            } else {
                $("#qcNotPass").css("display", "none");
            }
        }
    }
};

$(document).ready(function () {
    SCPlanFormCheck.bindAll();
});