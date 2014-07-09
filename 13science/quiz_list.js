/**
 * @author 문병무
 */
var console = console || {
	log : function() {
	},
	warn : function() {
	},
	error : function() {
	}
};
var isLoading = false;
var tClass = localStorage.getItem("tClass");
console.log(localStorage);
console.log(tClass);
var htmls = '';

var getItem = function() {
	if (isLoading)
		return false;

	isLoading = true;

	// parsing
	// $.ajaxPrefilter('json', function(options, orig, jqXHR) {
	// return 'jsonp'
	// });
	var request = $.ajax("db.php", {
		type : "GET",
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		data : {
			select : "list",
			mClass : tClass
		}

	});

	request.done(function(json) {
		if ( typeof json === "object" && json.list.length > 0) {
			$(json.list).each(function() {

				$('#movie-list').append('<li class="btn btn-default btn-large btn-block" style="width:98%;"  onclick="answer_list(\'' + this.quiz + '\')"><h3>' + this.quiz + '</h3></li>');
				console.log("quiz: " + this.quiz);

			});
			// $('#movie-list').listview('refresh');
		}
		isLoading = false;

	});

	request.fail(function(jqXHR, textStatus, errorThrown) {
		alert("jqXHR: " + jqXHR.status + "\ntextStatus: " + textStatus + "\nerrorThrown: " + errorThrown);

		isLoading = false;
	});
	// parsing end

	return true;

};
function answer_list(argument) {
	localStorage.setItem("quiz", argument);
	window.location = "answer_list.html"
}

var ajax = {
	parseJSONP : function(result) {
		movieInfo.result = result.results;
		$.each(result.results, function(i, row) {
			console.log(JSON.stringify(row));
			$('#movie-list').append('<li "><a href="" data-id="' + row.id + '"><img src="http://d3gtl9l2a4fn1j.cloudfront.net/t/p/w185' + row.poster_path + '"/><h3>' + row.title + '</h3><p>' + row.vote_average + '/10</p></a></li>');
		});
		$('#movie-list').listview('refresh');
	}
}
function quiz_graph() {
	window.location = "quiz_graph.html"

}

getItem();

