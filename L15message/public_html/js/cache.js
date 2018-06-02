var ws;

init = function () {
    ws = new WebSocket("ws://localhost:8080/cache");
    ws.onopen = function (event) {

    }
    ws.onmessage = function (event) {
        var $textarea = document.getElementById("cache");
        $textarea.value = event.data;
        console.log("onmessage: " + event.data)
    }

    var $input = document.getElementById("username");
    $input.value = getCookie("login");

    ws.onclose = function (event) {

    }
    deleteAllCookies();
};

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