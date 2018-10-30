function beforeClickWarehouse(treeId, treeNode) {
    var check = true;
    if(treeNode.level!=($("#grade").val()-2)){
        check = false;
        alertx("请选择正确的上级仓库");
    }
    return check;
}

function onClickWarehouse(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("warehouseTree"),
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
    var cityObj = $("#parentWarehouseName");
    cityObj.attr("value", v);
    $("#parentWarehouse").val(id);
    hideMenuWarehouse();
}

function showWarehouseMenu() {
    if ("" == $("#grade").val()) {
        hideMenuWarehouse();
        alertx("请选择仓库级别");
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
                beforeClick: beforeClickWarehouse,
                onClick: onClickWarehouse
            }
        };

        $.ajax({
            url: $('#warehouseTreeMenuURL').text(),
            type: "GET",
            cache: false,
            data: {
                "level": $("#grade").val()
            },
            dataType: 'json',
            success: function (data) {
                $.fn.zTree.init($("#warehouseTree"), setting, data.warehouseTreeMap);
            }
        });

        var cityObj = $("#parentWarehouseName");
        var cityOffset = $("#parentWarehouseName").offset();
        $("#warehouseMenuContent").css({
            left: cityOffset.left + "px",
            top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownWarehouse);
    }
}

function hideMenuWarehouse() {
    $("#warehouseMenuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownWarehouse);
}

function onBodyDownWarehouse(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "warehouseMenuContent" || $(event.target).parents("#warehouseMenuContent").length > 0)) {
        hideMenuWarehouse();
    }
}

function clearParentWarehouse() {
    $("#parentWarehouseName").val("");
    $("#parentWarehouse").val("");
}

$(document).ready(function () {
    $("#grade").change(function () {
        hideMenuWarehouse();
        clearParentWarehouse();
        if ("" == $("#grade").val() || "1" == $("#grade").val()) {
            $("#parentWarehouseName").attr("disabled", "disabled");
        } else {
            $("#parentWarehouseName").attr("readonly", "true");
            $("#parentWarehouseName").removeAttr("disabled");
        }
    });
});