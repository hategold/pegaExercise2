<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Brands Records</title>
</head>
<body>

	<table>
		<thead>
			<tr>
				<th>BrandID</th>
				<th>BrandName</th>
				<th>Website</th>
				<th>Country</th>
				<th colspan="2">Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${brandList}" var="brand">
				<tr>
					<td><c:out value="${brand.getBrandID()}" /></td>
					<td><c:out value="${brand.getBrandName()}" /></td>
					<td><c:out value="${brand.getWebsite()}" /></td>
					<td><c:out value="${brand.getCountry()}" /></td>
					<td><a
						href="ShoesDBController.do?action=edit&BrandID=<c:out value="${brand.getBrandID() }"/>">Update</a></td>
					<td><a
						href="ShoesDBController.do?action=delete&BrandID=<c:out value="${brand.getBrandID() }"/>">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<p>
		<a href="ShoesDBController.do?action=insert">Add Shoes Brands</a>
	</p>
</body>
<%-- 	<table>
		<%
		%>
		<tr><td></td></tr>
	</table>> --%>


</body>
</html>
