//对应于productExtendAttribute.jsp页面的js
var SwitchActiveClass = {};
SwitchActiveClass = {
    bindAll: function () {
        this.switchActiveClass();
    },

    switchActiveClass: function () {
        $("a[id^='prod']").click(function () {
            $(this).addClass("active");
            var hrefId = $(this).attr("id");
            $("#" + hrefId.substring(0, hrefId.length - 4)).removeClass("hidden");

            $("#myTab a").each(function (i, item) {
                var itemId = $(item).attr("id");
                if (itemId != hrefId) {
                    $(item).removeClass("active");
                    $("#" + itemId.substring(0, itemId.length - 4)).addClass("hidden");
                }
            });
        });
    }
};

$(document).ready(function () {
    SwitchActiveClass.bindAll();
});