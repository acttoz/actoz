package kr.moon.words800;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class LockScreenAppActivity extends Activity {

	/** Called when the activity is first created. */
	KeyguardManager.KeyguardLock k1;
	boolean inDragMode;
	int selectedImageViewX;
	int selectedImageViewY;
	int imageW;
	int imageH;
	String level = "1";
	String newLevel;
	DayHelper mHelper;
	SQLiteDatabase db;
	int windowwidth;
	int windowheight;
	ImageView droid, phone;
	String answer;
	TextView home;
	TextView left;
	TextView right;
	// int phone_x,phone_y;
	int home_x, home_y;
	int[] droidpos;
	SharedPreferences idPrefs;
	SharedPreferences.Editor editor;
	private LayoutParams layoutParams;
	JSONObject jsonobject = null;
	JSONArray jsonarray = null;
	ArrayList<HashMap<String, String>> arraylist;
	TextView quiz;
	boolean popup = true;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();
		level = idPrefs.getString("LEVEL", "1");
		popup = idPrefs.getBoolean("POPUP", true);
		// 정답 정하기
		int n = (int) (Math.random() * 10) + 1;
		Log.d("정답 난수", String.valueOf(n));

		if (n > 5) {
			answer = "left";
		} else {
			answer = "right";
		}

		quiz = (TextView) findViewById(R.id.quiz);
		left = (TextView) findViewById(R.id.left);
		right = (TextView) findViewById(R.id.right);

		// level = "1"; // 테스트

		// 날짜
		final TextView text02 = (TextView) findViewById(R.id.cal);
		final Calendar c = Calendar.getInstance();
		int Year = c.get(Calendar.YEAR);
		int Month = c.get(Calendar.MONTH) + 1; // 1월(0), 2월(1), ..., 12월(11)
		int Day = c.get(Calendar.DAY_OF_MONTH);
		int DayOfWeek = c.get(Calendar.DAY_OF_WEEK); // 일요일(1), 월요일(2), ...,
		String stringDayOfWeek[] = { "", "일", "월", "화", "수", "목", "금", "토" }; // 일요일이
		String stringDayAndTimeFormat = String.format("%4d년 %d월 %d일 "
				+ stringDayOfWeek[DayOfWeek] + "요일 ", Year, Month, Day);
		text02.setText(stringDayAndTimeFormat);
		// 날짜..

		MyThread threadparse = new MyThread();
		threadparse.setDaemon(true);
		threadparse.start(); // 테스트
		droid = (ImageView) findViewById(R.id.droid);
		try {
			// initialize receiver

			startService(new Intent(this, MyService.class));

			/*
			 * KeyguardManager km
			 * =(KeyguardManager)getSystemService(KEYGUARD_SERVICE); k1 =
			 * km.newKeyguardLock("IN"); k1.disableKeyguard();
			 */
			StateListener phoneStateListener = new StateListener();
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			telephonyManager.listen(phoneStateListener,
					PhoneStateListener.LISTEN_CALL_STATE);

			windowwidth = getWindowManager().getDefaultDisplay().getWidth();
			System.out.println("windowwidth" + windowwidth);
			windowheight = getWindowManager().getDefaultDisplay().getHeight();
			System.out.println("windowheight" + windowheight);
			// home = (TextView) findViewById(R.id.home);
			BitmapDrawable bd = (BitmapDrawable) this.getResources()
					.getDrawable(R.drawable.button);
			final int imageW = bd.getBitmap().getWidth();

			MarginLayoutParams marginParams2 = new MarginLayoutParams(
					droid.getLayoutParams());

			// marginParams2.setMargins((windowwidth / 24) * 10,
			// ((windowheight / 32) * 25), 0, 0);

			marginParams2.setMargins(((windowwidth) / 2 - imageW / 2), 0, 0, 0);
			RelativeLayout.LayoutParams layoutdroid = new RelativeLayout.LayoutParams(
					marginParams2);

			droid.setLayoutParams(layoutdroid);

			droid.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					layoutParams = (LayoutParams) v.getLayoutParams();

					switch (event.getAction()) {

					case MotionEvent.ACTION_DOWN:
						// int[] phonepos=new int[2];
						droidpos = new int[2];
						// phone.getLocationOnScreen(phonepos);
						// phone_x=phonepos[0];
						// phone_y=phonepos[1];

						break;
					case MotionEvent.ACTION_MOVE:
						int x_cord = (int) event.getRawX();
						int y_cord = (int) event.getRawY();

						if (x_cord < windowwidth - imageW / 4
								&& x_cord > imageW / 4) {
							layoutParams.leftMargin = x_cord - imageW / 2;
						}
						droid.getLocationOnScreen(droidpos);
						v.setLayoutParams(layoutParams);
						// if (y_cord > windowheight - (windowheight / 32)) {
						// y_cord = windowheight - (windowheight / 32) * 2;
						// }

						// if (((x_cord - home_x) <= (windowwidth / 24) * 5 &&
						// (home_x - x_cord) <= (windowwidth / 24) * 4)
						// && ((home_y - y_cord) <= (windowheight / 32) * 5)) {
						// // 정답
						// System.out.println("home overlapps");
						// System.out.println("homeee" + home_x + "  "
						// + (int) event.getRawX() + "  " + x_cord
						// + " " + droidpos[0]);
						//
						// System.out.println("homeee" + home_y + "  "
						// + (int) event.getRawY() + "  " + y_cord
						// + " " + droidpos[1]);
						//
						// v.setVisibility(View.GONE);
						//
						// // startActivity(new Intent(Intent.ACTION_VIEW,
						// // Uri.parse("content://contacts/people/")));
						// finish();
						if (answer.equals("left")
								&& x_cord <= (windowwidth / 4)) {
							// 정답
							v.setVisibility(View.GONE);
							finish();
						} else if (answer.equals("right")
								&& x_cord >= (windowwidth / 4) * 3) {
							// 정답
							v.setVisibility(View.GONE);
							finish();
						}

						break;
					case MotionEvent.ACTION_UP:

						// int x_cord1 = (int) event.getRawX();
						// int y_cord2 = (int) event.getRawY();

						// if (((x_cord1 - home_x) <= (windowwidth / 24) * 5 &&
						// (home_x - x_cord1) <= (windowwidth / 24) * 4)
						// && ((home_y - y_cord2) <= (windowheight / 32) * 5)) {
						// // System.out.println("home overlapps");
						// // System.out.println("homeee" + home_x + "  "
						// // + (int) event.getRawX() + "  " + x_cord1
						// // + " " + droidpos[0]);
						// //
						// // System.out.println("homeee" + home_y + "  "
						// // + (int) event.getRawY() + "  " + y_cord2
						// // + " " + droidpos[1]);
						//
						// // startActivity(new Intent(Intent.ACTION_VIEW,
						// // Uri.parse("content://contacts/people/")));
						// // finish();
						// } else {

						layoutParams.leftMargin = ((windowwidth) / 2 - imageW / 2);

						v.setLayoutParams(layoutParams);

					}

					return true;
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
		}
		initQuiz();

		if (popup)
			showDialog();
	}

	public void showDialog() {
		Context mContext = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.book_dialog,
				(ViewGroup) findViewById(R.id.layout_root));
		AlertDialog.Builder aDialog = new AlertDialog.Builder(
				LockScreenAppActivity.this);// 여기서buttontest는
		// 패키지이름
		aDialog.setTitle("도전 정복 800단어");
		aDialog.setView(layout);

		aDialog.setPositiveButton("게임 다운",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						startActivity(new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("market://details?id=kr.moon.words800game")));
					}
				});
		aDialog.setNeutralButton("다시 안보기",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						editor.putBoolean("POPUP", false);
						editor.commit();
					}
				});
		aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog ad = aDialog.create();
		ad.show();
	}

	public void initQuiz() {
		// 레벨에 따른 문제 출제
		Random rnd = new Random();
		int r = rnd.nextInt(100) + 1; // 정답 난수
		int w = rnd.nextInt(100) + 1; // 오답 난수

		if (level == null)
			level = "1";
		if (level.equals(""))
			level = "1";

		String wLevel = level;

		Log.d("initquiz", level + answer + r + " " + w);
		mHelper = new DayHelper(LockScreenAppActivity.this);

		db = mHelper.getReadableDatabase();

		// 정답 가져오기
		Cursor cursor = db.rawQuery("SELECT eng,kor FROM quiz" + level
				+ " where _id='" + String.valueOf(r) + "'", null);
		cursor.moveToFirst();

		if (wLevel.equals("1")) {
			wLevel = "2";
		} else {
			wLevel = "1";
		}
		// 오답 가져오기
		Cursor cursor2 = db.rawQuery("SELECT kor FROM quiz" + wLevel
				+ " where _id='" + String.valueOf(w) + "'", null);
		cursor2.moveToFirst();
		quiz.setText(cursor.getString(0));
		if (answer.equals("left")) {
			left.setText(cursor.getString(1));
			right.setText(cursor2.getString(0));

		} else {
			right.setText(cursor.getString(1));
			left.setText(cursor2.getString(0));

		}
		cursor.close();
		cursor2.close();
		db.close();
		TextView levelNum = (TextView) findViewById(R.id.level);
		levelNum.setText("Lv." + level);
		ImageView levelCol = (ImageView) findViewById(R.id.quiz_back);
		String tmpSign3 = "quiz_back" + level;
		int lid3 = this.getResources().getIdentifier(tmpSign3, "drawable",
				this.getPackageName());

		levelCol.setImageResource(lid3);

	}

	class StateListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				System.out.println("call Activity off hook");
				finish();

				break;
			case TelephonyManager.CALL_STATE_IDLE:
				break;
			}
		}
	};

	class MyThread extends Thread {
		final Handler mHandler = new Handler();

		// 쓰레드 ( 로딩바 )
		public synchronized void run() {
			// openDb();
			// db.close();
			Log.d("json", "다운시작");
			Log.d("json", "다운시작");
			Log.d("dd", idPrefs.getString("SCHOOL", ""));
			String resultUrl = String
					.format("http://actoz.dothome.co.kr/13words800/math.php?select=result&school=%s&grade=%s&ban=%s&name=%s",
							idPrefs.getString("SCHOOL", ""),
							idPrefs.getString("GRADE", ""),
							idPrefs.getString("BAN", ""),
							idPrefs.getString("NAME", ""));
			Log.d("dd", resultUrl);
			// callUrl(resultUrl);

			// Create the array
			arraylist = new ArrayList<HashMap<String, String>>();
			// Retrive JSON Objects from the given website URL in
			// JSONfunctions.class

			try {
				jsonobject = JSONfunctions.getJSONfromURL(resultUrl);
				if (jsonobject != null) {
					// Locate the array name
					jsonarray = jsonobject.getJSONArray("result");
					Log.d("dd", "여기");
					for (int i = 0; i < jsonarray.length(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						jsonobject = jsonarray.getJSONObject(i);
						// Retrive JSON Objects
						Log.d("json", "레벨" + jsonobject.getString("level"));
						// Set the JSON Objects into the array
						// openDb();
						newLevel = jsonobject.getString("level");

					}

				}

				mHandler.post(new Runnable() {

					public void run() {
						if (!level.equals(newLevel)) {
							// 레벨 업
							level = newLevel;
							editor.putString("LEVEL", newLevel);
							editor.commit();
							initQuiz();
							// Toast.makeText(LockScreenAppActivity.this,
							// "" + "레벨 업!!", Toast.LENGTH_SHORT).show();
						}
					}
				});
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			Log.d("json", "다운끝");

		}// while
	}

	@Override
	public void onBackPressed() {
		// Don't allow back to dismiss.
		return;
	}

	// only used in lockdown mode
	@Override
	protected void onPause() {
		super.onPause();

		// Don't hang around.
		// finish();
	}

	@Override
	protected void onStop() {
		super.onStop();

		// Don't hang around.
		// finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
				|| (keyCode == KeyEvent.KEYCODE_POWER)
				|| (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
				|| (keyCode == KeyEvent.KEYCODE_CAMERA)) {
			// this is where I can do my stuff
			return true; // because I handled the event
		}

		return false;

	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_POWER
				|| (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)
				|| (event.getKeyCode() == KeyEvent.KEYCODE_POWER)) {
			// Intent i = new Intent(this, NewActivity.class);
			// startActivity(i);
			return false;
		}

		return false;
	}

	public void onDestroy() {
		// k1.reenableKeyguard();

		super.onDestroy();
	}

	public void openDb() {

		mHelper = new DayHelper(LockScreenAppActivity.this);
		db = mHelper.getReadableDatabase();
	}
}