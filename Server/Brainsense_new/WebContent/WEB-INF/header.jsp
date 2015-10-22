<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html">
<html>
<head>
<title>Header</title>
<link rel="shortcut icon" type="image/png" HREF="../img/favicons/favicon.png"/>
</head>
<body>
	<header id="top">
		<div class="wrapper">
			<div id="title">
				<img SRC="../img/logo.png" alt="Brainsense" class="logo" />
			</div>
			<div id="topnav">
				<label style="color:red;">${sessionScope.user}</label><a href="logout" style="color: white; font-weight: bold">Logout</a><br />
			</div>
			<nav id="menu">
				<ul class="sf-menu">
					<li>
						<a href="">Authorization</a>
						<ul>
							<li><a HREF="generateKeyCode">Generate Key Code</a></li>
							<li><a href="macAddressManager">MAC Address Manager</a></li>
						</ul>
					</li>
					<li>
						<a href="">APK Package</a>
						<ul>
							<li><a href="initPackage">Upload Content</a></li>
							<li><a href="initXBMC">Upload XBMC</a></li>
							<li><a href="initWatchdog">Upload Watchdog</a></li>
							<li><a href="getContentPackage">Content package manager</a></li>
							<li><a href="getBasePackage">Base package manager</a></li>
						</ul>
					</li>
					<li>
						<a href="">Settings</a>
						<ul>
							<li><a href="AddProperties">Update Types</a></li>
							<li><a href="initDays">Set waiting days</a>
							<!-- <li><a href="getPath">Set Path</a></li> -->
						</ul>
					</li>
					<li>
						<a href="initUser">Reset</a>
					</li>
				</ul>
			</nav>

		</div>
	</header>
</body>
</html>