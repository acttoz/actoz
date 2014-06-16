/**
 * @author 문병무
 */

var $el = $("#example");
var $cl = $("#comment");
var newComment = false;
var isLoading = false;
var html;
var selectId;
var getItem = function($el) {
    if (isLoading)
	return false;

    isLoading = true;

    // parsing
    // $.ajaxPrefilter('json', function(options, orig, jqXHR) {
    // return 'jsonp'
    // });
    var request = $.ajax("http://actoz.dothome.co.kr/13chunk/db.php", {
	dataType : "json",
	data : {
	    select : "list"
	}

    });

    request
	    .done(function(json) {
		console.log("성공");
		html = '<ul class="list-group">';
		if (typeof json === "object" && json.list.length > 0) {
		    $(json.list)
			    .each(
				    function() {
					html += '<li class="list-group-item" style="height:100px;width:100%;  vertical-align:middle;"  id="'
						+ this._id
						+ '" pName="'
						+ this.name
						+ '"file="'
						+ this.file
						+ '" onclick="get_comment(';
					html += this._id + ')">';
					html += '<div  name="left" style="vertical-align:middle; float:left; width:85%">';
					html += '   <div style="width:100%; text-align: left;">';
					html += '닉네임: '+this.name + '(' + this.month
						+ '월 ' + this.day + '일)';
					html += '   </div>';
					html += '   <br/>';
					html += '   <div style="width:100%; text-align: left;">';
					html += '멘트: '+this.ment;
					html += '   </div>';
					html += '</div>';
					html += '<div  name="right" style="float:right; width:14%; height:100px; vertical-align:middle" >';
					html += '   <div style="width:100%;vertical-align:middle;width:100%;height:100px;text-align: center;margin:30px 0px 0px 0px">';
					html += '   댓글&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <span class="badge">'
						+ this.vote + '</span>';
					html += '   </div>';
					html += '</div>';
					html += '</li>';
				    });
		    html += "</ul>";
		    $el.featuredListView("addItem", html);
		}
		html = "";
		isLoading = false;
	    });

    request.fail(function(jqXHR, textStatus) {
	console.log("Request failed: " + textStatus);
	isLoading = false;
    });
    // parsing end

    return true;

};

/*
 * $el.featuredListView({ optimization : true, scrollEndAction : function() {
 * console.log("window scrollEndAction"); getItem($el); }
 * }).on("scrollEnd.cs.liveView", function() { console.log("window
 * scrollEndEvent"); });
 */
getItem($el);

// get comment
var getComment = function(parent) {
    var pName = $('#' + parent).attr("pName");
    var file = $('#' + parent).attr("file");
    var audioPlay = document.getElementById('audio');
    audioPlay.src = "http://actoz.dothome.co.kr/13chunk/" + file;
    audioPlay.load();
    console.log("getcomment" + parent + pName + file);
    if (isLoading)
	return false;

    isLoading = true;

    // parsing

    var request = $.ajax("http://actoz.dothome.co.kr/13chunk/db.php", {
	dataType : "json",
	type : "POST",
	data : {
	    select : "get_comment",
	    parent : parent
	}

    });

    request
	    .done(function(json) {
		console.log("성공2" + parent);
		html = '<ul class="list-group" style="margin:60px 0px 0px 0px" >';
		if (typeof json === "object" && json.list.length > 0) {
		    $(json.list)
			    .each(
				    function() {
					html += '<li class="list-group-item" style="width:100%;  vertical-align:middle ;">';
					html += '   <div style="width:100%; text-align: left;">';
					html += this.content;
					html += '   </div>';
					html += '   <div style="width:100%; text-align: right;">';
					html += this.name + '(' + this.date
						+ ')';
					html += '   </div>';
					html += '</div>';
					html += '</li>';
				    });
		    html += "</ul>";
		    $("#comment").featuredListView("addItem", html);
		}
		html = "";
		$("#dialog-form").dialog("open");
		console.log(pName);
		$("span.ui-dialog-title").text(pName);
		isLoading = false;
//		document.body.style.overflow = "hidden";
//		document.body.style.position = "fixed";

		spinnerOff();
	    });

    request.fail(function(jqXHR, textStatus) {
	console.log("Request failed: " + textStatus);
	isLoading = false;
    });
    // parsing end
    return true;

};

// submit comment
function submitComment(name, comment, parent) {
    if (!isLoading) {

	isLoading = true;
	console.log(parent + name + comment);
	// parsing

	$.ajax({
	    url : "http://actoz.dothome.co.kr/13chunk/db.php",
	    type : 'POST',
	    cache : false,
	    data : {
		select : "signin",
		_id : parent,
		name : name,
		comment : comment
	    },
	    success : function(args) {
		alert("댓글이 등록되었습니다.");
		$("#comment").featuredListView("removeItem",
			$("#comment").find("ul:last"));
		getComment(parent);

	    }
	});
    }

    isLoading = false;
    // parsing end
}

// popup

$("#dialog-form").dialog(
	{
	    dialogClass: 'fixed-dialog',
	    autoOpen : false,
	    height : window.innerHeight,
	    width : window.innerWidth,
	    modal : true,
	    buttons : {
		"올리기" : function() {
		    newComment = true;
		    console.log($("#name").val() + $("#comment2").val()
			    + selectId);
		    if ($("#name").val() != "" && $("#comment2").val() != "") {
			submitComment($("#name").val(), $("#comment2").val(),
				selectId);
		    } else {
			alert("이름과 댓글을 모두 입력하세요.")
		    }
		    // $("#example").featuredListView("removeItem",
		    // $("#example").find("ul:last"));
		    // getItem($el);

		    // $(this).dialog("close");
		},
		"닫기" : function() {
		    $(this).dialog("close");
		}
	    },
	    close : function() {
		if (newComment) {
		    document.location.href = "sns.html"
		}
		$.each($('audio'), function() {
		    this.pause();
		});
		$("#comment").featuredListView("removeItem",
			$("#comment").find("ul:last"));
//		document.body.style.overflow = "visible";
//		document.body.style.position = "static";
	    }
	});

function get_comment(_id) {

    spinner();

    console.log(_id);
    selectId = _id;
    console.log(selectId);
    getComment(_id);
}
 
// spinner
$("#spinner").dialog({
    autoOpen : false,
    height : window.innerHeight / 2,
    width : window.innerWidth / 2,
    modal : true,
    my : "center",
    at : "center",
    of : window
});

function spinner() {
    $("span.ui-dialog-title").text("알림");
    $("#spinner").dialog("open");
}

function spinnerOff() {
    $("#spinner").dialog("close");
}
