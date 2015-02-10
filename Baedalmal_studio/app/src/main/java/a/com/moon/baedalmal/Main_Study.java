package a.com.moon.baedalmal;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;

public class Main_Study extends Activity implements OnClickListener {
	String dateName, onlyName, ment;
	TextView tv, instruction;
	View tab1, tab2, tab3;
	LinearLayout sub_menu;
	TextView sub1;
	TextView sub2;
	TextView sub3;
	TextView sub4;
	TextView sub5;
	TextView sub6;
	TextView sub7;
	int flag_tab;
	TextView sub_back;
	Drawable alpha1;
	Drawable alpha2;
	Drawable alpha3;
	Animation slideInT;
	Animation slideOutT;
	Animation slideInB;
	Animation slideOutB;
	Animation slideInL;
	Animation slideOutL;
	Animation slideInR;
	Animation slideOutR;
	static MediaPlayer mp = null;
	String chapter;
	Calendar cal;
	private final int MAX_RECORD_TIME = 30000;
	private int currentRecordTimeMs = 0;
	private boolean isPlayed = false;

	private MediaPlayer mediaPlayer = null;
	private Uri uri = null;

	private ImageButton recordImageButton, playImageButton, btn_share;
	ImageView shareBtn;
	private ProgressBar pb;
	// upload
	String selectedPath = "";

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
		setContentView(R.layout.main_study);
		ImageView btn1, btn2, btn3, btn4, quiz_Image, speakImage, helpBtn;
		Button playBtn1, playBtn2;
		cal = Calendar.getInstance();

		sub_menu = (LinearLayout) findViewById(R.id.sub_menu);
		sub1 = (TextView) findViewById(R.id.sub1);
		sub2 = (TextView) findViewById(R.id.sub2);
		sub3 = (TextView) findViewById(R.id.sub3);
		sub4 = (TextView) findViewById(R.id.sub4);
		sub5 = (TextView) findViewById(R.id.sub5);
		sub6 = (TextView) findViewById(R.id.sub6);
		sub7 = (TextView) findViewById(R.id.sub7);
		sub_back = (TextView) findViewById(R.id.sub_back);

		sub1.setOnClickListener(this);
		sub2.setOnClickListener(this);
		sub3.setOnClickListener(this);
		sub4.setOnClickListener(this);
		sub5.setOnClickListener(this);
		sub6.setOnClickListener(this);
		sub7.setOnClickListener(this);
		sub_back.setOnClickListener(this);

		btn1 = (ImageView) findViewById(R.id.btn1);
		btn2 = (ImageView) findViewById(R.id.btn2);
		btn3 = (ImageView) findViewById(R.id.btn3);
		helpBtn = (ImageView) findViewById(R.id.help);
		shareBtn = (ImageView) findViewById(R.id.share);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		helpBtn.setOnClickListener(this);
		shareBtn.setOnClickListener(this);

		tab1 = (View) findViewById(R.id.tab1);
		tab2 = (View) findViewById(R.id.tab2);
		tab3 = (View) findViewById(R.id.tab3);

		alpha1 = btn1.getDrawable();
		alpha2 = btn2.getDrawable();
		alpha3 = btn3.getDrawable();

		dialog1 = new ProgressDialog(act);
		toast = Toast.makeText(this, "네트워크 연결을 확인하세요!!", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);

