<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <h1>success</h1>

    <h3>账号：${user.username}</h3>
    <h3>密码：${user.password}</h3>

    <form method="post" action="/logout">
        <input type="submit" value="logout">
        <#--使用csrf防伪验证-->
        <input id="csrf" type="hidden" name="_csrf" value="${_csrf.token}">
    </form>
</body>
</html>