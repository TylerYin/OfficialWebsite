//对应于dealerForm.jsp页面的js
var DealerForm = {
    bindAll : function () {
        this.Events.validateForm();
    },

    Events : {
        validateForm: function () {
            $.validator.addMethod('phone' ,function(value, element){
                var phoneReg = /^1\d{10}$|^0\d{2,3}-?\d{7,8}$/;
                return this.optional(element) || (phoneReg.test(value));
            }, '请输入正确的电话');

            $.validator.addMethod('fax' ,function(value, element){
                var faxReg = /^0\d{2,3}-?\d{7,8}$/;
                return this.optional(element) || (faxReg.test(value));
            }, '请输入正确的传真');

            $.validator.addMethod('qq' ,function(value, element){
                var qqReg = /^[1-9]\d{4,12}$/;
                return this.optional(element) || (qqReg.test(value));
            }, '请输入正确的QQ');

            $("#inputForm").validate({
                submitHandler: function (form) {
                    if (DealerForm.Events.customizedValidteForm()) {
                        if (""!=$("#id").val() && $("#parentDealer").val() == $("#id").val()) {
                            alertx("当前经销商和上级经销商不能相同！")
                        } else {
                            loading('正在提交，请稍等...');
                            form.submit();
                        }
                    } else {
                        alertx("请选择上级经销商！")
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
                    "email" :{
                        required :true,
                        "email" : true
                    },
                    "phone" :{
                        required :true,
                        "phone" : true
                    },
                    "fax" :{
                        required :false,
                        "fax" : true
                    },
                    "qq" :{
                        required :false,
                        "qq" : true
                    },
                    account: {
                        remote: $("#validateUserAccountUrl").text()
                    }
                },
                messages :{
                    "email" :{
                        required :"必填信息",
                        "email" :"请输入正确的邮箱"
                    },
                    account: {
                        remote: "经销商登录名已存在"
                    }
                }
            });
        },

        customizedValidteForm: function () {
            var grade = $("#grade").val();
            var parentDealer = $("#parentDealerName").val();
            if (grade != "1" && parentDealer == "") {
                return false;
            }
            return true;
        }
    }
};

$(document).ready(function() {
    DealerForm.bindAll();
});