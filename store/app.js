$(".icon").click(function(event) {
	var clickedIcon = event.target.id;
	window.location.href = clickedIcon+".html";
});
