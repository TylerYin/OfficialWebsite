//对应于bsProductForm页面验证
var copiedTr, rowNum;
var BSProductForMultiSpecForm = {};
BSProductForMultiSpecForm = {
    bindAll : function () {
        this.Events.validateForm();
        $(document).on('click','.deleteProductSpec',this.Events.deleteProductSpec);
        $(document).on('click','#btnAddProductSpec',this.Events.btnAddProductSpec);
        $(document).on('click','#prodSpecDesc',this.Events.changeProdSpec);
        $(document).on('click','#prodUnit',this.Events.changeProdUnit);
    },

    Events : {
        validateForm: function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    var testResult = BSProductForMultiSpecForm.Events.customizedValidtion();
                    if ("valid" == testResult) {
                        loading('正在提交，请稍等...');
                        form.submit();
                    } else if ("invalid" == testResult) {
                        alertx("请输入有效信息！");
                    } else if ("duplicateProduct" == testResult) {
                        alertx("该产品已经存在！");
                    } else if("duplicateSpec" == testResult){
                        alertx("规格重复");
                    } else {
                        alertx("请输入有效包装比例！");
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

        deleteProductSpec: function () {
            $(this).parent().parent().remove();
        },

        btnAddProductSpec: function () {
            rowNum = rowNum + 1;
            var copyTr = copiedTr;
            copyTr = copyTr.html().replace("<td></td>","<td><a class='deleteProductSpec'>删除</a></td>");
            copyTr = '<tr>' + copyTr.split("0").join(rowNum) + '</tr>';
            if($('#contentTable').find('tbody tr:last').length > 0){
                $('#contentTable').find('tbody tr:last').after(copyTr);
            }else{
                $('#contentTable').find('tbody').append(copyTr);
            }
            BSProductForMultiSpecForm.Events.getProdNo(rowNum);
        },

        getProdNo : function(rowNum) {
            $.ajax({
                url: $('#generateProdNoURL').text(),
                type: "GET",
                cache: false,
                dataType: 'text',
                success: function (data) {
                    $('#contentTable').find("input[id='productSpecs" + rowNum + ".prodNo']").val(data);
                }
            });
        },

        changeProdSpec: function () {
            var currentRow = $(this).parent().parent().find("input");
            $.colorbox({
                html: $("#chooseProductSpec").html(),
                width: "680",
                close: "关闭",
                opacity: "0.4",
                transition: "none",
                overlayClose: true,
                onComplete: function () {
                    $('#cboxClose').hide();
                    $('#cboxContent').css("height", $('#cboxLoadedContent').css("height") + 5);
                    $("#productItems tr").click(function () {
                        var td = $(this).find("td");
                        $.each(currentRow, function (i, item) {
                            if ($(item).attr('id') == 'prodSpecDesc') {
                                $(item).val(td[1].innerHTML);
                            }
                            if ($(item).attr('id') == 'prodSpecId') {
                                $(item).val(td[2].innerHTML);
                            }
                        });
                        $("#cboxClose").click();
                    });
                }
            });
        },

        changeProdUnit: function(){
            var currentRow = $(this).parent().parent().find("input");
            $.colorbox({
                html: $("#chooseProductUnit").html(),
                width: "680",
                close: "关闭",
                opacity: "0.4",
                overlayClose: true,
                onComplete: function () {
                    $('#cboxClose').hide();
                    $('#cboxContent').css("height", $('#cboxLoadedContent').css("height") + 5);
                    $("#productItems tr").click(function () {
                        var td = $(this).find("td");
                        $.each(currentRow, function (i, item) {
                            if ($(item).attr('id') == 'prodUnit') {
                                $(item).val(td[0].innerHTML);
                            }
                            if ($(item).attr('id') == 'prodUnitValue') {
                                $(item).val(td[2].innerHTML);
                            }
                        });
                        $("#cboxClose").click();
                    });
                }
            });
        },

        customizedValidtion : function () {
            var isValid = true;
            var isPackRateValid = true;
            var isDuplicateProduct = false;
            var packRateRegx = /^1[:：][1-9][0-9]?$|^1[:：][1-9][0-9]?[:：][1-9][0-9]?$/;

            var prodUnit, prodSpec, regCode, companyId, prodName;
            $("#productSpecContent").find("tr").each(function(i, row){
                prodUnit = "", prodSpec = "", regCode = $("#regCode").val(), companyId = $("#companyId").val(), prodName = $("#prodName").val();
                $(row).find("input").each(function(k, input){
                    var attr = $(input).attr("id");
                    if("prodSpecDesc"==attr){
                        if("请选择"==$(input).val()){
                            isValid = false;
                            $(input).css("border-color","red");
                        }else{
                            prodSpec = $(input).next().val();
                            $(input).css("border-color","#ccc");
                        }
                    }

                    if("prodUnit"==attr){
                        if("请选择"==$(input).val()){
                            isValid = false;
                            $(input).css("border-color","red");
                        }else{
                            prodUnit = $(input).next().val();
                            $(input).css("border-color","#ccc");
                        }
                    }

                    if ("packRate" == attr) {
                        if ("" == $(input).val()) {
                            isValid = false;
                            $(input).css("border-color", "red");
                        } else {
                            if (packRateRegx.test($(input).val())) {
                                $(input).css("border-color", "#ccc");
                            } else {
                                isPackRateValid = false;
                                $(input).css("border-color", "red");
                            }
                        }
                    }
                });

                if ("" != regCode && "" != companyId && "" != prodName && "" != prodSpec && "" != prodUnit) {
                    $.ajax({
                        url : $('#isDuplicateProductURL').text(),
                        type : "POST",
                        cache : false,
                        async : false,
                        dataType : 'json',
                        data : {
                            regCode : regCode,
                            companyId : companyId,
                            prodName : prodName,
                            prodSpec : prodSpec,
                            prodUnit : prodUnit
                        },
                        success : function(data) {
                            if(data){
                                isDuplicateProduct = true;
                                $(row).find("input:first").css("border-color", "red");
                            }else{
                                $(row).find("input:first").css("border-color", "#CCCCCC");
                            }
                        }
                    });
                }
            });

            if (!isValid) {
                return "invalid";
            } else if (!isPackRateValid) {
                return "packRateInvalid";
            } else if (isDuplicateProduct) {
                return "duplicateProduct";
            } else {
                var list = [], duplicateSpec;
                $("#productSpecContent").find("tr").each(function(i, row) {
                    product = new Object();
                    $(row).find("input").each(function(k, input) {
                        var attr = $(input).attr("id");
                        if ("prodSpecDesc" == attr) {
                            product.prodSpec = $(input).val();
                        }
                        if ("prodUnit" == attr) {
                            product.prodUnit = $(input).val();
                        }
                    });

                    duplicateSpec = false;
                    if (0 == list.length) {
                        list.push(product);
                    } else {
                        $.each(list, function(i, item){
                            if (item.prodSpec==product.prodSpec && item.prodUnit==product.prodUnit) {
                                duplicateSpec= true;
                            }
                        });

                        if(!duplicateSpec){
                            list.push(product);
                        }
                    }
                });

                if (list.length < $("#productSpecContent").find("tr").length) {
                    return "duplicateSpec";
                }
                return "valid";
            }
        }
    }
};

$(function(){
    rowNum = 0;
    copiedTr = $('#contentTable').find('tbody tr:first').clone();
    BSProductForMultiSpecForm.bindAll();
});