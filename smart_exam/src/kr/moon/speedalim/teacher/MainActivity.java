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
		toast = Toast.makeText(this, "��Ʈ��ũ ������ Ȯ���ϼ���!!", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);

		// ���ۿ��� �����ϴ� ũ��Ŭ���̾�Ʈ�� �����Ѵ�.
		WebChromeClient testChromeClient = new WebChromeClient();

		// ������ ũ�� Ŭ���̾�Ʈ�� ���信 �����Ѵ�

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
