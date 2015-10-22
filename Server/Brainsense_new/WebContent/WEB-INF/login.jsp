<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Admin</title>
<meta name="description" content="Brainsense" />
<link rel="shortcut icon" type="image/png" HREF="../img/favicons/brainsense.png"/>
<meta name="keywords" content="Admin" />
<link rel="stylesheet" href="../css/style.css" type="text/css" />
<link rel="stylesheet" href="../css/custom.css" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<script>
	function checkInput(){
		var userName = $("#userName").val();
		var password = $("#password").val();
		if(userName == "" || password == ""){
			alert("Please input Username and Password!");
			return false;
		}
	}
</script>
<style type="text/css">
.logo {
	width: 20%;
}
#page{
	height:720px;
}
</style>
</head>
<body>
	<header id="top">
		<div class="wrapper">
			<div id="title">
				<img SRC="../img/logo.png" alt="Brainsense" class="logo" />
			</div>
		</div>
	</header>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>Admin login</h1>
		</div>
	</div>
	<div id="page">
		<div class="wrapper-login">
			<section class="full">
				<h3>Login</h3>
				<form id="loginform" method="post" action="loginCheck"
					onsubmit="return checkInput()" autocomplete="off">
					<p>
						<label class="required" for="userName">Username:</label> <br />
						<input type="text" id="userName" class="full" maxlength="20" value=""
							name="userName" autocomplete="off" />
						<s:fielderror cssStyle="color: red" fieldName="userName" />
					</p>
					<p>
						<label class="required" for="password">Password:</label> <br />
						<input id="password" maxlength="20" class="full" value=""
							name="password" autocomplete="off" onfocus="this.type='password'" />
						<s:fielderror cssStyle="color: red" fieldName="password" />
					</p>
					<label style = "color:red;">${message }</label>
					<p>
						<input type="submit" class="btn btn-green big" value="Login" />
						&nbsp;
					</p>
					<div class="clear">&nbsp;</div>
				</form>
			</section>

		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>