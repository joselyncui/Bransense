$(document).ready(function() {
	$("#statusSelect").change(function(){
		var statusSelect = $("#statusSelect").val();
		if(statusSelect == "1"){
			location.href = "macAddressManager";
		} else if (statusSelect == "2") {
			location.href = "getApprove";
		} else if (statusSelect == "3") {
			location.href = "getRejected";
		} else if (statusSelect == "4") {
			location.href = "getTimeout";
		}
	});
});