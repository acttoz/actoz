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
	<script>
		window.onfocus = function() {
		}
		window.onload = function() {
			window.focus();
			// 현재 window 즉 익스플러러를 윈도우 최상단에 위치
			window.moveTo(0, 0);
			// 웹 페이지의 창 위치를 0,0 (왼쪽 최상단) 으로 고정
			window.resizeTo(1280, 1024);
			// 웹페이지의 크기를 가로 1280 , 세로 800 으로 고정(확장 및 축소)
			window.scrollTo(0, 0);
			// 페이지 상단 광고를 바로 볼 수 있게 스크롤 위치를 조정
		}
	</script>
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
<div id="hd" height="100px" style="position: relative;left:0px; top: 0px;">
	<div align="center" style="position: relative;padding-bottom: 5px">
		<div style="position:absolute; top:0px; left:0px; z-index:1;">
			<img  src="<?php echo G5_IMG_URL ?>/header_btn.png" width="100%" usemap="#Map"  border="0" align="middle" />
			<map name="Map" id="Map">
				<area shape="rect" coords="78,91,212,210" href="#" onclick="javascript:saveMenu(1)" />
				<area shape="rect" coords="246,90,381,220" href="#" onclick="javascript:saveMenu(2)" />
				<area shape="rect" coords="414,95,547,211" href="#" onclick="javascript:saveMenu(3)" />
				<area shape="rect" coords="571,95,720,219" href="#" onclick="javascript:saveMenu(4)"/>
				<area shape="rect" coords="743,93,888,214" href="#" onclick="javascript:saveMenu(5)" />
			</map>
		</div>
		<div style="position:absolute; top:70px; left:1000px; z-index:2; float:right">
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
<div id="wrapper" style="position:relative; top: 240px">
<div id="aside2" align="center" >
<div style="background:#503b02;border-bottom:#b5b5b5 3px solid; padding-top: 10px; height: 50px;">
<font size='6' color="#ffffff"> <p id="menu_title" ></p> </font>
</div>
<div style="padding: 20px;padding-top:50px; height: 200px;vertical-align: middle">
<font size="4"> <p id="menu_summary" ></p> </font>
</div>

</div>
<div id="container">
<script language="JavaScript" type="text/JavaScript">
	function saveMenu(num) {
		localStorage.setItem("menu", num);
		switch(num) {
			case 1:
				document.location.href = "";
				break;
			case 2:
				document.location.href = "http://actoze.dothome.co.kr/month.php?month=3";
				break;
			case 3:
				document.location.href = "http://actoze.dothome.co.kr/bbs/write2.php?bo_table=year";
				break;
			case 4:
				document.location.href = "http://actoze.dothome.co.kr/pds.php";
				break;
			case 5:
				document.location.href = "";
				break;
		}
	}

	var menu = localStorage.getItem("menu");
	function instruction() {
		switch(menu*=1) {
			case 1:
				document.getElementById("menu_title").innerHTML = "사용안내";
				document.getElementById("menu_summary").innerHTML = "New text!";
				break;
			case 2:
				document.getElementById("menu_title").innerHTML = "역사달력";
				document.getElementById("menu_summary").innerHTML = "New text!";
				break;
			case 3:
				document.getElementById("menu_title").innerHTML = "연  표";
				document.getElementById("menu_summary").innerHTML = "New text!";
				break;
			case 4:
				document.getElementById("menu_title").innerHTML = "자료실";
				document.getElementById("menu_summary").innerHTML = "New text!";
				break;
			case 5:
				document.getElementById("menu_title").innerHTML = "추천사이트";
				document.getElementById("menu_summary").innerHTML = "New text!";
				break;

		}

	}


	window.onload = instruction;

</script>
<?php if ((!$bo_table || $w == 's' ) && !defined("_INDEX_")) { ?><?php } ?>