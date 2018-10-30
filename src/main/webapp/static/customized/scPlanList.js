//对应于scPlanList.jsp页面的js
var SCPlanList = {};
SCPlanList.selectProduct = {
    bindAll: function () {
        this.Events.selectProduct();
        this.Events.submitAllPlans();
        this.Events.itemCheck();
    },

    Events: {
        selectProduct: function () {
            $("#selectAllProduct").click(function () {
                if ($(this).is(":checked")) {
                    $("#contentBody").find(":checkbox").each(function () {
                        $(this).attr("checked", true);
                    });
                }
                else {
                    $("#contentBody").find(":checkbox").each(function () {
                        $(this).attr("checked", false);
                    });
                }
            });
        },

        itemCheck: function () {
            $(".itemCheck").click(function () {
                var isAllChecked = true;
                $("#contentBody").find(":checkbox").each(function () {
                    if (!$(this).is(":checked")) {
                        isAllChecked = false;
                    }
                });

                $("#selectAllProduct").attr("checked", isAllChecked);
            });
        },

        submitAllPlans: function () {
            $('#btnSubmitPlans').click(function () {
                var plans = "";
                $("#contentBody").find(":checkbox").each(function () {
                    if ($(this).is(":checked")) {
                        if (plans == "") {
                            plans += $(this).parent().find("span").html();
                        }
                        else {
                            plans += "," + $(this).parent().find("span").html();
                        }
                    }
                });

                if (plans.length > 0) {
                    $.ajax({
                        url: $('#submitPlanUrl').text(),
                        type: "POST",
                        cache: false,
                        data: {
                            "planIds": plans
                        },
                        dataType: 'json',
                        success: function (data) {
                            if (data) {
                                $("#btnSubmit").click();
                            }
                            else {
                                showTip("提交失败！");
                            }
                        },
                        error: function (data) {
                            alertx("提交失败!");
                        }
                    });
                }
                else {
                    showTip("请选择需要提交的任务计划！");
                }
            });
        }
    }
};

$(document).ready(function () {
    SCPlanList.selectProduct.bindAll();
});







