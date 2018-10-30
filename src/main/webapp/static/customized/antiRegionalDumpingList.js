//对应于fwmFileList.jsp页面的js
var AntiRegionalDumpingList = {};
AntiRegionalDumpingList = {
    bindAll: function () {
        $(document).on('click', '#prodName', this.Events.changeInfo);
        var refreshDateInterval = $("#refreshDateInterval").text();
        window.setInterval(AntiRegionalDumpingList.Events.refreshDate, parseInt(refreshDateInterval));
    },

    Events: {
        changeInfo: function () {
            $.colorbox({
                html: $(".productInfoClass").html(),
                width: "1400",
                close: "关闭",
                opacity: "0.4",
                overlayClose: true,
                onComplete: function () {
                    AntiRegionalDumpingList.Events.bindClickEvent();
                    $("#filterProdName, #filterProdSpec").live("keyup", function () {
                        $.ajax({
                            url: $('#findProductsURL').text(),
                            type: "POST",
                            cache: false,
                            async: false,
                            dataType: 'html',
                            data: {
                                "prodName": $("#cboxContent").find("#filterProdName").val(),
                                "prodSpec": $("#cboxContent").find("#filterProdSpec").val()
                            },
                            success: function (data) {
                                $("#cboxContent").find("#productDiv").html(data);
                                $('#cboxContent').css("height", $(data).find("tr").length * 29 + 160);
                                AntiRegionalDumpingList.Events.bindClickEvent();
                            }
                        });
                    });
                    $("#productItems tbody").find("tr:odd").each(function (i, tr) {
                        $(tr).css("background-color", "#F5F5F5");
                    });

                    $("#productItems tbody").find("tr:even").each(function (i, tr) {
                        $(tr).css("background-color", "white");
                    });
                    $("#cboxContent").find("#filterProdName").focus();
                }
            });
        },

        bindClickEvent: function () {
            $('#cboxClose').hide();
            $("#productItems tr").click(function () {
                var td = $(this).find("td");
                $("#prodName").val(td[2].innerHTML);
                $("#prodId").val(td[7].innerHTML);
                $("#cboxClose").click();
            });
        },

        refreshDate: function () {
            $("#btnSubmit").click();
        }
    }
};

$(document).ready(function () {
    AntiRegionalDumpingList.bindAll();
});