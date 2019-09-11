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
            <span style="color: #FF0000; ">${msg}</span>
        </p>
    </fieldset>
</form>
<hr>
<form action="${pageContext.request.contextPath}/check/phone" method="post">
    <fieldset>
        <legend>发送验证码</legend>
        <p>
            手机：<input type="text" name="phone" style="width:100px">
            <input type="submit" value="发送验证码">
            <span style="color: #FF0000; ">${checkMsg}</span>
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
            手机：<input type="text" name="phone" style="width:100px">
            验证码：<input type="text" name="code" style="width:100px">
            <input type="submit" value="注册">
            <span style="color: #FF0000; ">${registeredMsg}</span>
        </p>
    </fieldset>
</form>

</body>
</html>
