package a.com.moon.baedalmal;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main_Study extends Activity implements OnClickListener,
		OnTouchListener {
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
	TextView sub_back;
	Drawable alpha1;
	Drawable alpha2;
	Drawable alpha3;
	Animation slideInT;
	Animation slideInB;
	Animation slideInL;
	Animation slideInR;
	Animation slideOutB;
	Animation slideOutT;
	Animation slideOutR;
	Animation slideOutL;
	static MediaPlayer mp = null;
	String chapter;
	Calendar cal;
	private final int MAX_RECORD_TIME = 30000;
	private int currentRecordTimeMs = 0;
	private boolean isPlayed = false;

	private RecordManager recordManager = null;
	private RecordAsyncTask recordAsyncTask = null;
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
		tv = (TextView) findViewById(R.id.status2);

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
		toast = Toast.makeText(this, "��Ʈ��ũ ������ Ȯ���ϼ���!!", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);

		slideInB = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);
		slideInL = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
		slideInR = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
		slideInT = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
		slideOutB = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
		slideOutL = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
		slideOutR = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
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
		wv.getSettings().setPluginsEnabled(true);

		// javascript�� window.open ���
		wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

		// javascript ���
		wv.getSettings().setJavaScriptEnabled(true);

		// meta�±��� viewport��� ����
		wv.getSettings().setUseWideViewPort(true);
		wv.loadUrl("http://115.68.2.89/~baedalmal/m/bbs/board.php?bo_table=21");
		wvclient = new WebView_Client();
		wv.setWebViewClient(wvclient);

		tab2();
	}

	public void share(String editName, String ment) {
		if (isPlayed) {
			pause();
		}

		uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + "loup", "record.m4a"));
		mediaPlayer = MediaPlayer.create(this, uri);
		if (mediaPlayer != null) {
			// ���ε�
			Log.d("cc", "���ε�");
			pb.setVisibility(View.VISIBLE);
			String format = new String("yyyyMMddHHmmss");
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);
			dateName = sdf.format(new Date())
					+ String.valueOf((int) Math.random() * 10);
			File file1 = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + File.separator + "loup", "record.m4a");
			File file2 = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + File.separator + "loup", dateName
					+ ".m4a");
			file1.renameTo(file2);

			Log.d("���ε����ϸ�", dateName);
			new MyAsyncTask().execute(editName);

		} else {
			Toast.makeText(this, "RecordFile doesn't exist.",
					Toast.LENGTH_SHORT).show();
		}

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
				Log.d("���ε����ϸ�(http)", String.valueOf(file));
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
					Log.d("����", "Response: " + responseStr);

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

	private void initialize() {
		recordImageButton = (ImageButton) findViewById(R.id.ib_record);
		playImageButton = (ImageButton) findViewById(R.id.ib_play);
		btn_share = (ImageButton) findViewById(R.id.share);
		btn_share.setOnClickListener(this);
		playImageButton.setOnClickListener(this);
		recordImageButton.setOnTouchListener(this);
	}

	private void playHandler() {
		if (isPlayed) {
			pause();
		} else {
			uri = Uri.fromFile(new File(Environment
					.getExternalStorageDirectory().getAbsolutePath()
					+ File.separator + "loup", "record.m4a"));
			mediaPlayer = MediaPlayer.create(this, uri);
			if (mediaPlayer != null) {
				isPlayed = true;
				mediaPlayer
						.setOnCompletionListener(mediaPlayerOnCompletionListener);
				mediaPlayer.start();
				playImageButton.setImageResource(R.drawable.btn_pause);
			} else {
				Toast.makeText(this, "RecordFile doesn't exist.",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void pause() {
		isPlayed = false;
		mediaPlayer.reset();
		playImageButton.setImageResource(R.drawable.btn_play);
	}

	private MediaPlayer.OnCompletionListener mediaPlayerOnCompletionListener = new MediaPlayer.OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp) {
			pause();
		}
	};

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			currentRecordTimeMs = 0;
			recordAsyncTask = new RecordAsyncTask();
			recordAsyncTask.execute();
			tv.setText("������..");

		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (recordAsyncTask != null)
				recordAsyncTask.cancel(true);
			Toast.makeText(this, "Record Success!", Toast.LENGTH_SHORT).show();
			tv.setText("����");
		}
		return false;
	}

	public void blankName() {
		showDialog();
	}

	public void showDialog() {
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.custom_dialog,
				(ViewGroup) findViewById(R.id.layout_root));

		AlertDialog.Builder aDialog = new AlertDialog.Builder(Main_Study.this);// ���⼭buttontest��
																				// ��Ű���̸�
		aDialog.setTitle("���� ���� ���ε忡 ����� �г��Ӱ� �Ѹ��� �Է��Ͻÿ�.");
		aDialog.setView(layout);

		aDialog.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				EditText editText = (EditText) layout
						.findViewById(R.id.inputText);
				dateName = editText.getText().toString();
				onlyName = editText.getText().toString();
				if (onlyName.equals("") || ment.equals("")) {
					Toast.makeText(Main_Study.this, "�г��Ӱ� �Ѹ��� �Է��ϼ���!",
							Toast.LENGTH_SHORT).show();
					blankName();
				} else {
					share(dateName, ment);
				}
			}
		});
		aDialog.setNegativeButton("���", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog ad = aDialog.create();
		ad.show();
	}

	public class RecordAsyncTask extends AsyncTask<Void, Integer, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			recordManager = new RecordManager();
			recordManager.start();
			while (true) {
				if (!recordManager.isRecorded()
						|| currentRecordTimeMs > MAX_RECORD_TIME) {
					recordManager.stop();
					return null;
				}
				try {
					currentRecordTimeMs += 100;
					publishProgress(currentRecordTimeMs);
					Thread.sleep(100);
				} catch (InterruptedException e) {
					recordManager.stop();
					return null;
				}
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			if (recordManager != null)
				recordManager.stop();
		}
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

			// ���̾�α� ����
			final Dialog dialog = new Dialog(Main_Study.this);
			// ���̾�α��� ������ ���
			Window window = dialog.getWindow();
			// ���̾�αװ� ������ �����츦 �帴�ϰ� �����
			window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
					WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
			// Ÿ��Ʋ�� �����Ѵ�
			dialog.setTitle("����� ���� �پ ����");
			/*
			 * setContentView() �ϱ����������� �������� ���ʿ� �߰��ϱ� ���ؼ������� ���Ȯ���� Ȱ��ȭ�Ѵ�
			 */

			dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
			// ���̾ƿ��� ���÷���Ʈ ��Ų��
			dialog.setContentView(R.layout.notice_dialog);
			/*
			 * ������ ���ʿ� ���ҽ� ID ��ġ�� �̹����� �׸����� �Լ� ȣ������ requestWindowFeature()�ϰ�
			 * setContentView()�� �Ѵ�
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

		case R.id.play1:
			Intent activityIntent = new Intent(Main_Study.this, M1.class);
			activityIntent.putExtra("CHUNK", "1");
			activityIntent.putExtra("CHAPTER", chapter);
			startActivity(activityIntent);
			break;

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

		case R.id.ib_play: {
			playHandler();
			break;
		}

		default:
			break;
		}

	}

	public void share() {
		String webUrl = wv.getUrl();

		Intent msg = new Intent(Intent.ACTION_SEND);

		msg.addCategory(Intent.CATEGORY_DEFAULT);

		msg.putExtra(Intent.EXTRA_SUBJECT, "");

		msg.putExtra(Intent.EXTRA_TEXT, "��޸����� " + webUrl);

		msg.putExtra(Intent.EXTRA_TITLE, "����");

		msg.setType("text/plain");

		startActivity(Intent.createChooser(msg, "����"));
	}

	private void tab1() {
		tab2.setVisibility(View.GONE);
		tab1.setVisibility(View.VISIBLE);
		tab3.setVisibility(View.GONE);
		alpha2.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha1.setColorFilter(0xffffff55, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffffff, Mode.MULTIPLY);

	}

	private void tab2() {
		tab1.setVisibility(View.GONE);
		tab2.setVisibility(View.VISIBLE);
		tab3.setVisibility(View.GONE);
		alpha1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffff55, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffffff, Mode.MULTIPLY);

	}

	private void tab3() {
		final ImageView game1 = (ImageView) findViewById(R.id.game1);
		final ImageView game2 = (ImageView) findViewById(R.id.game2);
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

		tab2.setVisibility(View.GONE);
		tab3.setVisibility(View.VISIBLE);
		tab1.setVisibility(View.GONE);
		alpha1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffff55, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffffff, Mode.MULTIPLY);

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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		ImageView game1 = (ImageView) findViewById(R.id.game1);
		ImageView game2 = (ImageView) findViewById(R.id.game2);
		game1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		game2.setColorFilter(0xffffffff, Mode.MULTIPLY);
	}
}
