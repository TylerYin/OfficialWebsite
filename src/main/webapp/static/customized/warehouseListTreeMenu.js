function beforeClickWarehouse(treeId, treeNode) {
    var check = true;
    return check;
}

function onClickWarehouse(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("warehouseListTree"),
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
    var cityObj = $("#warehouseName");
    cityObj.attr("value", v);
    $("#warehouse").val(id);
    hideMenuWarehouse();
}

function showWarehouseMenu() {
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
            beforeClick: beforeClickWarehouse,
            onClick: onClickWarehouse
        }
    };

    $.ajax({
        url: $('#warehouseTreeMenuURL').text(),
        type: "GET",
        cache: false,
        dataType: 'json',
        success: function (data) {
            if(data.warehouseTreeMap.length>0){
                $.fn.zTree.init($("#warehouseListTree"), setting, data.warehouseTreeMap);
            }
        }
    });

    var cityObj = $("#warehouseName");
    var cityOffset = $("#warehouseName").offset();
    $("#warehouseListMenuContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");
    $("body").bind("mousedown", onBodyDownWarehouse);
}

function hideMenuWarehouse() {
    $("#warehouseListMenuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownWarehouse);
}

function onBodyDownWarehouse(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "warehouseListMenuContent" || $(event.target).parents("#warehouseListMenuContent").length > 0)) {
        hideMenuWarehouse();
    }
}

$(document).ready(function () {

});