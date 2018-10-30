//对应于fwmFileForm.jsp页面的js
var FwmFileForm = {};
FwmFileForm.formValidateAndSelectProduct = {
    bindAll : function () {
        this.Events.validateForm();
        $(document).on('click','#prodNo',this.Events.changeInfo);
    },

    Events : {
        validateForm: function () {
            $.validator.addMethod('bsProduct.id' ,function(value, element){
                var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
                return this.optional(element) || (!reg.test(value));
            }, '必填信息');

            $("#inputForm").validate({
                submitHandler: function (form) {
                    if(FwmFileForm.formValidateAndSelectProduct.Events.customizedValidteForm()){
                        loading('正在提交，请稍等...');
                        form.submit();
                    }else{
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
                },
                rules : {
                    "bsProduct.id" :{
                        required :true,
                        "bsProduct.id" : true
                    },
                    codeNumber :{
                        number :true,
                        range : [1,500000]
                    }
                }
            });
        },

        changeInfo: function () {
            $.colorbox({
                html: $(".productInfoClass").html(),
                width: "1400",
                close: "关闭",
                opacity: "0.4",
                overlayClose: true,
                onComplete: function () {
                    FwmFileForm.formValidateAndSelectProduct.Events.bindClickEvent();
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
                                FwmFileForm.formValidateAndSelectProduct.Events.bindClickEvent();
                            }
                        });
                    });
                    $("#cboxContent tbody").find("tr:odd").each(function (i, tr) {
                        $(tr).css("background-color", "#F5F5F5");
                    });

                    $("#cboxContent tbody").find("tr:even").each(function (i, tr) {
                        $(tr).css("background-color", "white");
                    });
                    $("#cboxContent").find("#filterProdName").focus();
                }
            });
        },

        bindClickEvent: function () {
            $('#cboxClose').hide();
            $("#productItems tr").click(function () {
                var td = $(this).find("td");
                $("#prodNo").val(td[0].innerText);
                $("#pesticideName").val(td[1].innerText);
                $("#prodName").val(td[2].innerText);
                $("#specDesc").val(td[3].innerText);
                $("#prodId").val(td[7].innerText);
                $("#cboxClose").click();
            });
        },

        customizedValidteForm : function () {
            var isValidate = true;
            $("#inputForm :input").each(function(i, input){
                var attr = $(input).attr("id");
                if("prodId"==attr){
                    if("请选择"==$(input).val()){
                        isValidate = false;
                        $(input).css("border-color","red");
                    }else{
                        $(input).css("border-color","#ccc");
                    }
                }

                if("codeNumber"==attr){
                    if(""==$(input).val()){
                        isValidate = false;
                        $(input).css("border-color","red");
                    } else {
                        var codeNumberReg = /^\d{1,6}$/;
                        var testResult = codeNumberReg.test($(input).val());
                        if (testResult) {
                            $(input).css("border-color", "#ccc");
                        } else {
                            isValidate = false;
                            $(input).css("border-color", "red");
                        }
                    }
                }
            });
            return isValidate;
        }
    }
};

$(document).ready(function() {
    FwmFileForm.formValidateAndSelectProduct.bindAll();
});