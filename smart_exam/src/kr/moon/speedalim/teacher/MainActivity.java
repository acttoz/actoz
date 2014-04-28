package kr.moon.speedalim.teacher;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
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
	WebViewClient wvclient;
	static ProgressDialog dialog1;
	static Toast toast;

	Toast finishToast;
	private boolean mFlag = false;
	private Handler mHandler;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act2);
		// TODO Auto-generated method stub

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

		// TODO Auto-generated method stub
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
