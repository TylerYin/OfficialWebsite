//对应于AntiRegionalParaSettingForm页面验证
var AntiRegionalParaSettingForm = {};
AntiRegionalParaSettingForm.formValidate = {
    Events : {
        validateForm: function () {
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
                },
                rules : {
                    antiRegionalThreshold :{
                        required: true,
                        number : true,
                        range : [1,999]
                    }
                }
            });
        }
    }
};

$(function(){
    AntiRegionalParaSettingForm.formValidate.Events.validateForm();
});