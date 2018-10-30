//对应于scPlanListCheck.jsp页面的js
var SCPlanListCheck = {};
SCPlanListCheck = {
    bindAll: function () {
        $(document).on('click', '#showVerifyResult', this.Events.showVerifyResult);
        $(document).on('click', '.showQcNotPassReason', this.Events.showQcNotPassReason);
    },

    Events: {
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
        },

        showQcNotPassReason: function () {
            var planId = $(this).find("span").text();
            $.ajax({
                url: $('#findQcNotPassURL').text(),
                type: "POST",
                cache: false,
                async: false,
                dataType: 'json',
                data: {
                    "planId": planId
                },
                success: function (data) {
                    var reason = "<div style='margin-left: 35%;margin-top: 10px;font-size: large'><b>质检未通过原因</b></div></p>";
                    reason += "<table class='table table-striped table-bordered table-condensed' style='width: 560px;margin-left: 20px;'>";
                    reason += "<tr><th>序号</th><th>原因</th><th>日期</th></tr>";
                    $(data).each(function (i, item) {
                        reason += "<tr>";
                        reason += "<td width='80px'>" + (i + 1) + "</td>";
                        reason += "<td>" + item.reason + "</td>";
                        reason += "<td width='140px'>" + item.createDate + "</td>";
                        reason += "</tr>";
                    });
                    reason += "</table>";

                    $.colorbox({
                        html: reason,
                        close: "关闭",
                        width: "600",
                        opacity: "0.4",
                        overlayClose: true,
                        onComplete: function () {
                            $('#cboxContent').css("height", $('#cboxLoadedContent').css("height"));
                            $('#cboxClose').hide();
                        }
                    });
                }
            });
        }
    }
};

$(document).ready(function () {
    SCPlanListCheck.bindAll();
});