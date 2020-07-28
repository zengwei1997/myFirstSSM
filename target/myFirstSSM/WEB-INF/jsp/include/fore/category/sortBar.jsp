<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/7/3
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    $(function () {
        $("input.sortBarPrice").onkeyup(function () {
            var num = $(this).val();
            if(num.length == 0){
                $("div.productUnit").show();
                return;
            }
            if(isNaN(num))
                num = 0;
            if(num < 0)
                num = 0;
            $(this).val(num);
            var begin = $("input.beginPrice").val();
            var end = $("input.endPrice").val();
            if(!isNaN(begin) && !isNaN(end)){
                $("div.productUnit").hidden();
                $("div.productUnit").each(function(){
                    var price = $(this).attr("price");
                    price = new Number(price);
                    if(price <= end && price >= begin){
                        $(this).show();
                    }
                });
            }

        });
    });
</script>

<div class="categorySortBar">
    <%--综合，人气，新品，销量，价格，--%>
    <table class="categorySortBarTable categorySortTable">
        <tr>

            <%--category.jsp?sort=all
            param.sort 是EL表达式的取参等同于request.getParameter("sort")--%>

            <td<c:if test="${'all' == param.sort || empty param.sort}"> class = "grayColumn"</c:if>>
                <a href="?cid=${c.id}&sort=all">综合<span class="glyphicon glyphicon-arrow-down"></span></a>
            </td>
            <td<c:if test="${'review' == param.sort}"> class = "grayColumn"</c:if>>
                <a href="?cid=${c.id}&sort=review">人气<span class="glyphicon glyphicon-arrow-down"></span></a>
            </td>
            <td<c:if test="${'date' == param.sort}"> class = "grayColumn"</c:if>>
                <a href="?cid=${c.id}&sort=date">新品<span class="glyphicon glyphicon-arrow-down"></span></a>
            </td>
            <td<c:if test="${'saleCount' == param.sort}"> class = "grayColumn"</c:if>>
                <a href="?cid=${c.id}&sort=saleCount">销量<span class="glyphicon glyphicon-arrow-down"></span></a>
            </td>
            <td<c:if test="${'price' == param.sort}"> class = "grayColumn"</c:if>>
                <a href="?cid=${c.id}&sort=price">价格<span class="glyphicon glyphicon-resize-vertical"></span></a>
            </td>
        </tr>
    </table>

    <%--价格区间--%>
    <table class="categorySortBarTable" >
        <tr>
            <td><input class="sortBarPrice beginPrice" type="text" placeholder="请输入"/></td>
            <td class="grayColumn priceMiddleColumn">-</td>
            <td><input class="sortBarPrice endPrice" type="text" placeholder="请输入"/></td>
            <input type="password" autocomplete="off" style="display: none"/>
        </tr>
    </table>
</div>