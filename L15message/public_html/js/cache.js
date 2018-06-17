var ws;

init = function () {
    ws = new WebSocket("ws://localhost:8080/cache");
    ws.onopen = function (event) {

    }
    ws.onmessage = function (event) {
        setInterval(function(){writeCacheInfo(event)}, 1000);
    }

    ws.onclose = function (event) {

    }
    //deleteAllCookies();
};

function writeCacheInfo(event){
    var $textarea = document.getElementById("cache");
    $textarea.value = event.data;
    console.log("onmessage: " + event.data)
}

function getCookie(name) {
  var matches = document.cookie.match(new RegExp(
    "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
  ));
  return matches ? decodeURIComponent(matches[1]) : undefined;
}

function deleteAllCookies() {
    var cookies = document.cookie.split(";");

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("=");
        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }
}

function sendCacheQuery() {
    ws.send("cache");
}