<%--
  Created by IntelliJ IDEA.
  User: pyoung
  Date: 2016-11-28
  Time: 11:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<html>
<head>
    <title>Contracts</title>
</head>

<body>
    <div class="container">
        <h1>Contracts</h1>
        <table class="table table-hover">
            <tr>
                <th>Product ID</th>
                <th>Product Type</th>
                <th>Product Name</th>
                <th>Revenue</th>
                <th>Date when signed</th>
                <th>Calculate Revenue</th>
            </tr>
            <c:forEach items="${contracts}" var="contract">
                <tr>
                    <td>${contract.getProductId()}</td>
                    <td>${contract.getProductType()}</td>
                    <td>${contract.getProductName()}</td>
                    <td>${contract.getRevenue()}</td>
                    <td>${contract.getWhenSigned()}</td>
                    <td><a href="${request.getRequestURL()}/contract?id=${contract.getID()}" class="btn btn-default" role="button">View</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
