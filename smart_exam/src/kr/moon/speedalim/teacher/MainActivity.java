package kr.moon.speedalim.teacher;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
	final Activity act = this;
	SoundPool mPool;
	int mTouch;
	WebView wv;
	boolean popup = true;
	WebViewClient wvclient;
	static ProgressDialog dialog1;
	static Toast toast;
	SharedPreferences idPrefs;
	SharedPreferences.Editor editor;
	Toast finishToast;
	private boolean mFlag = false;
	private Handler mHandler;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act2);
		// TODO Auto-generated method stub
		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();
		popup = idPrefs.getBoolean("POPUP", true);
		dialog1 = new ProgressDialog(act);
		toast = Toast.makeText(this, "네트워크 연결을 확인하세요!!", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);

		// 구글에서 제공하는 크롬클라이언트를 생성한다.
		WebChromeClient testChromeClient = new WebChromeClient();

		// 생성한 크롬 클라이언트를 웹뷰에 셋팅한다

		wv = (WebView) findViewById(R.id.webview);
		wv.setWebChromeClient(testChromeClient);
		wv.setVerticalScrollbarOverlay(true);
		wv.getSettings().setJavaScriptEnabled(true);

		wv.setVerticalScrollBarEnabled(false);
		wv.setHorizontalScrollBarEnabled(false);
		wv.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
		wv.getSettings().setLoadWithOverviewMode(false);
		wv.getSettings().setUseWideViewPort(false);
		wv.loadUrl("http://clicknote.cafe24.com/xe/main?m=1");
		wvclient = new WebView_Client();
		wv.setWebViewClient(wvclient);
		if (popup)
			showNotice();

		// TODO Auto-generated method stub
	}

	public void showNotice() {
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.notice_dialog,
				(ViewGroup) findViewById(R.id.layout_root));
		AlertDialog.Builder aDialog = new AlertDialog.Builder(MainActivity.this);// 여기서buttontest는
		// 패키지이름
		aDialog.setTitle("알림");
		aDialog.setMessage("스피드 알림장 교사용 앱입니다.\n학교에서는 PC로\n(speednote.net)\n퇴근후에는 스마트폰으로\n언제나 학생들에게 메세지를 전달하세요.\n다른 선생님께도 스피드 알림장 추천하기!");
		aDialog.setView(layout);

		aDialog.setPositiveButton("추천하기",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						shareImage();
					}
				});
		aDialog.setNeutralButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		aDialog.setNegativeButton("다시 안보기",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						editor.putBoolean("POPUP", false);
						editor.commit();
					}
				});
		AlertDialog ad = aDialog.create();
		ad.show();
	}

	public void shareImage() { // 공유 이미지 함수
	// Intent shareIntent = new Intent();
	// shareIntent.setAction(Intent.ACTION_SEND);
	// shareIntent.setType("image/jpg");
	// Uri uri = Uri.parse("android.resource://kr.moon.speedalim.teacher/"
	// + R.drawable.link);
	// shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
	// shareIntent.putExtra(Intent.EXTRA_TEXT,
	// "설치하기 market://details?id=packageName");
	// startActivity(Intent.createChooser(shareIntent, "Send your image"));
		Intent intent;


		intent = new Intent(Intent.ACTION_SEND);
		// text
		intent.putExtra(Intent.EXTRA_TEXT, "10000다운로드 돌파! 개설학급 300개 돌파!\nPC(speednote.net)나 스마트폰을 이용해서 간단하게 알림장을 학생들에게 보낼수 있습니다.\n1.교사용 설치하기 http://m.site.naver.com/0a3zM\n2.학생용 설치하기 http://m.site.naver.com/0a3zX");
		// image
		// type of things
		intent.setType("text/plain");
		// sending
		startActivity(intent);
		
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {

			wv.goBack();

			return true;

		}

		return super.onKeyDown(keyCode, event);

	}

}
