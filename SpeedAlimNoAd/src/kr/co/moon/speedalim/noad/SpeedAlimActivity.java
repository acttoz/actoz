package kr.co.moon.speedalim.noad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import net.daum.adam.publisher.AdView;
import net.daum.adam.publisher.AdView.AnimationType;
import net.daum.adam.publisher.AdView.OnAdClickedListener;
import net.daum.adam.publisher.AdView.OnAdClosedListener;
import net.daum.adam.publisher.AdView.OnAdFailedListener;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;
import net.daum.adam.publisher.AdView.OnAdWillLoadListener;
import net.daum.adam.publisher.impl.AdError;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class SpeedAlimActivity extends Activity {
	/** Called when the activity is first created. */
	private static final String LOGTAG = "BannerTypeXML1";
	private AdView adView = null;
	static final int notiID = 1;
	static String grade = "null";
	static String ban = "null";
	static String mSchool;
	int newNoticeId;
	public static String gcmSchoolId;
	static int toFirst;
	int daySum;
	static int syncTime;
	int newVer;
	static int curVer;

	private FrameLayout main_layout;

	static PendingIntent sender;

	// static String xmlUrl = "http://nurinamu1.cafe24.com/alim/sunam0503.jsp";
	// String xmlUrl = "http://www.kma.go.kr/XML/weather/sfc_web_map.xml";
	private ProgressDialog progressDialog;
	static String xmlUrl;
	static String noXmlUrl;
	// File idXml;
	SharedPreferences idPrefs;
	SharedPreferences firstRun = null;
	SharedPreferences.Editor editor;
	TextView title1;
	TextView title2;
	TextView title3;
	TextView title4;
	ArrayList<Custom_List_Data> dateList;
	Custom_List_Adapter listAdapter;
	Custom_List_Data data;
	ListView dateListView;
	Button noti;
	private Handler mHandler;
	private boolean mFlag = false;
	// Toast toast;
	static AlarmManager mManager;
	NotificationManager mNotification;
	static Intent intent, popIntent;
	public static Activity mainActivity;
	Toast finishToast;
	DayHelper mHelper;
	SQLiteDatabase db;
	String db_Day;
	// DailyHelper mDailyHelper;
	// SQLiteDatabase dailyDb;
	// ContentValues dailyRow;
	MyThread threadparse;
	public static Context mContext = null;
	Context context;
	Dialog dialog;
	Window window;
	String title;
	public static String schoolId;

	// static final String TYPEFACE_NAME = "nanum_pen.ttf.mp3";
	static Typeface mTypeface = null;
	public static String PROJECT_ID = "178050819419";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// initAdam();
		popIntent = getIntent();
		mContext = this;

		mManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		intent = new Intent(SpeedAlimActivity.this, AlarmReceiver.class);
		sender = PendingIntent.getBroadcast(SpeedAlimActivity.this, 0, intent,
				0);
		// syncTime = 1000 * 10;
		syncTime = 1000 * 60 * 30;
		Log.d("dd", "싱크타임=" + syncTime);

		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();

		curVer = 51;

		if (SpeedAlimActivity.mTypeface == null) {
			SpeedAlimActivity.mTypeface = Typeface.createFromAsset(getAssets(),
					"font.ttf");
		}

		title1 = (TextView) findViewById(R.id.title1);
		title2 = (TextView) findViewById(R.id.title2);
		title3 = (TextView) findViewById(R.id.title3);
		title4 = (TextView) findViewById(R.id.title4);

		threadparse = new MyThread();

		// DB

		grade = idPrefs.getString("GRADE", "null");
		ban = idPrefs.getString("BAN", "null");
		mSchool = idPrefs.getString("SCHOOLID", "null");

		// 아이디 유무 확인

		if (grade.equals("null") | ban.equals("null") | mSchool.equals("null")) {
			// C2dm_BroadcastReceiver.c2dmIdCreate(this);
			mManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
					SystemClock.elapsedRealtime(), syncTime, sender);
			Intent firstLogin = new Intent(this, FirstLogin.class);
			startActivity(firstLogin);
			finish();

		} else {

			DownloadText checkVer = new DownloadText();
			checkVer.start();

			NoticeThread noticeCheck = new NoticeThread();
			noticeCheck.start();
			// 네트워크 연결 확인
			// checkNetwokState();
			finishToast = Toast.makeText(SpeedAlimActivity.this,
					"'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
			// -------------버전 정보 -----------

			// 알람 매니저를 취득

			title1.setTypeface(mTypeface);
			title1.setText(grade);

			title2.setTypeface(mTypeface);
			title2.setText("학년");

			title3.setTypeface(mTypeface);
			title3.setText(ban);

			title4.setTypeface(mTypeface);
			title4.setText("반");

			xmlUrl = String.format(

			"http://clicknote.cafe24.com/cc/xmlp.jsp?nick_name=%s0%s0%s",
					mSchool, grade, ban);

			schoolId = mSchool + grade + ban;

			dateList = new ArrayList<Custom_List_Data>();

			listAdapter = new Custom_List_Adapter(this, R.layout.customlist,
					dateList);
			dateListView = (ListView) findViewById(R.id.dateList);
			// dateListView.setCacheColorHint(Color.rgb(255,2555,255));
			dateListView.setAdapter(listAdapter);

			dateListView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,

				int position, long arg3) {
					// TODO Auto-generated method stub
					// Toast.makeText(SpeedAlimActivity.this,
					// dateList.get(position),
					// Toast.LENGTH_SHORT).show();

					mManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
							SystemClock.elapsedRealtime(), syncTime, sender);

					String day = dateList.get(position).Data;
					dateList.clear();
					openDb();
					db.execSQL("update daytable set newNote='0' where DAY like '"
							+ day + "';");

					Cursor cursor2 = db
							.rawQuery(
									"SELECT DAY,newNote FROM daytable ORDER BY DAY desc",
									null);
					cursor2.moveToFirst();
					do {
						db_Day = cursor2.getString(0);
						data = new Custom_List_Data();
						data.Data = db_Day;
						data.newNote = cursor2.getInt(1);
						dateList.add(data);
					} while (cursor2.moveToNext());
					Log.d("dd", "로드디비");
					listAdapter.notifyDataSetChanged();
					dateListView.invalidate();

					cursor2.close();
					db.close();
					Intent intent = new Intent(SpeedAlimActivity.this,
							DailyList.class);
					intent.putExtra("day", day);
					// 인테트 추가부분
					intent.putExtra("grade", grade);
					intent.putExtra("ban", ban);
					// 인텐트 추가부분 끝

					startActivity(intent);

				}
			});

			if (Integer.parseInt(ban) < 10) {
				gcmSchoolId = mSchool + "0" + grade + "0" + ban;
			} else {
				gcmSchoolId = mSchool + "0" + grade + ban;
			}

			// 푸시받기
			Log.d("test", "푸쉬 메시지를 받습니다.");

			GCMRegistrar.checkDevice(SpeedAlimActivity.this);

			GCMRegistrar.checkManifest(SpeedAlimActivity.this);

			if (GCMRegistrar.getRegistrationId(SpeedAlimActivity.this).length() == 0) {
				GCMRegistrar.register(SpeedAlimActivity.this, PROJECT_ID);
				mManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
						SystemClock.elapsedRealtime(), syncTime, sender);

			}
			loadDb();

		}

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					mFlag = false;
				}
			}
		};

		final ImageButton changeId = (ImageButton) findViewById(R.id.firstLogin);
		changeId.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				openOptionsMenu();
			}
		});

	}

	public void loadDb() {
		openDb();

		// DB 유무 검사
		Cursor cursor = db.rawQuery(
				"SELECT DAY FROM daytable ORDER BY DAY desc", null);
		boolean dbExist = true;

		if (cursor.getCount() > 0) {
			dbExist = true;
		} else {
			dbExist = false;
		}

		// 새알림장 검사
		Cursor cursor_newNotice = db.rawQuery("SELECT newNote FROM NEWNOTE",
				null);
		cursor_newNotice.moveToFirst();
		newNoticeId = cursor_newNotice.getInt(0);
		cursor_newNotice.close();
		db.close();

		if (dbExist && newNoticeId == 0) {
			openDb();
			Cursor cursor2 = db.rawQuery(
					"SELECT DAY,newNote FROM daytable ORDER BY DAY desc", null);
			cursor2.moveToFirst();
			do {
				db_Day = cursor2.getString(0);
				data = new Custom_List_Data();
				data.Data = db_Day;
				data.newNote = cursor2.getInt(1);
				dateList.add(data);
			} while (cursor2.moveToNext());
			Log.d("dd", "로드디비");
			listAdapter.notifyDataSetChanged();
			dateListView.invalidate();

			cursor2.close();
			db.close();
			// //syncsum();

		} else {
			if (!dbExist)
				newNoticeId = 0;
			// gcm 정말 파싱.
			// TODO: handle exception
			dateList.clear();
			listAdapter.notifyDataSetChanged();

			openDb();

			db.delete("daytemp", null, null);
			threadparse.setPriority(Thread.MIN_PRIORITY);
			threadparse.start();

		}

	}

	public void openDb() {

		mHelper = new DayHelper(SpeedAlimActivity.this);
		db = mHelper.getReadableDatabase();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		finish();
		startActivity(new Intent(this, SpeedAlimActivity.class));
		// openDb();
		// Cursor cursor2 = db.rawQuery(
		// "SELECT DAY,newNote FROM daytable ORDER BY DAY desc", null);
		// cursor2.moveToFirst();
		// do {
		// db_Day = cursor2.getString(0);
		// data = new Custom_List_Data();
		// data.Data = db_Day;
		// data.newNote = cursor2.getInt(1);
		// dateList.add(data);
		// } while (cursor2.moveToNext());
		// Log.d("dd", "로드디비");
		//
		// listAdapter.notifyDataSetChanged();
		// dateListView.invalidate();
		//
		// cursor2.close();
		// db.close();
	}

	public synchronized void loadSqlite() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 0, 0, "학반 새로 고르기").setIcon(
				android.R.drawable.ic_menu_agenda);

		menu.add(0, 1, 0, "커뮤니티").setIcon(android.R.drawable.ic_menu_help);
		menu.add(0, 2, 0, "별점주기").setIcon(android.R.drawable.star_off);

		// menu.add(0, 2, 0, "알림 설정").setIcon(
		// android.R.drawable.ic_menu_info_details);

		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 0:
			// 처리할 이벤트
			// 등록아이디 삭제
			GCMRegistrar.unregister(SpeedAlimActivity.this);

			// 알림장서버에서 삭제하기
			callUrl("http://clicknote.cafe24.com/gcm/delete.jsp?reg_id="
					+ GCMRegistrar.getRegistrationId(SpeedAlimActivity.this));

			openDb();
			db.execSQL("update idtable set school='0',grade='0',ban='0' where _id like '1';");
			db.execSQL("update prevsum set daysum='0' where _id like '1';");
			db.delete("daytable", null, null);
			db.close();
			startActivity(new Intent(this, FirstLogin.class));
			finish();
			break;

		case 1:
			// 처리할 이벤트

			Uri uri = Uri.parse("http://clicknote.cafe24.com/xe/board4");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);
			startActivity(intent);

			break;

		case 2:
			// 처리할 이벤트
			Uri uri2 = Uri
					.parse("market://details?id=kr.co.moon.speedalim.noad");
			Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
			intent2.addCategory(Intent.CATEGORY_BROWSABLE);
			startActivity(intent2);
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
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

	private class DownloadText extends Thread {
		final Handler mHandler = new Handler();
		StringBuilder text;

		public void run() {
			try {
				text = new StringBuilder();
				text.append("");

				URL url = new URL("http://actoz.dothome.co.kr/ver.txt");
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
					} else {
					}
					conn.disconnect();
				}

				mHandler.post(new Runnable() {
					public void run() {
						try {
							newVer = Integer.parseInt((text.toString()));

						} catch (NumberFormatException nfe) {
							// TODO: handle exception
						}
						if (curVer < newVer) {

							AlertDialog.Builder dlg = new AlertDialog.Builder(
									SpeedAlimActivity.this);
							dlg.setTitle("업데이트 알림");
							dlg.setMessage("새버전이 있습니다. 업데이트를 해주세요!");
							dlg.setIcon(R.drawable.search);
							dlg.setNegativeButton("업데이트 바로 가기",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {

											Uri uri = Uri
													.parse("market://details?id=kr.co.moon.speedalim.noad");
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
					}
				});
			} catch (Exception ex) {
			}

		}
	}

	public void callUrl(String webUrl) {
		try {
			URL url = new URL(webUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn != null) {
				conn.setConnectTimeout(1000); // 1초 동안 인터넷 연결을 실패할경우 Fall 처리
				conn.setUseCaches(false);
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					Log.d("dd", "호출 성공");

				}
				conn.disconnect();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private class NoticeThread extends Thread {
		final Handler mHandler = new Handler();
		StringBuilder text;

		public void run() {
			try {
				text = new StringBuilder();
				text.append("");

				URL url = new URL("http://actoz.dothome.co.kr/notice.txt");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				if (conn != null) {
					conn.setConnectTimeout(1000); // 1초 동안 인터넷 연결을 실패할경우 Fall 처리
					conn.setUseCaches(false);
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(conn.getInputStream()));
						for (;;) {
							String line = br.readLine();
							if (line == null)
								break;
							text.append(line);
						}
						br.close();
					}
					conn.disconnect();
				}

				mHandler.post(new Runnable() {
					public void run() {

						if (text.length() != idPrefs.getInt("noticeConfirm", 0)
								&& text.length() > 0) {
							// AlertDialog.Builder dlg = new
							// AlertDialog.Builder(
							// SpeedAlimActivity.this);
							// dlg.setTitle("공지");
							// dlg.setMessage(text);
							// dlg.setIcon(R.drawable.search);
							// dlg.setNegativeButton("확인",
							// new DialogInterface.OnClickListener() {
							// public void onClick(
							// DialogInterface dialog,
							// int whichButton) {
							// editor.putInt("noticeConfirm",
							// text.length());
							// editor.commit();
							// }
							// });
							// dlg.setCancelable(true);
							// dlg.show();

							// 다이얼로그 생성
							final Dialog dialog = new Dialog(
									SpeedAlimActivity.this);
							// 다이얼로그의 윈도우 얻기
							Window window = dialog.getWindow();
							// 다이얼로그가 가리는 윈도우를 흐릿하게 만든다
							window.setFlags(
									WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
									WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
							// 타이틀을 설정한다
							dialog.setTitle("공지사항");
							/*
							 * setContentView() 하기전에윈도우 아이콘을 왼쪽에 추가하기 위해서윈도우
							 * 기능확장을 활성화한다
							 */

							dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
							// 레이아웃을 인플레이트 시킨다
							dialog.setContentView(R.layout.notice_dialog);
							/*
							 * 윈도우 왼쪽에 리소스 ID 위치의 이미지를 그린다이 함수 호출전에
							 * requestWindowFeature()하고 setContentView()을 한다
							 */
							window.setFeatureDrawableResource(
									Window.FEATURE_LEFT_ICON, R.drawable.search);
							TextView notice_tv = (TextView) dialog
									.findViewById(R.id.server_textView);
							notice_tv.setText(text);
							Button sb = (Button) dialog
									.findViewById(R.id.server_search_button);
							sb.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Log.e("test", "bb");
									editor.putInt("noticeConfirm",
											text.length());
									editor.commit();
									dialog.cancel();
								}
							});

							dialog.show();

						}

					}
				});
			} catch (Exception ex) {
			}

		}
	}

	class MyThread extends Thread {

		final Handler mHandler = new Handler();

		// 쓰레드 ( 로딩바 )
		public synchronized void run() {

			Log.d("dd", "파서 스레드 시작" + mSchool + grade + ban);
			try {

				// 데이터 처리

				ContentValues row = new ContentValues();
				URL url = new URL(xmlUrl);
				XmlPullParserFactory parserCreator = XmlPullParserFactory
						.newInstance();
				XmlPullParser parser = parserCreator.newPullParser();

				// mHandler.post(new Runnable() {
				// public void run() {
				// progressDialog = ProgressDialog.show(
				// SpeedAlimActivity.this, "",
				// "데이터를 가져오고 있습니다!");
				// progressDialog.setCancelable(true);
				//
				// TimerTask myTask = new TimerTask() {
				// public void run() {
				// progressDialog.dismiss();
				// }
				// };
				// Timer timer = new Timer();
				// timer.schedule(myTask, 5000); // 5초후 실행하고 종료
				// }
				// });

				parser.setInput(url.openStream(), null);

				int parserEvent = parser.getEventType();
				String tag = null;

				boolean checkDate = false;

				boolean checkContent1 = false;
				boolean checkContent2 = false;
				boolean checkContent3 = false;
				boolean checkContent4 = false;
				boolean checkContent5 = false;
				boolean checkContent6 = false;
				boolean checkContent7 = false;
				boolean checkContent8 = false;
				boolean checkContent9 = false;
				boolean checkContent10 = false;

				while (parserEvent != XmlPullParser.END_DOCUMENT) {

					switch (parserEvent) {

					case XmlPullParser.START_TAG:
						tag = parser.getName();

						if (tag.compareTo("date") == 0) {
							checkDate = true;
						} else if (tag.compareTo("content1") == 0) {
							checkContent1 = true;
						} else if (tag.compareTo("content2") == 0) {
							checkContent2 = true;
						} else if (tag.compareTo("content3") == 0) {
							checkContent3 = true;
						} else if (tag.compareTo("content4") == 0) {
							checkContent4 = true;
						} else if (tag.compareTo("content5") == 0) {
							checkContent5 = true;
						} else if (tag.compareTo("content6") == 0) {
							checkContent6 = true;
						} else if (tag.compareTo("content7") == 0) {
							checkContent7 = true;
						} else if (tag.compareTo("content8") == 0) {
							checkContent8 = true;
						} else if (tag.compareTo("content9") == 0) {
							checkContent9 = true;
						} else if (tag.compareTo("content10") == 0) {
							checkContent10 = true;
						}

						break;

					case XmlPullParser.TEXT:

						if (checkDate) {
							row.put("DAY", parser.getText());
							row.put("newNote", newNoticeId);
						} else if (checkContent1) {
							row.put("content1", parser.getText());
						} else if (checkContent2) {
							row.put("content2", parser.getText());
						} else if (checkContent3) {
							row.put("content3", parser.getText());
						} else if (checkContent4) {
							row.put("content4", parser.getText());
						} else if (checkContent5) {
							row.put("content5", parser.getText());
						} else if (checkContent6) {
							row.put("content6", parser.getText());
						} else if (checkContent7) {
							row.put("content7", parser.getText());
						} else if (checkContent8) {
							row.put("content8", parser.getText());
						} else if (checkContent9) {
							row.put("content9", parser.getText());
						} else if (checkContent10) {
							row.put("content10", parser.getText());

							if (row != null) {
								row.put("school", mSchool);
								row.put("grade", grade);
								row.put("ban", ban);
							}
						}

						break;

					case XmlPullParser.END_TAG:
						tag = parser.getName();
						if (tag.compareTo("date") == 0) {
							checkDate = false;
						} else if (tag.compareTo("content1") == 0) {
							checkContent1 = false;
						} else if (tag.compareTo("content2") == 0) {
							checkContent2 = false;
						} else if (tag.compareTo("content3") == 0) {
							checkContent3 = false;
						} else if (tag.compareTo("content4") == 0) {
							checkContent4 = false;
						} else if (tag.compareTo("content5") == 0) {
							checkContent5 = false;
						} else if (tag.compareTo("content6") == 0) {
							checkContent6 = false;
						} else if (tag.compareTo("content7") == 0) {
							checkContent7 = false;
						} else if (tag.compareTo("content8") == 0) {
							checkContent8 = false;
						} else if (tag.compareTo("content9") == 0) {
							checkContent9 = false;
						} else if (tag.compareTo("content10") == 0) {
							checkContent10 = false;

							db.insert("daytemp", null, row);
							// db.execSQL("insert or replace into daytable (_id, school, grade, ban) values (1, '"
							// + mSchool+ "', '"+grade+"', '"+ban+"')");
						}

						break;
					}

					parserEvent = parser.next();

				}

				mHandler.post(new Runnable() {
					public void run() {
						Log.d("dd", "noticeId" + newNoticeId);

						if (newNoticeId == 0) {
						} else {
							Cursor cursor_local = db
									.rawQuery(
											"SELECT DAY FROM daytable ORDER BY DAY desc",
											null);
							cursor_local.moveToFirst();
							Cursor cursor_temp = db
									.rawQuery(
											"SELECT DAY FROM daytemp ORDER BY DAY desc",
											null);
							cursor_temp.moveToFirst();

							do {

								do {

									if (cursor_temp.getInt(0) == cursor_local
											.getInt(0)) {
										db.execSQL("update daytemp set newNote='0' where DAY like '"
												+ cursor_temp.getInt(0) + "';");

									}

								} while (cursor_local.moveToNext());
								cursor_local.moveToFirst();
							} while (cursor_temp.moveToNext());

						}

						db.delete("daytable", null, null);
						db.execSQL("INSERT INTO daytable SELECT * FROM daytemp");

						Cursor cursor = db
								.rawQuery(
										"SELECT DAY,newNote FROM daytable ORDER BY DAY desc",
										null);
						cursor.moveToFirst();
						if (cursor.getCount() > 0) {

							int sumDay;
							Cursor cursor3 = db.rawQuery(
									"SELECT sum(day) FROM daytable", null);
							cursor3.moveToFirst();
							sumDay = cursor3.getInt(0);
							Log.d("dd", "날짜 맞추기 파싱sumDay" + sumDay);
							db.execSQL("update prevsum set daysum='" + sumDay
									+ "' where _id like '1';");
							cursor3.close();

							do {
								db_Day = cursor.getString(0);
								data = new Custom_List_Data();
								data.Data = db_Day;
								data.newNote = cursor.getInt(1);
								dateList.add(data);
							} while (cursor.moveToNext());

							listAdapter.notifyDataSetChanged();
							dateListView.invalidate();

						} else {
							Toast.makeText(
									SpeedAlimActivity.this,
									"알림장이 안나오네요.^^;; \n 1. 학반을 잘못 골랐거나 \n 2. 네트워크가 문제거나 \n 3. 특정 어플과 충돌 때문이에요. ㅠㅠ",
									Toast.LENGTH_LONG).show();
						}
						cursor.close();
						db.execSQL("update idtable set school='" + mSchool
								+ "', grade='" + grade + "', ban='" + ban
								+ "' where _id like '1';");

						ContentValues row3 = new ContentValues();
						row3.put("newNote", 0);
						db.update("NEWNOTE", row3, "_id=1", null);
						db.close();

					}
				});
			}

			catch (Exception e) {

				e.printStackTrace();

			}

		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (adView != null) {
			adView.destroy();
			adView = null;
		}
		GCMRegistrar.onDestroy(this);

	}

}
