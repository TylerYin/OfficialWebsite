

//放大图片
/*使用例子：
bigimg("src", "tjzmark", "tjimgdiv");
src：图片地址
tjzmark:遮罩层ID 
tjimgdiv：图片容器DIV id
*/
function bigimg(src, markid, timgid) {

    if ($("#" + markid).length > 0) {
        $("#" + markid).show();
        $("#" + timgid).show();
    } else {
        var Wheight = $(window).height(),
            Wwidth = $(window).width(),
            tmark = $("<div></div>").addClass("TJtczmark").css({ "width": Wwidth + "px", "height": Wheight + "px" }).attr("id", markid),
            twrap = $("<div></div>").addClass("TJbigimgWarp").css({ "width": Wwidth + "px" }).attr("id", timgid),
            timg = $("<img></img>").css({ "width": "100%" }).attr({ "src": src });
        twrap.append(timg);

        $("body").append(tmark, twrap);
        var rtcbox = $("#" + timgid),
            theight = rtcbox.outerHeight(),
            twidth = rtcbox.outerWidth(),
            tleft = (Wwidth - twidth) / 2 + "px",
            ttop = (Wheight - theight) / 2 + "px";
        rtcbox.css({ "left": tleft, "top": ttop });

        tmark.on("click", function () {
            $("#" + markid).hide();
            $("#" + timgid).hide();
        })
        twrap.on("click", function () {
            $("#" + markid).hide();
            $("#" + timgid).hide();
        })
    }
};

//弹出框

/*使用例子
Opentck({
mkid: 'tcmark',    //遮罩层ID ，not null
boxid: 'tcbox',     // tcboxID，not null
content: '领取成功1', //显示内容，not null
url: 'index.aspx',   // 确定后跳转url ， null
time: '3000'         // 毫秒后关闭， null
}) */

function Opentck(obj) {
    var tcmarkid = obj.mkid,
        tcboxid = obj.boxid,
        content = obj.content;

    if ($("#" + tcmarkid).length > 0) {
        $("#" + tcmarkid).show();
        $("#" + tcboxid).show();
    } else {
        var Wheight = $(window).height(),
            Wwidth = $(window).width(),
            tmark = $("<div></div>").addClass("TJtczmark").css({ "width": Wwidth + "px", "height": Wheight + "px" }).attr("id", tcmarkid),
            tcbox = $("<div></div>"),
            tcul = $("<ul></ul>"),
            content = $("<li></li>").addClass("Ttccontentwrap").html("<p>" + content + "</p>"),
            choose = $("<li></li>").addClass("Ttcchoosewrap Ttcchoosewrapone"),
            aok = $("<a></a>").addClass("Ttcchooseyes").text("确定");
        choose.append(aok);
        tcul.append(content, choose);
        tcbox.append(tcul).addClass("TJtcbox").attr("id", tcboxid);

        $("body").append(tmark, tcbox);
        var rtcbox = $("#" + tcboxid),
            theight = rtcbox.outerHeight(),
            twidth = rtcbox.outerWidth(),
            tleft = (Wwidth - twidth) / 2 + "px",
            ttop = (Wheight - theight) / 2 + "px";
        rtcbox.css({ "left": tleft, "top": ttop });

        if (obj.url) {
            aok.on("click", function () {
                window.location.href = obj.url;
            })
        } else {
            aok.on("click", function () {
                $("#" + tcmarkid).hide();
                $("#" + tcboxid).hide();
                WeixinJSBridge.call('closeWindow');
            });
        }
    }
    if (obj.time) {
        clearTimeout(shutdowm);
        var shutdowm = setTimeout(function () {
            $("#" + tcmarkid).hide();
            $("#" + tcboxid).hide();
        }, obj.time)
    }
};

