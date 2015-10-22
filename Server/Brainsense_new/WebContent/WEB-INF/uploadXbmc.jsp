<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Upload XBMC</title>
<meta name="description" content="Brainsense" />
<meta name="keywords" content="Admin,Brainsense" />
<link rel="shortcut icon" type="image/png"
	HREF="../img/favicons/brainsense.png" />
<link rel="stylesheet" href="../css/style.css" type="text/css" />
<link rel="stylesheet" href="../css/custom.css" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="../js/jquery.tipTip.min.js"></script>
<script type="text/javascript" src="../js/jquery.superfish.min.js"></script>
<script type="text/javascript" src="../js/jquery.supersubs.min.js"></script>
<script type="text/javascript" src="../js/jquery.validate_pack.js"></script>
<script type="text/javascript" src="../js/jquery.nyroModal.pack.js"></script>
<script type="text/javascript" src="../js/verify.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	Administry.setup();
});
</script>
<style type="text/css">
.logo {
	width: 20%;
}

.file-box {
	position: relative;
}

.txt {
	height: 22px;
	border: 1px solid #cdcdcd;
	width: 180px;
}

.btn {
	background-color: #FFF;
	border: 1px solid #CDCDCD;
}

.file {
	position: absolute;
	top: 0;
	right: 80px;
	height: 24px;
	filter: alpha(opacity : 0);
	opacity: 0;
	width: 260px
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
			<h1>Upload XBMC</h1>
		</div>
	</div>
	<div id="page" style="text-align: center">
		<div class="clearfix">
			<div class="column width3 first"
				style="margin: 0 auto; float: inherit">
				<h3></h3>
				<div class="file-box">
					<form action="uploadXbmcFile" method="post"
						enctype="multipart/form-data" onsubmit="return validateXbmcName()">
						<input type='button' class='btn' value='Browse' /> <input
							type='text' name='xbmcName' id='textfield' class='txt' /> <input
							type="file" name="xbmcFile" class="file" id="fileField" size="28"
							onchange="document.getElementById('textfield').value=this.value" />
						&nbsp;&nbsp;&nbsp;
						<input type="submit" class="btn btn-green big" value="Upload" />
						<input type="radio" id="rdoAvailable" checked="checked"
							name="rdoAvailable" value="1" /><label for="rdoAvailable">Available</label>
						<c:if test="${fn:length(xbmcs) != 0}">
							<input type="radio" id="rdoUnavailable" name="rdoAvailable"
							value="0" /><label for="rdoUnavailable">Unavailable</label> <br />
						</c:if>
						<br/>
						<label style="color: red;">${message }</label>
						<s:fielderror cssStyle="color: red" fieldName="xbmcName" />
					</form>
				</div>
				<h3></h3>

				<h3></h3>

			</div>
			<div class="wrapper">
				<table id="report" class="stylized full" style="">
					<thead>
						<tr>
							<th>Name</th>
							<th>Path</th>
							<th>Version</th>
							<th>Status</th>
							<th>Delete</th>
							<th>Available</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="xbmc" items="${xbmcs}">
							<tr>
								<td>xbmc.apk</td>
								<td>${xbmc.packagePath }</td>
								<td>${xbmc.versionName }</td>
								<c:if test="${xbmc.available == 1 }">
									<td style = "color:green;">Available</td>
								</c:if>
								<c:if test="${xbmc.available == 0 }">
									<td style = "color:red;">Unavailable</td>
								</c:if>
								<td><a href="deleteXBMC?xbmcId=${xbmc.xbmcId }">Delete</a></td>
								<c:if test="${xbmc.available == 0 }">
									<td><a href="setAvailable?xbmcId=${xbmc.xbmcId }">Set available</a></td>
								</c:if>
								<c:if test="${xbmc.available == 1 }">
									<td></td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<form id="macForm"></form>
				<div class="clear">&nbsp;</div>
				<div class="clear">&nbsp;</div>
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