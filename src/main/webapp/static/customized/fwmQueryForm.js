//对应于fwmQuery.jsp页面的js
var FwmQueryForm = {};
FwmQueryForm = {
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
                        $("#queryResult").remove();
                        alertx("请输入有效的防伪码！");
                    }
                }
            });
        }
    }
};

$(document).ready(function () {
    FwmQueryForm.bindAll();
});