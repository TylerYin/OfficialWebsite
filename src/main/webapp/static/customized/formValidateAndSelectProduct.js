//对应于fwmFileForm.jsp页面的js
var FwmFileForm = {};
FwmFileForm.formValidateAndSelectProduct = {
    bindAll : function () {
        //this.Events.validateForm();
        $(document).on('click','#prodNo',this.Events.changeInfo);
    },

    Events : {
        validateForm: function () {
            //$("#name").focus();
            $("#inputForm")
                .validate(
                    {
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
                        }
                    });
        },

        changeInfo: function () {
            $.colorbox({
                html: $(".productInfoClass").html(),
                width: "1400",
                close: "关闭",
                opacity: "0.4",
                overlayClose: true,
                onComplete: function () {
                    $('#cboxClose').hide();
                    $('#cboxContent').css("height", $('#cboxLoadedContent').css("height"));
                    $("#productItems tr").click(function () {
                        var td = $(this).find("td");
                        $("#prodNo").val(td[0].innerHTML);
                        $("#pesticideName").val(td[1].innerHTML);
                        $("#prodName").val(td[2].innerHTML);
                        $("#specDesc").val(td[3].innerHTML);
                        $("#cboxClose").click();
                    });
                }
            });
        }
    }
};

//对应于scPlanListCheck.jsp页面的js
var SCPlanListCheck = {};
SCPlanListCheck.paginationAndProductVerify = {
    bindAll : function () {
        //this.Events.page();
        $(document).on('click','#showVerifyResult',this.Events.showVerifyResult);
    },

    Events : {
        page: function (n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        },

        showVerifyResult: function () {
            var imgSrc = $(this).find('img').attr('src');
            if (imgSrc != "") {
                $.colorbox({
                    html: "<img src=" + imgSrc + " style=''/>",
                    close: "关闭",
                    opacity: "0.4",
                    overlayClose: true,
                    onComplete: function () {
                        $('#cboxContent').css("height", $('#cboxLoadedContent').css("height"));
                        $('#cboxClose').hide();
                    }
                });
            }
        }
    }
};

//对应于scPlanForm.jsp页面的js
var SCPlanForm = {};
SCPlanForm.formValidateAndSelectProduct = {
    bindAll : function () {
        //this.Events.validateForm();
        $(document).on('click','#prodNo',this.Events.changeInfo);
    },

    Events : {
        validateForm: function () {
            //$("#name").focus();
            $("#inputForm")
                .validate(
                    {
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
                        }
                    });
        },

        changeInfo: function () {
            $.colorbox({
                html: $(".productInfoClass").html(),
                width: "1400",
                close: "关闭",
                opacity: "0.4",
                overlayClose: true,
                onComplete: function () {
                    $('#cboxClose').hide();
                    $('#cboxContent').css("height", $('#cboxLoadedContent').css("height"));
                    $("#productItems tr").click(function () {
                        var td = $(this).find("td");
                        $("#prodNo").val(td[0].innerHTML);
                        $("#pesticideName").val(td[1].innerHTML);
                        $("#prodName").val(td[2].innerHTML);
                        $("#specDesc").val(td[3].innerHTML);
                        $("#prodId").val(td[7].innerHTML);
                        $("#cboxClose").click();
                    });
                }
            });
        }
    }
};

$(document).ready(function() {
    FwmFileForm.formValidateAndSelectProduct.bindAll();
    SCPlanListCheck.paginationAndProductVerify.bindAll();
    SCPlanForm.formValidateAndSelectProduct.bindAll();
});


