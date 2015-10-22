function verifykeycodeNum(a) {
	var codeNumVerify = /[^\d]/g;
	a.value = a.value.replace(codeNumVerify, '');
	if (a.value > 10) {
		alert("The key code num isn't more than 10! ");
		a.value = "";
	}
}

String.prototype.endWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substring(this.length - s.length) == s)
		return true;
	else
		return false;
	return true;
};

function verifyName(name) {
	var loginVerify = /[^\w]/g;
	name.value = name.value.replace(loginVerify, '');
}

function verifyPassword(password) {
	var passwordVerify = /[^\S]/g;
	password.value = password.value.replace(passwordVerify, '');
}

function verifyEnglishname(txt) {
	var ver = /^[A-Za-z0-9]+$/;
	var flag=ver.test(txt);
	return flag;
	
}

function validateContentPackage() {
	var packageName = $("#packageName");
	var verify = /[^\S]/g;
	var name2 = packageName.val().replace(verify, '');
	if (packageName.val() == "" || name2 == "") {
		alert("Package name can not be null!");
		packageName.val('');
		return false;
	}
	var iconName = $("#iconName").val();
	var packageName = $("#contentName").val();
	if (iconName == "" || packageName == "") {
		alert("You must upload icon and package!");
		return false;
	} else if (!packageName.endWith(".zip")) {
		alert("You must upload a ZIP file!");
		return false;
	} else if (!(iconName.endWith(".png") || iconName.endWith(".jpg")
			|| iconName.endWith(".gif") || iconName.endWith(".bmp"))) {
		alert("You must upload a PNG or GIF or BMP or JPEG file!");
		return false;
	} else {
		$(".clearfix").hide();
		return true;
	}
	return true;
}

function validateBasePackage() {
	var baseFileName = $("#baseName").val();
	if (baseFileName == "") {
		alert("You must upload a base package!");
		return false;
	}
	if (!baseFileName.endWith(".zip")) {
		alert("You must upload a ZIP file!");
		return false;
	}
	$(".clearfix").hide();
	return true;
}

function validateXbmcName() {
	var xbmcName = $("#textfield").val();
	if (!xbmcName.endWith(".apk")) {
		alert("You must upload an APK file!");
		return false;
	}
	return true;
}

function validateWatchdogName(){
	var watchdogName = $("#textfield").val();
	if (!watchdogName.endWith(".apk")) {
		alert("You must upload an APK file!");
		return false;
	}
	return true;
}

function validateCodeNumber() {
	var codeNum = $("#codeCount").val();
	if (codeNum == "") {
		alert("Please input code number!");
		return false;
	}
	return true;
}

function verifyNum(a) {
	var codeNumVerify = /[^\d]/g;
	a.value = a.value.replace(codeNumVerify, '');
}

function checkValueIsNull(){
	var value = $("#publicValue").val();
	if(value == "" || $.trim(value) == ""){
		alert("You must input days!");
		return false;
	}
	return true;
}

