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
    var json = JSON.parse(event.data);
    $textarea.value = json.cacheInfo;
    console.log("onmessage: " + event.data)
}

function sendCacheQuery() {
  var json = JSON.stringify({
        type : "ru.otus.shtyka.messages.MsgGetCacheEngineInfo",
        to : "DB"
    });
    ws.send(json);
}

function sendActions() {
    var json = JSON.stringify({
        type : "ru.otus.shtyka.messages.MsgSomeActionsSimulate",
        to : "DB"
    });
    ws.send(json);
    sendCacheQuery();
}