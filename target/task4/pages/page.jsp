<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><tiles:getAsString name="title"/></title>
    <link href="${pageContext.request.contextPath}/pages/bootstrap/bootstrap.css" rel="stylesheet">
</head>
<body>
<header>
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="menu"/>
</header>
    <tiles:insertAttribute name="body"/>
    <tiles:insertAttribute name="footer"/>
</body>
</html>