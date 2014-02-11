package a.kr.co.moon.result;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
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
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NoticeThread extends Activity {
	/** Called when the activity is first created. */
	static final int notiID = 1;
	static String grade = "null";
	static String ban = "null";
	static String mSchool;
	static int toFirst;
	int daySum;
	static int syncTime;
	int newVer;
	int curVer;

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

	// static final String TYPEFACE_NAME = "nanum_pen.ttf.mp3";
	static Typeface mTypeface = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		popIntent = getIntent();

		syncTime = 1000 * 5;
		curVer = 28;
		DownloadText checkVer = new DownloadText();
		checkVer.start();
		// ��Ʈ��ũ ���� Ȯ��
		// checkNetwokState();
		finishToast = Toast.makeText(NoticeThread.this,
				"'�ڷ�'��ư�� �ѹ� �� �����ø� ����˴ϴ�.", Toast.LENGTH_SHORT);
		// -------------���� ���� -----------

		// �˶� �Ŵ����� ���
		mManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		intent = new Intent(NoticeThread.this, AlarmReceiver.class);

		sender = PendingIntent.getBroadcast(NoticeThread.this, 0, intent,
				0);
		if (NoticeThread.mTypeface == null) {
			NoticeThread.mTypeface = Typeface.createFromAsset(getAssets(),
					"font.ttf");
		}
//		mManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
//				SystemClock.elapsedRealtime(), syncTime, sender);

		title1 = (TextView) findViewById(R.id.title1);
		title2 = (TextView) findViewById(R.id.title2);
		title3 = (TextView) findViewById(R.id.title3);
		title4 = (TextView) findViewById(R.id.title4);

		threadparse = new MyThread();

		// DB
		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();

		grade = idPrefs.getString("GRADE", "null");
		ban = idPrefs.getString("BAN", "null");
		mSchool = idPrefs.getString("SCHOOLID", "null");

		// ���̵� ���� Ȯ��

		if (grade == "null" | ban == "null" | mSchool == "null") {
			// C2dm_BroadcastReceiver.c2dmIdCreate(this);

			Intent firstLogin = new Intent(this, FirstLogin.class);
			startActivity(firstLogin);
			finish();

		} else {

			title1.setTypeface(mTypeface);
			title1.setText(grade);

			title2.setTypeface(mTypeface);
			title2.setText("�г�");

			title3.setTypeface(mTypeface);
			title3.setText(ban);

			title4.setTypeface(mTypeface);
			title4.setText("��");

		}

		xmlUrl = String.format(

		"http://nurinamu.net/cc/xml.jsp?nick_name=%s0%s0%s", mSchool, grade,
				ban);
		noXmlUrl = String.format(

		"http://nurinamu.net/cc/notice.jsp?nick_name=%s0%s0%s", mSchool, grade,
				ban);

		dateList = new ArrayList<Custom_List_Data>();

		listAdapter = new Custom_List_Adapter(this, R.layout.customlist,
				dateList);
		dateListView = (ListView) findViewById(R.id.dateList);
		dateListView.setCacheColorHint(Color.rgb(143, 58, 12));
		dateListView.setAdapter(listAdapter);

		dateListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				// Toast.makeText(SpeedAlimActivity.this,
				// dateList.get(position),
				// Toast.LENGTH_SHORT).show();
				String day = dateList.get(position).Data;

				Intent intent = new Intent(NoticeThread.this,
						DailyList.class);
				intent.putExtra("day", day);
				// ����Ʈ �߰��κ�
				intent.putExtra("grade", grade);
				intent.putExtra("ban", ban);
				// ����Ʈ �߰��κ� ��

				startActivity(intent);

			}
		});

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

		// final ImageButton notice_Act = (ImageButton)
		// findViewById(R.id.noticelayoutbtn);
		// notice_Act.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// startActivity(new Intent(SpeedAlimActivity.this, Notice.class));
		// }
		// });

		// final Button refreshBtn = (Button) findViewById(R.id.refreshBtn);
		// refreshBtn.setTypeface(mTypeface);
		// refreshBtn.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		//
		// // ---------------------------�Ľ� ------------
		// threadParse();
		//
		// }
		// });

		// DB �ε�-------------------
		mHelper = new DayHelper(NoticeThread.this);
		db = mHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT DAY FROM daytable ORDER BY DAY desc", null);

		while (cursor.moveToNext()) {
			db_Day = cursor.getString(0);
			data = new Custom_List_Data();
			data.Data = db_Day;
			dateList.add(data);
		}
		listAdapter.notifyDataSetChanged();
		dateListView.invalidate();
		db.close();
		cursor.close();

		if (!db.isDbLockedByOtherThreads()) {

		}

		if (db_Day == null) {
			mHelper = new DayHelper(NoticeThread.this);
			db = mHelper.getWritableDatabase();
			threadparse.start();
		} else if (newNotice()) {
			mHelper = new DayHelper(NoticeThread.this);
			db = mHelper.getWritableDatabase();
			db.delete("daytable", null, null);
			db.delete("websum", null, null);
			dateList.clear();
			listAdapter.notifyDataSetChanged();
			threadparse.start();

		}

		

	}

	boolean newNotice() {
		mHelper = new DayHelper(NoticeThread.this);
		db = mHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT notice FROM onsync WHERE _id LIKE '%1%';", null);
		cursor.moveToFirst();
		if (cursor.getInt(0) == 1) {
			cursor.close();
			db.close();
			return true;
		} else {
			cursor.close();
			db.close();
			return false;
		}

	}


	

	void offThread() {
		mHelper = new DayHelper(this);
		db = mHelper.getWritableDatabase();
		db.execSQL("update onsync set notice='0' where _id like '1'");
		db.close();
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

			// ����� �� ��Ʈ��ũ ���� üũ�� ������� ������ �� �ȵɶ� ��� ��� ����
			AlertDialog.Builder dlg = new AlertDialog.Builder(
					NoticeThread.this);
			dlg.setTitle("��Ʈ��ũ ����");
			dlg.setMessage("'���ǵ� �˸���'�� ��Ʈ��ũ ������ �ʼ��Դϴ�. ��Ʈ��ũ ������ �ٽ� ���� ���ּ���.^^");
			dlg.setIcon(R.drawable.search);
			dlg.setNegativeButton("����", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {

				}
			});
			dlg.setCancelable(false);
			dlg.show();
			return false;
		}
	}

	public synchronized void loadSqlite() {

	}

	// Cursor ti = db.rawQuery("PRAGMA table_info(daytable)", null);
	// if ( ti.moveToFirst() ) {
	// do {
	// // System.out.println("col: " + ti.getString(1));
	// db_Day = ti.getString(1);
	// Toast.makeText(SpeedAlimActivity.this,
	// db_Day,
	// Toast.LENGTH_SHORT).show();
	//
	// } while (ti.moveToNext());
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 0, 0, "�й� ���� ����").setIcon(
				android.R.drawable.ic_menu_agenda);

		menu.add(0, 1, 0, "About").setIcon(android.R.drawable.ic_menu_help);

		// menu.add(0, 2, 0, "�˸� ����").setIcon(
		// android.R.drawable.ic_menu_info_details);

		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 0:
			// ó���� �̺�Ʈ
			mHelper = new DayHelper(NoticeThread.this);
			db = mHelper.getWritableDatabase();
			db.delete("daytable", null, null);
			db.delete("websum", null, null);
			startActivity(new Intent(this, FirstLogin.class));
			finish();
			break;

		case 1:
			// ó���� �̺�Ʈ

			startActivity(new Intent(this, About.class));

			break;

		// case 2:
		// // ó���� �̺�Ʈ
		// Intent i = new Intent(this, Setting.class);
		// startActivity(i);
		//
		// break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	class MyThread extends Thread {
		// db.delete("daytable", null, null);
		final Handler mHandler = new Handler();

		// ������ ( �ε��� )
		public synchronized void run() {

			try {

				// ������ ó��

				ContentValues row = new ContentValues();
				URL url = new URL(xmlUrl);
				XmlPullParserFactory parserCreator = XmlPullParserFactory
						.newInstance();
				XmlPullParser parser = parserCreator.newPullParser();

				// mHandler.post(new Runnable() {
				// public void run() {
				// progressDialog = ProgressDialog.show(
				// SpeedAlimActivity.this, "",
				// "�����͸� �������� �ֽ��ϴ�!");
				// progressDialog.setCancelable(true);
				//
				// TimerTask myTask = new TimerTask() {
				// public void run() {
				// progressDialog.dismiss();
				// }
				// };
				// Timer timer = new Timer();
				// timer.schedule(myTask, 5000); // 5���� �����ϰ� ����
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

							db.insert("daytable", null, row);
							// db.execSQL("insert or replace into daytable (_id, school, grade, ban) values (1, '"
							// + mSchool+ "', '"+grade+"', '"+ban+"')");
						}

						break;
					}

					parserEvent = parser.next();

				}

				mHandler.post(new Runnable() {
					public void run() {

						Cursor cursor = db.rawQuery(
								"SELECT DAY FROM daytable ORDER BY DAY desc",
								null);

						while (cursor.moveToNext()) {
							String db_Day = cursor.getString(0);
							data = new Custom_List_Data();
							data.Data = db_Day;
							dateList.add(data);
						}
						listAdapter.notifyDataSetChanged();
						dateListView.invalidate();

						db.close();
						cursor.close();
						offThread();

					}
				});
			}

			catch (Exception e) {

				e.printStackTrace();

			}

		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

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
					conn.setConnectTimeout(1000); // 1�� ���� ���ͳ� ������ �����Ұ�� Fall ó��
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
						newVer = Integer.parseInt((text.toString()));
						if (curVer < newVer) {
							AlertDialog.Builder dlg = new AlertDialog.Builder(
									NoticeThread.this);
							dlg.setTitle("������Ʈ �˸�");
							dlg.setMessage("�������� �ֽ��ϴ�. ������Ʈ�� ���ּ���!");
							dlg.setIcon(R.drawable.search);
							dlg.setNegativeButton("������Ʈ �ٷ� ����",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											Uri uri = Uri
													.parse("market://details?id=a.kr.co.moon.result");
											Intent intent = new Intent(
													Intent.ACTION_VIEW, uri);
											intent.addCategory(Intent.CATEGORY_BROWSABLE);
											startActivity(intent);
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
}
