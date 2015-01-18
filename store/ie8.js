$(".icon").click(function(event) {
	var clickedIcon = event.target.id;
	window.location.href = "app_ie8.html?id=" + clickedIcon;
});