//弹出框新，可二次修改内容
function OpentckNEW(obj) {
    var tcmarkid = obj.mkid,
        content = obj.content,
        contentid = obj.contentid;
    if ($("#" + tcmarkid).length > 0) {
        $("#" + tcmarkid).show();
        $("#" + contentid).text(content);

    } else {
        var Wheight = obj.height ? obj.height : $(window).height() + 1,
            Wwidth = $(window).width(),
            tmark = $("<div></div>").addClass("TJtczmark").css({ "width": Wwidth + "px", "height": Wheight + "px" }).attr("id", tcmarkid),
            tcbox = $("<div></div>"),
            tcul = $("<ul></ul>"),
            content = $("<li></li>").addClass("Ttccontentwrap").html("<p id=" + contentid + ">" + content + "</p>"),
            choose = $("<li></li>").addClass("Ttcchoosewrap Ttcchoosewrapone"),
            aok = $("<a></a>").addClass("Ttcchooseyes").text("确定");
        choose.append(aok);
        tcul.append(content, choose);
        tcbox.append(tcul).addClass("TJtcboxnew").css({ "margin": "60% auto" });
        tmark.append(tcbox);
        $("body").append(tmark);
    }
    var bta = $("#" + tcmarkid + "  a");
    if (obj.url) {
        bta.click(function () {
            window.location.href = obj.url;
        })
    } else {
        if (obj.closeW) {
            bta.click(function () {
                $("#" + tcmarkid).hide();
                WeixinJSBridge.call('closeWindow');
            });
        }
        else {
            bta.click(function () {
                $("#" + tcmarkid).hide();
                clearTimeout(shutdowm);
            });
        }
    }
    if (obj.time) {
        clearTimeout(shutdowm);
        var shutdowm = setTimeout(function () {
            $("#" + tcmarkid).hide();
        }, obj.time)
    }
};


function TJJZloading(obj) {
    var tcmarkid = obj.mkid,
        content = obj.content;
    if ($("#" + tcmarkid).length > 0) {
        $("#" + tcmarkid + "  span").text(content);
        $("#" + tcmarkid).show();
    } else {
        var Wheight = obj.height ? obj.height : $(window).height() + 1,
            Wwidth = $(window).width(),
            tmark = $("<div></div>").addClass("TJJZZmark").css({ "width": Wwidth + "px", "height": Wheight + "px" }).attr("id", tcmarkid),
            twrap = $("<div></div>"),
            timg = $("<img/>").attr("src", obj.img ? obj.img:"../image/jz2.gif"),
            tcontent = $("<span></span>").text(content);
        twrap.append(timg, tcontent);
        tmark.append(twrap);
        $("body").append(tmark);
        var tleft = (Wwidth - 158) / 2 + "px",
            ttop = (Wheight - twrap.outerHeight()) / 2 + "px";
        twrap.css({ "left": tleft, "top": ttop });
        twrap.show();
    }
}

