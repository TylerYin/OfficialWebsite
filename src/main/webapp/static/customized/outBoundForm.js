//对应于outBoundForm.jsp页面的js
var copiedTr, rowNum, indirectSellingStatus;
var OutBoundForm = {};
OutBoundForm.formValidateAndSelectProduct = {
    bindAll: function () {
        this.Events.validateForm();
        $(document).on('click', '#prodName', this.Events.changeInfo);
        $(document).on('click', '.deleteLink', this.Events.deleteLink);
        $(document).on('click', '#btnAddNewProduct', this.Events.addNewProduct);
        $(document).on('click', '#indirectSelling', this.Events.displayParentDealer);
    },

    Events: {
        validateForm: function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    var validResult = OutBoundForm.formValidateAndSelectProduct.Events.customizedValidteForm();
                    if ("valid" == validResult) {
                        loading('正在提交，请稍等...');
                        form.submit();
                    } else if ("inValid" == validResult) {
                        alertx("请输入有效信息！");
                    } else if ("planNumberEmpty" == validResult) {
                        alertx("当前选择的产品缺货，请重新选择！");
                    } else if ("planNumberInconsistent" == validResult) {
                        alertx("计划数量不一致！");
                    } else if ("warehouseInconsistent" == validResult) {
                        alertx("仓库不一致！");
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

            if ("1" == indirectSellingStatus) {
                if ("" == $("#firstDealerName").val() || "请选择" == $("#firstDealerName").val()) {
                    $("#firstDealerName").css("border-color", "red");
                } else {
                    $("#firstDealerName").css("border-color", "#ccc");
                }

                if ("" == $("#secondDealerName").val() || "请选择" == $("#secondDealerName").val()) {
                    $("#secondDealerName").css("border-color", "red");
                } else {
                    $("#secondDealerName").css("border-color", "#ccc");
                }
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
                                var planNumberReg = /^[1-9]\d{0,8}$/;
                                var testResult = planNumberReg.test($(input).val());
                                if (testResult) {
                                    $(input).css("border-color", "#ccc");
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

                    if (-1 != attr.indexOf("warehouseName")) {
                        if ("请选择" == $(input).val()) {
                            isValidate = false;
                            $(input).css("border-color", "red");
                        } else {
                            $(input).css("border-color", "#ccc");
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
                var warehouses = new Array();

                $("#planContent").find("tr").each(function (i, row) {
                    $(row).find("input").each(function (k, input) {
                        if ("hidden" != $(row).attr("class")) {
                            var attr = $(input).attr("id");
                            if (-1 != attr.indexOf("planNumber")) {
                                planNumbers[i] = $(input).val();
                            }
                            if (-1 != attr.indexOf("warehouse")) {
                                warehouses[i] = $(input).val();
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

                if (warehouses.length > 1) {
                    for (var i = 1; i < warehouses.length; i++) {
                        if (warehouses[0] != warehouses[i]) {
                            return "warehouseInconsistent";
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
                    OutBoundForm.formValidateAndSelectProduct.Events.bindClickEvent(currentRow);
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
                                OutBoundForm.formValidateAndSelectProduct.Events.bindClickEvent(currentRow);
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
                        var warehouseId = $("#warehouse" + currentIndex).val();
                        var planNumberId = "planNumber" + currentIndex;
                        OutBoundForm.formValidateAndSelectProduct.Events.findStockLevel(prodId, warehouseId, planNumberId);
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

        findStockLevel: function (prodId, warehouseId, planNumberId) {
            if (prodId != undefined && prodId != "" && warehouseId != undefined && warehouseId != "") {
                $.ajax({
                    url: $('#getLatestStockLevelURL').text(),
                    type: "POST",
                    data: {
                        prodId: prodId,
                        warehouseId: warehouseId
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
        },

        displayParentDealer: function () {
            var dealerLevel = $("#dealerLevel").text();

            $("input[name='indirectSelling']").each(function (i, item) {
                if ("checked" == $(item).attr("checked")) {
                    indirectSellingStatus = $(item).val();
                }
            });

            if ("1" == indirectSellingStatus) {
                if ("1" == dealerLevel) {
                    $("#firstDealerDiv").css("display", "block");
                    $("#secondDealerDiv").css("display", "none");

                    $("#secondDealer").val("");
                    $("#secondDealerName").val("请选择");
                } else if ("2" == dealerLevel) {
                    $("#firstDealerDiv").css("display", "block");
                    $("#secondDealerDiv").css("display", "block");
                } else {
                    $("#firstDealerDiv").css("display", "none");
                    $("#secondDealerDiv").css("display", "none");

                    $("#firstDealer").val("");
                    $("#firstDealerName").val("请选择");
                    $("#secondDealer").val("");
                    $("#secondDealerName").val("请选择");
                }
            } else {
                $("#firstDealerDiv").css("display", "none");
                $("#secondDealerDiv").css("display", "none");

                $("#firstDealer").val("");
                $("#firstDealerName").val("请选择");
                $("#secondDealer").val("");
                $("#secondDealerName").val("请选择");
            }
        }
    }
};

$(document).ready(function () {
    rowNum = 0;
    copiedTr = $('#contentTable').find('tbody tr:first');
    OutBoundForm.formValidateAndSelectProduct.bindAll();
});