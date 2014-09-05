$(document).ready(function() {

	$("#btnOpenSuggestionPanel").click(function(event) {
		event.preventDefault();
		$("#openSuggestionPanel").hide();
		$("#suggestionPanel").show();
	});

	$("#btnCloseSuggestionPanel").click(function(event) {
		event.preventDefault();
		$("#suggestionPanel").hide();
		$("#openSuggestionPanel").show();
	});
});