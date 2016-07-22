<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert/Edit the shoes brands record</title>
</head>
<body>
	<form action="ControlServlet.do" method="post">
		<fieldset>
			<div>
				<label>BrandID</label><input type="text" name="brandID"
					value="${BrandID}" readonly="readonly">
			</div>
			<div>
				<label>BandName</label><input type="text" name="brandName"
					value="${BrandName}">
			</div>
			<div>
				<label>Website</label><input type="text" name="website"
					value="${Website}">
			</div>
			<div>
				<label>Country</label><input type="text" name="country"
					value="${Country}">
			</div>
			<div>
				<input type="submit" value="Submit" />
			</div>
		</fieldset>
	</form>
</body>
</html>