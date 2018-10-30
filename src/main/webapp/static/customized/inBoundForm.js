//对应于inBoundForm.jsp页面的js
var copiedTr, rowNum;
var InBoundForm = {};
InBoundForm.formValidateAndSelectProduct = {
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
                    if (InBoundForm.formValidateAndSelectProduct.Events.customizedValidteForm()) {
                        loading('正在提交，请稍等...');
                        form.submit();
                    } else {
                        alertx("请输入有效信息！");
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
                        if ("" == $(input).val() || 0 == $(input).val().trim().indexOf("0")) {
                            isValidate = false;
                            $(input).css("border-color", "red");
                        } else {
                            $(input).css("border-color", "#ccc");
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
            return isValidate;
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
                    InBoundForm.formValidateAndSelectProduct.Events.bindClickEvent(currentRow);
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
                                InBoundForm.formValidateAndSelectProduct.Events.bindClickEvent(currentRow);
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
                var findBody = $('#contentTable').find("tbody input[id='prodId']");
                $.each(findBody, function (i, item) {
                    if ($(item).val() != "请选择" && $(item).val() == prodId) {
                        isAllowAdd = false;
                        alertx("该产品已经添加到计划中，请勿重复添加！");
                    }
                });

                if (isAllowAdd) {
                    $.each(currentRow, function (i, item) {
                        if ($(item).attr('id') == 'prodName') {
                            $(item).val(td[2].innerText);
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
            rowNum = rowNum + 1;
            var copyTr = copiedTr;
            copyTr = '<tr>' + copyTr.split("0").join(rowNum) + '</tr>';
            if ($('#contentTable').find('tbody tr:last').length > 0) {
                $('#contentTable').find('tbody tr:last').after(copyTr);
            } else {
                $('#contentTable').find('tbody').append(copyTr);
            }
        },

        deleteLink: function () {
            $(this).parent().parent().remove();
        }
    }
};

$(document).ready(function () {
    rowNum = 0;
    copiedTr = $('#contentTable').find('tbody tr:first').html();
    InBoundForm.formValidateAndSelectProduct.bindAll();
});