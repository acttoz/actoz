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
					<img src="<?php echo G5_IMG_URL ?>/month<?php echo $_REQUEST['month'] ?>.png" width="617" height="565" usemap="#Map2" border="0">
					<map name="Map2" id="Map2">
						<area shape="rect" coords="245,-1,341,95" href="http://actoze.dothome.co.kr/bbs/write2.php?bo_table=cal1_<?php echo $_REQUEST["month"]?>" />
						<area shape="rect" coords="102,140,200,233" href="http://actoze.dothome.co.kr/bbs/write2.php?bo_table=cal2_<?php echo $_REQUEST["month"]?>" />
						<area shape="rect" coords="392,138,487,234" href="http://actoze.dothome.co.kr/bbs/write2.php?bo_table=cal3_<?php echo $_REQUEST["month"]?>" />
						<area shape="rect" coords="241,278,341,370" href="http://actoze.dothome.co.kr/bbs/write2.php?bo_table=cal4_<?php echo $_REQUEST["month"]?>" />
						<area shape="rect" coords="1,393,53,442" href="http://actoze.dothome.co.kr/month.php?month=3" />
						<area shape="rect" coords="57,394,113,445" href="http://actoze.dothome.co.kr/month.php?month=4" />
						<area shape="rect" coords="118,395,169,442" href="http://actoze.dothome.co.kr/month.php?month=5" />
						<area shape="rect" coords="175,394,229,445" href="http://actoze.dothome.co.kr/month.php?month=6" />
						<area shape="rect" coords="231,393,287,447" href="http://actoze.dothome.co.kr/month.php?month=7" />
						<area shape="rect" coords="292,393,342,445" href="http://actoze.dothome.co.kr/month.php?month=9" />
						<area shape="rect" coords="347,394,400,444" href="http://actoze.dothome.co.kr/month.php?month=10" />
						<area shape="rect" coords="404,395,456,445" href="http://actoze.dothome.co.kr/month.php?month=11" />
						<area shape="rect" coords="462,393,515,445" href="http://actoze.dothome.co.kr/month.php?month=12" />
						<area shape="rect" coords="521,391,574,444" href="http://actoze.dothome.co.kr/month.php?month=2" />
						<area shape="rect" coords="89,473,186,557" href="http://actoze.dothome.co.kr/category.php?category=2" />
						<area shape="rect" coords="196,473,286,557" href="http://actoze.dothome.co.kr/category.php?category=1" />
						<area shape="rect" coords="302,474,395,558" href="http://actoze.dothome.co.kr/category.php?category=3" />
						<area shape="rect" coords="410,472,503,556" href="http://actoze.dothome.co.kr/category.php?category=4" />
					</map>

				</div>
			</div>
		</div>

	</body>
</html>
<?php
include_once ('./_tail.php');
?>
