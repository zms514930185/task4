<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!--导航栏起始 -->
<div style="background-color:#26a26f">
    <div class="container">
        <div class="row ">
            <nav class="navbar navbar-expand-lg navbar-light bg-light ">
                <a class="navbar-brand" href="#">
                    <div>技能树 </div>
                    <p> www.jnshu.com</p>
                </a>
                <div>
                    <button class="navbar-toggler" type="button" data-toggle="collapse"
                            data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse" id="navbarTogglerDemo02" style=" text-align: center">
                    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                        <li class="nav-item active ">
                            <a class="nav-link " href="/home">首页
                                <span class="sr-only">(current)</span>
                            </a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link" href="/u/job">职业</a>
                        </li>
                        <%--
                        <li class="nav-item active">
                            <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">关于</a>
                        </li>--%>
                    </ul>
                </div>
            </nav>
        </div>
    </div>
</div>
<!-- 导航结尾 -->