//对应于fwmFileList.jsp页面的js
var FWMFileList = {};
FWMFileList.selectProduct = {
    bindAll : function () {
        $(document).on('click','#prodName',this.Events.changeInfo);
        if ($("#isSystemManager").text() == "false") {
            FWMFileList.selectProduct.Events.showPieChart();
        }
    },

    Events : {
        changeInfo: function () {
            $.colorbox({
                html: $(".productInfoClass").html(),
                width: "1400",
                close: "关闭",
                opacity: "0.4",
                overlayClose: true,
                onComplete: function () {
                    FWMFileList.selectProduct.Events.bindClickEvent();
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
                                FWMFileList.selectProduct.Events.bindClickEvent();
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

        delete: function (fwmFileId) {
            var prodId = $("#prodId").val();
            var prodName = $("#prodName").val();
            var fileName = $("#fileName").val();
            var createDate = $("#createDate").val();
            window.location.href = $("#ctxId").text() + "/fwzs/fwmFile/delete?fwmFileId=" + fwmFileId + "&fileName=" + fileName + "&prodId=" + prodId + "&prodName=" + prodName + "&createDate=" + createDate;
        },

        showPieChart: function () {
            var myChart = echarts.init(document.getElementById('pieChart'));
            var option = {
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                series : [
                    {
                        type: 'pie',
                        radius : '65%',
                        center: ['50%', '50%'],
                        selectedMode: 'single',
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        },
                        data:[ ]
                    }
                ]
            };
            myChart.setOption(option);

            myChart.showLoading();
            $.ajax({
                url: $("#ctxId").text() + "/fwzs/fwmQrcodeStatistics/getChartData",
                type: "post",
                cache: false,
                data:{
                    "beginDate" : $("#beginDate").val(),
                    "endDate" : $("#endDate").val()
                },
                dataType: 'json',
                success: function (data) {
                    myChart.hideLoading();
                    myChart.setOption({
                        title: data.echartTitle,
                        legend: data.echartLegend,
                        series: [{
                            "name": data.echartSeries.name,
                            data: data.echartSeries.eChartDataList
                        }]
                    });
                }
            });
        }
    }
};

$(document).ready(function() {
    FWMFileList.selectProduct.bindAll();
});