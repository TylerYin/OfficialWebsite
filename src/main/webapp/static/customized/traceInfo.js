//对应于traceInfo.jsp页面的js
var TraceInfo = {};
TraceInfo.location = {
    bindAll: function () {
        if (window.sessionStorage) {
            var selectNum = parseInt($("#qrCodeId").text());
            if (null == sessionStorage.getItem("loadCount")) {
                if (selectNum > 1) {
                    $("#qrCodeDiv").css("color", "#d53b06");
                    $("#warningId").append("，谨防假冒");
                }
                this.Events.insertLocationData();
            } else {
                if (selectNum > 2) {
                    $("#qrCodeDiv").css("color", "#d53b06");
                    $("#warningId").append("，谨防假冒");
                }
                sessionStorage.removeItem("loadCount");
                $("#qrCodeId").text(selectNum - 1);
            }
        }
    },

    Events: {
        insertLocationData: function () {
            var sUserAgent = navigator.userAgent.toLowerCase();
            var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
            var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
            var bIsMidp = sUserAgent.match(/midp/i) == "midp";
            var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
            var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
            var bIsAndroid = sUserAgent.match(/android/i) == "android";
            var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
            var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";

            if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc
                || bIsAndroid || bIsCE || bIsWM) {
                TraceInfo.location.Events.getPosition();
            }
        },

        getPosition: function () {
            var map = new BMap.Map("allmap");
            var point = new BMap.Point(116.331398, 39.897445);
            map.centerAndZoom(point, 12);

            var geolocation = new BMap.Geolocation();
            geolocation.getCurrentPosition(function (r) {
                if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                    var mk = new BMap.Marker(r.point);
                    map.addOverlay(mk);
                    map.panTo(r.point);

                    var gc = new BMap.Geocoder();
                    gc.getLocation(r.point, function (rs) {
                        var lat = r.point.lat;
                        var lng = r.point.lng;
                        var qrCode = $('#qrCode').val();
                        var location = rs.address;
                        var addressComponents = rs.addressComponents;
                        var province = addressComponents.province;
                        var city = addressComponents.city;
                        var town = addressComponents.district;
                        TraceInfo.location.Events.insertLocation(qrCode, location, lat, lng, province, city, town);
                    });
                } else {
                    alert('无法获取位置信息');
                }
            }, {
                enableHighAccuracy: true
            });
        },

        insertLocation: function (qrCode, location, lat, lng, province, city, town) {
            $.ajax({
                url: $('#insertQueryDataURL').text(),
                type: "POST",
                cache: false,
                dataType: 'json',
                data: {
                    "lat": lat,
                    "lng": lng,
                    "province": province,
                    "city": city,
                    "town": town,
                    "qrCode": qrCode,
                    "location": location
                }
            });
        }
    }
};

$(document).ready(function () {
    TraceInfo.location.bindAll();
});