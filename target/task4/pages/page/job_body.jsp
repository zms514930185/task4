<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath}/pages/CSS/task8-3.css" rel="stylesheet">
<%--我是职业页的body你造吗，你告诉我，你造吗--%>
<!-- /* 开始以下内容 */ -->
<main>
    <div class="container ">
        <div class="titlebox">
            <span class="title-home">首页></span>
            <div class="title-zhiye"> 职业</div>
        </div>
        <div class="titlebox2">
            <span class="title-fx">方向：</span>
            <ui class="fx-box">
                <li><span class="spanhover">&#12288;全部&#12288;</span></li>
                <li><span class="spanhover">前端开发</span></li>
                <li><span class="spanhover">后端开发</span></li>
                <li><span class="spanhover">移动开发</span></li>
                <li><span class="spanhover">整站开发</span></li>
                <li><span class="spanhover">运营维护</span></li>
            </ui>
        </div>
    </div>
        <div class="container">
           <%-- <div class="qianduan">
                前端开发
            </div>--%>
            <div class="row">
                <c:forEach items="${jobList}" var="job">
                    <div class=" col-lg-4  col-md-6  col-12 col-sm-12  namebox">
                        <div class="namebox1">
                            <div class="ios">
                                <span>${job.name}</span>
                                <div> ${job.ipo}</div>
                            </div>
                            <div class="txbox">
                                <img src="${pageContext.request.contextPath}${job.img}" width="139" height="139" class="xueyuanimg">
                                <div class="xueyuan-p">
                                    <p>${job.name}</p>
                                    <span>${job.ipo}</span>
                                </div>
                            </div>
                            <div class="mkk">
                                <div class="mk-box">
                                    <p class="mk-p">门槛</p>
                                    <c:forEach var="i" begin="1" end="${job.degree}" step="1">
                                        <img src="${pageContext.request.contextPath}/pages/image/小图标/星星.png">
                                    </c:forEach>
                                </div>
                                <div class="ny-box">
                                    <p class="ny">难易程度</p>
                                    <c:forEach var="i" begin="1" end="${job.threshold}" step="1">
                                        <img src="${pageContext.request.contextPath}/pages/image/小图标/星星.png">
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="mkk">
                                <div class="mk-box">
                                    <p class="mk-p">成长周期 </p>
                                    <span>
                                    <p class="xq-red">${job.growthStage}</p>年
                                </span>
                                </div>
                                <div class="ny-box">
                                    <p class="ny">稀缺程度</p>
                                    <span>
                                    <p class="xq-red">${job.num}</p>家公司需要
                                </span>
                                </div>
                            </div>
                            <div class="mkk">
                                <div class="m6-box">
                                    <p class="mk-p">薪资待遇</p>
                                </div>
                                <div class="nx-box">
                                    <div class="nx">
                                        <p class="ny">0-3年</p>
                                        <span>5k-10k/月</span>
                                    </div>
                                    <div class="nx">
                                        <p class="ny">1-3年</p>
                                        <span>10k-20k/月</span>
                                    </div>
                                    <div class="nx">
                                        <p class="ny">0-1年</p>
                                        <span>20k-50k/月</span>
                                    </div>
                                </div>
                            </div>
                            <div class="zaixue">
                                有<span>286</span>人正在学
                            </div>
                            <div class="zaixue1">
                                提示:在你学习之前你应该已经掌握XXXXX、XXXXX、XX等语言基础
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
</main>