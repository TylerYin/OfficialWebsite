//对应于scPlanForm.jsp页面的js
var copiedTr, rowNum;
var SCPlanForm = {};
SCPlanForm.formValidateAndSelectProduct = {
    bindAll: function () {
        this.Events.validateForm();
        $(document).on('click', '#prodNo', this.Events.changeInfo);
        $(document).on('click', '.deleteLink', this.Events.deleteLink);
        $(document).on('click', '#btnAddNewProduct', this.Events.addNewProduct);
    },

    Events: {
        validateForm: function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    var validResult = SCPlanForm.formValidateAndSelectProduct.Events.customizedValidteForm();
                    if ("valid" == validResult) {
                        loading('正在提交，请稍等...');
                        form.submit();
                    } else {
                        if ("planNumberInvalid" == validResult) {
                            alertx("请输入有效数字！");
                        } else if ("dateInvalid" == validResult) {
                            alertx("有效期必须在生产日期之后！");
                        } else if ("indateInconsistent" == validResult) {
                            alertx("有效期不一致！");
                        } else if ("planNumberInconsistent" == validResult) {
                            alertx("计划数量不一致！");
                        } else if ("warehouseInconsistent" == validResult) {
                            alertx("仓库不一致！");
                        } else {
                            alertx("请输入有效信息！");
                        }
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
            var isValid = true;
            var isPlanNumberValid = true;
            var isDateValid = true;
            if ("" == $('#scPlanNo').val()) {
                isValid = false;
                $('#scPlanNo').css("border-color", "red");
            } else {
                $('#scPlanNo').css("border-color", "#ccc");
            }

            if ("" == $('#madeDate').val()) {
                isValid = false;
                $('#madeDate').css("border-color", "red");
            } else {
                $('#madeDate').css("border-color", "#ccc");
            }

            $("#planContent").find("tr").each(function (i, row) {
                $(row).find("input").each(function (k, input) {
                    var attr = $(input).attr("id");
                    if ("prodNo" == attr) {
                        if ("请选择" == $(input).val()) {
                            isValid = false;
                            $(input).css("border-color", "red");
                        } else {
                            $(input).css("border-color", "#ccc");
                        }
                    }

                    if ("pesticideName" == attr) {
                        if ("" == $(input).val()) {
                            isValid = false;
                            $(input).css("border-color", "red");
                        } else {
                            $(input).css("border-color", "#ccc");
                        }
                    }

                    if ("prodName" == attr) {
                        if ("" == $(input).val()) {
                            isValid = false;
                            $(input).css("border-color", "red");
                        } else {
                            $(input).css("border-color", "#ccc");
                        }
                    }

                    if ("specDesc" == attr) {
                        if ("" == $(input).val()) {
                            isValid = false;
                            $(input).css("border-color", "red");
                        } else {
                            $(input).css("border-color", "#ccc");
                        }
                    }

                    if ($(input).attr("id").indexOf('batchNo') != -1) {
                        if ("" == $(input).val()) {
                            isValid = false;
                            $(input).css("border-color", "red");
                        } else {
                            $(input).css("border-color", "#ccc");
                        }
                    }

                    if ("indate" == attr) {
                        if ("" == $(input).val()) {
                            isValid = false;
                            $(input).css("border-color", "red");
                        } else {
                            if ("" != $('#madeDate').val()) {
                                var madeDate = new Date(Date.parse($('#madeDate').val()));
                                var indate = new Date(Date.parse($(input).val()));
                                if (indate <= madeDate) {
                                    isDateValid = false;
                                    $(input).css("border-color", "red");
                                } else {
                                    $(input).css("border-color", "#ccc");
                                }
                            } else {
                                $(input).css("border-color", "#ccc");
                            }
                        }
                    }

                    if ("planNumber" == attr) {
                        if ("" == $(input).val()) {
                            isValid = false;
                            $(input).css("border-color", "red");
                        } else {
                            var planNumberReg = /^[1-9]\d{0,8}$/;
                            var testResult = planNumberReg.test($(input).val());
                            if (testResult) {
                                $(input).css("border-color", "#ccc");
                            } else {
                                isPlanNumberValid = false;
                                $(input).css("border-color", "red");
                            }
                        }
                    }
                });
            });

            if (!isPlanNumberValid) {
                return "planNumberInvalid";
            } else if (!isDateValid) {
                return "dateInvalid";
            } else if (!isValid) {
                return "invalid";
            } else {
                var indates = new Array();
                var planNumbers = new Array();
                var warehouses = new Array();

                $("#planContent").find("tr").each(function (i, row) {
                    $(row).find("input").each(function (k, input) {
                        if ("hidden" != $(row).attr("class")) {
                            var attr = $(input).attr("id");
                            if ("indate" == attr) {
                                indates[i] = $(input).val();
                            }
                            if ("planNumber" == attr) {
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

                if (indates.length > 1) {
                    for (var i = 1; i < indates.length; i++) {
                        if (indates[0] != indates[i]) {
                            return "indateInconsistent";
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
                    SCPlanForm.formValidateAndSelectProduct.Events.bindClickEvent(currentRow);
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
                                SCPlanForm.formValidateAndSelectProduct.Events.bindClickEvent(currentRow);
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
                var prodNo = td[0].innerText;

                var isAllowAdd = true;
                var findBody = $('#contentTable').find("tbody input[id='prodNo']");
                $.each(findBody, function (i, item) {
                    if ($(item).val() != "请选择" && $(item).val() == prodNo) {
                        isAllowAdd = false;
                        alertx("该产品已经添加到计划中，请勿重复添加！");
                    }
                });

                if (isAllowAdd) {
                    $.each(currentRow, function (i, item) {
                        if ($(item).attr('id') == 'prodNo') {
                            $(item).val(td[0].innerText);
                        }
                        if ($(item).attr('id') == 'pesticideName') {
                            $(item).val(td[1].innerText);
                        }
                        if ($(item).attr('id') == 'prodName') {
                            $(item).val(td[2].innerText);
                        }
                        if ($(item).attr('id') == 'specDesc') {
                            $(item).val(td[3].innerText);
                        }
                        if ($(item).attr('id') == 'prodId') {
                            $(item).val(td[7].innerText);
                        }
                    });
                    $("#cboxClose").click();
                }
            });
        },

        addNewProduct: function () {
            var isEditAdd = false;
            var copyTr = copiedTr.html();
            if ("" == copiedTr.find('td:last').html()) {
                rowNum = rowNum + 1;
                copyTr = '<tr>' + copyTr.split("0").join(rowNum) + '</tr>';
                copyTr = copyTr.replace("<td></td>", "<td><a class='deleteLink'>删除</a></td>");
            } else {
                if (0 == rowNum) {
                    rowNum = $('#contentTable').find('tbody tr').length;
                } else {
                    rowNum += 1;
                }

                isEditAdd = true;
                copyTr = '<tr>' + copiedTr.html().split("0").join(rowNum) + '</tr>';
            }

            if ($('#contentTable').find('tbody tr:last').length > 0) {
                $('#contentTable').find('tbody tr:last').after(copyTr);
            } else {
                $('#contentTable').find('tbody').append(copyTr);
            }

            if (isEditAdd) {
                var lastTr = $('#contentTable').find('tbody tr:last');
                lastTr.find("td:last").removeAttr("class").removeAttr("id").html("<a class='deleteLink'>删除</a>");
                $.each(lastTr.find('td input'), function (i, item) {
                    $(item).attr("value", "");
                });
            }

            $("#warehouse" + rowNum).val("");
            $("#warehouseName" + rowNum).val("请选择");
            SCPlanForm.formValidateAndSelectProduct.Events.getBatchNo(rowNum);
        },

        getBatchNo: function (rowNum) {
            $.ajax({
                url: $('#generateBatchNoUrl').text(),
                type: "GET",
                cache: false,
                dataType: 'text',
                success: function (data) {
                    $('#contentTable').find("input[id='bsProductList" + rowNum + ".batchNo']").val(data);
                }
            });
        },

        deleteLink: function () {
            $(this).parent().parent().remove();
        },

        deleteScPlan: function (deleteKeys) {
            var planId = deleteKeys.split(",")[0];
            var prodId = deleteKeys.split(",")[1];
            $("#SC" + prodId).parent().addClass("hidden");
            var deleteKey = $('#deleteKey').val() + planId + "#" + prodId + "&";
            $('#deleteKey').val(deleteKey);
        }
    }
};

$(document).ready(function () {
    rowNum = 0;
    copiedTr = $('#contentTable').find('tbody tr:first');
    SCPlanForm.formValidateAndSelectProduct.bindAll();
});