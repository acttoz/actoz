<?php
if (!defined('_GNUBOARD_'))
	exit ;
// 개별 페이지 접근 불가

include_once (G5_PATH . '/head.sub.php');
include_once (G5_LIB_PATH . '/latest.lib.php');
include_once (G5_LIB_PATH . '/outlogin.lib.php');
include_once (G5_LIB_PATH . '/poll.lib.php');
include_once (G5_LIB_PATH . '/visit.lib.php');
include_once (G5_LIB_PATH . '/connect.lib.php');
include_once (G5_LIB_PATH . '/popular.lib.php');

// 상단 파일 경로 지정 : 이 코드는 가능한 삭제하지 마십시오.
if ($config['cf_include_head']) {
	if (!@include_once ($config['cf_include_head'])) {
		die('기본환경 설정에서 상단 파일 경로가 잘못 설정되어 있습니다.');
	}
	return;
	// 이 코드의 아래는 실행을 하지 않습니다.
}

if (G5_IS_MOBILE) {
	include_once (G5_MOBILE_PATH . '/head.php');
	return;
}
?>

<!-- 상단 시작 { -->
<head>
	<div>

		<style>
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

<div style="position: relative;">
	<div class="fullbackground">
		<img class="fullbackground" src="<?php echo G5_IMG_URL ?>/back.jpg" />
	</div>
	<br/>
</div>
<div id="hd" height="300" style="position: relative;left:0px; top: 0px;">
	<div align="center" style="padding-top:30px;padding-bottom:50px">

		<img  src="<?php echo G5_IMG_URL ?>/header_btn.png" width="970
		" height="130" usemap="#Map"  border="0" align="middle" />
		<map name="Map" id="Map">
			<area shape="rect" coords="106,2,232,100" href="#" />
			<area shape="rect" coords="266,1,396,100" href="<?php echo G5_URL ?>/main_calendar.php" />
			<area shape="rect" coords="430,1,553,100" href="google.com" />
			<area shape="rect" coords="583,3,710,98" href="<?php echo G5_URL ?>/pds.php" />
			<area shape="rect" coords="741,0,865,101" href="#" />
		</map>
		<div style="padding-right: 50px;float: right">
			<div id="aside">
				
				<?php echo outlogin('basic');
				// 외부 로그인
				?>
				<?php echo poll('basic');
					// 설문조사
				?>
			</div>
		</div>
	</div>
</div>
<div align="center">
	<!-- } 상단 끝 -->

</div>
<hr>

<!-- 콘텐츠 시작 { -->
<div id="wrapper">
<div id="aside2" align="center" >
<div style="background:#C3E189;border-bottom:#b5b5b5 3px solid; padding-top: 10px; height: 50px;">
<font size='6' >
<?php
if ($menu == 1) {
	echo inform;
} else if ($menu == 2) {
	echo cal;
} else if ($menu == 3) {
	echo year;
} else if ($menu == 4) {
	echo pds;
} else if ($menu == 5) {
	echo link;
}
?>
</font>
</div>
<div style="padding: 20px;padding-top:50px; height: 200px;vertical-align: middle">
<font size="4">
<?php
if ($menu == 1) {
	echo inform1;
} else if ($menu == 2) {
	echo cal1;
} else if ($menu == 3) {
	echo year1;
} else if ($menu == 4) {
	echo pds1;
} else if ($menu == 5) {
	echo link1;
}
?>
</font>
</div>

</div>
<div id="container">
<?php if ((!$bo_table || $w == 's' ) && !defined("_INDEX_")) { ?><?php } ?>
