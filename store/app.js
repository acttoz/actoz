function getRequest() {
	if (location.search.length > 1) {
		var get = new Object();
		var ret = location.search.substr(1).split('&');
		for (var i = 0; i < ret.length; i++) {
			var r = ret[i].split('=');
			get[r[0]] = r[1];
		}
		return get;
	} else {
		return false;
	}
}

var get = getRequest();
