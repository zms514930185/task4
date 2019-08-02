<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath}/pages/CSS/task8-1.css" rel="stylesheet">
<%--主页body--%>
<!-- banner图 -->
<div id="carouselExampleIndicators" class="carousel slide carousel-fade" data-ride="carousel">
    <ol class="carousel-indicators">
        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner">
        <div class="carousel-item active">
            <img src="${pageContext.request.contextPath}/pages/image/banner/banner1.png" class="d-block w-100" alt="...">
        </div>
        <div class="carousel-item">
            <img src="${pageContext.request.contextPath}/pages/image/banner/banner2.png" class="d-block w-100" alt="...">
        </div>
        <div class="carousel-item">
            <img src="${pageContext.request.contextPath}/pages/image/banner/banner4.png" class="d-block w-100" alt="...">
        </div>
    </div>
    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>
<!--banner图结束-->
<main>
    <div class="container">
        <div class="row mainbox1">
            <div class="gaoxiao-p col-md-6 col-sm-6 col-12 col-lg-3">
                <div class="gaoxiao-icon "></div>
                <h4>高效</h4>
                <span>将五到七年的成长时间，缩短到一年到三年。</span>
            </div>
            <div class="guifan-p col-md-6 col-sm-6 col-12 col-lg-3">
                <div class="guifan-icon "></div>
                <h4>规范</h4>
                <span>标准的实战教程，不会走弯路。</span>
            </div>
            <div class="renmai-p col-md-6 col-sm-6 col-12 col-lg-3">
                <div class="renmai-icon "></div>
                <h4>人脉</h4>
                <span>同班好友，同院学长，技术大师，入学就混入职脉圈，为以后铺平道路。</span>
            </div>
            <div class="yonghu col-md-6 col-sm-6 col-12 col-lg-3">
                <div>
                    <div class="yonghu-p">
                        <div class="yonghu-icon"></div>
                        <p class="renshu">10240</p>
                    </div>
                    <div class="renshu-p">累计在线学习人数</div>
                    <div class="yonghu-p">
                        <div class="yonghu-icon"></div>
                        <p class="renshu">10240</p>
                    </div>
                    <div class="gongzuo-p">学员已经找到满意工作</div>
                </div>
            </div>
        </div>
    </div>
    <div class="container how ">
        <div class="howStudy">如何学习</div>
        <div class="row ">
            <div class="col-lg-3 col-md-6  col-12 howHeight">
                <div class="number">1</div>
                <div class="numberWord">匹配你现在的个人情况寻找适合自己的岗位</div>
                <div class="arrow"></div>
            </div>
            <div class="col-lg-3 col-md-6 col-12 howHeight ">
                <div class="number">2</div>
                <div class="numberWord">了解职业前景薪金待遇、竞争压力等</div>
                <div class="arrow22"></div>
            </div>
            <div class="col-lg-3 col-md-6 col-12  howHeight ">
                <div class="number">3</div>
                <div class="numberWord">掌握行业内顶级技能</div>
                <div class="arrow"></div>
            </div>
            <div class="col-lg-3 col-md-6  col-12 howHeight ">
                <div class="number">4</div>
                <div class="numberWord">查看职业目标任务</div>
                <div class="arrow2"></div>
            </div>
        </div>
        <div class="row ">
            <div class="col-lg-3 col-md-6  col-12 howHeight">
                <div class="number">5</div>
                <div class="numberWord">参考学习资源，掌握
                    技能点，逐个完成任务
                </div>
                <div class="arrow"></div>
            </div>
            <div class="col-lg-3 col-md-6 col-12 howHeight ">
                <div class="number">6</div>
                <div class="numberWord">加入班级，和小伙伴们
                    互帮互助，一块学习
                </div>
                <div class="arrow22"></div>
            </div>
            <div class="col-lg-3 col-md-6 col-12  howHeight ">
                <div class="number">7</div>
                <div class="numberWord">选择导师，一路引导，
                    加速自己成长
                </div>
                <div class="arrow"></div>
            </div>
            <div class="col-lg-3 col-md-6  col-12 howHeight ">
                <div class="number">8</div>
                <div class="numberWord">完成职业技能，升
                    级业界大牛
                </div>
                <div class="arrow2"></div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="youxiu">优秀学员展示</div>
        <div class="row">
            <c:forEach var="userVOList" items="${userVOList}">
            <div class="col-lg-3 col-md-6  col-12  ">
                <div class="yxbox">
                    <img src="${pageContext.request.contextPath}${userVOList.img}" class="xyimg">
                    <span>${userVOList.jobName}：${userVOList.name}</span>
                    <p>${userVOList.ipo}</p>
                </div>
            </div>
            </c:forEach>
            <%--<div class="col-lg-3 col-md-6  col-12  ">
                <div class="yxbox1 ">
                    <img src="${pageContext.request.contextPath}/pages/image/学员展示/2.png" class="xyimg">
                    <span>发型顾问：汤小尼</span>
                    <p>百度技术总监：互联网基础
                        服务领域，从事虚拟主机、
                        云服务器、域名。曾任新网
                        高级技术经理，负责技术研
                        发、团队管理与建设。</p>
                </div>
            </div>
            <div class="col-lg-3 col-md-6  col-12 ">
                <div class="yxbox1 yxbox-1">
                    <img src="${pageContext.request.contextPath}/pages/image/学员展示/3.png" class="xyimg">
                    <span>发型顾问：汤小尼</span>
                    <p>百度技术总监：互联网基础
                        服务领域，从事虚拟主机、
                        云服务器、域名。曾任新网
                        高级技术经理，负责技术研
                        发、团队管理与建设。</p>
                </div>
            </div>
            <div class="col-lg-3 col-md-6  col-12">
                <div class="yxbox1 ybox2">
                    <img src="${pageContext.request.contextPath}/pages/image/学员展示/4.png" class="xyimg">
                    <span>发型顾问：汤小尼</span>
                    <p>百度技术总监：互联网基础
                        服务领域，从事虚拟主机、
                        云服务器、域名。曾任新网
                        高级技术经理，负责技术研
                        发、团队管理与建设。</p>
                </div>
            </div>--%>
            <div class="container">
                <div class="col-lg-3 col-md-6  col-12 ybbox1">
                    <div class="ydbox">
                        <span class="yd"></span>
                        <span class="yd"></span>
                        <span class="yd"></span>
                        <span class="yd"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="zlqy">战略合作企业</div>
        <div class="row">
            <div class="col-lg-12 col-md-12  col-12 hezuo-box">
                <img src="${pageContext.request.contextPath}/pages/image/小图标/阿里巴巴.png" class="hezuo">
                <img src="${pageContext.request.contextPath}/pages/image/小图标/金山云.png" class="hezuo">
                <img src="${pageContext.request.contextPath}/pages/image/小图标/环信.png" class="hezuo">
                <img src="${pageContext.request.contextPath}/pages/image/小图标/容联.png" class="hezuo">
                <img src="${pageContext.request.contextPath}/pages/image/小图标/骑牛1.png" class="hezuo">
            </div>
        </div>
    </div>
</main>