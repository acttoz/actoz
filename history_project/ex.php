<?php
$menu = 4;
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
		<div>
			<div  style="position: relative;left:0px; top: 0px;">
				<div align="center">

					<img width="748" height="450" src="<?php echo G5_IMG_URL ?>/ex.png"   usemap="#Map2"  align="middle" />
					 

				</div>
			</div>
		</div>

	</body>
</html>
<?php
include_once ('./_tail.php');
?>
