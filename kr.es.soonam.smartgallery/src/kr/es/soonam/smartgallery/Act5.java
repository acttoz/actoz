package kr.es.soonam.smartgallery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Act5 extends Activity implements OnClickListener {
	final Activity act2 = this;
	SoundPool mPool;
	int mTouch;
	WebView wv3;
	WebViewClient wvclient3;
	static ProgressDialog dialog3;
	static Toast toast2;
	int btnId;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.act5);
	    // TODO Auto-generated method stub
	    
	    Intent intent = getIntent();
		btnId = intent.getIntExtra("ID", 0);
		
		String tmpSign = "button" + btnId;
		
		int lid = this.getResources().getIdentifier(tmpSign, "drawable",
				this.getPackageName());
		
		ImageView title = (ImageView)findViewById(R.id.title_image);
		title.setBackgroundResource(lid);

	   
	    dialog3 = new ProgressDialog(act2);
	    toast2 = Toast.makeText(this, "네트워크 연결을 확인하세요!!", Toast.LENGTH_SHORT);
	    toast2.setGravity(Gravity.CENTER,
	    		0,0);
	    
	    wv3=(WebView)findViewById(R.id.webview3);
	    wv3.setVerticalScrollbarOverlay(true);
	    wv3.getSettings().setJavaScriptEnabled(true);
	    wv3.setVerticalScrollBarEnabled(false);
		wv3.setHorizontalScrollBarEnabled(false);
		wv3.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
		wv3.getSettings().setLoadWithOverviewMode(false);
		wv3.getSettings().setUseWideViewPort(false);
		
		String xmlUrl = String.format(

				"http://actoz.dothome.co.kr/home/index.php?mid=smart%s", btnId);
		
	    wv3.loadUrl(xmlUrl);
	    wvclient3 = new WebView3();
	    wv3.setWebViewClient(wvclient3);
	    
	    
	    
	    
	    ImageButton btn = (ImageButton)findViewById(R.id.homebtn);
	    btn.setOnClickListener(this);
	    
	    mPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mTouch = mPool.load(this, R.raw.touch, 1);
	    // TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mPool.play(mTouch, 1, 1, 0, 0, 1);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (wv3.canGoBack())) {
			wv3.goBack();
			return true;
			// TODO Auto-generated method stub

		}
		return super.onKeyDown(keyCode, event);
	}

}
