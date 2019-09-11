<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath}/pages/CSS/task8-1.css" rel="stylesheet">
用户昵称：${userName}<br>
用户头像：<img src="${userImg}" width="139" height="139" ><br>
<form name="serForm" action="${pageContext.request.contextPath}/u/user" method="post" enctype="multipart/form-data">
    <input type="file" name="file">
    <input type="submit" value="上传"/>
    <span style="color: #FF0000; ">${imgMsg}</span>
</form>