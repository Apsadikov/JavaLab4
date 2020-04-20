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
        <input type="text" placeholder="Room id">
    </label>
    <div class="join">join</div>
</div>
<div class="rooms">
    <div class="room">
        <div class="title">Name</div>
        <div class="messages">
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message blue">
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message blue">
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
        </div>
        <div class="input-wrapper">
            <label>
                <input type="text" class="text" placeholder="Message..">
            </label>
            <div class="send"><i class="fas fa-paper-plane"></i></div>
        </div>
    </div>
    <div class="room">
        <div class="title">Name</div>
        <div class="messages">
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message blue">
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message blue">
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
        </div>
        <div class="input-wrapper">
            <label>
                <input type="text" class="text" placeholder="Message..">
            </label>
            <div class="send"><i class="fas fa-paper-plane"></i></div>
        </div>
    </div>
    <div class="room">
        <div class="title">Name</div>
        <div class="messages">
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message blue">
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message blue">
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
            <div class="message light">
                <div class="name">Ivan</div>
                <div class="text">Text</div>
            </div>
        </div>
        <div class="input-wrapper">
            <label>
                <input type="text" class="text" placeholder="Message..">
            </label>
            <div class="send"><i class="fas fa-paper-plane"></i></div>
        </div>
    </div>
</div>
<script
        src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script>
    let webSocket = new SockJS("http://localhost:8080/chat");
    webSocket.onmessage = function (data) {
        console.log(data);
    };
    webSocket.onopen = function () {
        console.log("open");
        webSocket.send(JSON.stringify({
            header: "message",
            payload: {
                text: "text",
                roomId: "dsopvkas"
            }
        }));
    };
</script>
</body>
</html>