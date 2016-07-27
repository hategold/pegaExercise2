<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert/Edit the shoes brands record</title>

</head>
<body>
	<div class="container">
		<div class="row" id="up-padding">
			<div class="col-xs-12 col-md-12 col-lg-12 vcenter">
				<div style="height: 4em;"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-offset-2 col-md-8 jumbotron">
				<h2>Insert/Edit the shoes brands record</h2>
				<form action="ShoesDBController.do" method="post">
					<fieldset>
						<div class="form-group">
							<label>BrandID</label><input type="text" name="brandID"
								value="${brand.getBrandID()}" class="form-control" readonly>
						</div>
						<div class="form-group">
							<label>BandName</label><input type="text" name="brandName"
								value="${brand.getBrandName()}" class="form-control">
						</div>
						<div class="form-group">
							<label>Website</label><input type="text" name="website"
								value="${brand.getWebsite()}" class="form-control">
						</div>
						<div class="form-group">
							<label>Country</label><input type="text" name="country"
								value="${brand.getCountry()}" class="form-control">
						</div>

						<button class="btn btn-success" type="submit">Submit</button>

					</fieldset>
				</form>
			</div>
		</div>
	</div>
</body>
</html>