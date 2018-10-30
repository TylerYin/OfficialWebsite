//对应于loginForm页面验证
var LoginForm = {};
LoginForm.formValidate = {
    Events: {
        validateForm: function () {
            $("#loginForm").validate({
                submitHandler: function (form) {
                    var username = $("#username").val();
                    var password = $("#password").val();
                    var code = $("#validateCode").val();

                    $("span[class='error']").remove();
                    $("span[class='codeerror']").remove();
                    if (undefined == code) {
                        if ("" != username && "" != password) {
                            loading('正在提交，请稍等...');
                            form.submit();
                        } else {
                            if ("" == username && "" == password) {
                                $("#username").focus();
                                $("#username").after("<span class='error'>请输入登录名</span>");
                                $("#password").after("<span class='error'>请输入密码</span>");
                            } else if ("" == username) {
                                $("#username").focus();
                                $("#username").after("<span class='error'>请输入登录名</span>");
                            } else {
                                $("#password").focus();
                                $("#password").after("<span class='error'>请输入密码</span>");
                            }
                        }
                    } else {
                        if ("" != username && "" != password && "" != code) {
                            if (LoginForm.formValidate.Events.validateCode()) {
                                loading('正在提交，请稍等...');
                                form.submit();
                            } else {
                                $("#validateCode").focus();
                                $("#refreshCode").after("<span class='codeerror'>请输入正确的验证码</span>");
                            }
                        } else {
                            var isEmpty = false;
                            if ("" == username && "" == password) {
                                isEmpty = true;
                                $("#username").focus();
                                $("#username").after("<span class='error'>请输入登录名</span>");
                                $("#password").after("<span class='error'>请输入密码</span>");
                            } else {
                                if ("" == username) {
                                    isEmpty = true;
                                    $("#username").focus();
                                    $("#username").after("<span class='error'>请输入登录名</span>");
                                }

                                if ("" == password) {
                                    isEmpty = true;
                                    $("#password").focus();
                                    $("#password").after("<span class='error'>请输入密码</span>");
                                }
                            }

                            if ("" == code) {
                                if (!isEmpty) {
                                    $("#validateCode").focus();
                                }
                                $("#refreshCode").after("<span class='codeerror' style='margin-left: 10px'>请输入验证码</span>");
                            } else {
                                if (!LoginForm.formValidate.Events.validateCode()) {
                                    if (!isEmpty) {
                                        $("#validateCode").focus();
                                    }
                                    $("#refreshCode").after("<span class='codeerror'>请输入正确的验证码</span>");
                                }
                            }
                        }
                    }
                }
            });
        },

        validateCode: function () {
            var isValid = false;
            $.ajax({
                url: $("#validateCodeURL").text(),
                type: "GET",
                cache: false,
                async: false,
                dataType: 'json',
                data: {
                    "validateCode": $("#validateCode").val()
                },
                success: function (data) {
                    isValid = data;
                }
            });
            return isValid;
        }
    }
};

$(function () {
    if ("true" == $("#isValidateCodeLogin").text()) {
        $('li').not(':last').removeClass('li3');
    } else {
        $('li').not(':last').addClass('li3');
    }
    $("#password").val("");
    LoginForm.formValidate.Events.validateForm();
});