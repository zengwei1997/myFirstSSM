<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/7/11
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
pageEncoding="UTF-8" isELIgnored="false" %>

<title>管理员登陆</title>
<body style="padding-top: 0px">
<%@ include file="../include/admin/adminHeader.jsp"%>;

<script>
    $(function () {
        <c:if test="${!empty msg}">
        $("span.errorMessage").html("${msg}");
        $("div.loginErrorMessageDiv").show();
        </c:if>

        $("form.loginForm").submit(function(){
            if(0==$("#name").val().length||0==$("#password").val().length){
                $("span.errorMessage").html("请输入账号密码");
                $("div.loginErrorMessageDiv").show();
                return false;
            }
            return true;
        });

        $("form.loginForm input").keyup(function(){
            $("div.loginErrorMessageDiv").hide();
        });


    });
</script>
<div class="admin_login_div">

    <div class="adminLoginImage">
        <img src="img/site/simpleLogo.png"/>
    </div>
    <div class="adminLoginForm">
        <form class="loginForm" action="admin_login" method="post">
            <div id="loginSmallDiv" class="loginSmallDiv">
                <div class="loginErrorMessageDiv">
                    <div class="alert alert-danger" >
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                        <span class="errorMessage"></span>
                    </div>
                </div>

                <div class="login_account_text">管理员账户登录</div>
                <div class="loginInput" >
                    <span class="loginInputIcon ">
                        <span class=" glyphicon glyphicon-user"></span>
                    </span>
                    <input id="name" name="name" placeholder="手机/会员名/邮箱" type="text">
                </div>

                <div class="loginInput" >
                    <span class="loginInputIcon ">
                        <span class=" glyphicon glyphicon-lock"></span>
                    </span>
                    <input id="password" name="password" type="password" placeholder="密码" type="text">
                </div>

                <div style="margin-top:20px">
                    <button class="btn btn-block redButton" type="submit">登录</button>
                </div>
                <div style="clear: both"></div>
            </div>
        </form>
    </div>
    <div style="clear:both;"></div>

</div>


<%@ include file="../include/admin/adminFooter.jsp"%>
</body>