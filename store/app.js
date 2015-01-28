var app_names = ['<img src="img/icon/android.png"/>손안에서 둘러보는 <br/> 우리나라 특산물', '<img src="img/icon/android.png"/>스마트 사이언스', '<img src="img/icon/android.png"/>전래동화와 함께<br/>떠나는 수학나라', '<img src="img/icon/android.png"/>가야의 땅 경남', '<img src="img/icon/android.png"/>지구와 달', '<img src="img/icon/android.png"/>위인이 들려주는<br/> 역사 이야기', '<img src="img/icon/android.png"/>역사 속 인물탐구', '<img src="img/icon/android.png"/>스마트 실험실', '<img src="img/icon/android.png"/>한국사 타임머신', '<img src="img/icon/android.png"/>사회과부도', '<img src="img/icon/android.png"/>학교생활<br/>퀵 가이드', '<img src="img/icon/ios.png"/>무지개 학급경영', '모듬학습<br/>도구 Top 20', '내꺼야?<br/>네꺼야?','내 손안의<br/>스마트 2.0'];
var app_ids = ['pe.giulim.specialities1', 'com.moon.recycler', 'com.g11tg.Company.ProductName', 'zz_gimhae.exitschool.kr', 'com.moon.earth.moon', 'com.knowledgeware.modelexecutor.greatman_1410220155', 'com.distoma.personedu', 'air.SmartExperiment', 'kr.go.gne.time.machine.korean.history', 'appinventor.ai_jhujubi.k', 'com.knowledgeware.modelexecutor.teach_helper_1410310126'];
var appId;

function init() {

	if (location.search.length > 1) {
		appId = location.search.split('id=')[1]
	}

	$(".icon2").attr('src', 'img/icon/' + appId + '.png');
	$("#app_content").attr('src', 'img/content/' + appId + '.jpg');
	$("#app_name").html(app_names[appId - 1]);

	if (appId > 12)
		$("#app_down").attr('src', 'img/down_pdf.png');
}


$(".btn_back").click(function(event) {
	window.location.href = "index.html";
});
$("#app_down").click(function(event) {
	var down_link;
	if (appId < 12) {
		down_link = "https://play.google.com/store/apps/details?id=" + app_ids[appId - 1];
	} else if (appId == 12) {
		down_link = "https://itunes.apple.com/kr/app/leinbou-haggeubgyeong-yeong/id933807155?mt=8";
	} else if (appId > 12) {
		down_link = "pdf/" + appId+".pdf";
		console.log(down_link)
	}
	window.location.href = down_link;
});

$("#app_ppt").click(function(event) {
	var down_ppt_link;
	if(appId<15)
		down_ppt_link = 'ppt/' + appId + '.pptx';
	else
		down_ppt_link = 'img/content/' + appId + '.jpg';
	window.open(down_ppt_link);
});

init();

