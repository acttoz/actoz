package a.com.kr.moon.math.phone;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Math_Select_Main extends Activity implements OnClickListener,
		SensorEventListener {
	ImageView goInfoView, goSocialView, goMapView;
	View infoView, mapView, socialView, students;
	ListView listView1, listView2, listView3, listView4, listView5;
	String mPeriod, mChapter, mTime;
	boolean dbCopied;
	DayHelper mHelper;
	MyThread threadparse;
	Handler mHandler;
	private boolean mFlag = false;
	Toast finishToast;
	SQLiteDatabase db;
	SharedPreferences idPrefs;
	SharedPreferences firstRun = null;
	SharedPreferences.Editor editor;
	Custom_List_Adapter listAdapter1, listAdapter2, listAdapter3, listAdapter4;
	Student_Adapter listAdapter5;
	Custom_List_Data data;
	Custom_List_Data data2;
	Custom_List_Data data3;
	Custom_List_Data data4;
	Student_Data data5;
	ArrayList<Custom_List_Data> dateList1, dateList2, dateList3, dateList4;
	ArrayList<Student_Data> dateList5;
	AlarmManager mManager;
	NotificationManager mNotification;
	Intent intent, popIntent;
	Drawable alpha1;
	Drawable alpha2;
	Drawable alpha3;
	public static String viewFlag;
	ProgressDialog mProgressDialog;
	String tempPrd;
	String tempCht;
	String tempTim;
	String tableName = "";
	Boolean isTeacher;
	int syncTime;
	String jsonUrl;
	static ArrayAdapter<String> schoolAdapter;
	JSONObject jsonobject = null;
	JSONArray jsonarray = null;
	ArrayList<HashMap<String, String>> arraylist;
	TextView temp;
	int tempNum = 0;
	boolean stop = false;
	private long lastTime;
	private float speed;
	private float lastX;
	private float lastY;
	private float lastZ;
	private float x, y, z;

	private static final int SHAKE_THRESHOLD = 1600;
	private static final int DATA_X = SensorManager.DATA_X;
	private static final int DATA_Y = SensorManager.DATA_Y;
	private static final int DATA_Z = SensorManager.DATA_Z;

	private SensorManager sensorManager;
	private Sensor accelerormeterSensor;
	boolean initEnd;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.math_select_main);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerormeterSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		syncTime = 10000;
		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();
		dbCopied = idPrefs.getBoolean("DBCOPY", false);
		isTeacher = idPrefs.getBoolean("ISTEACHER", false);
		editor.putString("PERIOD", "");
		editor.commit();
		editor.putString("CHAPTER", "");
		editor.commit();
		editor.putString("TIME", "");
		editor.commit();
		threadparse = new MyThread();
		finishToast = Toast.makeText(Math_Select_Main.this,
				"'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
		if (isTeacher) {
			tableName = "result_class";
			Toast toast = Toast.makeText(this, "견본:2학기 1단원 1차시", 5000);
			LinearLayout linearLayout = null;
			linearLayout = (LinearLayout) toast.getView();
			View child = linearLayout.getChildAt(0);
			TextView messageTextView = null;
			messageTextView = (TextView) child;
			messageTextView.setTextSize(20);
			toast.show();
		} else {
			tableName = "result";
		}

		// openDb();
		// db.close();
		// if (!dbCopied)
		// copySQLiteDB(this);
		//
		// Intent i = getIntent();
		// if (i.getIntExtra("COPY", 0) != 0) {
		// Log.d("dd", "갔다 온다.");
		//
		// threadparse.start();
		// finish();
		// }

		mPeriod = idPrefs.getString("PERIOD", "");
		mChapter = idPrefs.getString("CHAPTER", "");
		mTime = idPrefs.getString("TIME", "");
		// 상단 버튼 등록
		goInfoView = (ImageView) findViewById(R.id.goInfoView_btn);
		goMapView = (ImageView) findViewById(R.id.goMapView_btn);
		goSocialView = (ImageView) findViewById(R.id.goSocialView_btn);
		goInfoView.setOnClickListener(this);
		goMapView.setOnClickListener(this);
		goSocialView.setOnClickListener(this);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					mFlag = false;
				}
			}
		};
		// 각 그룹 뷰 등록
		infoView = (View) findViewById(R.id.poiInfoXml);
		mapView = (View) findViewById(R.id.poiMapXml);
		socialView = (View) findViewById(R.id.poiSocialXml);
		students = (View) findViewById(R.id.studentsXml);

		initInfoView();
		listView1 = (ListView) findViewById(R.id.listview1);
		listView2 = (ListView) findViewById(R.id.listview2);
		listView3 = (ListView) findViewById(R.id.listview3);
		listView4 = (ListView) findViewById(R.id.listview4);
		listView5 = (ListView) findViewById(R.id.listview5);

		dateList1 = new ArrayList<Custom_List_Data>();
		dateList2 = new ArrayList<Custom_List_Data>();
		dateList3 = new ArrayList<Custom_List_Data>();
		dateList4 = new ArrayList<Custom_List_Data>();
		dateList5 = new ArrayList<Student_Data>();

		data = new Custom_List_Data();
		data.Data = "1학기";
		dateList1.add(data);
		data = new Custom_List_Data();
		data.Data = "2학기";
		dateList1.add(data);

		data2 = new Custom_List_Data();
		data2.Data = "학기를 선택하세요.";
		data3 = new Custom_List_Data();
		data3.Data = "단원을 선택하세요.";
		dateList2.add(data2);
		dateList3.add(data3);
		listAdapter1 = new Custom_List_Adapter(this, R.layout.customlist,
				dateList1);
		listAdapter2 = new Custom_List_Adapter(this, R.layout.customlist,
				dateList2);
		listAdapter3 = new Custom_List_Adapter(this, R.layout.customlist,
				dateList3);
		listAdapter4 = new Custom_List_Adapter(this, R.layout.numlist,
				dateList4);
		listAdapter5 = new Student_Adapter(this, R.layout.student_item,
				dateList5);
		// dateListView.setCacheColorHint(Color.rgb(255,2555,255));
		listView1.setAdapter(listAdapter1);
		listView2.setAdapter(listAdapter2);
		listView3.setAdapter(listAdapter3);
		listView4.setAdapter(listAdapter4);
		listView5.setAdapter(listAdapter5);

		listAdapter1.notifyDataSetChanged();
		listAdapter2.notifyDataSetChanged();
		listAdapter3.notifyDataSetChanged();
		listAdapter4.notifyDataSetChanged();
		listAdapter5.notifyDataSetChanged();
		listView1.setOnItemClickListener(new OnItemClickListener() {
			// 학기
			public void onItemClick(AdapterView<?> arg0, View arg1,

			int position, long arg3) {
				editor.putString("PERIOD",
						dateList1.get(position).Data.substring(0, 1));
				editor.commit();
				initMapView();

				initChapter(dateList1.get(position).Data.substring(0, 1));
				setClickItem2();
				// String day = dateList.get(position).Data;
			}
		});
		temp = (TextView) findViewById(R.id.tempNum);

	}

	public void initChapter(String period) {
		viewFlag = "initChapter";
		openDb();
		Cursor cursor = db.rawQuery(
				"SELECT DISTINCT col_1 FROM math_test WHERE col_0 LIKE '%"
						+ period + "%' order by col_1 asc", null);
		cursor.moveToFirst();
		dateList2.clear();
		do {
			data = new Custom_List_Data();
			data.Data = cursor.getString(0);
			Log.d("dd", "테이블명" + tableName);
			Cursor cursor2 = db.rawQuery(
					"SELECT  (num1+num2+num3) as sumcolumn FROM " + tableName
							+ " WHERE period='" + period + "' and chapter='"
							+ cursor.getString(0).substring(0, 1) + "'", null);
			if (cursor2.moveToFirst()) {
				do {
					data.score = data.score + cursor2.getInt(0);
					data.sum = data.sum + 3;
					Log.d("그래프", "점수" + data.score + " " + data.sum);
					Log.d("그래프", "점수로드" + cursor2.getInt(0));
				} while (cursor2.moveToNext());
			}
			cursor2.close();
			dateList2.add(data);
		} while (cursor.moveToNext());
		cursor.close();
		db.close();
		listAdapter2.notifyDataSetChanged();
		listView2.invalidate();
	}

	public void initTime(String chapter) {
		openDb();
		viewFlag = "initTime";
		Log.d("차시", "차시로딩");
		Cursor cursor = db.rawQuery(
				"SELECT DISTINCT col_2 FROM math_test WHERE col_1='" + chapter
						+ "' and col_0='" + idPrefs.getString("PERIOD", "")
						+ "' order by col_2 asc", null);
		if (cursor.moveToFirst() & cursor.getCount() > 0) {
			dateList3.clear();
			do {
				data = new Custom_List_Data();
				data.Data = cursor.getString(0);

				if (!isTeacher) {
					viewFlag = "time";
					Cursor cursor2 = db.rawQuery(
							"SELECT  sum(col_9) FROM math_test WHERE col_0='"
									+ idPrefs.getString("PERIOD", "")
									+ "' and col_1='"
									+ idPrefs.getString("CHAPTER", "")
									+ "' and col_2='" + cursor.getString(0)
									+ "'", null);
					if (cursor2.moveToFirst()) {
						if (cursor2.getInt(0) > 0) {
							data.score = 1;

						} else {
							data.score = 0;
						}
						Log.d("풀었나?", Integer.toString(data.score));
					}
					cursor2.close();
				}
				dateList3.add(data);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		listAdapter3.notifyDataSetChanged();
		listView3.invalidate();
	}

	private void initNum() {

		viewFlag = "initNum";

		Log.d("json", "initNum");
		openDb();
		dateList4.clear();
		Cursor cursor = db.rawQuery(
				"SELECT Count(num1),sum(num1),sum(num2),sum(num3)  FROM "
						+ tableName + " WHERE period='"
						+ idPrefs.getString("PERIOD", "") + "' and chapter='"
						+ idPrefs.getString("CHAPTER", "").substring(0, 1)
						+ "' and time='"
						+ idPrefs.getString("TIME", "").substring(0, 1) + "'",
				null);
		Cursor cursor2 = db.rawQuery(
				"SELECT DISTINCT col_3,col_4 FROM math_test WHERE col_1='"
						+ idPrefs.getString("CHAPTER", "") + "' and col_0='"
						+ idPrefs.getString("PERIOD", "") + "' and col_2='"
						+ idPrefs.getString("TIME", "")
						+ "' order by col_3 asc", null);
		int i = 0;
		if (cursor.moveToFirst() && cursor2.moveToFirst()) {
			if (cursor.getInt(0) > 0) {
				do {
					i++;

					data = new Custom_List_Data();
					data.score = cursor.getInt(i);
					data.sum = cursor.getInt(0);
					data.Data = cursor2.getString(0) + ". "
							+ cursor2.getString(1);
					Log.d("json", "graph " + " " + data.score + " " + data.sum
							+ " " + data.Data);
					dateList4.add(data);

				} while (cursor2.moveToNext());
			} else {
				data = new Custom_List_Data();
				data.score = 0;
				data.sum = 0;
				data.Data = "응시 학생이 없습니다.";
				Log.d("json", "graph " + " " + data.score + " " + data.sum
						+ " " + data.Data);
				dateList4.add(data);

			}
		}
		cursor.close();
		cursor2.close();
		// 학생 리스트
		dateList5.clear();
		Cursor cursor3 = db.rawQuery(
				"SELECT no,name,num1,num2,num3 FROM result_class WHERE period='"
						+ idPrefs.getString("PERIOD", "") + "' and chapter='"
						+ idPrefs.getString("CHAPTER", "").substring(0, 1)
						+ "' and time='"
						+ idPrefs.getString("TIME", "").substring(0, 1)
						+ "' order by no asc", null);
		i = 0;
		Log.d("json",
				"목록 쿼리 SELECT no,name,num1,num2,num3 FROM result_class WHERE period='"
						+ idPrefs.getString("PERIOD", "") + "' and chapter='"
						+ idPrefs.getString("CHAPTER", "").substring(0, 1)
						+ "' and time='"
						+ idPrefs.getString("TIME", "").substring(0, 1)
						+ "' order by no asc");
		if (cursor3 != null && cursor3.getCount() > 0) {
			if (cursor3.moveToFirst()) {
				do {
					i++;
					data5 = new Student_Data();
					data5.no = cursor3.getString(0);
					data5.name = cursor3.getString(1);
					data5.num1 = cursor3.getInt(2);
					data5.num2 = cursor3.getInt(3);
					data5.num3 = cursor3.getInt(4);
					dateList5.add(data5);

					Log.d("json", "목록 리스트" + data5 + data5.no + data5.name
							+ " " + data5.num1 + data5.num2 + data5.num3);

				} while (cursor3.moveToNext());
			}
		}

		cursor3.close();
		db.close();
		listAdapter4.notifyDataSetChanged();
		listAdapter5.notifyDataSetChanged();
		listView4.invalidate();
		listView5.invalidate();

	}

	private void initNumView() {
		viewFlag = "initNum";
		students.setVisibility(View.VISIBLE);
		infoView.setVisibility(View.GONE);
		mapView.setVisibility(View.GONE);
		socialView.setVisibility(View.GONE);
		Toast toast = Toast.makeText(this, "새로고침을 하려면 기기를 흔들어주세요.", 1000);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout linearLayout = null;
		linearLayout = (LinearLayout) toast.getView();
		View child = linearLayout.getChildAt(0);
		TextView messageTextView = null;
		messageTextView = (TextView) child;
		messageTextView.setTextSize(25);
		toast.show();
		initEnd = true;
	}

	public void setClickItem2() {
		listView2.setOnItemClickListener(new OnItemClickListener() {
			// 단원
			public void onItemClick(AdapterView<?> arg0, View arg1,

			int position, long arg3) {
				editor.putString("CHAPTER", dateList2.get(position).Data);
				editor.commit();
				initSocialView();
				// initChoice(
				// "SELECT DISTINCT col_2 FROM math_test WHERE col_1 LIKE '%"
				// + dateList2.get(position).Data
				// + "%' order by col_2 asc", listView3, 3);
				initTime(dateList2.get(position).Data);
				setClickItem3();

				// String day = dateList.get(position).Data;
			}
		});
	}

	public void setClickItem3() {
		listView3.setOnItemClickListener(new OnItemClickListener() {
			// 차시
			public void onItemClick(AdapterView<?> arg0, View arg1,

			int position, long arg3) {

				if (isTeacher) {
					editor.putString("TIME", dateList3.get(position).Data);
					editor.commit();
					initNum();
					initNumView();
				} else {
					editor.putString("TIME", dateList3.get(position).Data);
					editor.commit();
					viewFlag = "initSocialView";
					Intent quizIntent = new Intent(Math_Select_Main.this,
							Math_Quiz_Main.class);
					quizIntent.putExtra("QUIZ", 0);
					startActivity(quizIntent);
					finish();
				}

			}
		});
	}

	class MyThread extends Thread {
		final Handler mHandler = new Handler();

		// 쓰레드 ( 로딩바 )
		public synchronized void run() {
			openDb();
			db.delete("result_class", null, null);
			// db.close();
			Log.d("json", "다운시작");

			String resultUrl = String
					.format(

					"http://actoz.dothome.co.kr/math/math.php?select=result&class=%s",
							idPrefs.getString("SCHOOL", "")
									+ idPrefs.getString("BAN", ""));
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

					for (int i = 0; i < jsonarray.length(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						jsonobject = jsonarray.getJSONObject(i);
						// Retrive JSON Objects
						map.put("no", jsonobject.getString("no"));
						map.put("name", jsonobject.getString("name"));
						map.put("period", jsonobject.getString("period"));
						map.put("chapter", jsonobject.getString("chapter"));
						map.put("time", jsonobject.getString("time"));
						map.put("num1", jsonobject.getString("num1"));
						map.put("num2", jsonobject.getString("num2"));
						map.put("num3", jsonobject.getString("num3"));
						// Set the JSON Objects into the array
						// openDb();
						db.execSQL("INSERT INTO result_class (no,name,period,chapter,time,num1,num2,num3) VALUES "
								+ "('"
								+ map.get("no")
								+ "','"
								+ map.get("name")
								+ "','"
								+ map.get("period")
								+ "','"
								+ map.get("chapter")
								+ "','"
								+ map.get("time")
								+ "','"
								+ map.get("num1")
								+ "','"
								+ map.get("num2")
								+ "','"
								+ map.get("num3") + "' );");

					}

				}
				db.close();
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			Log.d("json", "다운끝");
			mHandler.post(new Runnable() {

				public void run() {
					initAll();

				}
			});

		}
	}

	public void initAll() {
		if (!idPrefs.getString("CHAPTER", "").equals("")
				&& !idPrefs.getString("PERIOD", "").equals("")) {
			initTime(idPrefs.getString("CHAPTER", ""));
			if (!idPrefs.getString("TIME", "").equals("")) {
				initNum();
			}

		}
	}

	public static class PushWakeLock {
		private static PowerManager.WakeLock sCpuWakeLock;

		// private static KeyguardManager.KeyguardLock mKeyguardLock;
		// private static boolean isScreenLock;

	}

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// // TODO Auto-generated method stub
	//
	// switch (keyCode) {
	// case KeyEvent.KEYCODE_BACK:
	//
	// if (viewFlag.equals("initInfoView"))
	// finish();
	// if (viewFlag.equals("initMapView"))
	// initInfoView();
	// if (viewFlag.equals("initSocialView"))
	// initMapView();
	// if (viewFlag.equals("initNum"))
	// initSocialView();
	//
	// break;
	// }
	// return true;
	// }

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.goInfoView_btn:
			initInfoView();
			break;

		case R.id.goMapView_btn:
			initMapView();
			if (!idPrefs.getString("PERIOD", "").equals(""))
				initChapter(idPrefs.getString("PERIOD", ""));
			// if (mPeriod != "") {
			// } else {
			// Toast toast = Toast.makeText(this, "학기를 고르시오.",
			// Toast.LENGTH_SHORT);
			// toast.setGravity(Gravity.CENTER, 0, 0);
			// toast.show();
			// }
			break;

		case R.id.goSocialView_btn:
			initSocialView();
			if (!idPrefs.getString("CHAPTER", "").equals(""))
				initTime(idPrefs.getString("CHAPTER", ""));
			break;

		}

	}

	public void openDb() {

		mHelper = new DayHelper(Math_Select_Main.this);
		db = mHelper.getReadableDatabase();
	}

	private void initSocialView() {
		viewFlag = "initSocialView";
		infoView.setVisibility(View.GONE);
		mapView.setVisibility(View.GONE);
		students.setVisibility(View.GONE);
		socialView.setVisibility(View.VISIBLE);
		goInfoView.setImageResource(R.drawable.choice1);
		goMapView.setImageResource(R.drawable.choice2);
		goSocialView.setImageResource(R.drawable.choice3);
		alpha1 = goInfoView.getDrawable();
		alpha2 = goMapView.getDrawable();
		alpha3 = goSocialView.getDrawable();
		alpha1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffff55, Mode.MULTIPLY);

	}

	private void initMapView() {
		viewFlag = "initMapView";
		students.setVisibility(View.GONE);
		infoView.setVisibility(View.GONE);
		mapView.setVisibility(View.VISIBLE);
		socialView.setVisibility(View.GONE);
		goInfoView.setImageResource(R.drawable.choice1);
		goMapView.setImageResource(R.drawable.choice2);
		goSocialView.setImageResource(R.drawable.choice3);
		alpha1 = goInfoView.getDrawable();
		alpha2 = goMapView.getDrawable();
		alpha3 = goSocialView.getDrawable();
		alpha1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffff55, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffffff, Mode.MULTIPLY);

	}

	private void initInfoView() {
		viewFlag = "initInfoView";
		students.setVisibility(View.GONE);
		infoView.setVisibility(View.VISIBLE);
		mapView.setVisibility(View.GONE);
		socialView.setVisibility(View.GONE);
		goInfoView.setImageResource(R.drawable.choice1);
		goMapView.setImageResource(R.drawable.choice2);
		goSocialView.setImageResource(R.drawable.choice3);
		alpha1 = goInfoView.getDrawable();
		alpha2 = goMapView.getDrawable();
		alpha3 = goSocialView.getDrawable();
		alpha1.setColorFilter(0xffffff55, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffffff, Mode.MULTIPLY);
	}

	private void copySQLiteDB(Context context) {

		editor.putBoolean("DBCOPY", true);
		editor.commit();

		AssetManager manager = context.getAssets();
		String filePath = "/data/data/" + "a.com.kr.moon.math.phone"
				+ "/databases/" + "quiz.db";
		File file = new File(filePath);

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			InputStream is = manager.open("quiz.db");
			BufferedInputStream bis = new BufferedInputStream(is);

			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);

			int read = -1;
			byte[] buffer = new byte[1024];
			while ((read = bis.read(buffer, 0, 1024)) != -1) {
				bos.write(buffer, 0, read);
			}
			bos.flush();

			bos.close();
			fos.close();
			bis.close();
			is.close();

		} catch (IOException e) {
			Log.e("ErrorMessage : ", e.getMessage());
		}
	}

	// @Override
	// public boolean onKeyUp(int keyCode, KeyEvent event) {
	//
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// if (!mFlag) {
	// finishToast.show();
	// mFlag = true;
	// mHandler.sendEmptyMessageDelayed(0, 2000);
	// return false;
	// } else {
	// finishToast.cancel();
	// finish();
	// }
	// }
	//
	// return super.onKeyUp(keyCode, event);
	// }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stop = true;
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			long currentTime = System.currentTimeMillis();
			long gabOfTime = (currentTime - lastTime);
			if (gabOfTime > 100) {
				lastTime = currentTime;
				x = event.values[SensorManager.DATA_X];
				y = event.values[SensorManager.DATA_Y];
				z = event.values[SensorManager.DATA_Z];

				speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime
						* 10000;

				if (speed > SHAKE_THRESHOLD) {
					if (isTeacher) {
						// 이벤트발생!!
						if (initEnd) {
							if (threadparse != null && !threadparse.isAlive()) {
								threadparse = new MyThread();
								threadparse.start();
							}

						}
						Toast toast = Toast.makeText(this, "새로고침", 1000);
						toast.show();
					} else {
					}
				}

				lastX = event.values[DATA_X];
				lastY = event.values[DATA_Y];
				lastZ = event.values[DATA_Z];
			}

		}

	}

	@Override
	public void onStart() {
		super.onStart();
		if (accelerormeterSensor != null)
			sensorManager.registerListener(this, accelerormeterSensor,
					SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (sensorManager != null)
			sensorManager.unregisterListener(this);
	}
}