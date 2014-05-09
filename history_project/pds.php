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

					<img width="579" height="506" src="<?php echo G5_IMG_URL ?>/pds.png"   usemap="#Map2"  align="middle" />
					<map name="Map2" id="Map2">
						<area shape="rect" coords="353,261,579,478" href="http://actoze.dothome.co.kr/bbs/board.php?bo_table=pds4">
						<area shape="rect" coords="0,259,228,473" href="http://actoze.dothome.co.kr/bbs/board.php?bo_table=pds3">
						<area shape="rect" coords="350,2,575,211" href="http://actoze.dothome.co.kr/bbs/board.php?bo_table=pds2">
						<area shape="rect" coords="2,2,235,214" href="http://actoze.dothome.co.kr/bbs/board.php?bo_table=pds1">
					</map>

				</div>
			</div>
		</div>

	</body>
</html>
<?php
include_once ('./_tail.php');
?>