var BigNumberWeb = {
    GetAddr: function (adobj) {
        var me = this;
        if (!navigator.geolocation) {
            TJJZloading({ mkid: "jzzid", content: "正在加载" });
            me.add = "不支持";
            me.pro = "不支持";
            me.sj = "不支持";
            me.xj = "不支持";
            adobj.success();
            return;
        }
        navigator.geolocation.getCurrentPosition(function (res) {
            TJJZloading({ mkid: "jzzid", content: "正在加载" });
            var currentLat = res.coords.latitude;; //纬度
            var jd = res.coords.longitude;
          
            var URL = "http://api.map.baidu.com/geocoder/v2/?location=" + currentLat + "," + jd + "&output=json&ak=ASl2xmUaYpjwdWy3GPBQbpUK&callback=?&pois=0";

            //var URL = "http://apis.map.qq.com/ws/geocoder/v1/?location=" + currentLat + "," + jd + "&key=MWKBZ-DP7A6-IDOSB-MDDUB-JD66S-7QFYW&output=jsonp&callback=?&get_poi=0";
            $.getJSON(URL, function (data) {                        
                var status = data.status;
                if (status == "0") {
                    var oaddr = data.result.addressComponent;
                    me.add = data.result.formatted_address;
                    me.pro = oaddr.province;
                    me.sj = oaddr.city;
                    me.xj = oaddr.district;
                  
                }
                else {
                    me.add = "未允许";
                    me.pro = "未允许";
                    me.sj = "未允许";
                    me.xj = "未允许";
                }
                adobj.success();
            });
        }, function () {
            TJJZloading({ mkid: "jzzid", content: "正在加载" });
            me.add = "未允许";
            me.pro = "未允许";
            me.sj = "未允许";
            me.xj = "未允许";
            adobj.success();
        });
    },
    GetTXAddr: function (adobj) {
        var me = this,
            geolocation = new qq.maps.Geolocation("MWKBZ-DP7A6-IDOSB-MDDUB-JD66S-7QFYW", "天鉴地址");
        TJJZloading({ mkid: "jzzid", content: "正在加载" });
        geolocation.getLocation(function (res) {
            if (res.type == "ip") {
                var currentLat = res.lat, //纬度
                    jd = res.lng;
                me.pro = res.province;
                me.sj = res.city;
                me.xj = "";
                me.add = res.province + res.city;
                var URL = "http://apis.map.qq.com/ws/geocoder/v1/?location=" + currentLat + "," + jd + "&key=MWKBZ-DP7A6-IDOSB-MDDUB-JD66S-7QFYW&output=jsonp&callback=?&get_poi=0";
                $.getJSON(URL, function (data) {
                    var status = data.status;
                    if (status == "0") {
                        var oaddr = data.result.ad_info;
                        me.add = data.result.address;
                        me.pro = oaddr.province;
                        me.sj = oaddr.city;
                        me.xj = oaddr.district;
                    }
                    adobj.success();
                });
            }
            else {
                me.add = res.province + res.city + res.addr;
                me.pro = res.province;
                me.sj = res.city;
                me.xj = res.district;
                adobj.success();
            }
        }, function (res) {

            var URL = "http://apis.map.qq.com/ws/location/v1/ip?ip=" + adobj.ip + "&key=MWKBZ-DP7A6-IDOSB-MDDUB-JD66S-7QFYW&output=jsonp&callback=?";
            $.getJSON(URL, function (data) {
                var status = data.status;
                if (status = "0") {
                    var oaddr = data.result.ad_info;
                    me.pro = oaddr.province;
                    me.sj = oaddr.city;
                    me.xj = "";
                    me.add = oaddr.province + oaddr.city;
                }
                else {
                    me.add = "未允许";
                    me.pro = "未允许";
                    me.sj = "未允许";
                    me.xj = "未允许";
                }
                adobj.success();
            });
        });
    },
    GetBDAddr: function (adobj) {
        var me = this,
            geolocation = new BMap.Geolocation();
        TJJZloading({ mkid: "jzzid", content: "正在加载" });
        geolocation.getCurrentPosition(function (res) {
            if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                var currentLat = res.point.lat, //纬度
                   jd = res.point.lng;
                me.pro = res.address.province;
                me.sj = res.address.city;
                me.xj = "";
                me.add = me.pro + me.sj;
              
                var URL = "http://api.map.baidu.com/geocoder/v2/?location=" + currentLat + "," + jd + "&output=json&ak=ASl2xmUaYpjwdWy3GPBQbpUK&callback=?&pois=0";
                $.getJSON(URL, function (data) {
                    var status = data.status;
                    if (status == "0") {
                        var oaddr = data.result.addressComponent;
                        me.add = data.result.formatted_address;
                        me.pro = oaddr.province;
                        me.sj = oaddr.city;
                        me.xj = oaddr.district;
                    }
                    adobj.success();
                });
            }
            else {
                var URL = "http://apis.map.qq.com/ws/location/v1/ip?ip=" + adobj.ip + "&key=MWKBZ-DP7A6-IDOSB-MDDUB-JD66S-7QFYW&output=jsonp&callback=?";
                $.getJSON(URL, function (data) {
                    var status = data.status;
                    if (status = "0") {
                        var oaddr = data.result.ad_info;
                        me.pro = oaddr.province;
                        me.sj = oaddr.city;
                        me.xj = "";
                        me.add = oaddr.province + oaddr.city;
                    }
                    else {
                        me.add = "未允许";
                        me.pro = "未允许";
                        me.sj = "未允许";
                        me.xj = "未允许";
                    }
                    adobj.success();
                });
            }
        }, { enableHighAccuracy: true })
    },
    SCAddr: function (scobj) {
        //参数例子：{type:post,url:ashx,uid:25,compid:1,cpid:6674,success:fucs,error:fuce} 
        var me = this;
        $.ajax({
            type: scobj.type,
            url: scobj.url,
            data: { adr: me.add, uid: scobj.uid, cmid: scobj.compid, cid: scobj.cpid, pro: me.pro, sj: me.sj, xj: me.xj }, //请求参数  
            //请求成功执行的方法，function内参数名随意，不影响  
            success: function (result) {
                try {
                    var scimg = JSON.parse(result);
                    scobj.success(scimg);
                } catch (err) {
                    scobj.error(result);
                }
            },
            error: function (res) {
                if (scobj.error) {
                    scobj.error(res);
                }
            }

        })
    }
};

