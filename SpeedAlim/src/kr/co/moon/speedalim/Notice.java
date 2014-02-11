package kr.co.moon.speedalim;

import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Notice extends Activity {
	/** Called when the activity is first created. */
	static final int notiID = 1;
	static String grade = "null";
	static String ban = "null";
	static String mSchool;
	int daySum;
	static int syncTime;

	// static String xmlUrl = "http://nurinamu1.cafe24.com/alim/sunam0503.jsp";
	// String xmlUrl = "http://www.kma.go.kr/XML/weather/sfc_web_map.xml";
	static String xmlUrl;
	// File idXml;
	SharedPreferences idPrefs;
	SharedPreferences firstRun = null;
	SharedPreferences.Editor editor;
	TextView title1;
	TextView title2;
	TextView title3;
	TextView title4;
	ArrayList<Notice_List_Data> dateList;
	Notice_List_Adapter listAdapter;
	Notice_List_Data data;
	ListView dateListView;
	Button noti;
	// Toast toast;
	static Intent intent;
	NotificationManager mNotification;

	public static Activity mainActivity;

	DayHelper mHelper;
	SQLiteDatabase db;
	ContentValues row;
	// DailyHelper mDailyHelper;
	// SQLiteDatabase dailyDb;
	// ContentValues dailyRow;

	// static final String TYPEFACE_NAME = "nanum_pen.ttf.mp3";
	static Typeface mTypeface = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice);

		// 네트워크 연결 확인
		// checkNetwokState();

		// -------------버전 정보 -----------

		// 알람 매니저를 취득
		syncTime = 1000 * 60 * 30;
		if (Notice.mTypeface == null) {
			Notice.mTypeface = Typeface
					.createFromAsset(getAssets(), "font.ttf");
		}
		// if (typeface == null)
		// typeface = Typeface.createFromAsset(getAssets(), TYPEFACE_NAME);

		// idXml = new
		// File("/data/data/kr.co.moon.speedalim/shared_prefs/id.xml");
		title1 = (TextView) findViewById(R.id.title1);
		title1.setTypeface(mTypeface);
		// DB
		mHelper = new DayHelper(Notice.this);
		db = mHelper.getWritableDatabase();
		row = new ContentValues();

		// mDailyHelper = new DailyHelper(this);
		// dailyDb = mDailyHelper.getWritableDatabase();
		// dailyRow = new ContentValues();
		// 알람 매니저를 취득
		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();

		grade = idPrefs.getString("GRADE", "null");
		ban = idPrefs.getString("BAN", "null");
		mSchool = idPrefs.getString("SCHOOLID", "null");

		// 아이디 유무 확인

		xmlUrl = String.format(

		"http://nurinamu.net/cc/notice.jsp?nick_name=%s0%s0%s", mSchool,
				grade, ban);

		dateList = new ArrayList<Notice_List_Data>();

		listAdapter = new Notice_List_Adapter(this, R.layout.notice_list,
				dateList);
		dateListView = (ListView) findViewById(R.id.dateList);
		dateListView.setCacheColorHint(Color.rgb(143, 58, 12));
		dateListView.setAdapter(listAdapter);

		final ImageButton changeId = (ImageButton) findViewById(R.id.firstLogin);
		changeId.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// final Button refreshBtn = (Button) findViewById(R.id.refreshBtn);
		// refreshBtn.setTypeface(mTypeface);
		// refreshBtn.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		//
		// // ---------------------------파싱 ------------
		// threadParse();
		//
		// }
		// });

		// DB 로딩-------------------
		// threadParse();
		dateList.clear();
		listAdapter.notifyDataSetChanged();
		loadSqlite();

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		dateList.clear();
		listAdapter.notifyDataSetChanged();
		loadSqlite();
	}

	public boolean checkNetwokState() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo lte_4g = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
		boolean blte_4g = false;
		if (lte_4g != null)
			blte_4g = lte_4g.isConnected();
		if (mobile.isConnected() || wifi.isConnected() || blte_4g)
			return true;
		else {

			// 여기는 걍 네트워크 상태 체크와 상관없는 연결이 잘 안될때 경고문 띄움 ㅋㅋ
			AlertDialog.Builder dlg = new AlertDialog.Builder(Notice.this);
			dlg.setTitle("네트워크 오류");
			dlg.setMessage("'스피드 알림장'은 네트워크 연결이 필수입니다. 네트워크 연결후 다시 실행 해주세요.^^");
			dlg.setIcon(R.drawable.search);
			dlg.setNegativeButton("종료", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {

				}
			});
			dlg.setCancelable(false);
			dlg.show();
			return false;
		}

	}

	public void loadSqlite() {

		Cursor cursor = db.rawQuery(
				"SELECT CONTENT, DAY FROM notice ORDER BY NO desc", null);

		while (cursor.moveToNext()) {
			String db_data = cursor.getString(0);
			int db_date = cursor.getInt(1);
			data = new Notice_List_Data();
			data.Data = db_data;
			data.date = db_date;
			dateList.add(data);

		}
		cursor.close();
		db.close();
		listAdapter.notifyDataSetChanged();
		dateListView.invalidate();

	}

	// public synchronized void threadParse() {
	// final Handler mHandler = new Handler();
	// // 쓰레드 ( 로딩바 )
	// new Thread() {
	// public void run() {
	// try {
	//
	// URL url = new URL(xmlUrl);
	// XmlPullParserFactory parserCreator = XmlPullParserFactory
	// .newInstance();
	// XmlPullParser parser = parserCreator.newPullParser();
	//
	// parser.setInput(url.openStream(), null);
	//
	// int parserEvent = parser.getEventType();
	// String tag = null;
	//
	// boolean checkDate = false;
	// boolean checkDay = false;
	//
	// boolean checkContent1 = false;
	//
	// while (parserEvent != XmlPullParser.END_DOCUMENT) {
	//
	// switch (parserEvent) {
	//
	// case XmlPullParser.START_TAG:
	// tag = parser.getName();
	//
	// if (tag.compareTo("no") == 0) {
	// checkDate = true;
	// } else if (tag.compareTo("content") == 0) {
	// checkContent1 = true;
	// } else if (tag.compareTo("date") == 0) {
	// checkDay = true;
	// }
	//
	// break;
	//
	// case XmlPullParser.TEXT:
	//
	// if (checkDate) {
	// row.put("NO", parser.getText());
	// } else if (checkContent1) {
	// row.put("CONTENT", parser.getText());
	// } else if (checkDay) {
	// row.put("DAY", parser.getText());
	// }
	//
	// break;
	//
	// case XmlPullParser.END_TAG:
	// tag = parser.getName();
	// if (tag.compareTo("no") == 0) {
	// checkDate = false;
	// } else if (tag.compareTo("content") == 0) {
	// checkContent1 = false;
	// db.insert("notice", null, row);
	//
	// } else if (tag.compareTo("date") == 0) {
	// checkDay = false;
	//
	// }
	//
	// break;
	// }
	//
	// parserEvent = parser.next();
	//
	// }
	//
	// mHandler.post(new Runnable() {
	// public void run() {
	//
	// mManager.setInexactRepeating(
	// AlarmManager.ELAPSED_REALTIME,
	// SystemClock.elapsedRealtime(), syncTime,
	// sender);
	// // Toast.makeText(Notice.this, "dd",
	// // Toast.LENGTH_SHORT).show();
	// }
	// });
	//
	// }
	//
	// catch (Exception e) {
	//
	// e.printStackTrace();
	//
	// }
	//
	// // catch (XmlPullParserException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // } catch (MalformedURLException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // } catch (IOException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // }
	// }
	//
	// }.start(); // 스레드
	//
	// }// 파서 스레드

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
