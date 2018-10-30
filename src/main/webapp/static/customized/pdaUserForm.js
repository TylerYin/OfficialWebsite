//对应于pdaUserForm.jsp页面的js
var pdaUserForm;
pdaUserForm = {
    bindAll: function () {
        this.Events.validateForm();
    },

    Events: {
        validateForm: function () {
            $.validator.addMethod('mobile', function (value, element) {
                var phoneReg = /^1\d{10}$|^0\d{2,3}-?\d{7,8}$/;
                return this.optional(element) || (phoneReg.test(value));
            }, '请输入正确的电话');

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
                rules: {
                    mobile: {
                        required: true,
                        mobile: true
                    },
                    loginName: {
                        remote: $("#validateUserAccountUrl").text()
                    }
                },
                messages :{
                    loginName: {
                        remote: "该帐户已存在!"
                    }
                }
            });
        }
    }
};

$(document).ready(function () {
    pdaUserForm.bindAll();
});