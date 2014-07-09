var xmlHttp = createXmlHttpRequestObject();

function createXmlHttpRequestObject() {
    var xmlHttp;

    // IE
    if (window.ActiveXObject) {
	try {
	    xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	} catch (e) {
	    xmlHttp = false;
	}
    }
    // Mozila or Somthing
    else {
	try {
	    xmlHttp = new XMLHttpRequest();
	} catch (e) {
	    xmlHttp = false;
	}
    }

    if (!xmlHttp)
	alert("XMLHttpRequest error!!");
    else
	return xmlHttp;
}

function process() {
    // when xmlHttp is not busy
    if (xmlHttp.readyState == 4 || xmlHttp.readyState == 0) {
	param = $('#inputName').val();
	xmlHttp.open("GET", "../php/quickstart.php?param=" + param, true);
	xmlHttp.onreadystatechange = handleServerResponse;
	xmlHttp.send(null);
    } else {
	setTimeout('process()', 1000);
    }
}

function handleServerResponse() {
    if (xmlHttp.readyState == 4) {
	if (xmlHttp.status == 200) {
	    xmlResponse = xmlHttp.responseXML;
	    xmlDocumentElement = xmlResponse.documentElement;
	    helloMessage = xmlDocumentElement.firstChild.data;
	    $('#divMessage').text(helloMessage);
	} else {
	    alert("server error" + xmlHttp.statusText);
	}
    }
}