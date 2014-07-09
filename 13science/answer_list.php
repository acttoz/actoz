<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<!-- 스타일 -->

		<meta
		name="viewport"
		content="width=device-width, initial-scale=1.0, user-scalable=no, maximum-scale=1"
		/>
		<link
		href="assets/css/bootstrap.css"
		rel="stylesheet"
		>
		<link rel="stylesheet" href="framework/bootstrap.css">
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>
		<script src="framework/jquery-1.10.2.js"></script>
		<script src="framework/infinity.min.js"></script>
		<script src="framework/widget-listview.js"></script>
		<script src="framework/jquery-ui.js"></script>

		<link
		href="assets/css/style.css"
		rel="stylesheet"
		>
		<link
		href="assets/css/font-awesome.min.css"
		rel="stylesheet"
		>
	</head>

	<body style="width:100%">
		<div id="wrap" style="width:100%">

			<div class="nav-top">
				<!-- navbar-fixed-top -->
				<div
				class="navbar  navbar-inverse"
				id="top-nav"
				>
					<!-- INNER-->
					<div class="navbar-inner">
						<div class="container">

							<!-- LOGO -->
							<div
							class="brand"
							style="text-align: center; margin: 0 auto;"
							>

								
								<div
								id="container"
								style="width: 100%"
						>
								<div style="width: 100%; float: left;">
										<span id="title"> <i class="fa-icon-folder-open"></i> 제출 리스트 </span>
								</div>
								<div style="width: 0%; float: right;">
								<a href="http://actoz.dothome.co.kr/13science/quiz_list.html">
									<input type="button" class="btn btn-success"								onclick="answer_input()" value="홈으로">
								</a>
								</div>
						</div>
								<!--<img src="assets/img/logo.png" alt="Logo">-->
							</div>

						</div>
					</div>
				</div>
			</div>
			
			<div id="chart_div" align="center" style="width:100%;"></div>

			
			
			<!---------------- 예제 코드 시작 ---------------->

			<div id="ctlGrid"  class="table-responsive" style="width:100%;padding: 10px"></div>

			<!----------------  팝업 ---------------->
			<br/>
			<input type="button" class="btn btn-primary btn-large btn-block"								onclick="answer_input()" value="정답입력하기">
			<input type="button" class="btn btn-primary btn-large btn-block" onclick="tableToExcel('ctlGrid', 'W3C Example Table')" value="엑셀로 저장하기">
			<br/>
			<br/>
		</div>
		<script src="answer_list.js"></script>

	</body>
</html>