$(document).ready(function() {
	$("#countrySelect").change(function(){
		$("#contentPackageForm").submit();
	});
	$("#categorySelect").change(function(){
		$("#contentPackageForm").submit();
	});
});