//对应于retrospectSetForm.jsp页面的js
var RetrospectSetForm = {};
RetrospectSetForm.selectProduct = {
    bindAll: function () {
        this.Events.retrospectSet();
        this.Events.getProperty();
    },

    Events: {
        retrospectSet: function () {
            $('#btnSubmit').click(function () {
                var productId = $("#productId").val();
                var id = $("#id").val();
                if (productId == '') {
                    alert("请选择产品!");
                    return false;
                }

                //判断至少写了一项
                var checkedNum = $("input[name='subBox']:checked").length;
                if (checkedNum == 0) {
                    alert("请至少选择一项属性!");
                    return false;
                }

                if (confirm("确定提交所选属性?")) {
                    var checkedList = new Array();
                    $("input[name='subBox']:checked").each(function () {
                        checkedList.push($(this).val());
                    });
                    loading('正在提交，请稍等...');
                    $.ajax({
                        type: "POST",
                        url: $('#saveURL').text(),
                        data: {
                            "pId": checkedList.toString(),
                            "productId": productId,
                            "id": id,
                        },
                        datatype: "html",
                        success: function (data) {
                            closeLoading();
                            if (data) {
                                showTip("设置成功！");
                            } else {
                                showTip("设置失败！");
                            }
                            /* 	$("[name='checkbox2']:checkbox").attr("checked", false);
                                alertx("设置成功！");
                                location.reload();//页面刷新   */
                        },
                        error: function (data) {

                            alertx("提交失败!");
                        }
                    });
                }
            });
        },

        /**
         * 根据产品获取相关属性
         */
        getProperty: function () {
            $('#productId').click(function () {
                var productId = $("#productId").val();
                if (productId == '') {
                    alert("请选择产品!");
                    return false;
                }
                loading('正在提交，请稍等...');
                $("#p").html("");//清空info内容
                $.ajax({
                    type: "POST",
                    url:  $('#selectProductURL').text(),
                    data: {
                        "productId": productId
                    },
                    datatype: "json",
                    success: function (data) {
                        closeLoading();
                        var ret = data.fwmRetrospectSet;
                        $.each(
                            data.propertys,
                            function (i, item) {
                                if (ret != '') {
                                    if (ret.property.indexOf(item.columnName) != -1) {
                                        $("#p")
                                            .append(
                                                "<input type='checkbox' name='subBox' value='" + item.columnName + "' checked ='true'>"
                                                + item.columnComment
                                                + "</input></br>");
                                    } else {
                                        $("#p")
                                            .append(
                                                "<input type='checkbox' name='subBox' value='" + item.columnName + "' >"
                                                + item.columnComment
                                                + "</input></br>");
                                    }
                                } else {
                                    $("#p")
                                        .append(
                                            "<input type='checkbox' name='subBox' value='" + item.columnName + "' >"
                                            + item.columnComment
                                            + "</input></br>");
                                }
                            });
                        //
                        if (ret != '') {
                            $("#id").val(ret.id);
                        }
                    },
                    error: function (data) {
                        // art.dialog.tips('激活失败!');
                    }
                });
            });
        }
    }
};

$(document).ready(function () {
    RetrospectSetForm.selectProduct.bindAll();
});