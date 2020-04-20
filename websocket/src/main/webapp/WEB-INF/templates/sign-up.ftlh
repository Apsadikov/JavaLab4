<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/static/css/reset.css">
    <link rel="stylesheet" href="/static/css/sign-in.css">
</head>
<body>
<div class="form-wrapper">
    <div class="form">
        <div class="title">Sign Up</div>
        <form action="/sign-up" class="inputs" method="post">
            <div class="input-wrapper">
                <label for="name" class="label">Name</label>
                <input type="text" id="name" name="name" placeholder="Name" class="input">
            </div>
            <div class="input-wrapper">
                <label for="email" class="label">Email</label>
                <input type="text" id="email" name="email" placeholder="Email" class="input">
            </div>
            <div class="input-wrapper">
                <label for="password" class="label">Password</label>
                <input type="password" id="password" name="password" placeholder="Password" class="input">
            </div>
            <input type="submit" id="send" value="Sign Up" class="button">
        </form>
    </div>
</div>
</body>
</html>