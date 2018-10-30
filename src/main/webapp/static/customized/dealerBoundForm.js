//对应于dealerBoundForm.jsp页面的js
var copiedTr, rowNum;
var DealerBoundForm = {};
DealerBoundForm.formValidateAndSelectProduct = {
    bindAll: function () {
        this.Events.validateForm();
        $(document).on('click', '#prodName', this.Events.changeInfo);
        $(document).on('click', '.deleteLink', this.Events.deleteLink);
        $(document).on('click', '#btnAddNewProduct', this.Events.addNewProduct);
    },

    Events: {
        validateForm: function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    var validResult = DealerBoundForm.formValidateAndSelectProduct.Events.customizedValidteForm();
                    if ("valid" == validResult) {
                        loading('正在提交，请稍等...');
                        form.submit();
                    } else if ("inValid" == validResult) {
                        alertx("请输入有效信息！");
                    } else if ("planNumberEmpty" == validResult) {
                        alertx("当前选择的产品缺货，请重新选择！");
                    } else if ("planNumberInconsistent" == validResult) {
                        alertx("计划数量不一致！");
                    } else {
                        alertx("请输入有效的出库数量！");
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
            var isValidate = true;
            var isPlanNumberValid = true;
            var planNumberEmpty = false;
            if ("请选择" == $("#inDealerName").val()) {
                isValidate = false;
                $("#inDealerName").css("border-color", "red");
            } else {
                $("#inDealerName").css("border-color", "#ccc");
            }

            if ("请选择" == $("#outDealerName").val()) {
                isValidate = false;
                $("#outDealerName").css("border-color", "red");
            } else {
                $("#outDealerName").css("border-color", "#ccc");
            }

            $("#planContent").find("tr").each(function (i, row) {
                $(row).find("input").each(function (k, input) {
                    var attr = $(input).attr("id");
                    if ("prodName" == attr) {
                        if ("请选择" == $(input).val()) {
                            isValidate = false;
                            $(input).css("border-color", "red");
                        } else {
                            $(input).css("border-color", "#ccc");
                        }
                    }

                    if (-1 != attr.indexOf("planNumber")) {
                        if ("disabled" != $(input).attr("disabled")) {
                            if ("" == $(input).val()) {
                                isValidate = false;
                                $(input).css("border-color", "red");
                            } else {
                                planNumberEmpty = false;
                                var planNumberReg = /^[1-9]\d{0,8}$/;
                                var testResult = planNumberReg.test($(input).val());
                                if (testResult) {
                                    var maxStockLevel = $(input).siblings("span").text();
                                    if (undefined != maxStockLevel && "" != maxStockLevel) {
                                        if (parseInt(maxStockLevel.split(":")[1].trim()) < parseInt($(input).val())) {
                                            $(input).css("border-color", "red");
                                            isPlanNumberValid = false;
                                        } else {
                                            $(input).css("border-color", "#ccc");
                                        }
                                    } else {
                                        $(input).css("border-color", "red");
                                        planNumberEmpty = true;
                                    }
                                } else {
                                    isPlanNumberValid = false;
                                    $(input).css("border-color", "red");
                                }
                            }
                        } else {
                            $(input).css("border-color", "red");
                            planNumberEmpty = true;
                        }
                    }
                });
            });

            if (!isValidate) {
                return "inValid";
            } else if (planNumberEmpty) {
                return "planNumberEmpty";
            } else if (!isPlanNumberValid) {
                return "planNumberInValid";
            } else {
                var planNumbers = new Array();

                $("#planContent").find("tr").each(function (i, row) {
                    $(row).find("input").each(function (k, input) {
                        if ("hidden" != $(row).attr("class")) {
                            var attr = $(input).attr("id");
                            if (-1 != attr.indexOf("planNumber")) {
                                planNumbers[i] = $(input).val();
                            }
                        }
                    });
                });

                if (planNumbers.length > 1) {
                    for (var i = 1; i < planNumbers.length; i++) {
                        if (planNumbers[0] != planNumbers[i]) {
                            return "planNumberInconsistent";
                        }
                    }
                }

                return "valid";
            }
        },

        changeInfo: function () {
            var currentRow = $(this).parent().parent().parent().parent().find("input");
            $.colorbox({
                html: $(".productInfoClass").html(),
                width: "1400",
                close: "关闭",
                opacity: "0.4",
                overlayClose: true,
                onComplete: function () {
                    DealerBoundForm.formValidateAndSelectProduct.Events.bindClickEvent(currentRow);
                    $("#filterProdName, #filterProdSpec").live("keyup", function () {
                        $.ajax({
                            url: $('#findProductsURL').text(),
                            type: "POST",
                            cache: false,
                            async: false,
                            dataType: 'html',
                            data: {
                                "prodName": $("#cboxContent").find("#filterProdName").val(),
                                "prodSpec": $("#cboxContent").find("#filterProdSpec").val()
                            },
                            success: function (data) {
                                $("#cboxContent").find("#productDiv").html(data);
                                $('#cboxContent').css("height", $(data).find("tr").length * 29 + 160);
                                DealerBoundForm.formValidateAndSelectProduct.Events.bindClickEvent(currentRow);
                            }
                        });
                    });
                    $("#productItems tbody").find("tr:odd").each(function (i, tr) {
                        $(tr).css("background-color", "#F5F5F5");
                    });

                    $("#productItems tbody").find("tr:even").each(function (i, tr) {
                        $(tr).css("background-color", "white");
                    });
                    $("#cboxContent").find("#filterProdName").focus();
                }
            });
        },

        bindClickEvent: function (currentRow) {
            $('#cboxClose').hide();
            $("#productItems tr").click(function () {
                var td = $(this).find("td");
                var prodId = td[7].innerText;

                var isAllowAdd = true;
                var findBody = $('#contentTable').find("tbody input[id^='prodId']");
                $.each(findBody, function (i, item) {
                    if ($(item).val() != "请选择" && $(item).val() == prodId) {
                        isAllowAdd = false;
                        alertx("该产品已经添加到计划中，请勿重复添加！");
                    }
                });

                if (isAllowAdd) {
                    var currentItem = null;
                    $.each(currentRow, function (i, item) {
                        if ($(item).attr('id') == 'prodName') {
                            $(item).val(td[2].innerText);
                        }
                        if (-1 != $(item).attr('id').indexOf('prodId')) {
                            currentItem = $(item);
                            $(item).val(td[7].innerText);
                        }
                    });
                    $("#cboxClose").click();

                    if (null != currentItem && undefined != currentItem) {
                        var currentIndex = currentItem.attr('id').substring(currentItem.attr('id').length - 1, currentItem.attr('id').length);
                        var prodId = currentItem.val();
                        var planNumberId = "planNumber" + currentIndex;
                        DealerBoundForm.formValidateAndSelectProduct.Events.findStockLevel(prodId, planNumberId);
                    }
                }
            });
        },

        addNewProduct: function () {
            rowNum = rowNum + 1;
            var copyTr = copiedTr.clone(true);
            copyTr.find("td:last").html("<a class='deleteLink'>删除</a>");
            copyTr = copyTr.html();
            copyTr = '<tr>' + copyTr.split("0").join(rowNum) + '</tr>';
            if ($('#contentTable').find('tbody tr:last').length > 0) {
                $('#contentTable').find('tbody tr:last').after(copyTr);
            } else {
                $('#contentTable').find('tbody').append(copyTr);
            }
        },

        deleteLink: function () {
            $(this).parent().parent().remove();
        },

        findStockLevel: function (prodId, planNumberId) {
            if (prodId != undefined && prodId != "") {
                $.ajax({
                    url: $('#getLatestStockLevelURL').text(),
                    type: "POST",
                    data: {
                        prodId: prodId
                    },
                    cache: false,
                    dataType: 'json',
                    success: function (data) {
                        $("#" + planNumberId).siblings("span").remove();
                        if (parseInt(data) > 0) {
                            $("#" + planNumberId).removeAttr("disabled");
                            $("#" + planNumberId).after("&nbsp;<span style='color: red'>可用库存: " + data + "</span>");
                        } else {
                            $("#" + planNumberId).val("");
                            $("#" + planNumberId).attr("disabled", true);
                            $("#" + planNumberId).siblings("span").remove();
                            $("#" + planNumberId).after("&nbsp;<span style='color: red'>缺货 </span>");
                        }
                    }
                });
            }
        }
    }
};

$(document).ready(function () {
    rowNum = 0;
    copiedTr = $('#contentTable').find('tbody tr:first');
    DealerBoundForm.formValidateAndSelectProduct.bindAll();
});