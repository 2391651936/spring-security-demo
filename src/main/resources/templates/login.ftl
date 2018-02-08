<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          co1ntent="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="/a.css">
    <title>Document</title>
</head>
<body>
    <#if (SPRING_SECURITY_LAST_EXCEPTION.message)?? >
        ${SPRING_SECURITY_LAST_EXCEPTION.message}
    </#if>
    <form method="post" action="/login">
        账号 <input type="text" name="username" autofocus>
        密码 <input type="password" name="password">
        验证码 <input type="text" name="validate"> <img src="/captcha">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" name="submit">
    </form>
</body>
</html>