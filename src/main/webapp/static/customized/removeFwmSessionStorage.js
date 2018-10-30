//对应于query_history.jsp页面的js
var RemoveFwmSessionStorage = {};
RemoveFwmSessionStorage.location = {
    bindAll: function () {
        this.setSessionStorage();
    },

    setSessionStorage: function () {
        if (window.sessionStorage) {
            sessionStorage.setItem("loadCount", 1);
        }
    }
};

$(document).ready(function () {
    RemoveFwmSessionStorage.location.bindAll();
});