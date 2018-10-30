var type;

function beforeClickDealer(treeId, treeNode) {
    var check = true;
    return check;
}

function onClickDealer(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(type + "DealerListTree"),
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
    var cityObj = $("#" + type + "DealerName");
    cityObj.attr("value", v);
    $("#" + type + "Dealer").val(id);

    hideMenuDealer();
}

function showDealerMenu(obj) {
    type = obj;
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
        dataType: 'json',
        success: function (data) {
            if (data.dealerTreeMap.length > 0) {
                $.fn.zTree.init($("#" + type + "DealerListTree"), setting, data.dealerTreeMap);
            }
        }
    });

    var cityObj = $("#" + type + "DealerName");
    var cityOffset = $("#" + type + "DealerName").offset();
    $("#" + type + "DealerListMenuContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");
    $("body").bind("mousedown", onBodyDownDealer);
}

function hideMenuDealer() {
    $("#" + type + "DealerListMenuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownDealer);
}

function onBodyDownDealer(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "inDealerListMenuContent" || event.target.id == "outDealerListMenuContent"
            || $(event.target).parents("#" + type + "DealerListMenuContent").length > 0)) {
        hideMenuDealer();
    }
}

$(document).ready(function () {

});