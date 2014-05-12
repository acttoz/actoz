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

					<img width="871" height="402" src="<?php echo G5_IMG_URL ?>/link.png"   usemap="#Map2"  align="middle" />
					<map name="Map2" id="Map2">
						<area shape="rect" coords="4,5,343,80" href="http://www.museum.go.kr" />
						<area shape="rect" coords="457,5,821,79" href="http://www.iidd222.net/" />
						<area shape="rect" coords="3,116,343,182" href="http://gyeongju.museum.go.kr/html/kr/" />
						<area shape="rect" coords="457,120,824,183" href="http://history.doumi1004.com/" />
						<area shape="rect" coords="4,226,347,290" href="http://www.gogung.go.kr/" />
						<area shape="rect" coords="454,223,868,290" href="http://blog.naver.com/ausubel78" />
						<area shape="rect" coords="4,330,347,398" href="http://gimhae.museum.go.kr/" />
						<area shape="rect" coords="453,334,823,398" href="http://contents.history.go.kr/front/index.do" />
					</map>

				</div>
			</div>
		</div>

	</body>
</html>
<?php
include_once ('./_tail.php');
?>
