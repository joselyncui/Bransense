<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Add Properties</title>
<meta name="description" content="Brainsense" />
<meta name="keywords" content="Admin,Brainsense" />
<link rel="shortcut icon" type="image/png" HREF="../img/favicons/brainsense.png"/>

<link rel="stylesheet" href="../css/style.css" type="text/css" />
<link rel="stylesheet" href="../css/custom.css" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="../js/jquery.tipTip.min.js"></script>
<script type="text/javascript" src="../js/jquery.superfish.min.js"></script>
<script type="text/javascript" src="../js/jquery.supersubs.min.js"></script>
<script type="text/javascript" src="../js/jquery.validate_pack.js"></script>
<script type="text/javascript" src="../js/jquery.nyroModal.pack.js"></script>
<script type="text/javascript" src="../js/properties.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		Administry.setup();
		$("#typeSelect").val("${select}");
	});
</script>
<style type="text/css">
.logo {
	width: 20%;
}
</style>
<style>
.liketable {
	width: 270px;
}

.liketable tr {
	height: 50px;
}

.liketable tr:nth(2) td {
	text-align: right;
	background: red;
	height: 1000px;
}

.liketable input {
	width: 200px;
}

.liketable select {
	width: 210px;
}

#propertiesTb tbody tr:hover {
	background-color: #5a98de;
	cursor: pointer;
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
			<h1>Add properties</h1>
		</div>
	</div>
	<div id="page">
		<div class="wrapper">
			<div class="colgroup leading">
				<div class="column width3 first">
					<h4>
						Value List: <a href="#"></a>
					</h4>
					<hr />
					<div>
						<input type="text" id="txtPropertiesName" name = "propertiesName" style="width: 180px;" form="add" maxlength="20" /> 
						<input type="button" class="btn btn-green big" id="btnUpdate" value="Update" />
						<input type="button" class="btn btn-green big" id="btnDelete" value="Delete" />
						<input type="hidden" id="hidPropertiesId" name = "propertiesId" form="add" />
					</div>
					<h4></h4>
					<table class="no-style full tbl" id="propertiesTb">
						<thead>
							<tr>
								<th>Id</th>
								<th class="ta-center">Name</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${results }">
								<tr>
									<c:if test="${select == 1}">
										<td class="id">${result.languageId }</td>
									</c:if>
									<c:if test="${select == 2}">
										<td class="id">${result.countryId }</td>
									</c:if>
									<c:if test="${select == 3}">
										<td class="id">${result.categoryId }</td>
									</c:if>
									<c:if test="${select == 4 }">
										<td class="id">${result.typeId }</td>
									</c:if>
									<td class="ta-center name">${result.name }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="column width3">
					<h4>
						Add: <a href="#"></a>
					</h4>
					<hr />
					<form id="add">
						<table class="liketable">
							<tr>
								<td><strong>Name:</strong></td>
								<td><input type="text" class="half" id="addPropertiesName"
									name="addPropertiesName" maxlength="20" /></td>
							</tr>
							<tr>
								<td><strong>Type:</strong></td>
								<td><select id="typeSelect" name="typeSelect">
										<option value="1">Language</option>
										<option value="2">Country</option>
										<option value="3">Category</option>
										<option value="4">Type</option>
								</select></td>
							</tr>
							<tr>
								<td></td>
								<td><input type="submit" id="btnSave"
									class="btn btn-green big" value="Save"></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
	<jsp:include page="footer.jsp"></jsp:include>
    </div>
	<script type="text/javascript" SRC="../js/administry.js"></script>
</body>
</html>