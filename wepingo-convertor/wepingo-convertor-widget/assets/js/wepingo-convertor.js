$(document).ready(function() {
	
	jQuery.getJSON("http://localhost:8080/wepingo-convertor-api/event/", function(data){
		alert(data["id"]);	
	});
	
});