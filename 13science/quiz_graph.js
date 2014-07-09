var console = console || {
	log : function() {
	},
	warn : function() {
	},
	error : function() {
	}
};
var tClass = localStorage.getItem("tClass");
// Load the Visualization API and the piechart package.
google.load('visualization', '1', {
	packages : ["corechart"]
});

// Set a callback to run when the Google Visualization API is loaded.
google.setOnLoadCallback(drawChart);

function drawChart() {
	var request = $.ajax("http://actoz.dothome.co.kr/13science/db.php", {
		dataType : "json",
		data : {
			select : "graph",
			mClass : tClass

		}

	});

	request.done(function(json) {
		// Create our data table out of JSON data loaded from server.
		var data = new google.visualization.DataTable(json);

		// Instantiate and draw our chart, passing in some options.
		var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
		chart.draw(data, {
			width : 800,
			height : 400,
			vAxis : {
				title : "정답률(%)",
				viewWindowMode : 'explicit',
				viewWindow : {
					max : 100,
					min : 0
				}
			}
		});

	});

	request.fail(function(jqXHR, textStatus) {
		console.log("Request failed: " + textStatus);
	});

}