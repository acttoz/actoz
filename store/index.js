$(".icon").click(function(event) {
	var clickedIcon = event.target.id;
	window.location.href = "app.html?id=" + clickedIcon;
});
 