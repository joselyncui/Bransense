<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Package Path</title>
<link rel="shortcut icon" type="image/png"
	HREF="../img/favicons/brainsense.png" />
<meta name="description" content="Brainsense" />
<meta name="keywords" content="Admin,Brainsense" />

<link rel="stylesheet" href="../css/style.css" type="text/css" />
<link rel="stylesheet" href="../css/custom.css" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="../js/jquery.tipTip.min.js"></script>
<script type="text/javascript" src="../js/jquery.superfish.min.js"></script>
<script type="text/javascript" src="../js/jquery.supersubs.min.js"></script>
<script type="text/javascript" src="../js/jquery.nyroModal.pack.js"></script>
<script type="text/javascript" src="../js/packagePath.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		Administry.setup();
	});
</script>
<style type="text/css">
.logo {
	width: 20%;
}

#page {
	height: 680px;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>Path setting</h1>
		</div>
	</div>
	<div id="page" style="text-align: center">
		<h3></h3>
		<div class="wrapper">
			<section class="column width6 first" style="width: 1000px;">
				<form action="updatePackagePath"  method="post" onsubmit="return checkPath()">
					<table id="path" style="margin: auto;">
						<tr>
							<td></td>
							<td><label style="color: red;">${message }</label></td>
						</tr>
						<tr>
							<td><label for="basePath">Base Path: </label></td>
							<td><input type="text" id="basePath" name="basePath"
								style="width: 280px;" maxlength="200" value="${basePath }" /></td>
						</tr>
						<tr>
							<td></td>
							<td><label>example:/Brainsense/BasePath</label></td>
						</tr>
						<tr>
							<td><label for="contentPath">Content Path: </label></td>
							<td><input type="text" id="contentPath" name="contentPath"
								style="width: 280px;" maxlength="200" value="${contentPath }" /></td>
						</tr>
						<tr>
							<td></td>
							<td><label>example:/Brainsense/ContentPath</label></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" value="Update" class="btn btn-green big" /></td>
						</tr>
					</table>
					<s:token />
				</form>
			</section>


		</div>
	</div>
	<footer id="bottom">
		<div class="wrapper">
			<p>Copyright &copy; 2014</p>
		</div>
	</footer>

	<script type="text/javascript" src="../js/administry.js"></script>
</body>
</html>