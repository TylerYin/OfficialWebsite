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
                            $('#specCodeValidateError').css('display', 'inline');
                        }
                        else {
                            $('#specCodeValidateError').css('display', 'none');
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
                                $('#lineNoValidateError').css('display', 'inline');
                            }
                            else {
                                $('#lineNoValidateError').css('display', 'none');
                            }
                        }
                    });
                }
            });
        }
    }
};

$(document).ready(function() {
    FwmSpecForm.formValidateAndSelectProduct.bindAll();
    SCLinesForm.formValidateAndSelectProduct.bindAll();
});