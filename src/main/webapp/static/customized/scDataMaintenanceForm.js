//对应于scPlanForm.jsp页面的js
var SCDataMaintenanceForm = {
    bindAll : function () {
        this.Events.validateForm();
        $(document).on('click','#fwmFileName',this.Events.chooseFwmFile);
    },

    Events : {
        validateForm: function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    var validResult = SCDataMaintenanceForm.Events.customizedValidteForm();
                    if ("valid" == validResult) {
                        loading('正在提交，请稍等...');
                        form.submit();
                    } else {
                        if ("fieldInvalid" == validResult) {
                            alertx("请输入有效信息！");
                        } else if ("indateInconsistent" == validResult) {
                            alertx("有效期不一致！");
                        } else {
                            alertx("有效期必须在生产日期之后！");
                        }
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
                }
            });
        },

        deleteScPlan: function (deleteKeys) {
            var planId = deleteKeys.split(",")[0];
            var prodId = deleteKeys.split(",")[1];
            $("#SC" + prodId).parent().addClass("hidden");
            var deleteKey = $('#deleteKey').val() + planId + "#" + prodId + "&";
            $('#deleteKey').val(deleteKey);
        },

        chooseFwmFile: function () {
            if ("" == $(this).val()) {
                var currentRow = $(this).parent().parent().parent().parent().find("input");
                $.each(currentRow, function (i, item) {
                    if ("prodId" == $(item).attr("id") && "" != $(item).val()) {
                        $.ajax({
                            url: $('#findFwmFileByProductUrl').text(),
                            type: "post",
                            cache: false,
                            async: false,
                            data: {
                                prodId: $(item).val()
                            },
                            success: function (data) {
                                var fwmFileList = "";
                                $.each(data, function (i, item) {
                                    fwmFileList += "<tr class='itemRow'>";
                                    fwmFileList += "<td>" + item.fileName + "</td>";
                                    fwmFileList += "<td>" + item.bsProduct.prodNo + "</td>";
                                    fwmFileList += "<td>" + item.bsProduct.prodName + "</td>";
                                    fwmFileList += "<td>" + item.bsProduct.prodSpec.specDesc + "</td>";
                                    fwmFileList += "<td>" + item.codeNumber + "</td>";
                                    fwmFileList += "<td>" + item.updateDate + "</td>";
                                    fwmFileList += "<td style='display:none;'>" + item.id + "</td>";
                                    fwmFileList += "</tr>";
                                });

                                if (undefined != fwmFileList && null != fwmFileList && "" != fwmFileList) {
                                    $("#fwmFileListBody").html(fwmFileList);
                                    $.colorbox({
                                        html: $(".productInfoClass").html(),
                                        width: "1400",
                                        close: "关闭",
                                        opacity: "0.4",
                                        overlayClose: true,
                                        onComplete: function () {
                                            $('#cboxClose').hide();
                                            $('#cboxContent').css("height", $('#cboxLoadedContent').css("height"));
                                            $("#fwmFileItems tr").click(function () {
                                                var td = $(this).find("td");
                                                $.each(currentRow, function (i, item) {
                                                    if ($(item).attr('id') == 'fwmFileId') {
                                                        $(item).val(td[6].innerText);
                                                    }
                                                    if ($(item).attr('id') == 'fwmFileName') {
                                                        $(item).val(td[0].innerText);
                                                    }
                                                });
                                                $("#cboxClose").click();
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        },

        customizedValidteForm: function () {
            var isValid = true;
            var isDateValid = true;
            $("#planContent").find("tr").each(function (i, row) {
                $(row).find("input").each(function (k, input) {
                    var attr = $(input).attr("id");
                    if ("indate" == attr) {
                        if ("" == $(input).val()) {
                            isValid = false;
                            $(input).css("border-color", "red");
                        } else {
                            if ("" != $('#madeDate').val()) {
                                var madeDate = new Date(Date.parse($('#madeDate').val()));
                                var indate = new Date(Date.parse($(input).val()));
                                if (indate <= madeDate) {
                                    isDateValid = false;
                                    $(input).css("border-color", "red");
                                } else {
                                    $(input).css("border-color", "#ccc");
                                }
                            } else {
                                $(input).css("border-color", "#ccc");
                            }
                        }
                    }
                });
            });

            if (!isValid) {
                return "fieldInvalid";
            } else if (!isDateValid) {
                return "dateInvalid";
            } else {
                var indates = new Array();
                $("#planContent").find("tr").each(function (i, row) {
                    $(row).find("input").each(function (k, input) {
                        if ("hidden" != $(row).attr("class")) {
                            var attr = $(input).attr("id");
                            if ("indate" == attr) {
                                indates[i] = $(input).val();
                            }
                        }
                    });
                });

                if (indates.length > 1) {
                    for (var i = 1; i < indates.length; i++) {
                        if (indates[0] != indates[i]) {
                            return "indateInconsistent";
                        }
                    }
                }

                return "valid";
            }
        }
    }
};

$(document).ready(function() {
    SCDataMaintenanceForm.bindAll();
});