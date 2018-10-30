//对应于生产线管理scLinesForm.jsp页面的js
var SCLinesForm = {};
var originalLineNoVal = $("#lineNo").val();
SCLinesForm.formValidateAndSelectProduct = {
    bindAll: function () {
        $("#lineNo").focus();
        $(document).on('blur', '#lineNo', this.Events.validateLineNo);
        this.Events.validateAndSubmitLineNoForm();
    },

    Events: {
        validateAndSubmitLineNoForm: function () {
            $("#scLinesInputForm").validate({
                submitHandler: function (form) {
                    if ($('#lineNoValidateError').css('display') == 'none') {
                        loading('正在提交，请稍等...');
                        form.submit();
                    }
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("请输入有效信息！");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        },

        validateLineNo: function () {
            $("#lineNo").blur(function () {
                if ($('#lineNo').val() != "" && $("#lineNo").val() != originalLineNoVal) {
                    $.ajax({
                        url: ctx + '/fwzs/scLines/validateCode/' + $('#lineNo').val(),
                        type: "GET",
                        cache: false,
                        dataType: 'json',
                        success: function (data) {
                            if (!data) {
                                $("#lineNo").focus();
                                $('#lineNoValidateError').removeClass('hidden').addClass('show');
                            }
                            else {
                                $('#lineNoValidateError').removeClass('show').addClass('hidden');
                            }
                        }
                    });
                }
            });
        }
    }
};

$(document).ready(function() {
    SCLinesForm.formValidateAndSelectProduct.bindAll();
});