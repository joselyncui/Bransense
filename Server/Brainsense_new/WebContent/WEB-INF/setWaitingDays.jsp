<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>MAC Address manager</title>
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
<script type="text/javascript" src="../js/verify.js"></script>
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

#publicValue {
	width: 190px;
}

td {
	padding: 5px;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>Set waiting days</h1>
		</div>
	</div>
	<div id="page" style="text-align: center">
		<div class="wrapper">
			<div class="clearfix">
				<div class="column width3 first"
					style="margin: 0 auto; float: inherit">
					<h3></h3>
					<div class="file-box">
						<select id="publicSelect" name="publicSelect">
							<option value="1">Waiting days</option>
						</select>
						<hr />
						<br />
						<form action="updateValue" id="update" onsubmit="return checkValueIsNull()">
							<table id="publicDiv">
								<tr>
									<td style="width: 120px;"><label for="publicValue"
										style="font-size: 14px;">Value: </label>
									</td>
									<td><input type="text" id="publicValue" name="publicValue"
										style="width: 210px;" form="update" maxlength="4" value="${currentDays }"
										onkeyup="verifyNum(this)" /></td>
								</tr>
								<tr>
									<td><label for="publicUnit" style="font-size: 14px;">Unit:
									</label></td>
									<td><select id="publicUnit" name="publicUnit"
										form="update" style="width: 220px;">
											<option value="1">day</option>
									</select></td>
								</tr>
								<tr>
									<td colspan="2"><label for="publicDescription"
										style="font-size: 14px;">Description: </label></td>
								</tr>
								<tr>
									<td colspan="2"><textarea id="publicDescription"
											maxlength="200" name="publicDescription" style="width: 340px">${publicDescription }</textarea></td>
								</tr>
								<tr>
									<td colspan="2"><input type="submit"
										class="btn btn-green big" value="Update" /></td>
								</tr>
							</table>
						</form>
						<label style="color:red;">${message }</label>
					</div>
				</div>
			</div>
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