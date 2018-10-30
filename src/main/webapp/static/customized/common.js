//对应于Form页面验证的公共js
var InputForm = {};
InputForm.formValidate = {
    Events : {
        validateForm: function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
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

        changeProduct: function () {
            $("#prodName").click(function () {
                $.colorbox({
                    html: $(".productInfoClass").html(),
                    width: "1400",
                    close: "关闭",
                    opacity: "0.4",
                    overlayClose: true,
                    onComplete: function () {
                        InputForm.formValidate.Events.bindClickEvent();
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
                                    InputForm.formValidate.Events.bindClickEvent();
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
            });
        },

        bindClickEvent: function () {
            $('#cboxClose').hide();
            $("#productItems tr").click(function () {
                var td = $(this).find("td");
                $("#prodName").val(td[2].innerHTML);
                $("#prodId").val(td[7].innerHTML);
                $("#cboxClose").click();
            });
        },

        addProduct: function () {
            var currentRow = $(this).parent().parent().parent().parent().find("input");
            $.colorbox({
                html: $(".productInfoClass").html(),
                width: "1400",
                close: "关闭",
                opacity: "0.4",
                overlayClose: true,
                onComplete: function () {
                    $('#cboxClose').hide();
                    $('#cboxContent').css("height", $('#cboxLoadedContent').css("height"));
                    $("#productItems tr").click(function () {
                        var td = $(this).find("td");
                        var prodId = td[7].innerHTML;

                        var isAllowAdd = true;
                        var findBody = $('#contentTable').find("tbody input[id^='prodId']");
                        $.each(findBody, function (i, item) {
                            if($(item).val() != "请选择" && $(item).val() == prodId){
                                isAllowAdd = false;
                                alertx("该产品已经添加到计划中，请勿重复添加！");
                            }
                        });

                        if(isAllowAdd){
                            var currentItem = null;
                            $.each(currentRow, function (i, item) {
                                if ($(item).attr('id') == 'prodName') {
                                    $(item).val(td[2].innerHTML);
                                }
                                if (-1 != $(item).attr('id').indexOf('prodId')) {
                                    currentItem = $(item);
                                    $(item).val(td[7].innerHTML);
                                }
                            });
                            $("#cboxClose").click();

                            if (null != currentItem && undefined != currentItem) {
                                var currentIndex = currentItem.attr('id').substring(currentItem.attr('id').length - 1, currentItem.attr('id').length);
                                var prodId = currentItem.val();
                                var warehouseId = $("#warehouse" + currentIndex).val();
                                var planNumberId = "planNumber" + currentIndex;
                            }
                        }
                    });
                }
            });
        }
    }
};

$(function(){
    InputForm.formValidate.Events.validateForm();
});