<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>登录注册</title>
</head>
<body>
<a href="/home">主页</a>
<form action="${pageContext.request.contextPath}/hello" method="post">
    <fieldset>
        <legend>登录</legend>
        <p>
            帐号：<input type="text" name="account" style="width: 100px;">
            密码：<input type="text" name="pwd" style="width:100px">
            <input type="submit" value="登录">
        </p>
    </fieldset>
</form>
<form action="${pageContext.request.contextPath}/hello/registered" method="post">
    <fieldset>
        <legend>注册</legend>
        <p>
            帐号：<input type="text" name="account" style="width: 100px;">
            密码：<input type="text" name="pwd" style="width:100px">
            昵称：<input type="text" name="name" style="width:100px">
            <input type="submit" value="注册">
        </p>
    </fieldset>
</form>
</body>
</html>
