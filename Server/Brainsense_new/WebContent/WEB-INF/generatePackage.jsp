<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<link rel="shortcut icon" type="image/png" HREF="../img/favicons/brainsense.png"/>
<title>Admin</title>
<meta name="description" content="Brainsense" />
<meta name="keywords" content="Admin,Brainsense" />
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
	$(document).ready(function() {
		Administry.setup();
		$("#contentDiv").hide();
		$(".radio").change(function() {
			$("#lblMsg").text("");
			$("#baseDiv").toggle();
			$("#contentDiv").toggle();
		});
	});
</script>
<style type="text/css">
.logo {
	width: 20%;
}

.file-box{
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
	height: 30px;
}

.file {
	position: absolute;
	top: 0;
	right: 38px;
	height: 24px;
	filter: alpha(opacity : 0);
	opacity: 0;
	width: 300px
}

.file2 {
	position: absolute;
	top: 0;
	right: 10px;
	height: 24px;
	filter: alpha(opacity : 0);
	opacity: 0;
	width: 300px
}

#page {
	height: 680px;
}

.btnDiv {
	text-align: right;
}
#language,#country,#category{
	margin-right: 0px;
	width:200px;
}
.baseText{
	margin-left:30px;
	width: 192px;
}

.packageText{
	margin-left:30px;
	width: 190px;
}

#packageName{
	width:190px;
}
td{
	padding: 5px;
}

</style>
</head>
<body>
	<!-- Header -->
	<jsp:include page="header.jsp"></jsp:include>
	<!-- End of Header -->
	<div id="pagetitle">
		<div class="wrapper">
			<h1>Generate Package</h1>
		</div>
	</div>
	<div id="page">
		<div class="clearfix">
			<div class="column width3 first"
				style="margin: 0 auto; float: inherit ;">
				<h3></h3>
				<div class="row">
					<input type="radio" name="action" id="baseRadio" value="baseRadio" checked="checked" class="radio" /> 
					<label for="baseRadio">Base Package</label> &nbsp;&nbsp; 
					<input type="radio" id="contentRadio" name="action" value="contentRadio" class="radio" /> 
					<label for="contentRadio">Content Package</label>
				</div>
				<hr />
				<br />
				<p>
				<label id="lblMsg" style="color: red;">${message }</label>
				<form action="addBasePackage" method="post" enctype="multipart/form-data" onsubmit="return validateBasePackage()">
					<table style="width:100%;" id="baseDiv">
						<tr>
							<td>
								<label for="language" style="font-size: 14px;">Language:</label> 
							</td>
							<td>
								<select id="language" class="half" name="languageSelect">
									<c:forEach var="language" items="${languages}">
										<option value="${language.languageId }">${language.name }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td><label for="type" style="font-size: 14px;">Type:</label> </td>
							<td>
								<select id="type" class="half" name="typeSelect">
									<c:forEach var="type" items="${types}">
										<option value="${type.typeId }">${type.name }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr><td colspan="2">Upload Icon:(256 * 256)</td></tr>
						<tr>
							<td colspan="2">
								<div class="file-box">
									<input type='button' class='btn' value='Browse' />
									<input type='text' name='baseIconName' id='baseIconName' class='txt packageText' /> .
									<input type="file" name="iconFile" class="file2" id="fileField" size="28" 
									onchange="document.getElementById('baseIconName').value=this.value" 
									accept="image/jpeg,image/gif,image/png,image/x-ms-bmp"  />
								</div>
							</td>
						</tr>
						<tr><td colspan="2">Upload Package:</td></tr>
						<tr>
							<td colspan="2">
								<div class="file-box">
									<input type='button' class='btn' value='Browse' />
									<input type='text' name='baseName' id='baseName' class='txt baseText' /> 
									<input type="file" name="baseFile" class="file" id="fileField" size="28" 
										onchange="document.getElementById('baseName').value=this.value" 
										accept="application/x-zip-compressed" />
								</div> 
								<s:fielderror cssStyle="color: red" fieldName="baseName" />	
							</td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center;">
								<input type="submit" class="btn btn-green big" value="Generate" />
							</td>
						</tr>
					</table>
				</form>
				<form action="addContent" method="post" enctype="multipart/form-data" onsubmit="return validateContentPackage()">
					<table id="contentDiv">
						<tr>
							<td>
								<label for="packageName">Package Name:</label>
							</td>
							<s:fielderror cssClass="color:red;" fieldName="packageName" />
							<td>
								<input type="text" maxlength="20" id="packageName" name = "packageName" />
							</td>
						</tr>
						<tr>
							<td>
								<label for="country">Country:</label>
							</td>
							<td>
								<select id="country"
									class="half" name="countrySelect">
									<c:forEach var="country" items="${countries }">
										<option value="${country.countryId }">${country.name }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<label for="category">Category:</label>
							</td>
							<td>
								<select id="category"
									class="half" name="categorySelect">
									<c:forEach var="category" items="${categories }">
										<option value="${category.categoryId }">${category.name }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<label>Upload icon</label>
							</td>
						</tr>
						<tr>
							<td colspan="2">
							<div class="file-box">
								<input type='button' class='btn' value='Browse' /> 	
								<input
								type='text' name='iconName' id='iconName' class='txt packageText' /> <input
								type="file" name="iconFile" class="file2" id="fileField"
								size="28"
								onchange="document.getElementById('iconName').value=this.value"
								accept="image/jpeg,image/gif,image/png,image/x-ms-bmp" /> 
								</div>
								<s:fielderror cssStyle="color: red" fieldName="iconName" />	
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<label>Upload package</label>
							</td>
						</tr>
						<tr>
							
							<td colspan="2">
							<div class="file-box">
								<input type='button' class='btn' value='Browse' />
								<input
								type='text' name='contentName' id='contentName' class='txt packageText' /> <input
								type="file" name="contentFile" class="file2" id="fileField"
								size="28"
								onchange="document.getElementById('contentName').value=this.value"
								accept="application/x-zip-compressed" />
							</div>
							<s:fielderror cssStyle="color: red" fieldName="contentName" />	
							</td>
							
						</tr>
						<tr>
							<td colspan="2">
								<label for="description">Description</label>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<textarea id="description" maxlength="200" name="description" style="width: 300px"></textarea>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="submit" class="btn btn-green big" value="Generate" />
								<s:token />
							</td>
						</tr>
					</table>
				</form>

			</div>
		</div>
		
		
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	
	<script type="text/javascript" src="../js/administry.js"></script>
</body>
</html>