//大数据处理（地址）
var BigNumberWX = {
    WXjssdk: function (jsdkobj) {
        //参数例子：{debug:true,appid:wx123,timestamp:145241,nonceStr;tj,signature:123}
        wx.config({
            debug: jsdkobj.debug,
            appId: jsdkobj.appid,
            timestamp: jsdkobj.timestamp,
            nonceStr: jsdkobj.nonceStr,
            signature: jsdkobj.signature,
            jsApiList: ["chooseImage", "previewImage", "uploadImage", "downloadImage", "scanQRCode", "getLocation"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
    },
    WxDW: function (dwobj) {
        var me = this;
        TJJZloading({ mkid: "jzzid", content: "正在加载" });
        wx.getLocation({
            type: 'wgs84',
            success: function (res) {
                var currentLat = res.latitude; //纬度
                var jd = res.longitude;
                var URL = "http://api.map.baidu.com/geocoder/v2/?location=" + currentLat + "," + jd + "&output=json&ak=ASl2xmUaYpjwdWy3GPBQbpUK&callback=?&pois=0";
                $.getJSON(URL, function (data) {
                    var status = data.status;
                    if (status == "0") {
                        var oaddr = data.result.addressComponent;
                        me.add = data.result.formatted_address;
                        me.pro = oaddr.province;
                        me.sj = oaddr.city;
                        me.xj = oaddr.district;
                    }
                    else {
                        me.add = "未允许";
                        me.pro = "未允许";
                        me.sj = "未允许";
                        me.xj = "未允许";
                    }
                    dwobj.success();
                   
                })
            },
            fail: function () {
                me.add = "未允许";
                me.pro = "未允许";
                me.sj = "未允许";
                me.xj = "未允许";
                dwobj.success();
            },
            cancel: function () {
                me.add = "未允许";
                me.pro = "未允许";
                me.sj = "未允许";
                me.xj = "未允许";
                dwobj.success();
            }
        })
    },
    SCAddr: function (scobj) {
        //参数例子：{type:post,url:ashx,uid:25,compid:1,cpid:6674,success:fucs,error:fuce} 
        var me = this;
        $.ajax({
            type: scobj.type,
            url: scobj.url,
            data: { adr: me.add, uid: scobj.uid, cmid: scobj.compid, cid: scobj.cpid, pro: me.pro, sj: me.sj, xj: me.xj }, //请求参数  
            //请求成功执行的方法，function内参数名随意，不影响  
            success: function (result) {
                try {
                    var scimg = JSON.parse(result);
                    scobj.success(scimg);
                } catch (err) {
                    scobj.error(err);
                }
            },
            error: function (res) {
                if (scobj.error) {
                    scobj.error(res);
                }
            }
        })
    }
};
//微信拍照
var Pzsc = {
    starpz: function (obj) {
        var me = this;
        wx.chooseImage({
            count: 1,
            sizeType: ['original', 'compressed'],
            sourceType: ['album', 'camera'],
            success: function (res) {
                me.picIDS = res.localIds;
                obj.success();
            }
        });
    },
    starsc: function (scobj) {
        //参数例子：{type:post,url:ashx,uid:25,compid:1,cpid:6674,success:fucs,error:fuce}
        var me = this;
        if (me.picIDS.length > 0) {
            wx.uploadImage({
                localId: me.picIDS[0],
                isShowProgressTips: 1,
                success: function (res) {
                    var serverId = res.serverId,
                        uid = scobj.uid,
                        cpid = scobj.cpid,
                        compid = scobj.compid;
                    me.picIDS = [];
                    $.ajax({
                        type: scobj.type, //请求方式：post，get                            
                        url: scobj.url, //请求的页面  
                        data: { picid: serverId, userid: uid, compid: compid, cpid: cpid }, //请求参数  
                        success: function (result) {
                            try {
                                var backdata = JSON.parse(result);
                                me.tjscid = backdata.res;
                                scobj.success(backdata);
                            } catch (err) {
                                scobj.error(result);
                            }
                        },
                        error: function (res) {
                            if (scobj.error) {
                                scobj.error(res);
                            }
                        }
                    });
                }
            })
        } else {
            alert("请重新拍照!!");
        }
    },
    starbd: function (bdobj) {
        //参数例子：{type:post,url:ashx,scid:1, uid:25,compid:1,cpid:6674,success:fucs,error:fuce}
        var me = this;
        $.ajax({
            type: bdobj.type, //请求方式：post，get                            
            url: bdobj.url, //图片处理
            data: { scid: me.tjscid }, //请求参数  
            success: function (result) {
                try {
                    var bdjg = JSON.parse(result);
                    bdobj.success(bdjg);
                } catch (err) {
                    bdobj.error(result);
                }
            },
            error: function (res) {
                if (bdobj.error) {
                    bdobj.error(res);
                }
            }
        })
    }
};

var csjs = {
    starbd: function (bdobj) {
        var j = hfdh;

        return bdobj.success(j);
    }
};