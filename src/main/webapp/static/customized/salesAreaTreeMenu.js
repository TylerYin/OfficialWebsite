function onClickSalesArea(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("areaTree"),
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
    var cityObj = $("#salesAreaName");
    cityObj.attr("value", v);
    $("#salesArea").val(id);
    hideMenuSalesArea();
}

function showSalesAreaMenu() {
    var cityObj = $("#salesAreaName");
    var cityOffset = $("#salesAreaName").offset();
    $("#areaMenuContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");
    $("body").bind("mousedown", onBodyDownSalesArea);
}

function hideMenuSalesArea() {
    $("#areaMenuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownSalesArea);
}

function onBodyDownSalesArea(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "areaMenuContent" || $(event.target).parents("#areaMenuContent").length > 0)) {
        hideMenuSalesArea();
    }
}

function openFirstLevel() {
    var tree = $.fn.zTree.getZTreeObj('areaTree');
    var nodes = tree.transformToArray(tree.getNodes());
    for(var i=0;i<nodes.length;i++){
        if(nodes[i].level == 0){
            console.log(nodes[i].name);
            tree.expandNode(nodes[i],true,true,false)
        }else{
            tree.expandNode(nodes[i],false,true,false)
        }
    }
}

$(document).ready(function () {
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
            onClick: onClickSalesArea
        }
    };

    $.ajax({
        url: $('#areaTreeMenuURL').text(),
        type: "GET",
        cache: false,
        dataType: 'json',
        success: function (data) {
            $.fn.zTree.init($("#areaTree"), setting, data.areaTreeMap);
            openFirstLevel();
        }
    });
});