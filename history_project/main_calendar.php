<?php
include_once ('./_common.php');
include_once (G5_LIB_PATH . '/latest.lib.php');
include_once ('./_head3.php');
?>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<!-- <script type="text/javascript" src="calendar.js"></script> -->
		<title>달력 스크립트</title>
		<meta name="generator" content="Microsoft FrontPage 4.0">
		<style>
			td {
				text-decoration: none;
			}
			font {
				text-decoration: none;
				line-height: 130%;
			}
			A:link, A:active, A:visited {
				text-decoration: none;
				color: '#333333';
			}
			A:hover {
				text-decoration: none;
				color: 'ff9900'
			}
			.fullbackground {
				position: relative;
			}
			img.fullbackground {
				position: absolute;
				z-index: -1;
				top: 0;
				left: 0;
				min-width: 100%; /* alternative: right:0; */
				min-height: 100%; /* alternative: bottom:0; */
			}
		</style>
	</head>

	<body>
		<SPAN ID=calendar STYLE="position:relative;"></SPAN>
		<script language="JavaScript" type="text/JavaScript">
			localStorage.setItem("menu", 2);
			document.getElementById("menu_title").innerHTML = "역사달력";
			document.getElementById("menu_summary").innerHTML = "New text!";
			var tClass = localStorage.getItem("menu");
			function menuTitle() {
				switch (tClass) {
					case 1:
						document.getElementById("menu_title").innerHTML = "1";
						document.getElementById("menu_summary").innerHTML = "New text!";
						break;
					case 2:
						document.getElementById("menu_title").innerHTML = "1";
						document.getElementById("menu_summary").innerHTML = "New text!";
						break;
					case 3:
						document.getElementById("menu_title").innerHTML = "1";
						document.getElementById("menu_summary").innerHTML = "New text!";
						break;
					case 4:
						document.getElementById("menu_title").innerHTML = "1";
						document.getElementById("menu_summary").innerHTML = "New text!";
						break;
					case 5:
						document.getElementById("menu_title").innerHTML = "1";
						document.getElementById("menu_summary").innerHTML = "New text!";
						break;
				}

			}

			if (!Array.prototype.indexOf) {
				Array.prototype.indexOf = function(elt /*, from*/) {
					var len = this.length >>> 0;

					var from = Number(arguments[1]) || 0;
					from = (from < 0) ? Math.ceil(from) : Math.floor(from);
					if (from < 0)
						from += len;

					for (; from < len; from++) {
						if ( from in this && this[from] === elt)
							return from;
					}
					return -1;
				};
			}
			var console = console || {
				log : function() {
				},
				warn : function() {
				},
				error : function() {
				}
			};
			var monthName = new Array("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월")
			var monthDays = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
			var weekList = new Array("3w1", "3w2", "3w3", "3w4", "3w5", "4w2", "4w3", "4w4", "5w2", "5w3", "5w4", "5w5", "6w1", "6w2", "6w3", "6w4", "7w2", "7w3", "7w4", "9w1", "9w2", "9w3", "9w4", "10w2", "10w3", "10w4", "10w5", "11w2", "11w3", "11w4", "11w5", "12w1", "12w2", "12w3", "12w4", "2w1", "2w2", "2w3", "2w4");
			var reviewList = new Array("3r31", "4r30", "5r30", "6r30", "7r31", "9r30", "10r31", "11r28", "12r31", "2r27");
			var subjectList = new Array();
			var now = new Date()
			var nowd = now.getDate()
			var nowm = now.getMonth()
			var nowy = now.getFullYear()
			var todayMon = nowm;
			//주차 -5월3주(5_3)
			var weekNum = 1
			var weekMonth
			var week
			var review
			var getItem = function(day, month, year) {

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
						month : (month + 1)
					}

				});

				request.done(function(json) {
					var i = 0;
					if ( typeof json === "object" && json.list.length > 0) {
						$(json.list).each(function() {

							subjectList.push(this.subject);
						});
						// $('#movie-list').listview('refresh');
					}
					showCalendar(nowd, nowm, nowy);
				});

				request.fail(function(jqXHR, textStatus, errorThrown) {
					alert("jqXHR: " + jqXHR.status + "\ntextStatus: " + textStatus + "\nerrorThrown: " + errorThrown);

				});
				// parsing end

				return true;

			};
			function showCalendar(day, month, year) {
				console.log(subjectList);
				console.log("" + todayMon);
				weekNum = 1;
				weekMonth = month + 1
				console.log(month + 1 + "")
				if ((year % 4 == 0 || year % 100 == 0) && (year % 400 == 0))
					monthDays[1] = 29;
				else
					monthDays[1] = 28
				//leap year test
				var firstDay = new Date(year, month, 1).getDay()

				var calStr = "<table border=0 cellpadding=5 cellspacing=1 align=center bgcolor=#CCCCCC style='table-layout:fixed;min-height: 500px;' height=700>"

				calStr += "<tr bgcolor=white height=100><td colspan=9>"

				calStr += "<table border=0 cellpadding=0 cellspacing=0 align=center width=100% style='table-layout:fixed' height=100>"

				if (year == 2014 && month == 2) {
					calStr += "<td><font size='5'><a href='javascript:;' onClick='nowm--; if (nowm<0) { nowy--; nowm=11; } getItem(nowd,nowm,nowy)' title='이전 월'> </a></font></td>"
					calStr += "<td align=center><font size='5'>" + year + "년 " + monthName[month].toUpperCase() + "</font></td>"
					calStr += "<td align=right><font size='5'><a href='javascript:;'  onClick='nowm++; if (nowm>11) { nowy++; nowm=0; } getItem(nowd,nowm,nowy)' title='다음 월'> >></a></font></td>"

				} else if (year == 2015 && month == 1) {

					calStr += "<td><font size='5'><a href='javascript:;' onClick='nowm--; if (nowm<0) { nowy--; nowm=11; } getItem(nowd,nowm,nowy)' title='이전 월'> <<</a></font></td>"
					calStr += "<td align=center><font size='5'>" + year + "년 " + monthName[month].toUpperCase() + "</font></td>"
					calStr += "<td align=right><font size='5'><a href='javascript:;'  onClick='nowm++; if (nowm>11) { nowy++; nowm=0; } getItem(nowd,nowm,nowy)' title='다음 월'> </a></font></td>"
				} else {
					calStr += "<td><font size='5'><a href='javascript:;' onClick='nowm--; if (nowm<0) { nowy--; nowm=11; } getItem(nowd,nowm,nowy)' title='이전 월'> <<</a></font></td>"
					calStr += "<td align=center><font size='5'>" + year + "년 " + monthName[month].toUpperCase() + "</font></td>"
					calStr += "<td align=right><font size='5'><a href='javascript:;'  onClick='nowm++; if (nowm>11) { nowy++; nowm=0; } getItem(nowd,nowm,nowy)' title='다음 월'> >></a></font></td>"
				}

				calStr += "</tr></table>"

				calStr += "</td></tr>"

				calStr += "<tr height=100 align=center bgcolor='#336666'>"
				calStr += "<th width='248'><font color='yellow' size='5'>주제</font></th>"
				calStr += "<th width='80'><font color='white' size='5'>월</font></th>"
				calStr += "<th width='80'><font color='white' size='5'>화</font></th>"
				calStr += "<th width='80'><font color='white' size='5'>수</font></th>"
				calStr += "<th width='80'><font color='white' size='5'>목</font></th>"
				calStr += "<th width='80'><font color='white' size='5'>금</font></th>"
				calStr += "<th width='80'><font color='#66CCFF' size='5'>토</font></th>"
				calStr += "</tr>"

				var dayCount = 1

				calStr += "<tr bgcolor=white>"
				console.log("firstday" + firstDay)
				if (firstDay != 1) {
					for (var i = 0; i < firstDay; i++) {
						if (i == 0) {
							calStr += "<td width='248'> "
						} else {
							calStr += "<td> "
						}
					}
				} else {

					for (var i = 0; i < firstDay; i++) {
						if (i == 0) {
							calStr += "<td style='word-break:break-all'"
							calStr += "width='248' align=center>"
							calStr += "<font size=3>"// 날짜
							calStr += subjectList.shift();
							calStr += "</font>"// 날짜
							console.log(subjectList);
							dayCount++
						} else {
							calStr += "<td> "
						}
					}
				}
				//공백
				for (var i = 0; i < monthDays[month]; i++) {
					week = weekMonth + "w" + weekNum
					review = weekMonth + "r" + weekNum;
					console.log(week);
					calStr += "<td style='word-break:break-all'"
					if ((i + firstDay + 1) % 7 == 1) {
						calStr += "width='248'"
					} else {
						calStr += "width='80'"
					}

					if (dayCount == nowd && todayMon == month) {
						calStr += " align=center bgcolor='#DFE7DE'><b>" // 오늘 날짜일때 배경색 지정,글자 진하게

					} else {

						calStr += " align=center>" // 오늘 날짜가 아닐때 배경색 지정
					}
					if (weekList.indexOf(week) > 0) {

						if ((i + firstDay + 1) % 7 == 0) {
							calStr += "<a href='http://actoze.dothome.co.kr/bbs/write2.php?bo_table=" + review + "&day=" + dayCount + "'>"// 링크설정
							calStr += "<font size=6>"// 날짜
							calStr += dayCount++// 날짜
							calStr += "</font>"// 날짜
							calStr += "<br/>"// 날짜
							calStr += "<font size=3>"// 날짜
							calStr += "(토&nbsp;&nbsp;의)"// 날짜
							calStr += "</font>"// 날짜
						} else if ((i + firstDay + 1) % 7 == 1) {
							calStr += "<font size=3>"// 날짜
							calStr += subjectList.shift();
							calStr += "</font>"// 날짜
							console.log(subjectList);
							dayCount++
						} else {
							calStr += "<a href='http://actoze.dothome.co.kr/bbs/write2.php?bo_table=" + week + "&day=" + dayCount + "'>"// 링크설정
							calStr += "<font size=6>"// 날짜
							calStr += dayCount++// 날짜
							calStr += "</font>"// 날짜
							calStr += "<br/>"// 날짜
							calStr += "<font size=3>"// 날짜
							calStr += "(글쓰기)"// 날짜
							calStr += "</font>"// 날짜
						}

						calStr += "</a>"
					} else {
						calStr += "<font size=6 color=#d4d4d4>"// 날짜
						calStr += dayCount++// 날짜
						calStr += "</font>"// 날짜
					}
					if (dayCount == nowd && todayMon == month) {

						calStr += "</b>" // 오늘 날짜일때 글자 진하게

					} else {

						calStr += "" // 오늘 날짜가 글자 진하게 안함
					}
					calStr += "</font>"
					if ((i + firstDay + 1) % 7 == 0 && (dayCount < monthDays[month] + 1)) {
						weekNum++
						calStr += "<tr bgcolor=white>"
					}
				}

				var totCells = firstDay + monthDays[month]
				for (var i = 0; i < (totCells > 28 ? (totCells > 35 ? 42 : 35) : 28) - totCells; i++)
					calStr += "<td> "
				calStr += "</table><BR>"

				calendar.innerHTML = calStr
				subjectList = []
			}

			getItem(nowd, nowm, nowy);

		</script>
	</body>
</html>
<?php
include_once ('./_tail.php');
?>
