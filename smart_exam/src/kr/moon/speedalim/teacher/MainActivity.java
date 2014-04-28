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
		AlertDialog.Builder aDialog = new AlertDialog.Builder(MainActivity.this);// ���⼭buttontest��
		// ��Ű���̸�
		aDialog.setTitle("�˸�");
		aDialog.setMessage("���ǵ� �˸��� ����� ���Դϴ�.\n�б������� PC��\n(speednote.net)\n����Ŀ��� ����Ʈ������\n������ �л��鿡�� �޼����� �����ϼ���.\n�ٸ� �����Բ��� ���ǵ� �˸��� ��õ�ϱ�!");
		aDialog.setView(layout);

		aDialog.setPositiveButton("��õ�ϱ�",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						shareImage();
					}
				});
		aDialog.setNeutralButton("Ȯ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		aDialog.setNegativeButton("�ٽ� �Ⱥ���",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						editor.putBoolean("POPUP", false);
						editor.commit();
					}
				});
		AlertDialog ad = aDialog.create();
		ad.show();
	}

	public void shareImage() { // ���� �̹��� �Լ�
	// Intent shareIntent = new Intent();
	// shareIntent.setAction(Intent.ACTION_SEND);
	// shareIntent.setType("image/jpg");
	// Uri uri = Uri.parse("android.resource://kr.moon.speedalim.teacher/"
	// + R.drawable.link);
	// shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
	// shareIntent.putExtra(Intent.EXTRA_TEXT,
	// "��ġ�ϱ� market://details?id=packageName");
	// startActivity(Intent.createChooser(shareIntent, "Send your image"));
		Intent intent;


		intent = new Intent(Intent.ACTION_SEND);
		// text
		intent.putExtra(Intent.EXTRA_TEXT, "10000�ٿ�ε� ����! �����б� 300�� ����!\nPC(speednote.net)�� ����Ʈ���� �̿��ؼ� �����ϰ� �˸����� �л��鿡�� ������ �ֽ��ϴ�.\n1.����� ��ġ�ϱ� http://m.site.naver.com/0a3zM\n2.�л��� ��ġ�ϱ� http://m.site.naver.com/0a3zX");
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
