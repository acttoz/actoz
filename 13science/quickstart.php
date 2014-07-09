<?php
header('Content-Type: text/xml');
echo	'<?xml version = "1.0" encoding="utf-8" standalone="yes"?>';
echo	'<response>';
$name	=	$_GET['param'];
$userNames	=	array('GONY','MIRAN');
if(in_array(strtoupper($name), $userNames))
	echo	'hi ' . $name. '!';
else if(trim($name) == '')
	echo	'input your name';
else
	echo	$name . ', is unknown!';

echo	'</response>';
?>