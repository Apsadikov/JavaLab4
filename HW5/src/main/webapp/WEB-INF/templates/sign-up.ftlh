<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link type="text/css" rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="../../static/css/reset.css">
    <link type="text/css" rel="stylesheet" href="../../static/css/style.css">
</head>
<body>
<div class="col-11 col-sm-9 col-md-6 col-lg-4 form">
    <form action="/sign-up" method="post">
        <input type="text" class="form-control input" name="email" placeholder="Email" autocomplete="false">
        <input type="password" class="form-control input" name="password" placeholder="Password">
        <input type="hidden" value="${_csrf.token}" name="${_csrf.parameterName}">
        <input type="submit" class="send" value="Sign Up">
    </form>
</div>
</body>
</html>