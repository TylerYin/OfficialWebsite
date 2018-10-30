//对应于产品规格管理fwmSpecForm.jsp页面的js
var FwmSpecForm = {};
var originalSpecCodeVal = $("#specCode").val();
FwmSpecForm.formValidateAndSelectProduct = {
    bindAll: function () {
        $("#specCode").focus();
        $(document).on('blur','#specCode',this.Events.validateSpecCode);
        this.Events.validateAndSubmitSpecForm();
    },

    Events: {
        validateSpecCode: function () {
            var ctx = $('#ctx').text();
            if ($('#specCode').val() != "" && $("#specCode").val() != originalSpecCodeVal) {
                $.ajax({
                    url: ctx + '/fwzs/fwmSpec/validateCode/' + $('#specCode').val(),
                    type: "GET",
                    cache: false,
                    dataType: 'json',
                    success: function (data) {
                        if (!data) {
                            $("#specCode").focus();
                            $('#specCodeValidateError').removeClass('hidden').addClass('show');
                        }
                        else {
                            $('#specCodeValidateError').removeClass('show').addClass('hidden');
                        }
                    }
                });
            }
        },

        validateAndSubmitSpecForm: function () {
            $("#fwmSpecInputForm").validate({
                submitHandler: function (form) {
                    if ($('#specCodeValidateError').css('display') == 'none') {
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
        }
    }
};

$(document).ready(function() {
    FwmSpecForm.formValidateAndSelectProduct.bindAll();
});