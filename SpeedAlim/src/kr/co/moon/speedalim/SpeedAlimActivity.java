package kr.co.moon.speedalim;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
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
	private AlertDialog mDialog = null;
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
	int refreshDay;

	// static final String TYPEFACE_NAME = "nanum_pen.ttf.mp3";
	static Typeface mTypeface = null;
	public static String PROJECT_ID = "178050819419";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initAdam();
		popIntent = getIntent();
		mContext = this;

		mManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		intent = new Intent(SpeedAlimActivity.this, AlarmReceiver.class);
		sender = PendingIntent.getBroadcast(SpeedAlimActivity.this, 0, intent,
				0);
		// syncTime = 1000 * 10;
		syncTime = 1000 * 60 * 30 + 1000 * 30 * 10
				* (int) (((int) (Math.random() * 10) + 2) / 2);
		Log.d("dd", "��ũŸ��=" + syncTime);

		idPrefs = getSharedPreferences("id", MODE_PRIVATE);
		editor = idPrefs.edit();

		curVer = 55;

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
		refreshDay = idPrefs.getInt("REFRESH", 0);

		// ���̵� ���� Ȯ��

		if (grade.equals("null") | ban.equals("null") | mSchool.equals("null")) {
			// C2dm_BroadcastReceiver.c2dmIdCreate(this);
			mManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
					SystemClock.elapsedRealtime(), syncTime, sender);
			Intent firstLogin = new Intent(this, FirstLogin.class);
			startActivity(firstLogin);
			finish();

		} else {
			ConnectivityManager manager = (ConnectivityManager) this
					.getSystemService(this.CONNECTIVITY_SERVICE);
			NetworkInfo mobile = manager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			NetworkInfo wifi = manager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

			if (mobile.isConnected() || wifi.isConnected()) {
				// WIFI, 3G ��������� ������� �ʾ�����
				Log.d("dd", "Network connect success");
				DownloadText checkVer = new DownloadText();
				checkVer.start();

				NoticeThread noticeCheck = new NoticeThread();
				noticeCheck.start();
			} else {
				Log.d("dd", "Network connect fail");
				AlertDialog.Builder dlg = new AlertDialog.Builder(
						SpeedAlimActivity.this);
				dlg.setTitle("�˸�");
				dlg.setMessage("��Ʈ��ũ ������ Ȯ���ϰ�, �ٽ� �������ּ���!");
				dlg.setIcon(R.drawable.search);
				dlg.setNegativeButton("����",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								finish();
							}
						});
				dlg.setCancelable(true);
				dlg.show();
			}

			// ��Ʈ��ũ ���� Ȯ��
			// checkNetwokState();
			finishToast = Toast.makeText(SpeedAlimActivity.this,
					"'�ڷ�'��ư�� �ѹ� �� �����ø� ����˴ϴ�.", Toast.LENGTH_SHORT);
			// -------------���� ���� -----------

			// �˶� �Ŵ����� ���

			title1.setTypeface(mTypeface);
			title1.setText(grade);

			title2.setTypeface(mTypeface);
			title2.setText("�г�");

			title3.setTypeface(mTypeface);
			title3.setText(ban);

			title4.setTypeface(mTypeface);
			title4.setText("��");

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
					Log.d("dd", "�ε���");
					listAdapter.notifyDataSetChanged();
					dateListView.invalidate();

					cursor2.close();
					db.close();
					Intent intent = new Intent(SpeedAlimActivity.this,
							DailyList.class);
					intent.putExtra("day", day);
					// ����Ʈ �߰��κ�
					intent.putExtra("grade", grade);
					intent.putExtra("ban", ban);
					// ����Ʈ �߰��κ� ��

					startActivity(intent);

				}
			});

			if (Integer.parseInt(ban) < 10) {
				gcmSchoolId = mSchool + "0" + grade + "0" + ban;
			} else {
				gcmSchoolId = mSchool + "0" + grade + ban;
			}

			// Ǫ�ùޱ�
			Log.d("test", "Ǫ�� �޽����� �޽��ϴ�.");

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
		final ImageButton refresh = (ImageButton) findViewById(R.id.refresh);
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
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
		});

	}

	private AlertDialog createDialog() {

		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("�˸�");
		Calendar c = Calendar.getInstance();
		final int day = c.get(Calendar.DAY_OF_MONTH);
		if (refreshDay != day) {
			String alert1 = "�������� �ø��� �˸����� ������ �����ɴϴ�.";
			String alert2 = "�˸����� ����� �������� ���� ��� �����ּ���.";
			String alert3 = "���ͳ��� �����Ҷ��� �����ּ���!";
			String alert4 = "�Ϸ翡 �ѹ��� �����մϴ�.";
			ab.setMessage(alert1 + "\n" + alert2 + "\n" + alert3 + "\n"
					+ alert4);
			ab.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {

					editor.putInt("REFRESH", day);
					editor.commit();
					refreshDay = day;
					Log.d("day", "" + day);

					dateList.clear();
					listAdapter.notifyDataSetChanged();
					openDb();
					db.delete("daytemp", null, null);
					threadparse = new MyThread();
					threadparse.setPriority(Thread.MIN_PRIORITY);
					threadparse.start();
				}

			});
			ab.setNegativeButton("���", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {

				}

			});
		} else {
			String alert = "������ �̹� ���ΰ�ħ�� �߽��ϴ�.";
			ab.setMessage(alert);
			ab.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {

				}

			});
		}

		ab.setCancelable(false);
		ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));

		return ab.create();
	}

	public void loadDb() {
		openDb();

		// DB ���� �˻�
		Cursor cursor = db.rawQuery(
				"SELECT DAY FROM daytable ORDER BY DAY desc", null);
		boolean dbExist = true;

		if (cursor.getCount() > 0) {
			dbExist = true;
		} else {
			dbExist = false;
		}

		// ���˸��� �˻�
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
			Log.d("dd", "�ε���");
			listAdapter.notifyDataSetChanged();
			dateListView.invalidate();

			cursor2.close();
			db.close();
			// //syncsum();

		} else {
			if (!dbExist)
				newNoticeId = 0;
			// gcm ���� �Ľ�.
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
		// Log.d("dd", "�ε���");
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

		menu.add(0, 0, 0, "�й� ���� ����").setIcon(
				android.R.drawable.ic_menu_agenda);

		menu.add(0, 1, 0, "Ŀ�´�Ƽ").setIcon(android.R.drawable.ic_menu_help);
		menu.add(0, 2, 0, "�����ֱ�").setIcon(android.R.drawable.star_off);
		menu.add(0, 3, 0, "������ ��ġ").setIcon(
				android.R.drawable.stat_sys_download);
		menu.add(0, 4, 0, "���� ũ�� ����").setIcon(R.drawable.icon_font);

		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 0:
			// ó���� �̺�Ʈ
			// ��Ͼ��̵� ����
			GCMRegistrar.unregister(SpeedAlimActivity.this);

			// �˸��弭������ �����ϱ�
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
			// ó���� �̺�Ʈ

			Uri uri = Uri.parse("http://clicknote.cafe24.com/xe/board4");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);
			startActivity(intent);

			break;

		case 2:
			// ó���� �̺�Ʈ
			Uri uri2 = Uri.parse("market://details?id=kr.co.moon.speedalim");
			Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
			intent2.addCategory(Intent.CATEGORY_BROWSABLE);
			startActivity(intent2);
			break;
		case 3:
			// ó���� �̺�Ʈ
			Uri uri3 = Uri.parse("market://details?id=kr.co.moon.speedalim.yb");
			Intent intent3 = new Intent(Intent.ACTION_VIEW, uri3);
			intent3.addCategory(Intent.CATEGORY_BROWSABLE);
			startActivity(intent3);
			break;
		case 4:
			// ó���� �̺�Ʈ
			if (idPrefs.getInt("FONT", 2) == 2) {
				Toast.makeText(SpeedAlimActivity.this, "�˸��� ���� ���ڰ� �۾����ϴ�.",
						Toast.LENGTH_LONG).show();
				editor.putInt("FONT", 1);
				editor.commit();

			} else {
				Toast.makeText(SpeedAlimActivity.this, "�˸��� ���� ���ڰ� Ŀ���ϴ�.",
						Toast.LENGTH_LONG).show();
				editor.putInt("FONT", 2);
				editor.commit();
			}
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
						try {
							newVer = Integer.parseInt((text.toString()));

							if (curVer < newVer) {

								AlertDialog.Builder dlg = new AlertDialog.Builder(
										SpeedAlimActivity.this);
								dlg.setTitle("������Ʈ �˸�");
								dlg.setMessage("�������� �ֽ��ϴ�. ������Ʈ�� ���ּ���!");
								dlg.setIcon(R.drawable.search);
								dlg.setNegativeButton("������Ʈ �ٷ� ����",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {

												Uri uri = Uri
														.parse("market://details?id=kr.co.moon.speedalim");
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

	public void callUrl(String webUrl) {
		try {
			URL url = new URL(webUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn != null) {
				conn.setConnectTimeout(1000); // 1�� ���� ���ͳ� ������ �����Ұ�� Fall ó��
				conn.setUseCaches(false);
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					Log.d("dd", "ȣ�� ����");

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
					conn.setConnectTimeout(1000); // 1�� ���� ���ͳ� ������ �����Ұ�� Fall ó��
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
							// dlg.setTitle("����");
							// dlg.setMessage(text);
							// dlg.setIcon(R.drawable.search);
							// dlg.setNegativeButton("Ȯ��",
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

							// ���̾�α� ����
							final Dialog dialog = new Dialog(
									SpeedAlimActivity.this);
							// ���̾�α��� ������ ���
							Window window = dialog.getWindow();
							// ���̾�αװ� ������ �����츦 �帴�ϰ� �����
							window.setFlags(
									WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
									WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
							// Ÿ��Ʋ�� �����Ѵ�
							dialog.setTitle("��������");
							/*
							 * setContentView() �ϱ����������� �������� ���ʿ� �߰��ϱ� ���ؼ�������
							 * ���Ȯ���� Ȱ��ȭ�Ѵ�
							 */

							dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
							// ���̾ƿ��� ���÷���Ʈ ��Ų��
							dialog.setContentView(R.layout.notice_dialog);
							/*
							 * ������ ���ʿ� ���ҽ� ID ��ġ�� �̹����� �׸����� �Լ� ȣ������
							 * requestWindowFeature()�ϰ� setContentView()�� �Ѵ�
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

		// ������ ( �ε��� )
		public synchronized void run() {

			Log.d("dd", "�ļ� ������ ����" + mSchool + grade + ban);
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
							Log.d("dd", "��¥ ���߱� �Ľ�sumDay" + sumDay);
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
									"�˸����� �ȳ����׿�.^^;; \n 1. �й��� �߸� ����ų� \n 2. ��Ʈ��ũ�� �����ų� \n 3. Ư�� ���ð� �浹 �����̿���. �Ф�",
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

	private void initAdam() {
		// Ad@m sdk �ʱ�ȭ ����
		adView = (AdView) findViewById(R.id.adview);
		// ���� ������ ����
		// 1. ���� Ŭ���� ������ ������
		adView.setOnAdClickedListener(new OnAdClickedListener() {
			@Override
			public void OnAdClicked() {
				Log.i(LOGTAG, "���� Ŭ���߽��ϴ�.");
			}
		});
		// 2. ���� �����ޱ� �������� ��쿡 ������ ������
		adView.setOnAdFailedListener(new OnAdFailedListener() {
			@Override
			public void OnAdFailed(AdError error, String message) {
				Log.w(LOGTAG, "" + message);
			}
		});
		// 3. ���� ���������� �����޾��� ��쿡 ������ ������
		adView.setOnAdLoadedListener(new OnAdLoadedListener() {
			@Override
			public void OnAdLoaded() {
				Log.i(LOGTAG, "���� ���������� �ε��Ǿ����ϴ�.");
			}
		});
		// 4. ���� �ҷ��ö� ������ ������
		adView.setOnAdWillLoadListener(new OnAdWillLoadListener() {
			@Override
			public void OnAdWillLoad(String url) {
				Log.i(LOGTAG, "���� �ҷ��ɴϴ�. : " + url);
			}
		});
		// 5. ������ ���� �ݾ����� ������ ������
		adView.setOnAdClosedListener(new OnAdClosedListener() {
			@Override
			public void OnAdClosed() {
				Log.i(LOGTAG, "���� �ݾҽ��ϴ�.");
			}
		});
		// �Ҵ� ���� clientId ����
		// adView.setClientId(��TestClientId��);

		// ���� ���� �ֱ⸦ 12�ʷ� ����
		// adView.setRequestInterval(12);
		// ���� ������ ĳ�� ��� ���� : �⺻ ���� true
		adView.setAdCache(false);
		// Animation ȿ�� : �⺻ ���� AnimationType.NONE
		adView.setAnimationType(AnimationType.FLIP_HORIZONTAL);
		adView.setVisibility(View.VISIBLE);
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
