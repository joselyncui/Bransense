<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Admin</title>
<meta name="description" content="Brainsense" />
<link rel="shortcut icon" type="image/png"
	HREF="../img/favicons/brainsense.png" />
<meta name="keywords" content="Admin" />
<link rel="stylesheet" href="../css/style.css" type="text/css" />
<link rel="stylesheet" href="../css/custom.css" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<script>
	$(document).ready(function() {
		Administry.setup();
	});
</script>
<style type="text/css">
.logo {
	width: 20%;
}

#page {
	height: 720px;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>For test page</h1>
		</div>
	</div>
	<div id="page">
		<div class="wrapper">
			<table id="report" class="stylized full" style="">
				<thead>
					<tr>
						<th>MAC</th>
						<th>User Name</th>
						<th>Email</th>
						<th>Reset</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${users}">
						<tr>
							<td>${user.device.macId }</td>
							<td>${user.name }</td>
							<td>${user.email }</td>
							<td><a href="resetUser?macId=${user.device.macId }">Reset</a></td>
							<td><a href="deleteUser?macId=${user.device.macId }">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="clear">&nbsp;</div>
			<div class="clear">&nbsp;</div>
		</div>
	</div>
	
	<jsp:include page="footer.jsp"></jsp:include>
	<script type="text/javascript" SRC="../js/administry.js"></script>
</body>
</html>