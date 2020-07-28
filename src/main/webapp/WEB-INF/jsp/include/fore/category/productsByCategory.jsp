<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/7/3
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
pageEncoding="UTF-8" isELIgnored="false" %>

<div class="categoryProducts">
    <c:forEach items="${c.products}" var="p" varStatus="st">
        <div class="productUnit" price="${p.promotePrice}">
            <div class="productUnitFrame">
                <a href="foreproduct?pid=${p.id}">
                    <img src="img/productSingle_middle/${p.firstProductImage.id}.jpg">
                </a>
                <span class="productPrice">￥<fmt:formatNumber type="number" value="${p.promotePrice}"
                    minFractionDigits="2"/> </span>
                <a class="tmallLink" href="foreproduct?pid=${p.id}">天猫专卖</a>
                <div class="show1 productInfo">
                    <span class="monthDeal">月成交
                        <span class= "productDealNumber">${p.saleCount}笔</span>
                    </span>
                    <span class="productReview">评价
                        <span class="productReviewNumber">${p.reviewCount}</span>
                    </span>
                    <span class="wangwang">
                        <a class="wangwanglink" href="#nowhere">
                            <img src="img/site/wangwang.png">
                        </a>
                    </span>
                </div>
            </div>
        </div>
    </c:forEach>
        <div style="clear: both"></div>
</div>