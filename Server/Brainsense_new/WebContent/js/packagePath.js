
	
function checkPath(){
	var basePath = $("#basePath").val();
	var contentPath = $("#contentPath").val();
	if(!checkUrl(basePath)){
		alert("Please check the format of base path!");
		return false;
	}
	if(!checkUrl(contentPath)){	
		alert("Please check the format of content path!");
		return false;
	}
	return true;
}

function checkUrl(str) {
	var verify = /^(\/(\w*))+$/;
	if (!verify.test(str)) {
		return false;
	} else {
		return true;
	}
}
	