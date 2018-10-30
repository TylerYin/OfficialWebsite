//对应于bitMatrixForm.jsp页面的js
var BitMatrix = {};
BitMatrix = {
    bindAll: function () {
        this.Events.validateForm();
    },

    Events: {
        validateForm: function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    if ("" != $("#url").val().trim()) {
                        form.submit();
                    } else {
                        $("#bitMatrix").remove();
                        alertx("请输入有效的地址信息！");
                    }
                }
            });
        }
    }
};

$(document).ready(function () {
    BitMatrix.bindAll();
});