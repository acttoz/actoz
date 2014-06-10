package kr.moon.chunk2_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main_Study extends Activity implements OnClickListener,
		OnTouchListener {
	String dateName, onlyName, ment, nickName;
	String[] answer_list = new String[] { "5번", "3번", "5번", "5번", "4번" };
	TextView instruction;
	View tab1, tab2, tab3;
	Drawable alpha1;
	Drawable alpha2;
	Drawable alpha3;
	boolean flagTab = true;
	static MediaPlayer mp = null;
	String chapter;
	Calendar cal;
	private final int MAX_RECORD_TIME = 30000;
	private int currentRecordTimeMs = 0;
	private boolean isPlayed = false;
	boolean isRecording = false;
	private RecordManager recordManager = null;
	private RecordAsyncTask recordAsyncTask = null;
	private MediaPlayer mediaPlayer = null;
	private Uri uri = null;
	private AlertDialog mDialog = null;
	private ImageButton changeBtn, recordImageButton, playImageButton,
			btn_share;
	int appVer, newVer;
	private ProgressBar pb;
	String APPNAME;
	// upload
	String selectedPath = "";
	SharedPreferences idPrefs;
	SharedPreferences.Editor editor;
	ImageView check1, check2, speakImage;
	String speak_eng;
	String speak_korean;
	int speak_img, speak_flag;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_study);
		appVer = 55;
		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();
		checkVer();
		ImageView btn1, btn2, btn3, quiz_Image;
		final ImageView playBtn1;
		final ImageView playBtn2;
		// Button playBtn1, playBtn2;
		cal = Calendar.getInstance();
		// tv = (TextView) findViewById(R.id.status2);
		// 테스트용
		Intent i = getIntent();
		String header_title = i.getStringExtra("TITLE");
		chapter = i.getStringExtra("CHAPTER");
		Log.d("packageName:", getApplicationContext()
				.getPackageName());
		String str=getApplicationContext()
				.getPackageName();
		String[] tempPack=str.split("\\.");
		Log.d("packArray", tempPack[0]);
		APPNAME=tempPack[tempPack.length-1];
		Log.d("packageName:", APPNAME);
		editor.putString("APP", APPNAME);
		editor.commit();
		
		TextView header = (TextView) findViewById(R.id.title);
		instruction = (TextView) findViewById(R.id.instruction);
		instruction.setOnClickListener(this);
		header.setText(header_title);
		// chapter = "1";
		String tmpSign = "q" + chapter;
		speak_eng = "speak" + chapter;
		speak_korean = "korean" + chapter;

		btn1 = (ImageView) findViewById(R.id.btn1);
		btn2 = (ImageView) findViewById(R.id.btn2);
		btn3 = (ImageView) findViewById(R.id.btn3);
		// btn4 = (ImageView) findViewById(R.id.btn4);
		playBtn1 = (ImageView) findViewById(R.id.play1);
		playBtn2 = (ImageView) findViewById(R.id.play2);
		check1 = (ImageView) findViewById(R.id.check1);
		check2 = (ImageView) findViewById(R.id.check2);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		// btn4.setOnClickListener(this);
		playBtn1.setOnClickListener(this);
		playBtn2.setOnClickListener(this);
		playBtn1.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (MotionEvent.ACTION_DOWN == event.getAction()) {
					playBtn1.setColorFilter(0xffffff55, Mode.MULTIPLY);

				} else if (MotionEvent.ACTION_UP == event.getAction()) {
					playBtn1.setColorFilter(0xffffffff, Mode.MULTIPLY);
					Intent activityIntent = new Intent(Main_Study.this,
							M1.class);
					activityIntent.putExtra("CHUNK", "1");
					activityIntent.putExtra("CHAPTER", chapter);
					startActivity(activityIntent);
				}

				return true;
			}
		});
		playBtn2.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (MotionEvent.ACTION_DOWN == event.getAction()) {
					playBtn2.setColorFilter(0xffffff55, Mode.MULTIPLY);

				} else if (MotionEvent.ACTION_UP == event.getAction()) {
					playBtn2.setColorFilter(0xffffffff, Mode.MULTIPLY);
					Intent activityIntent2 = new Intent(Main_Study.this,
							M1.class);
					activityIntent2.putExtra("CHUNK", "2");
					activityIntent2.putExtra("CHAPTER", chapter);
					startActivity(activityIntent2);
				}

				return true;
			}
		});

		tab1 = (View) findViewById(R.id.tab1);
		tab2 = (View) findViewById(R.id.tab2);
		tab3 = (View) findViewById(R.id.tab3);

		alpha1 = btn1.getDrawable();
		alpha2 = btn2.getDrawable();
		alpha3 = btn3.getDrawable();

		speakImage = (ImageView) findViewById(R.id.speak);
		speakImage.setOnClickListener(this);

		int lid = this.getResources().getIdentifier(tmpSign, "drawable",
				this.getPackageName());

		speak_img = this.getResources().getIdentifier(speak_eng, "drawable",
				this.getPackageName());
		speak_flag = 0;

		speakImage.setImageResource(speak_img);
		quiz_Image = (ImageView) findViewById(R.id.quiz);
		quiz_Image.setImageResource(lid);
		quiz_Image.setOnClickListener(this);
		String tmp_Sound = "s" + chapter;
		int lid2 = this.getResources().getIdentifier(tmp_Sound, "raw",
				this.getPackageName());
		mp = MediaPlayer.create(this, lid2);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.GONE);

		initialize();
		tab1();
	}

	public void checkVer() {

		// WIFI, 3G 어느곳에도 연결되지 않았을때
		Log.d("dd", "Network connect success");
		DownloadText checkVer = new DownloadText();
		checkVer.start();

	}

	private class DownloadText extends Thread {
		final Handler mHandler = new Handler();
		StringBuilder text;

		public void run() {
			try {
				text = new StringBuilder();
				text.append("");
				URL url = new URL(
						"http://actoz.dothome.co.kr/13chunk/"+APPNAME+".txt");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				if (conn != null) {
					conn.setConnectTimeout(1000); // 1초 동안 인터넷 연결을 실패할경우 Fall 처리
					conn.setUseCaches(false);
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(conn.getInputStream()));
						// for (;;) {
						String line = br.readLine();
						// if (line == null)
						// break;
						text.append(line);
						// }
						br.close();
					}
					conn.disconnect();
				}

				mHandler.post(new Runnable() {
					public void run() {
						try {
							newVer = Integer.parseInt((text.toString()));

							Log.d("checkver", "" + newVer);
							if (appVer < newVer) {

								AlertDialog.Builder dlg = new AlertDialog.Builder(
										Main_Study.this);
								dlg.setTitle("업데이트 알림");
								dlg.setMessage("새버전이 있습니다. 업데이트를 해주세요!");
								dlg.setIcon(R.drawable.ic_launcher);
								dlg.setNegativeButton("업데이트 바로 가기",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {

												Uri uri = Uri
														.parse("market://details?id="
																+ getApplicationContext()
																		.getPackageName());
												Intent intent = new Intent(
														Intent.ACTION_VIEW, uri);
												intent.addCategory(Intent.CATEGORY_BROWSABLE);
												startActivity(intent);
												finish();
											}
										});
								dlg.setCancelable(true);
								dlg.show();
							}
						} catch (NumberFormatException nfe) {
							// TODO: handle exception
						}
					}
				});
			} catch (Exception ex) {
			}

		}
	}

	public void share() {
		if (isPlayed) {
			pause();
		}

		uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + "loup", "record.m4a"));
		mediaPlayer = MediaPlayer.create(this, uri);
		if (mediaPlayer != null) {
			// 업로드
			Log.d("cc", "업로드");
			// pb.setVisibility(View.VISIBLE);
			String format = new String("yyyyMMddHHmmss");
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);
			dateName = sdf.format(new Date())
					+ String.valueOf((int) Math.random() * 10);
			File file1 = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + File.separator + "loup", "record.m4a");

			Intent intent = new Intent(Intent.ACTION_SEND);

			MimeTypeMap type = MimeTypeMap.getSingleton();
			intent.setType(type.getMimeTypeFromExtension(MimeTypeMap
					.getFileExtensionFromUrl(Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ File.separator + "loup/record.m4a")));

			intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file1));
			intent.putExtra(Intent.EXTRA_TEXT, "청크 녹음 파일");
			startActivity(intent);
			Log.d("cc", "업로드끝");
			// File file2 = new File(Environment.getExternalStorageDirectory()
			// .getAbsolutePath() + File.separator + "loup", dateName
			// + ".m4a");
			// file1.renameTo(file2);

			// Log.d("업로드파일명", dateName);
			// new MyAsyncTask().execute(editName);

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
				Log.d("업로드파일명(http)", String.valueOf(file));
				MultipartEntityBuilder entity = MultipartEntityBuilder.create();
				// MultipartEntityBuilder entity = new MultipartEntityBui(
				// HttpMultipartMode.STRICT);

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
						new StringBody(URLEncoder.encode(onlyName, "UTF-8"),
								ContentType.DEFAULT_TEXT));
				entity.addPart(URLEncoder.encode("ment", "UTF-8"),
						new StringBody(URLEncoder.encode(ment, "UTF-8"),
								ContentType.DEFAULT_TEXT));
				entity.addPart(URLEncoder.encode("file", "UTF-8"),
						new StringBody(URLEncoder.encode(dateName, "UTF-8"),
								ContentType.DEFAULT_TEXT));
				entity.addPart(URLEncoder.encode("month", "UTF-8"),
						new StringBody(URLEncoder.encode(month, "UTF-8"),
								ContentType.DEFAULT_TEXT));
				entity.addPart(URLEncoder.encode("day", "UTF-8"),
						new StringBody(URLEncoder.encode(day, "UTF-8"),
								ContentType.DEFAULT_TEXT));
				// entity.addPart(URLEncoder.encode("ment", "UTF-8"),
				// new StringBody(URLEncoder.encode(ment, "UTF-8")));
				// entity.addPart(URLEncoder.encode("month", "UTF-8"),
				// new StringBody(URLEncoder.encode(month, "UTF-8")));
				// entity.addPart(URLEncoder.encode("day", "UTF-8"),
				// new StringBody(URLEncoder.encode(day, "UTF-8")));
				entity.addPart("file", fb);
				httppost.setEntity(entity.build());

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
		mp.release();
	}

	private void initialize() {
		recordImageButton = (ImageButton) findViewById(R.id.ib_record);
		changeBtn = (ImageButton) findViewById(R.id.ib_change);
		playImageButton = (ImageButton) findViewById(R.id.ib_play);
		btn_share = (ImageButton) findViewById(R.id.share);
		btn_share.setOnClickListener(this);
		playImageButton.setOnClickListener(this);
		recordImageButton.setOnClickListener(this);
		changeBtn.setOnClickListener(this);
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

	public void blankName() {
		showDialog();
	}

	public void showDialog() {
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.custom_dialog,
				(ViewGroup) findViewById(R.id.layout_root));
		final EditText editText = (EditText) layout
				.findViewById(R.id.inputText);
		final EditText editMent = (EditText) layout.findViewById(R.id.ment);
		editText.setText(idPrefs.getString("NICK", ""));
		AlertDialog.Builder aDialog = new AlertDialog.Builder(Main_Study.this);// 여기서buttontest는
																				// 패키지이름
		aDialog.setTitle("녹음 파일 업로드에 사용할 닉네임과 한마디를 입력하시오.");
		aDialog.setView(layout);

		aDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				dateName = editText.getText().toString();
				onlyName = editText.getText().toString();
				ment = editMent.getText().toString();
				if (onlyName.equals("") || ment.equals("")) {
					Toast.makeText(Main_Study.this, "닉네임과 한마디를 입력하세요!",
							Toast.LENGTH_SHORT).show();
					blankName();
				} else {
					editor.putString("NICK", onlyName);
					editor.commit();
					// share(dateName, ment);
				}
			}
		});
		aDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog ad = aDialog.create();
		ad.show();
	}

	public void showDialog_answer() {
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.custom_dialog,
				(ViewGroup) findViewById(R.id.layout_root));
		final EditText editText = (EditText) layout
				.findViewById(R.id.inputText);
		final EditText editMent = (EditText) layout.findViewById(R.id.ment);
		editText.setText(idPrefs.getString("NICK", ""));
		AlertDialog.Builder aDialog = new AlertDialog.Builder(Main_Study.this);// 여기서buttontest는
		// 패키지이름
		aDialog.setTitle("녹음 파일 업로드에 사용할 닉네임과 한마디를 입력하시오.");
		aDialog.setView(layout);

		aDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				dateName = editText.getText().toString();
				onlyName = editText.getText().toString();
				ment = editMent.getText().toString();
				if (onlyName.equals("") || ment.equals("")) {
					Toast.makeText(Main_Study.this, "닉네임과 한마디를 입력하세요!",
							Toast.LENGTH_SHORT).show();
					blankName();
				} else {
					editor.putString("NICK", onlyName);
					editor.commit();
					// share(dateName, ment);
				}
			}
		});
		aDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog ad = aDialog.create();
		ad.show();
	}

	private AlertDialog createDialog() {
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("정답은..");
		ab.setMessage(answer_list[Integer.parseInt(chapter) - 1] + "입니다.");

		ab.setCancelable(false);
		ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));

		ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {

			}

		});

		return ab.create();
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

		case R.id.instruction:
			if (flagTab) {
				mDialog = createDialog();
				mDialog.show();
				mDialog.getWindow().getAttributes();
				TextView textView = (TextView) mDialog
						.findViewById(android.R.id.message);
				// TextView text2View = (TextView) mDialog
				// .findViewById(android.R.id.title);
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				// text2View.setTextSize(40);
				Button btn1 = mDialog
						.getButton(DialogInterface.BUTTON_NEGATIVE);
				Button btn2 = mDialog
						.getButton(DialogInterface.BUTTON_POSITIVE);
				btn1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				btn2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			}
			break;
		case R.id.btn1:
			tab1();
			flagTab = true;
			if (mp.isPlaying()) {
				mp.stop();
			}
			break;
		case R.id.btn2:
			flagTab = false;
			if (mp.isPlaying()) {
				mp.stop();
			}

			tab2();
			break;
		case R.id.btn3:
			flagTab = false;
			if (mp.isPlaying()) {
				mp.stop();
			}

			tab3();
			break;
		// case R.id.btn4:
		// flagTab = false;
		// Intent activityIntent3 = new Intent(Main_Study.this,
		// MainActivity.class);
		// startActivity(activityIntent3);
		// break;

		case R.id.ib_change:
			// showDialog();
			if (speak_flag == 0) {
				speak_img = this.getResources().getIdentifier(speak_korean,
						"drawable", this.getPackageName());
				speak_flag = 1;

			} else {
				speak_img = this.getResources().getIdentifier(speak_eng,
						"drawable", this.getPackageName());
				speak_flag = 0;
			}

			speakImage.setImageResource(speak_img);
			break;

		case R.id.ib_record:

			if (!isRecording) {
				isRecording = true;
				recordImageButton.setImageResource(R.drawable.btn_pause);
				currentRecordTimeMs = 0;
				recordAsyncTask = new RecordAsyncTask();
				recordAsyncTask.execute();
				// tv.setText("녹음중..");
			} else {
				isRecording = false;
				recordImageButton.setImageResource(R.drawable.btn_rec);
				if (recordAsyncTask != null)
					recordAsyncTask.cancel(true);
				Toast.makeText(this, "Record Success!", Toast.LENGTH_SHORT)
						.show();
				// tv.setText("녹음");
			}

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
			// showDialog();
			share();
			break;

		case R.id.ib_play: {
			playHandler();
			break;
		}

		case R.id.speak: {
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
		}

		default:
			break;
		}

	}

	private void tab1() {
		instruction.setText("정답 확인");
		tab2.setVisibility(View.GONE);
		tab1.setVisibility(View.VISIBLE);
		tab3.setVisibility(View.GONE);
		alpha2.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha1.setColorFilter(0xffffff55, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffffff, Mode.MULTIPLY);

	}

	private void tab2() {
		instruction.setText("동영상을 보고 청크듣기, 읽기를 해보세요.");
		tab1.setVisibility(View.GONE);
		tab2.setVisibility(View.VISIBLE);
		tab3.setVisibility(View.GONE);
		alpha1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffff55, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffffff, Mode.MULTIPLY);

	}

	private void tab3() {
		instruction.setText("카톡이나 메일로\n스피킹을 보내보세요!");
		tab2.setVisibility(View.GONE);
		tab3.setVisibility(View.VISIBLE);
		tab1.setVisibility(View.GONE);
		alpha1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffff55, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffffff, Mode.MULTIPLY);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		int check1 = idPrefs.getInt("CHECK" + chapter + "1", 0);
		Log.d("resume", "CHECK" + chapter + "1");
		Log.d("resume", "CHECK1" + check1);
		int check2 = idPrefs.getInt("CHECK" + chapter + "2", 0);
		int chunkId1 = this.getResources().getIdentifier(
				"check" + String.valueOf(check1), "drawable",
				this.getPackageName());
		Log.d("resume", "image" + "check" + String.valueOf(check1));
		int chunkId2 = this.getResources().getIdentifier(
				"check" + String.valueOf(check2), "drawable",
				this.getPackageName());
		this.check1.setImageResource(chunkId1);
		this.check2.setImageResource(chunkId2);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
