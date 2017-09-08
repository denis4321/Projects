//window.onload = init;
var socket = new WebSocket("ws://localhost:7001/ExportWS/export");
socket.onmessage = onMessage;

function onMessage(event) {   
    var blob1 = event.data;
    var fileName1 = "tapp.xlsx";
    saveAs(blob1, fileName1);
}

function makeExport() {    
    //addDevice(name, type, description);
    var json = document.getElementById("json_param").value;
    socket.send(json);    
}






