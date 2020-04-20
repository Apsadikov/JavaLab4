<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/static/css/reset.css">
    <link rel="stylesheet" href="/static/css/index.css">
    <link rel="stylesheet" href="/static/fontawesome-free-5.13.0-web/css/all.min.css">
</head>
<body>
<div class="join-room">
    <label>
        <input type="text" placeholder="Room id" id="id">
    </label>
    <div class="join" id="join">join</div>
</div>
<div class="rooms" id="rooms">
</div>
<script
        src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script>
    let webSocket = new SockJS("http://localhost:8080/chat");
    webSocket.onmessage = function (data) {
        let message = JSON.parse(data.data).payload;
        let messagesAttr = "[data-room-id-messages=" + message.roomId + "]";
        let messages = $(messagesAttr).toArray()[0];
        messages.innerHTML +=
                "<div class=\"message light\">" +
                "<div class=\"name\">" + message.name + "</div>" +
                "<div class=\"text\">" + message.text + "</div>" +
                "</div>";
    };

    let roomId = $("#id");
    let join = $("#join");
    let rooms = $("#rooms");
    join.click(function () {
        let id = roomId.val();
        roomId.val("");
        webSocket.send(JSON.stringify({
            header: "room",
            payload: {
                id: id
            }
        }));
        rooms.append(
                "<div class=\"room\">" +
                "<div class=\"title\"> " + id + "</div>" +
                "<div class=\"messages\" data-room-id-messages=\"" + id + "\">" +
                "</div>" +
                "<div class=\"input-wrapper\">" +
                "<label>" +
                "<input type=\"text\" class=\"text\" placeholder=\"Message..\" data-room-id-text=\"" + id + "\">" +
                "</label>" +
                "<div class=\"send\"><i class=\"fas fa-paper-plane\" data-room-id=\"" + id + "\"></i></div>" +
                "</div>" +
                "</div>"
        );
    });

    rooms.click(function (event) {
        let roomId = event.target.dataset.roomId;
        if (roomId) {
            let textAttr = "[data-room-id-text=" + roomId + "]";
            let messagesAttr = "[data-room-id-messages=" + roomId + "]";
            let messages = $(messagesAttr).toArray()[0];
            let text = $(textAttr).toArray()[0].value;
            webSocket.send(JSON.stringify({
                header: "message",
                payload: {
                    text: text,
                    roomId: roomId
                }
            }));
            messages.innerHTML +=
                    "<div class=\"message blue\">" +
                    "<div class=\"text\">" + text + "</div>" +
                    "</div>";
        }
    });
</script>
</body>
</html>