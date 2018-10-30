var currentObj, currentObjName;
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
    var cityObj = $("#" + currentObjName);
    cityObj.attr("value", v);
    $("#" + currentObj).val(id);

    if (null != currentObj && undefined != currentObj) {
        var currentIndex = currentObj.substring(currentObj.length - 1, currentObj.length);
        var prodId = $("#prodId" + currentIndex).val();
        var warehouseId = $("#warehouse" + currentIndex).val();
        var planNumberId = "planNumber" + currentIndex;
        OutBoundForm.formValidateAndSelectProduct.Events.findStockLevel(prodId, warehouseId, planNumberId);
    }

    hideMenuWarehouse();
}

function showWarehouseMenu(obj) {
    if (obj != undefined && obj.length > 0) {
        var objArray = obj.split(",");
        currentObj = objArray[0];
        currentObjName = objArray[1];
    } else {
        return;
    }

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

    var cityObj = $("#" + currentObjName);
    var cityOffset = cityObj.offset();
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