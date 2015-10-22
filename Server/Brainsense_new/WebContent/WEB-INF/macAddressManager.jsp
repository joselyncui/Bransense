<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<script type="text/javascript" src="../js/macAddress.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		Administry.setup();
		$("#statusSelect").val("${select}");
	});
</script>
<style type="text/css">
.logo {
	width: 20%;
}

#page {
	min-height: 680px;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div id="pagetitle">
		<div class="wrapper">
			<h1>MAC Address List</h1>
		</div>
	</div>
	<div id="page">
		<h3></h3>
		<div class="wrapper">
			<section class="macAddressList">
				<section class="column width6 first" style="width: 1000px;">
					<label style="color: red;">${message }</label> <select
						id="statusSelect" name="statusSelect" form="macForm">
						<option value="1">Waiting for</option>
						<option value="2">Approved</option>
						<option value="3">Rejected</option>
						<option value="4">Over time</option>
					</select>
					<table id="report" class="stylized full" style="">
						<thead>
							<tr>
								<th></th>
								<th>Mac Address</th>
								<th>Key Code</th>
								<th>Name</th>
								<th>Login time</th>
								<th>Email</th>
								<th>CPU</th>
								<th>OS</th>
								<th>Memory</th>
								<th>Sdcard</th>
								<th>Operate</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="device" items="${devices}">
								<tr>
									<c:if test="${device.online == 1}">
										<td>
											<p style="width:20px;"><img src="../img/online.png" /></p>
										</td>
									</c:if>
									<c:if test="${device.online == 0 }">
										<td>
											<p style="width:20px;"><img src="../img/offline.png" /></p>
										</td>
									</c:if>
									<td>
										<p style="width:150px;">${device.macId }</p>
									</td>
									<td>
										<p style="width:200px;">${device.keyCode.keyCodeId }</p>
									</td>
									<td>
										<p style = "width:80px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">${device.user.name }</p>
									</td>
									<td>
										<p style = "width:80px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">${device.datetime }</p>
									</td>
									<td>
										<p style = "width:80px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">${device.user.email }</p>
									</td>
									<td>
										<p style="width:50px;">${device.user.userHardware.cpu }</p>
									</td>
									<td>
										<p style = "width:50px;">${device.user.userHardware.os }</p>
									</td>
									<td>
										<p style = "width:50px;">${device.user.userHardware.memory }</p>
									</td>
									<td>
										<p style = "width:50px;">${device.user.userHardware.sdcard }</p>
									</td>
									<c:if test="${select == 1}">
										<td>
											<p style="width:150px;white-space: nowrap;">
												<a href="approve?macId=${device.macId }&select=1">Approve</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;
												<a href='reject?macId=${device.macId }&select=1'>Reject</a>&nbsp;&nbsp;&nbsp;
											</p>
										</td>
									</c:if>
									<c:if test="${select == 2}">
										<td>
											<p><a href='reject?macId=${device.macId }&select=2'>Reject</a>&nbsp;&nbsp;&nbsp;</p>
										</td>
									</c:if>
									<c:if test="${select == 3}">
										<td><a href="approve?macId=${device.macId }&select=3">Approve</a>&nbsp;&nbsp;&nbsp;
										</td>
									</c:if>
									<c:if test="${select == 4}">
										<td>
											<p style="width:100px;">
												<a href="approve?macId=${device.macId }&select=4">Approve</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;
												<a href='reject?macId=${device.macId }&select=4'>Reject</a>&nbsp;&nbsp;&nbsp;
											</p>
										</td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<form id="macForm"></form>
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