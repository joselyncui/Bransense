<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<link rel="shortcut icon" type="image/png" HREF="../img/favicons/brainsense.png"/>
<title>Generate key code</title>
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

$(document).ready(function(){
	Administry.setup();
});

</script>
<style type="text/css">
.logo {
	width: 20%;
}
#page{
	height:340px;
}
</style>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<div id="pagetitle">
  <div class="wrapper">
    <h1>Generate Key Code</h1>
  </div>
</div>
<div id="page">
  <div class="wrapper">
    <div class="colgroup leading">
      <div class="column width3 first">
        <h4>Key Code List: <a href="#"></a></h4>
        <hr/>
        <table class="no-style full" id="company">
          <tbody>
            <tr>
              <th>Id</th>
              <th class="ta-center">Code Number</th>
            </tr>
            <c:if test="${!empty keyCodes}">
	            <c:forEach var = "keyCode" items = "${keyCodes }">
	            	<tr>
		              <td>${keyCode.id }</td>
		              <td class="ta-center">${keyCode.keyCodeId }</td>
		            </tr>
	            </c:forEach>
            </c:if>
          </tbody>
        </table>
      </div>
      <div class="column width3">
      <label style = "color:red;">${message }</label>
      <s:fielderror fieldName="codeCount" />
        <h4>Add: <a href="#"></a></h4>
        <hr/>
        
        <form id="add" action="createKeyCode" method="post" onsubmit="return validateCodeNumber()">
          <label for="companyname"id="lbAdd">How many key code?</label>
          <br>
          <input type="text" class="half" id="codeCount" name="codeCount" onkeyup="verifykeycodeNum(this)"/>
          <input type="submit" id="addCode" class="btn btn-green big" value="Generate"/>
          <s:token />
        </form>
      </div>
    </div>
  </div>
</div>
<div id="page">
  <h3></h3>
  <br/>
</div>
<jsp:include page="footer.jsp"></jsp:include>
 
<script type="text/javascript" SRC="../js/administry.js"></script>
</body>
</html>
