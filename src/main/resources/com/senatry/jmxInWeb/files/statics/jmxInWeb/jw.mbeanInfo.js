// JavaScript Document

$(document).ready(function() {
    $('.js_editable').editable({
		url: function(params) {
			var d = new $.Deferred();
			if (params.value === 'abc') {
				return d.reject('error message'); //returning error via deferred object
			}
		},
		display: function (value) {
			console.log($(this).attr("data-readable"));
			console.log("--:" + value);
		}
	});
});
