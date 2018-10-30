//retrospectTemplateForm.jsp页面的js
var RetrospectTemplateForm = {};
RetrospectTemplateForm = {
    bindAll: function () {
        this.setRetrospectTemplate();
    },

    setRetrospectTemplate: function () {
        $("#btnSubmit").click(function () {
            var retrospectTemplate = $("#retrospectTemplateForm").find("input[type='radio']:checked").val();
            $.ajax({
                url: $('#retrospectTemplateForm').attr("action"),
                type: "POST",
                cache: false,
                dataType: 'json',
                data: {
                    "templateSetting": retrospectTemplate
                },
                success: function (data) {
                    if (data) {
                        alertx("模板设置成功！");
                    } else {
                        alertx("模板设置失败！");
                    }
                }
            });
        });
    }
};

$(document).ready(function () {
    RetrospectTemplateForm.bindAll();
});