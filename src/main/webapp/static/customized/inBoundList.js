//对应于inBoundList.jsp页面的js
var inBoundIds;
var InBoundList = {
    bindAll : function () {
        this.Events.selectPlan();
        this.Events.itemCheck();
        InputForm.formValidate.Events.changeProduct();
        this.Events.btnDeleteClick();
    },

    Events : {
        selectPlan: function () {
            $("#selectAllPlan").click(function () {
                if ($(this).is(":checked")) {
                    $("#contentBody").find(":checkbox").each(function () {
                        if("disabled"!=$(this).attr("disabled")){
                            $(this).attr("checked", true);
                        }
                    });
                }
                else {
                    $("#contentBody").find(":checkbox").each(function () {
                        if("disabled"!=$(this).attr("disabled")) {
                            $(this).attr("checked", false);
                        }
                    });
                }
            });
        },

        itemCheck: function () {
            $(".itemCheck").click(function () {
                var isAllChecked = true;
                $("#contentBody").find(":checkbox").each(function () {
                    if ("disabled"!=$(this).attr("disabled") && !$(this).is(":checked")) {
                        isAllChecked = false;
                    }
                });

                $("#selectAllPlan").attr("checked", isAllChecked);
            });
        },

        btnDeleteClick: function () {
            $('#btnDelete').click(function () {
                inBoundIds = "";
                $("#contentBody").find(":checkbox").each(function () {
                    if ($(this).is(":checked")) {
                        if (inBoundIds == "") {
                            inBoundIds += $(this).parent().find("span").html();
                        }
                        else {
                            inBoundIds += "," + $(this).parent().find("span").html();
                        }
                    }
                });

                if (inBoundIds.length > 0) {
                    return confirmx('确认要删除该入库计划吗？', 'javascript:InBoundList.Events.deletePlans();');
                }
                else {
                    showTip("请选择需要删除的数据！");
                }
            });
        },

        deletePlans: function () {
            $.ajax({
                url: $('#submitPlanUrl').text(),
                type: "POST",
                cache: false,
                data: {
                    "inBoundIds": inBoundIds
                },
                dataType: 'json',
                success: function (data) {
                    if (data) {
                        $("#btnSubmit").click();
                        alertx("删除成功!");
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
    }
};

$(document).ready(function() {
    InBoundList.bindAll();
});