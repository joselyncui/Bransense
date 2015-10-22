<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Base package manager</title>
<link rel="shortcut icon" type="image/png" HREF="../img/favicons/brainsense.png"/>
<meta name="description" content="Brainsense" />
<meta name="keywords" content="Admin,Brainsense" />

<link rel="stylesheet" href="../css/style.css" type="text/css" />
<link rel="stylesheet" href="../css/custom.css" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="../js/jquery.tipTip.min.js"></script>
<script type="text/javascript" src="../js/jquery.superfish.min.js"></script>
<script type="text/javascript" src="../js/jquery.supersubs.min.js"></script>
<script type="text/javascript" src="../js/jquery.nyroModal.pack.js"></script>
<script type="text/javascript" src="../js/basepackageManager.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		Administry.setup();
		$("#typeSelect").val("${typeSelect}");
		$("#languageSelect").val("${languageSelect}");
	});
</script>
<style type="text/css">
.logo {
	width: 20%;
}
#page{
	min-height:680px;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>Base package manager</h1>
		</div>
	</div>
	<div id="page">
		<h3></h3>
		<div class="wrapper">
			<section class="macAddressList">
				<section class="column width6 first" style="width: 1000px;">
					<label style="color: red;">${message }</label>
					<label>Type:</label>
					<select id="typeSelect" name="typeSelect" form="basePackageForm">
						<option value="0">All</option>
						<c:forEach var="type" items="${types }">
							<option value="${type.typeId }">${type.name }</option>
						</c:forEach>
					</select>
					<label>Language:</label>
					<select id="languageSelect" name="languageSelect" form="basePackageForm">
						<option value="0">All</option>
						<c:forEach var="language" items="${languages }">
							<option value="${language.languageId }">${language.name }</option>
						</c:forEach>
					</select>
					<table id="report" class="stylized full" style="width:1000px">
						<thead>
							<tr>
								<th>Id</th>
								<th>Name</th>
								<th>Size(Mb)</th>
								<th>Language</th>
								<th>Type</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="basePackage" items="${packages }">
								<tr>
									<td>${basePackage.baseId }</td>
									<td>${basePackage.name }</td>
									<td><fmt:formatNumber value="${basePackage.size/1024/1024 }" pattern="#,###.##" /></td>
									<td>${basePackage.language.name }</td>
									<td>${basePackage.type.name }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<form id="basePackageForm" action="getBaseByTypeAndLanguage"></form>
					<div class="clear">&nbsp;</div>
					<div class="clear">&nbsp;</div>
				</section>
			</section>
		</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
	<script type="text/javascript" SRC="../js/administry.js"></script>
</body>
</html>