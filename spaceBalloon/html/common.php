<?php

$dbName = 'actoze';
$secretKey = "0453";

function dbConnect()
{
	global  $dbName;
	global  $secretKey;

	$link = mysql_connect('localhost', 'actoze', '0453acac');
	
	if(!$link)
	{
		fail("Couldn�t connect to database server");
	}
	
	if(!@mysql_select_db($dbName))
	{
		fail("Couldn�t find database $dbName");
	}
	
	return $link;
	}
	
function safe($variable)
{
	$variable = addslashes(trim($variable));
	return $variable;
}

function fail($errorMsg)
{
	print $errorMsg;
	exit;
}

?>