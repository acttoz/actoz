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
					<img src="<?php echo G5_IMG_URL ?>/sa1.png" style="padding-top: 10px" width="850" height="468" usemap="#Map2" border="0">
					<map name="Map2" id="Map2">
						<area shape="circle" coords="306,37,36" href="http://actoze.dothome.co.kr/bbs/write2.php?bo_table=cal1_3" />
						<area shape="circle" coords="204,88,35" href="http://actoze.dothome.co.kr/bbs/write2.php?bo_table=cal1_4" />
						<area shape="circle" coords="112,154,42" href="http://actoze.dothome.co.kr/bbs/write2.php?bo_table=cal1_5" />
						<area shape="circle" coords="38,227,36" href="http://actoze.dothome.co.kr/bbs/write2.php?bo_table=cal1_6" />
						<area shape="circle" coords="110,277,30" href="http://actoze.dothome.co.kr/bbs/write2.php?bo_table=cal1_7" />
						<area shape="circle" coords="347,256,43" href="http://actoze.dothome.co.kr/bbs/write2.php?bo_table=cal1_9" />
						<area shape="circle" coords="456,229,41" href="http://actoze.dothome.co.kr/bbs/write2.php?bo_table=cal1_10" />
						<area shape="circle" coords="557,200,40" href="http://actoze.dothome.co.kr/bbs/write2.php?bo_table=cal1_11" />
						<area shape="circle" coords="648,154,38" href="http://actoze.dothome.co.kr/bbs/write2.php?bo_table=cal1_12" />
						<area shape="circle" coords="813,60,35" href="http://actoze.dothome.co.kr/bbs/write2.php?bo_table=cal1_2" />
						<area shape="rect" coords="156,345,275,462" href="http://actoze.dothome.co.kr/category1.php" />
						<area shape="rect" coords="277,346,394,463" href="http://actoze.dothome.co.kr/category2.php" />
						<area shape="rect" coords="397,346,514,463" href="http://actoze.dothome.co.kr/category3.php" />
						<area shape="rect" coords="516,346,645,464" href="http://actoze.dothome.co.kr/category4.php" />
					</map>

				</div>
			</div>
		</div>

	</body>
</html>
<?php
include_once ('./_tail.php');
?>
