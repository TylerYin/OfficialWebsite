function beforeClickDealer(treeId, treeNode) {
    var check = true;
    if ("false" == $("#currentUserIsDealer").text()) {
        if (treeNode.level != ($("#grade").val() - 2)) {
            check = false;
            alertx("请选择正确的上级经销商");
        }
    }
    return check;
}

function onClickDealer(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("dealerTree"),
        nodes = zTree.getSelectedNodes(),
        v = "";
    var id = "";
    nodes.sort(function compare(a, b) {
        return a.id - b.id;
    });
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].name + ",";
        id += nodes[i].id;
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);
    var cityObj = $("#parentDealerName");
    cityObj.attr("value", v);
    $("#parentDealer").val(id);
    $("#areaIdsId").val("");
    $("#areaIdsName").val("");
    hideMenuDealer();
}

function showDealerMenu() {
    if ("" == $("#grade").val()) {
        hideMenuDealer();
        alertx("请选择经销商级别");
    } else if ("2" == $("#grade").val() || "3" == $("#grade").val()) {
        var setting = {
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                beforeClick: beforeClickDealer,
                onClick: onClickDealer
            }
        };

        $.ajax({
            url: $('#dealerTreeMenuURL').text(),
            type: "GET",
            cache: false,
            data: {
                "level": $("#grade").val()
            },
            dataType: 'json',
            success: function (data) {
                $.fn.zTree.init($("#dealerTree"), setting, data.dealerTreeMap);
            }
        });

        var cityObj = $("#parentDealerName");
        var cityOffset = $("#parentDealerName").offset();
        $("#dealerMenuContent").css({
            left: cityOffset.left + "px",
            top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownDealer);
    }
}

function hideMenuDealer() {
    $("#dealerMenuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownDealer);
}

function onBodyDownDealer(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "dealerMenuContent" || $(event.target).parents("#dealerMenuContent").length > 0)) {
        hideMenuDealer();
    }
}

function clearParentDealer() {
    $("#parentDealerName").val("");
    $("#parentDealer").val("");
    $("#areaIdsId").val("");
    $("#areaIdsName").val("");
}

$(document).ready(function () {
    $("#grade").change(function () {
        hideMenuDealer();
        clearParentDealer();
        if ("" == $("#grade").val() || "1" == $("#grade").val()) {
            $("#parentDealerName").attr("disabled", "disabled");
        } else {
            $("#parentDealerName").attr("readonly", "true");
            $("#parentDealerName").removeAttr("disabled");
        }
    });
});