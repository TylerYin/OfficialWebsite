//对应于dealerBoundList.jsp页面的js
var dealerBoundIds;
var DealerBoundList = {
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
                dealerBoundIds = "";
                $("#contentBody").find(":checkbox").each(function () {
                    if ($(this).is(":checked")) {
                        if (dealerBoundIds == "") {
                            dealerBoundIds += $(this).parent().find("span").html();
                        }
                        else {
                            dealerBoundIds += "," + $(this).parent().find("span").html();
                        }
                    }
                });

                if (dealerBoundIds.length > 0) {
                    return confirmx('确认要删除该出库计划吗？', 'javascript:DealerBoundList.Events.deletePlans();');
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
                    "dealerBoundIds": dealerBoundIds
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
        },

        deliveryGoods: function (objId) {
            $.ajax({
                url: $('#validateRealNumberURL').text(),
                type: "POST",
                cache: false,
                data: {
                    "dealerBoundId": objId,
                    "status": $("#status").val()
                },
                dataType: 'json',
                success: function (data) {
                    if (data) {
                        $("#shipMessageTable").append("<tr style='display: none'><td colspan='2'><input name='id' type='text' value='" + objId + "'/></td></tr>")
                        $.colorbox({
                            html: $("#shipMessageArea").html(),
                            width: "600",
                            close: "关闭",
                            opacity: "0.4",
                            overlayClose: true,
                            onComplete: function () {
                                $('#cboxClose').hide();
                                $('#cboxContent').css("height", $('#cboxLoadedContent').css("height"));
                            }
                        });
                    }
                    else {
                        alertx("计划发货数量和实际发货数量不一致，不能发货！");
                    }
                }
            });
        }
    }
};

$(document).ready(function() {
    DealerBoundList.bindAll();
});