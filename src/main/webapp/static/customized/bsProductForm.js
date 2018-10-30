//对应于bsProductForm页面验证
var BSProductForm = {};
BSProductForm.formValidate = {
    Events : {
        validateForm: function () {
            $.validator.addMethod('packRate' ,function(value, element){
                var packRate = /^1[:：][1-9][0-9]?$|^1[:：][1-9][0-9]?[:：][1-9][0-9]?$/;
                return this.optional(element) || (packRate.test(value));
            }, '请输入正确的包装规格');

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
                    packRate :{
                        required : true,
                        packRate : true
                    }
                }
            });
        }
    }
};

$(function(){
    BSProductForm.formValidate.Events.validateForm();
});