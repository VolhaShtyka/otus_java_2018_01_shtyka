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
};

function writeCacheInfo(event){
    var $textarea = document.getElementById("cache");
    $textarea.value = event.data;
    console.log("onmessage: " + event.data)
}

function sendCacheQuery() {
    ws.send("cache");
}