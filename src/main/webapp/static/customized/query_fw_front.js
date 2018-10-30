//对应于query_fw.jsp页面的js
var FW = {};
FW.location = {
    bindAll: function () {
        FW.location.Events.validateForm();
        if("true"==$("#queryFlag").text()){
            if(window.sessionStorage)
            {
                if(null==sessionStorage.getItem("loadCount")) {
                    this.Events.insertLocationData();
                } else {
                    sessionStorage.removeItem("loadCount");
                }
            }
        }
    },

    Events: {
        validateForm: function () {
            $("#searchForm").validate({
                submitHandler: function (form) {
                    var qrCode = $("#qrcode").val().trim();
                    var validateCode = $("#validateCode").val().trim();

                    if ("" != qrCode && "" != validateCode) {
                        loading('正在提交，请稍等...');
                        form.submit();
                    } else if ("" == qrCode) {
                        alert("请输入防伪码！");
                    } else {
                        alert("请输入验证码！");
                    }
                }
            });
        },

        insertLocationData: function () {
            FW.location.Events.getPosition();
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
                        var qrCode = $('#qrcode').val();
                        var location = rs.address;
                        var addressComponents =  rs.addressComponents;
                        var province = addressComponents.province;
                        var city = addressComponents.city;
                        var town = addressComponents.district;
                        FW.location.Events.insertLocation(qrCode, location, lat, lng, province, city, town);
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

$(document).ready(function() {
    FW.location.bindAll();
});