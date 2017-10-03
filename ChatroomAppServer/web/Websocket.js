var socket;
var connected = false;
window.onload = () => {
    console.log("Window Loaded");
    socket = new WebSocket("ws://localhost:8080/server/websocket");
    socket.onmessage = (e) => {
        var message = e.data;
        switch (message) {
            case "connected": 
                connected = true;
                break;
            default:
                console.log(message);
        }
    };
    
    socket.onopen = (e) => {
      connectd = true;  
    };
};

function send(message) {
    socket.send(message);
}