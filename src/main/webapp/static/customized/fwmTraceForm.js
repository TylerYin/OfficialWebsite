//对应于fwmTraceForm.jsp页面的js
var FwmTraceForm = {};
FwmTraceForm = {
    bindAll: function () {
        this.Events.validateForm();
    },

    Events: {
        validateForm: function () {
            $("#searchForm").validate({
                submitHandler: function (form) {
                    if ("" != $("#qrCode").val().trim()) {
                        form.submit();
                    } else {
                        alertx("请输入有效的防伪码！");
                    }
                }
            });
        }
    }
};

$(document).ready(function () {
    FwmTraceForm.bindAll();
});