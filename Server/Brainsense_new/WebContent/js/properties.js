$(document).ready(function() {

	$("#typeSelect").change(function() {
		var selectValue = $("#typeSelect").val();
		if (selectValue == "1") {
			location.href = "AddProperties";
		} else if (selectValue == "2") {
			location.href = "getCountry";
		} else if (selectValue == "3") {
			location.href = "getCategory";
		} else if (selectValue == "4") {
			location.href = "getType";
		}
	});

	$("#propertiesTb tr").bind("click", function() {
		$("#hidPropertiesId").val($(this).find(".id").html());
		$("#txtPropertiesName").val($(this).find(".name").html());
		$("#propertiesTb tr").css({
			"background" : "none"
		});
		$(this).css({
			"background-color" : "#5a98de"
		});
	});

	function checkUpdateAndDelete() {
		var updateName = $("#txtPropertiesName").val();
		var updateId = $("#hidPropertiesId").val();

		if (updateId == "") {
			alert("You must select a value to operate!");
			return false;
		}
		if (updateName == "") {
			alert("You must input name!");
			return false;
		} else {
			$("#txtPropertiesName").val(trim(updateName));
			updateName = $("#txtPropertiesName").val();
			if (updateName == "") {
				alert("You must input name!");
				return false;
			}
		}
		return true;
	}

	$("#btnUpdate").click(function() {
		if (!checkUpdateAndDelete()) {
			return;
		}
		var selectValue = $("#typeSelect").val();
		if (selectValue == "1") {
			$("#add").attr("action", "updateLanguage");
		} else if (selectValue == "2") {
			$("#add").attr("action", "updateCountry");
		} else if (selectValue == "3") {
			$("#add").attr("action", "updateCategory");
		} else if (selectValue == "4") {
			$("#add").attr("action", "updateType");
		}
		$("#add").submit();
	});

	$("#btnDelete").click(function() {
		if (!checkUpdateAndDelete()) {
			return;
		}
		var gnl = confirm("Are you sure to delete?");
		if (gnl == true) {
			var selectValue = $("#typeSelect").val();
			if (selectValue == "1") {
				$("#add").attr("action", "deleteLanguage");
			} else if (selectValue == "2") {
				$("#add").attr("action", "deleteCountry");
			} else if (selectValue == "3") {
				$("#add").attr("action", "deleteCategory");
			} else if (selectValue == "4") {
				$("#add").attr("action", "deleteType");
			}
			$("#add").submit();
		}
	});

	$("#btnSave").click(function() {
		var propertiesName = $("#addPropertiesName").val();
		if (propertiesName == "") {
			alert("You must input name!");
			return;
		} else {
			$("#addPropertiesName").val(trim(propertiesName));
			propertiesName = $("#addPropertiesName").val();
			if (propertiesName == "") {
				alert("You must input name!");
				return;
			}
		}
		var selectValue = $("#typeSelect").val();
		if (selectValue == "1") {
			$("#add").attr("action", "insertLanguage");
		} else if (selectValue == "2") {
			$("#add").attr("action", "insertCountry");
		} else if (selectValue == "3") {
			$("#add").attr("action", "insertCategory");
		} else if (selectValue == "4"){
			$("#add").attr("action", "insertType");
		}
		$("#add").submit();
	});

});

function trim(str) {
	return str.replace(/^(\s|\u00A0)+/, '').replace(/(\s|\u00A0)+$/, '');
}