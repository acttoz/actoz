package kr.moon.chunk1_45;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
	final Activity act = this;
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
		setContentView(R.layout.sns);
		// TODO Auto-generated method stub

		dialog1 = new ProgressDialog(act);
		toast = Toast.makeText(this, "��Ʈ��ũ ������ Ȯ���ϼ���!!", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);

		wv = (WebView) findViewById(R.id.webview);
		wv.setVerticalScrollbarOverlay(true);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setVerticalScrollBarEnabled(false);
		wv.setHorizontalScrollBarEnabled(false);
		// wv.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
		wv.getSettings().setLoadWithOverviewMode(true);
		wv.getSettings().setUseWideViewPort(true);
		// wv.setInitialScale(26);
//		wv.getSettings().setUserAgent(0);
		wv.setBackgroundColor(0); // ����
		// wv.addJavascriptInterface(new AndroidBridge(), "HybridApp"); //��ũ��Ʈ
		// Ȯ��
		wv.setWebChromeClient(new WebChromeClient());
		// ĳ������ ��� ����(��߿� �ּ�ó�� �� ��)
		// wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

		// zoom ���
		// wv.getSettings().setBuiltInZoomControls(true);
		// wv.getSettings().setSupportZoom(true);

		// ���÷����� ���
//		wv.getSettings().setPluginsEnabled(true);

		// javascript�� window.open ���
		wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

		// javascript ���
		wv.getSettings().setJavaScriptEnabled(true);

		// meta�±��� viewport��� ����
		wv.getSettings().setUseWideViewPort(true);
		wv.loadUrl("file:///android_asset/13chunk/sns.html");

		wvclient = new WebView_Client();
		wv.setWebViewClient(wvclient);

		// TODO Auto-generated method stub
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_BACK) && (wv.canGoBack())) {
			wv.goBack();
			return true;
			// TODO Auto-generated method stub

		} else if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			wv.loadUrl("about:blank");
			finish();
			return super.onKeyUp(keyCode, event);
		}
		return true;
	}

}
