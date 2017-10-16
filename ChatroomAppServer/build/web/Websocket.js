var socket;
var nickname = "";
var channel;
var bookmarks = [];
window.onload = () => {
    socket = new WebSocket("ws://localhost:80/server/websocket");
    socket.onmessage = (e) => {
        split(e.data, (command, message) => {
            switch (command) {
            case "ERROR":
                alert(message);
                break;
            case "CHANNEL":
                enterChannel(message);
                break;
            case "MESSAGE":
                postMessage(message);
                break;
            case "USERS":
                setUsers(JSON.parse(message));
                break;
            default:
                console.log(command, message);
        }
        });     
    };
    
    var cookie = document.cookie;
    cookie = cookie.split('; ');
    for (var i = 0; i < cookie.length; i++) {
        var data = cookie[i].split('=');
        console.log(data)
        if (data[0] === "bookmarks") {
            bookmarks = JSON.parse(data[1]);
            setBookmarks();
        }
        if (data[0] === "nickname") {
            setNickname(data[1]);
        }
    }
    
    document.getElementById("bookmark").addEventListener("click", () => {bookmarkChannel();});
    document.getElementById("send").addEventListener("click", () => {sendMessage();});
    document.getElementById("set_button").addEventListener("click", () => {setNickname();});
    document.getElementById("join").addEventListener("click", () => {joinChannel();});
    document.getElementById("leave").addEventListener("click", () => {leaveChannel();});
    document.getElementById("remove_button").addEventListener("click", () => {toggleBookmarks();});
    document.getElementById("message_input").addEventListener("keypress", (e) => {
        if(e.key === "Enter")
            sendMessage();
    });
    document.getElementById("channel_input").addEventListener("keypress", (e) => {
        if(e.key === "Enter")
            joinChannel();
    });
    document.getElementById("name_input").addEventListener("keypress", (e) => {
        if(e.key === "Enter")
            setNickname();
    });
};



function sendMessage() {
    var input = document.getElementById("message_input");
    if (input.value !== "") {
        socket.send("send/"+input.value);
        postMessage("You>"+input.value);
        input.value = "";
    }
}

function setNickname(name) {
    if (name == null) {
        var input = document.getElementById("name_input");
        if (/^.*[^a-zA-Z0-9\s].*$/.exec(input.value) 
                || /^\s*$/.exec(input.value) || /you/i.exec(input.value.toLowerCase())) {
            alert("Invalid Nickname");
            input.value = "";
            return;
        }
        if (input.value.length > 16) {
            alert("Name too long");
            input.value = "";
            return;
        }
        nickname = input.value;
        input.value = "";
    }
    else {
        if (name.length <= 16)
            nickname = name;
    }
    if (nickname !== "") {
        document.cookie = "nickname="+nickname;        
        document.getElementById("name").innerHTML = "Current Nickname: " + nickname;
    }
}

function joinChannel(channel_name) {
    if (channel_name == null) {     
        var input = document.getElementById("channel_input");
        var channel_name = input.value;
    }
    if (channel_name !== "") {
        socket.send("join/"+channel_name+"/"+nickname);
    }
}

function enterChannel(channel_name){
    channel = channel_name;
    document.getElementById("channel_input").value="";
    document.getElementById("home").setAttribute("hidden", true);
    document.getElementById("channel").removeAttribute("hidden");
    document.getElementById("title").innerHTML = channel_name + " ~ " + nickname;
    removeChildren(document.getElementById("messages"));
    var bm_btn = document.getElementById("bookmark");
    isBookmarked()? bm_btn.innerHTML="Bookmarked!":bm_btn.innerHTML="Bookmark";
    postMessage("Joined Channel: " + channel_name);
    socket.send("list/"+channel_name);
}

function leaveChannel(){
    socket.send("exit/"+channel);
    document.getElementById("channel").setAttribute("hidden", true);
    document.getElementById("home").removeAttribute("hidden");
    document.getElementById("title").innerHTML = "";
    channel = null;
}

function postMessage(message) {
    var newMessage = document.createElement('li');
    newMessage.innerHTML = message;
    document.getElementById("messages").appendChild(newMessage);
}

function split(string, callback) {
    for (var i = 0; i < string.length-1; i++) {
        if (string.charAt(i) === "/") {
            callback(string.substring(0,i), string.substring(i+1)); 
            return;
        }
    }
    callback(string, null);
}

function setUsers(users){
    var list = document.createElement('ul');
    for (var i = 0; i < users.length; i++) {
        var user = document.createElement('li');
        user.innerHTML = users[i];
        list.appendChild(user);
    }
    var container = document.getElementById("users");
    if (container.firstChild !== null)
        container.removeChild(container.firstChild);
    container.appendChild(list);
}

function setBookmarks(){
    var list = document.createElement('ul');
    list.id="book";
    for (var i = 0; i < bookmarks.length; i++) {
        var bookmark = document.createElement('li');
        bookmark.class = "bookmark";
        
        var name = document.createElement('p');
        name.addEventListener("click", makeJoin(bookmarks[i]));
        name.innerHTML = bookmarks[i];
        
        var remove = document.createElement('button');
        remove.type = "button";
        remove.setAttribute('class', 'remove-btn hidden')
        remove.addEventListener("click", makeDelete(bookmark, bookmarks[i]));
        remove.innerHTML = "X";
        
        bookmark.appendChild(remove);
        bookmark.appendChild(name);
        
        list.appendChild(bookmark);
    }
    var node = document.getElementById("bookmarks");
    if (node.firstChild !== null)
        removeChildren(node);
    node.appendChild(list);
    
    function makeJoin(name) {
        return function () {
            joinChannel(name);
        };
    }
    function makeDelete(node, name) {
        return function () {
            node.parentNode.removeChild(node);
            bookmarks.splice(getBookmark(name), 1);
            document.cookie = "bookmarks="+JSON.stringify(bookmarks);
        };
    }
    document.cookie = "bookmarks="+JSON.stringify(bookmarks);
}


function bookmarkChannel(){
    if (channel !== null) {
        var button = document.getElementById("bookmark");
        for (var i = 0; i < bookmarks.length; i++) {
            if (bookmarks[i] === channel) {
                bookmarks.splice(i, 1);
                button.innerHTML = "Bookmark";
                setBookmarks();
                return;
            }
        }
        bookmarks.push(channel);
        button.innerHTML = "Bookmarked!";
        setBookmarks();
    }
}

function isBookmarked() {
    for (var i = 0; i < bookmarks.length; i++) {
        if (bookmarks[i] === channel)
            return true;
    }
    return false;
}

function removeChildren(node) {
    while (node.hasChildNodes()) {
        node.removeChild(node.lastChild);
    }
}

function getBookmark(name) {
    for (var i = 0; i < bookmarks.length; i++) {
        if (bookmarks[i] === name) {
            return i;
        }
    }
}

function toggleBookmarks() {
    var list = document.getElementsByClassName("remove-btn");
    for (var i = 0; i < list.length; i++) {        
        if (list.item(i).getAttribute('class')==='remove-btn hidden')
            list.item(i).setAttribute('class', 'remove-btn');
        else list.item(i).setAttribute('class', 'remove-btn hidden');
    }
}