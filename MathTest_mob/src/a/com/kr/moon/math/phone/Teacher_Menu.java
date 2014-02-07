package a.com.kr.moon.math.phone;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import a.com.kr.moon.math.phone.Math_Select_Main.MyThread;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Teacher_Menu extends Activity implements OnClickListener {
	SharedPreferences idPrefs;
	SharedPreferences firstRun = null;
	SharedPreferences.Editor editor;
	static String grade = "null";
	static String ban = "null";
	static String mSchool;
	ImageView bSolve, bView, bReview, bTutorial;
	Drawable alpha1, alpha2, alpha3, alpha4;
	DayHelper mHelper;
	Handler mHandler;
	SQLiteDatabase db;
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
	public static String viewFlag;
	ProgressDialog dialog;
	ProgressDialog mProgressDialog;
	String tempPrd;
	String tempCht;
	String mPeriod, mChapter, mTime;
	boolean dbCopied;
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

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacher_menu);
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
		mPeriod = idPrefs.getString("PERIOD", "");
		mChapter = idPrefs.getString("CHAPTER", "");
		mTime = idPrefs.getString("TIME", "");
		bSolve = (ImageView) findViewById(R.id.menu1);
		bView = (ImageView) findViewById(R.id.menu2);
		bReview = (ImageView) findViewById(R.id.menu3);
		bSolve.setOnClickListener(this);
		bView.setOnClickListener(this);
		bReview.setOnClickListener(this);

		alpha1 = bSolve.getDrawable();
		alpha2 = bView.getDrawable();
		alpha3 = bReview.getDrawable();
		timeThread();
		// MyThread threadparse = new MyThread();
		// threadparse.setDaemon(true);
		// threadparse.start();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.menu1:
			alpha1.setColorFilter(0x88FF0000, Mode.SRC_ATOP);
			Intent menu1Intent = new Intent(this, Math_Select_Main.class);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(menu1Intent);
			break;
		case R.id.menu2:
			alpha2.setColorFilter(0x88FF0000, Mode.SRC_ATOP);
			Intent menu2Intent = new Intent(this, TeacherChart.class);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(menu2Intent);
			break;
		case R.id.menu3:
			alpha3.setColorFilter(0x88FF0000, Mode.SRC_ATOP);
			Intent menu3Intent = new Intent(this, TeacherChart2.class);
			// firstLoginIntent.putExtra("BAN", ban);
			startActivity(menu3Intent);
			break;

		}
	}

	// class MyThread extends Thread {
	// final Handler mHandler = new Handler();
	//
	// // 쓰레드 ( 로딩바 )
	// public synchronized void run() {
	//
	// Log.d("json", "다운끝");
	// mHandler.post(new Runnable() {
	//
	// public void run() {
	//
	// }
	// });
	//
	// }
	// }

	public void timeThread() {

		dialog = new ProgressDialog(this);
		dialog = new ProgressDialog(this);
		dialog.setTitle("잠시만 기다려 주세요.");
		dialog.setMessage("학생들의 점수를 다운받고 있습니다.");
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		dialog.show();
		new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				mHelper = new DayHelper(Teacher_Menu.this);
				db = mHelper.getReadableDatabase();
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
							Log.d("json",
									"INSERT INTO result_class (no,name,period,chapter,time,num1,num2,num3) VALUES "
											+ "('" + map.get("no") + "','"
											+ map.get("name") + "','"
											+ map.get("period") + "','"
											+ map.get("chapter") + "','"
											+ map.get("time") + "','"
											+ map.get("num1") + "','"
											+ map.get("num2") + "','"
											+ map.get("num3") + "' );");

						}

					}
					db.close();
				} catch (JSONException e) {
					Log.e("Error", e.getMessage());
					e.printStackTrace();
				}
				dialog.dismiss();
			}
		}).start();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		alpha1.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha2.setColorFilter(0xffffffff, Mode.MULTIPLY);
		alpha3.setColorFilter(0xffffffff, Mode.MULTIPLY);
	}
}
