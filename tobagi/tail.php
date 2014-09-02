<?php
if (!defined('_GNUBOARD_'))
	exit ;
// 개별 페이지 접근 불가

// 하단 파일 경로 지정 : 이 코드는 가능한 삭제하지 마십시오.
if ($config['cf_include_tail']) {
	if (!@include_once ($config['cf_include_tail'])) {
		die('기본환경 설정에서 하단 파일 경로가 잘못 설정되어 있습니다.');
	}
	return;
	// 이 코드의 아래는 실행을 하지 않습니다.
}

if (G5_IS_MOBILE) {
	include_once (G5_MOBILE_PATH . '/tail.php');
	return;
}
?>
</div>
</div>

<!-- } 콘텐츠 끝 -->

<hr>

<!-- 하단 시작 { -->
<div id="ft">

	<div id="ft_company">
		<br>
		<div>
			<a href="http://tobagimal.kr" target="_blank"><img src="<?php echo G5_IMG_URL ?>/bottom_btn10.jpg" alt="1. 토박이말 갈모임(학회) 토박이말바라기"/></a>&nbsp;&nbsp;&nbsp; <a href="http://www.hangeul.or.kr" target="_blank"><img src="<?php echo G5_IMG_URL ?>/bottom_btn6.jpg" alt="한글학회"/></a>&nbsp;&nbsp;&nbsp;&nbsp; <a href="http://cafe.daum.net/malel/" target="_blank"><img src="<?php echo G5_IMG_URL ?>/bottom_btn7.jpg" alt="우리말살리는겨레모임"/></a>&nbsp;&nbsp;&nbsp; <a href="http://www.urimal.org/" target="_blank"><img src="<?php echo G5_IMG_URL ?>/bottom_btn8.jpg" alt="한글문화연대"/></a>&nbsp;&nbsp;&nbsp; <a href="http://urimal.cs.pusan.ac.kr/urimal_new/" target="_blank"><img src="<?php echo G5_IMG_URL ?>/bottom_btn9.jpg" alt="우리말배움터"/></a>
		</div>
		<br>
	</div>
	<div id="ft_copy">
		<div>
			<img src="<?php echo G5_IMG_URL ?>/mark.png" alt="<?php echo $config['cf_title']; ?>">
			<!-- <a href="<?php echo G5_BBS_URL; ?>/content.php?co_id=company">토박이말 바라기 누리집</a> -->
			<!-- <a href="<?php echo G5_BBS_URL; ?>/content.php?co_id=provision">서비스이용약관</a> -->
			<a href="<?php echo G5_BBS_URL; ?>/content.php?co_id=privacy" style="font-weight:bold">&nbsp;&nbsp;모람알거리다룸</a>
			<ff>
				&nbsp;&nbsp;Copyright &copy; <b> http://토박이말바라기.kr(tobagimal.kr) </b> All rights reserved.
			</ff>
			<!-- <a href="#hd" id="ft_totop">위로</a> -->
			<style type="text/css">
				.tg {
					border-collapse: collapse;
					border-spacing: 0;
					border: #C3C6CA solid;
					border-width: 1px 0px 1px 0px
				}
				.tg td {
					text-align: left;
					font-size: 14px;
					padding: 10px 5px;
					border-style: solid;
					border-width: 1px;
					overflow: hidden;
					word-break: normal;
				}
				.tg th {
					text-align: left;
					font-size: 14px;
					font-weight: normal;
					padding: 10px 5px;
					border-style: solid;
					border-width: 1px;
					overflow: hidden;
					word-break: normal;
				}
			</style>
			<table class="tg" border="1">
				<tr>
					<th class="tg-031e" rowspan="3"><img src="<?php echo G5_IMG_URL ?>/mark.png" alt="<?php echo $config['cf_title']; ?>"></th>
					<th class="tg-031e" colspan="3"><a href="<?php echo G5_BBS_URL; ?>/content.php?co_id=privacy" style="font-weight:bold">모람알거리다룸</a></th>
				</tr>
				<tr>
					<td class="tg-031e" colspan="3"> Copyright &copy; <b> http://토박이말바라기.kr(tobagimal.kr) </b> All rights reserved. </td>
				</tr>
				<tr>
					<td class="tg-031e" colspan="3"></td>
				</tr>
			</table>
		</div>

	</div>
</div>

<?php
if(G5_USE_MOBILE && !G5_IS_MOBILE) {
$seq = 0;
$href = $_SERVER['PHP_SELF'];
if($_SERVER['QUERY_STRING']) {
$sep = '?';
foreach($_GET as $key=>$val) {
if($key == 'device')
continue;

$href .= $sep.$key.'='.strip_tags($val);
$sep = '&amp;';
$seq++;
}
}
if($seq)
$href .= '&amp;device=mobile';
else
$href .= '?device=mobile';
?>
<!-- <a href="<?php echo $href; ?>" id="device_change">손전화틀로 보기</a> -->
<?php
}

if ($config['cf_analytics']) {
echo $config['cf_analytics'];
}
?>

<!-- } 하단 끝 -->

<script>
	$(function() {
		// 폰트 리사이즈 쿠키있으면 실행
		font_resize("container", get_cookie("ck_font_resize_rmv_class"), get_cookie("ck_font_resize_add_class"));
	}); 
</script>

<?php
include_once (G5_PATH . "/tail.sub.php");
?>