		slideInB = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);
		slideOutB = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
		slideInL = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
		slideOutL = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
		slideInR = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
		slideOutR = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
		slideInT = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
		slideOutT = AnimationUtils.loadAnimation(this, R.anim.slide_out_top);

		wv = (WebView) findViewById(R.id.webview);
		wv.setVerticalScrollbarOverlay(true);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setVerticalScrollBarEnabled(false);
		wv.setHorizontalScrollBarEnabled(false);
		// wv.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
		wv.getSettings().setLoadWithOverviewMode(true);
		wv.getSettings().setUseWideViewPort(true);
		// wv.setInitialScale(26);
		wv.getSettings().setUserAgent(0);
		wv.setBackgroundColor(0); // 배경색
		// wv.addJavascriptInterface(new AndroidBridge(), "HybridApp"); //스크립트
		// 확장
		wv.setWebChromeClient(new WebChromeClient());
		// 캐시파일 사용 금지(운영중엔 주석처리 할 것)
		// wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

		// zoom 허용
		// wv.getSettings().setBuiltInZoomControls(true);
		// wv.getSettings().setSupportZoom(true);

		// 웹플러그인 허용
		wv.getSettings().setPluginsEnabled(true);

		// javascript의 window.open 허용
		wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

		// javascript 허용
		wv.getSettings().setJavaScriptEnabled(true);

		// meta태그의 viewport사용 가능
		wv.getSettings().setUseWideViewPort(true);
		wv.loadUrl("http://115.68.2.89/~baedalmal/m/bbs/board.php?bo_table=21");
		wvclient = new WebView_Client();
		wv.setWebViewClient(wvclient);

		tab2();
	}

	private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

		@Override
		protected Double doInBackground(String... params) {
			// TODO Auto-generated method stub
			postData(params[0]);
			return null;
		}

		protected void onPostExecute(Double result) {
			pb.setVisibility(View.GONE);
			Toast.makeText(getApplicationContext(), "command sent",
					Toast.LENGTH_LONG).show();

			File file1 = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + File.separator + "loup", "record.m4a");
			File file2 = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + File.separator + "loup", dateName
					+ ".m4a");
			file2.renameTo(file1);
		}

		protected void onProgressUpdate(Integer... progress) {
			pb.setProgress(progress[0]);
		}

		public void postData(String valueIWantToSend) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://actoz.dothome.co.kr/13chunk/upload.php");

			try {
				File file = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + File.separator + "loup", dateName
						+ ".m4a");

				ContentBody fb = new FileBody(file);
				Log.d("업로드파일명(http)", String.valueOf(file));
				MultipartEntity entity = new MultipartEntity(
						HttpMultipartMode.STRICT);

				// ArrayList<NameValuePair> nameValuePairs = new
				// ArrayList<NameValuePair>(
				// 2);
				// nameValuePairs.add(new BasicNameValuePair("name", onlyName));
				// nameValuePairs.add(new BasicNameValuePair("month", cal
				// .get(Calendar.MONTH) + 1 + ""));
				// nameValuePairs.add(new BasicNameValuePair("day", cal
				// .get(Calendar.DATE) + ""));
				// nameValuePairs.add(new BasicNameValuePair("name", onlyName));
				// nameValuePairs.add(new BasicNameValuePair("month", cal
				// .get(Calendar.MONTH) + 1 + ""));
				// nameValuePairs.add(new BasicNameValuePair("day", cal
				// .get(Calendar.DATE) + ""));
				String month = cal.get(Calendar.MONTH) + 1 + "";
				String day = cal.get(Calendar.DATE) + "";

				entity.addPart(URLEncoder.encode("name", "UTF-8"),
						new StringBody(URLEncoder.encode(onlyName, "UTF-8")));
				entity.addPart(URLEncoder.encode("ment", "UTF-8"),
						new StringBody(URLEncoder.encode(ment, "UTF-8")));
				entity.addPart(URLEncoder.encode("month", "UTF-8"),
						new StringBody(URLEncoder.encode(month, "UTF-8")));
				entity.addPart(URLEncoder.encode("day", "UTF-8"),
						new StringBody(URLEncoder.encode(day, "UTF-8")));
				entity.addPart("file", fb);
				httppost.setEntity(entity);

				HttpResponse response = httpclient.execute(httppost);

				// execute HTTP post request
				HttpEntity resEntity = response.getEntity();

				if (resEntity != null) {

					String responseStr = EntityUtils.toString(resEntity).trim();
					Log.d("응답", "Response: " + responseStr);

					// you can add an if statement here and do other actions
					// based on the response
				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// mp.release();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn1:
			tab1();

			break;
		case R.id.btn2:
			sub_menu.setVisibility(View.VISIBLE);
			sub_menu.startAnimation(slideInL);
			sub_back.setVisibility(View.VISIBLE);
			tab2();
			break;
		case R.id.btn3:

			tab3();
			break;
		case R.id.help:

			// 다이얼로그 생성
			final Dialog dialog = new Dialog(Main_Study.this);
			// 다이얼로그의 윈도우 얻기
			Window window = dialog.getWindow();
			// 다이얼로그가 가리는 윈도우를 흐릿하게 만든다
			window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
					WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
			// 타이틀을 설정한다
			dialog.setTitle("토박이 말이 뛰어난 이유");
			/*
			 * setContentView() 하기전에윈도우 아이콘을 왼쪽에 추가하기 위해서윈도우 기능확장을 활성화한다
			 */

			dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
			// 레이아웃을 인플레이트 시킨다
			dialog.setContentView(R.layout.notice_dialog);
			/*
			 * 윈도우 왼쪽에 리소스 ID 위치의 이미지를 그린다이 함수 호출전에 requestWindowFeature()하고
			 * setContentView()을 한다
			 */
			window.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
					R.drawable.help_btn);
			// TextView notice_tv = (TextView) dialog
			// .findViewById(R.id.server_textView);
			// notice_tv.setText("@string/help");
			Button sb = (Button) dialog.findViewById(R.id.server_search_button);
			sb.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});

			dialog.show();

			break;
		case R.id.sub1:
			wv.loadUrl("http://115.68.2.89/~baedalmal/m/bbs/board.php?bo_table=21");
			sub_menu.startAnimation(slideOutL);
			sub_menu.setVisibility(View.GONE);
			sub_back.setVisibility(View.GONE);
			break;
		case R.id.sub2:
			wv.loadUrl("http://115.68.2.89/~baedalmal/m/bbs/board.php?bo_table=25");
			sub_menu.startAnimation(slideOutL);
			sub_back.setVisibility(View.GONE);
			sub_menu.setVisibility(View.GONE);
			break;
		case R.id.sub3:
			wv.loadUrl("http://115.68.2.89/~baedalmal/m/bbs/board.php?bo_table=24");
			sub_menu.startAnimation(slideOutL);
			sub_back.setVisibility(View.GONE);
			sub_menu.setVisibility(View.GONE);
			break;
		case R.id.sub4:
			wv.loadUrl("http://115.68.2.89/~baedalmal/m/bbs/board.php?bo_table=23");
			sub_menu.startAnimation(slideOutL);
			sub_back.setVisibility(View.GONE);
			sub_menu.setVisibility(View.GONE);
			break;
		case R.id.sub5:
			wv.loadUrl("http://115.68.2.89/~baedalmal/m/bbs/board.php?bo_table=22");
			sub_menu.startAnimation(slideOutL);
			sub_back.setVisibility(View.GONE);
			sub_menu.setVisibility(View.GONE);
			break;
		case R.id.sub6:
			wv.loadUrl("http://115.68.2.89/~baedalmal/m/bbs/board.php?bo_table=28");
			sub_menu.startAnimation(slideOutL);
			sub_back.setVisibility(View.GONE);
			sub_menu.setVisibility(View.GONE);
			break;
		case R.id.sub7:
			break;
		case R.id.sub_back:
			sub_menu.startAnimation(slideOutL);
			sub_menu.setVisibility(View.GONE);
			sub_back.setVisibility(View.GONE);
			break;

		// case R.id.game1:
		// Intent activityIntent2 = new Intent(Main_Study.this,
		// LockScreenAppActivity.class);
		// startActivity(activityIntent2);
		// break;
		// case R.id.game2:
		// Intent activityIntent3 = new Intent(Main_Study.this,
		// Act3.class);
		// startActivity(activityIntent3);
		// break;

		case R.id.quiz:

			if (!mp.isPlaying()) {
				mp.reset();
				String tmp_Sound = "s" + chapter;
				int lid2 = this.getResources().getIdentifier(tmp_Sound, "raw",
						this.getPackageName());
				mp = MediaPlayer.create(this, lid2);
				try {
					mp.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mp.start();
			} else {
				mp.stop();
			}

			break;

		case R.id.share:
			share();
			break;

		default:
			break;
		}

	}

	public void share() {
		String webUrl = wv.getUrl();

		Intent msg = new Intent(Intent.ACTION_SEND);

		msg.addCategory(Intent.CATEGORY_DEFAULT);

		msg.putExtra(Intent.EXTRA_SUBJECT, "");

		msg.putExtra(Intent.EXTRA_TEXT, "배달말누리 " + webUrl);

		msg.putExtra(Intent.EXTRA_TITLE, "제목");

		msg.setType("text/plain");

		startActivity(Intent.createChooser(msg, "공유"));
	}

	private void tab1() {
		flag_tab=1;
		tab2.setVisibility(View.GONE);
		tab1.setVisibility(View.VISIBLE);
		tab3.setVisibility(View.GONE);
		alpha2.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha1.setColorFilter(0xffffff55, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffffff, Mode.MULTIPLY);

	}

	private void tab2() {
		flag_tab=2;
		tab1.setVisibility(View.GONE);
		tab2.setVisibility(View.VISIBLE);
		tab3.setVisibility(View.GONE);
		alpha1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffff55, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffffff, Mode.MULTIPLY);

	}

	private void tab3() {
		flag_tab=3;
		final ImageView game1 = (ImageView) findViewById(R.id.game1);
        final ImageView game2 = (ImageView) findViewById(R.id.game2);
        final ImageView game3 = (ImageView) findViewById(R.id.game3);
		game1.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (MotionEvent.ACTION_DOWN == event.getAction()) {

				} else if (MotionEvent.ACTION_UP == event.getAction()) {
					game1.setColorFilter(0xffffff55, Mode.MULTIPLY);
					Intent activityIntent = new Intent(Main_Study.this,
							LockScreenAppActivity.class);
					startActivity(activityIntent);
				}

				return true;
			}
		});
		game2.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (MotionEvent.ACTION_DOWN == event.getAction()) {

				} else if (MotionEvent.ACTION_UP == event.getAction()) {
					game2.setColorFilter(0xffffff55, Mode.MULTIPLY);
					Intent activityIntent = new Intent(Main_Study.this,
							Act3.class);
					startActivity(activityIntent);
				}

				return true;
			}
		});
        game3.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {

                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    game3.setColorFilter(0xffffff55, Mode.MULTIPLY);
                    Intent activityIntent = new Intent(Main_Study.this,
                            Act3.class);
                    startActivity(activityIntent);
                }

                return true;
            }
        });

		tab2.setVisibility(View.GONE);
		tab3.setVisibility(View.VISIBLE);
		tab1.setVisibility(View.GONE);
		alpha1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffff55, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffffff, Mode.MULTIPLY);

	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_BACK) && (wv.canGoBack())&& flag_tab==2) {
			wv.goBack();
			return true;
			// TODO Auto-generated method stub

		} else if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finish();
			return super.onKeyUp(keyCode, event);
		}
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		ImageView game1 = (ImageView) findViewById(R.id.game1);
        ImageView game2 = (ImageView) findViewById(R.id.game2);
        ImageView game3 = (ImageView) findViewById(R.id.game3);
		game1.setColorFilter(0xffffffff, Mode.MULTIPLY);
        game2.setColorFilter(0xffffffff, Mode.MULTIPLY);
        game3.setColorFilter(0xffffffff, Mode.MULTIPLY);
	}
}
