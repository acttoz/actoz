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
var quiz = localStorage.getItem("quiz");
console.log(localStorage);
console.log(tClass);
console.log(quiz);
var htmls = '';
$('#title').html("문항번호: &nbsp; " + quiz);

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
			select : "table",
			mClass : tClass,
			quiz : this.quiz
		}

	});

	console.log("ajax" + tClass + this.quiz);

	request.done(function(json) {
		console.log("성공");
		htmls += '<table class="table span12 table-striped" style="width=100%;table-layout:fixed;">'
		htmls += "<tr><th style='width:5%'><h4>반</h4></th><th style='width:5%;' ><h4>번호</h4></th><th style='width:10%'><h4>이름</h4></th><th style='width:30%'><h4>정답</h4></th><th style='width:45%;padding-right:5%'><h4>근거</h4></th></tr>";
		if ( typeof json === "object" && json.list.length > 0) {
			$(json.list).each(function() {

				htmls += '<tr align="center" valign="middle">';
				htmls += "<td style='word-break:break-all'>" + this.ban + "</td>";
				htmls += "<td style='word-break:break-all'>" + this.num + "</td>";
				htmls += "<td style='word-break:break-all'>" + this.name + "</td>";
				if (this.answer != "") {
					htmls += "<td style='word-break:break-all'>" + this.answer + "</td>";
				} else {
					htmls += "<td style='word-break:break-all'>";
					if (this.a1 == 1)
						htmls += "1 ";
					if (this.a2 == 1)
						htmls += "2 ";
					if (this.a3 == 1)
						htmls += "3 ";
					if (this.a4 == 1)
						htmls += "4 ";
					if (this.a5 == 1)
						htmls += "5 ";

					htmls += "</td>";
				}
				htmls += "<td style='word-break:break-all'>" + this.reason + "</td>";
				htmls += "</tr>";
				console.log("memo: " + this.memo);
				if (this.memo != null)
					$("#result").html(this.memo);

			});
			htmls += "</table>";
			$('#ctlGrid').html(htmls);
		}
		htmls = "";
		isLoading = false;

	});

	request.fail(function(jqXHR, textStatus, errorThrown) {
		alert("jqXHR: " + jqXHR.status + "\ntextStatus: " + textStatus + "\nerrorThrown: " + errorThrown);

		isLoading = false;
	});
	// parsing end

	return true;

};

getItem();

function answer_input() {
	var encodedClass = encodeURI(tClass);
	var encodedQuiz = encodeURI(quiz);
	location.href = "answer_input.html?" + encodedClass + "&" + encodedQuiz;
}
function toHome() {
	location.href = "quiz_list.html";
}

function quiz_input() {
	var encodedClass = encodeURI(tClass);
	var encodedQuiz = encodeURI(quiz);
	location.href = "quiz_input.html?" + encodedClass + "&" + encodedQuiz;
}

function deleteAll() {
	console.log("delete" + tClass + this.quiz);
	var submitFlag = window.confirm("이 문항의 모든 자료가 삭제 됩니다.");
	if (submitFlag) {
		var request2 = $.ajax("db.php", {
			type : "POST",
			data : {
				select : "deleteall",
				mClass : tClass,
				quiz : this.quiz
			}

		});

		request2.done(function(data) {
			// Create our data table out of JSON data loaded from server.
			console.log("delete " + data);
			location.href = "quiz_list.html";

		});

		request2.fail(function(jqXHR, textStatus) {
			console.log("Request failed: " + textStatus);
			// location.href = "quiz_list.html";
		});
		// location.href = "quiz_list.html";
	}
}

// graph

google.load('visualization', '1', {
	packages : ["corechart"]
});
// Set a callback to run when the Google Visualization API is loaded.
google.setOnLoadCallback(drawChart);

function drawChart() {
	var request2 = $.ajax("db.php", {
		dataType : "json",
		data : {
			select : "graph2",
			mClass : tClass,
			quiz : this.quiz

		}

	});

	request2.done(function(json) {
		// Create our data table out of JSON data loaded from server.
		var data = new google.visualization.DataTable(json);

		// Instantiate and draw our chart, passing in some options.
		var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
		chart.draw(data, {
			width : 800,
			height : 400,
			title : "문항별 응답 비율(%)",

			vAxis : {

				viewWindowMode : 'explicit',
				viewWindow : {
					max : 100,
					min : 0
				}

			}
		});

	});

	request2.fail(function(jqXHR, textStatus) {
		console.log("Request failed: " + textStatus);
	});

}

function excel() {
	console.log("excel.php?mClass=" + tClass + "&quiz=" + quiz);
	location.href = "excel.php?mClass=" + tClass + "&quiz=" + quiz;

}

function view() {
 var obj = document.getElementById('ctlGrid');

 if (obj.style.display=="")
  obj.style.display="none";
 else
  obj.style.display="";
}

// setInterval(function() {
	// getItem();
	// drawChart();
// }, 5000);
