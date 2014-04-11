package kr.co.moon.alimy.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
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

		wv = (WebView) findViewById(R.id.webview);
		wv.setVerticalScrollbarOverlay(true);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setVerticalScrollBarEnabled(false);
		wv.setHorizontalScrollBarEnabled(false);
		wv.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
		wv.getSettings().setLoadWithOverviewMode(false);
		wv.getSettings().setUseWideViewPort(false);
		wv.loadUrl("http://clicknote.cafe24.com/xe/index.php?mid=alrim&act=dispMemberLoginForm");
		wvclient = new WebView_Client();
		wv.setWebViewClient(wvclient);

		// TODO Auto-generated method stub
	}
	
	
	

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!mFlag) {
				finishToast.show();
				mFlag = true;
				mHandler.sendEmptyMessageDelayed(0, 2000);
				return false;
			} else {
				finishToast.cancel();
				finish();
			}
		}

		return super.onKeyUp(keyCode, event);
	}

}
