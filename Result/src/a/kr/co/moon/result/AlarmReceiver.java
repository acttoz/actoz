package a.kr.co.moon.result;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;



import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	// static String grade = "null";
	// static String ban = "null";
	// static String mSchool;
	// static String mNull = null;
	String URL = "content://a.kr.co.moon.result/db";
	ContentValues row;
	String mSchool;
	String grade;
	String ban;
	boolean newNote;
	int sum = 0;
	int newNoteId = 0;
	int noSum;
	int webSum = 0;
	int noWebSum = 0;
	Context context;
	PowerManager pm;
	PowerManager.WakeLock wl;

	// static String xmlUrl = "http://nurinamu1.cafe24.com/alim/sunam0503.jsp";
	// String xmlUrl = "http://www.kma.go.kr/XML/weather/sfc_web_map.xml";
	static String xmlUrl;
	static String noxmlUrl;
	// File idXml;
	// Toast toast;
	Cursor c2;
	Cursor c3;
	Cursor c4;
	Cursor c5;
	Cursor c;
	int currentTime;
	ContentResolver cr;
	boolean onSync;
	public static Activity mainActivity;

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Calendar cal = Calendar.getInstance();
		currentTime = (int) cal.get(Calendar.HOUR_OF_DAY);
		row = new ContentValues();
		cr = arg0.getContentResolver();
		//
		c2 = cr.query(Uri.parse(URL), null, "day", null, null);
		c4 = cr.query(Uri.parse(URL), null, "daytemp", null, null);
		c5 = cr.query(Uri.parse(URL), null, "newnote", null, null);
		c = cr.query(Uri.parse(URL), null, null, null, null);

		// c2.moveToFirst();
		// Toast.makeText(arg0, "알람", Toast.LENGTH_SHORT).show();

		c2.moveToFirst();
		c4.moveToFirst();
		c5.moveToFirst();

		sum = c2.getInt(0);

		newNoteId = c5.getInt(0);
		c5.close();
		if (newNoteId > 0) {
			newNote = true;
		} else {
			newNote = false;
		}

		c.moveToFirst();
		mSchool = c.getString(0);
		grade = c.getString(1);
		ban = c.getString(2);

		// Toast.makeText(arg0, grade + ban + mSchool,
		// Toast.LENGTH_SHORT).show();

		// if(c2.getString(0) != null)
		c.close();
		c2.close();
		// TODO Auto-generated method stub
		// DB
		context = arg0;

		xmlUrl = String.format(
				"http://clicknote.cafe24.com/cc/xmlp.jsp?nick_name=%s0%s0%s",
				mSchool, grade, ban);

		Log.d("dd", "현재시간=" + currentTime);
		Log.d("dd", "랜덤=" + 1000 * 60 * 12 * ((int) (Math.random() * 10) + 1));
		Log.d("dd", mSchool + " " + grade + "  " + ban + " " + sum);
		// 오후 4시에서 7시 사이에
		// 유실 예방

		if (mSchool.equals("0") || mSchool.equals("null")
				|| mSchool.equals(null) || !newNote) {
		} else {

			// Toast.makeText(context, "new" + newNoteId, Toast.LENGTH_SHORT)
			// .show();
			if (newNoteId == 1) {
				showNotification(context, R.drawable.ic_launcher,
						"새 알림장이 있습니다.", "");
			} else if (sum == 2) {
				showNotification(context, R.drawable.ic_launcher,
						"알림장에 수정사항이 있습니다.", "");
			}
			PushWakeLock.acquireCpuWakeLock(context);
			TimerTask myTask = new TimerTask() {
				public void run() {
					PushWakeLock.releaseCpuLock();
				}
			};
			Timer timer = new Timer();
			timer.schedule(myTask, 5000);
		}
		if (21 > currentTime && currentTime > 15) {
			if ((int) c4.getInt(0) == 0) {

				Log.d("dd", "폴링시작");
				MyThread thre = new MyThread();
				thre.setPriority(Thread.MIN_PRIORITY);
				thre.start();
				c4.close();
			}

		}

	}

	private void showNotification(Context context, int statusBarIconID,

	String statusBarTextID, String detailedTextID) {

		Intent contentIntent = new Intent(context, SpeedAlimActivity.class);
		contentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		// PendingIntent theappIntent =

		// PendingIntent.getActivity(context, 0, contentIntent,
		// PendingIntent.FLAG_UPDATE_CURRENT);

		PendingIntent theappIntent = PendingIntent.getActivity(context, 0,
				contentIntent, 0);

		Notification notif = new Notification(statusBarIconID, statusBarTextID,
				System.currentTimeMillis());

		notif.flags = Notification.FLAG_AUTO_CANCEL
				| Notification.FLAG_ONLY_ALERT_ONCE;

		notif.setLatestEventInfo(context, statusBarTextID, "확인하려면 눌러주세요~",
				theappIntent);

		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		nm.notify(1234, notif);

	}

	public static class PushWakeLock {
		private static PowerManager.WakeLock sCpuWakeLock;

		// private static KeyguardManager.KeyguardLock mKeyguardLock;
		// private static boolean isScreenLock;

		static void acquireCpuWakeLock(Context context) {
			Log.e("PushWakeLock", "Acquiring cpu wake lock");
			Log.e("PushWakeLock", "wake sCpuWakeLock = " + sCpuWakeLock);

			if (sCpuWakeLock != null) {
				return;
			}
			PowerManager pm = (PowerManager) context
					.getSystemService(Context.POWER_SERVICE);
			sCpuWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
					| PowerManager.ACQUIRE_CAUSES_WAKEUP
					| PowerManager.ON_AFTER_RELEASE, "hello");

			sCpuWakeLock.acquire();
		}

		static void releaseCpuLock() {
			Log.e("PushWakeLock", "Releasing cpu wake lock");
			Log.e("PushWakeLock", "relase sCpuWakeLock = " + sCpuWakeLock);

			if (sCpuWakeLock != null) {
				sCpuWakeLock.release();
				sCpuWakeLock = null;
			}
		}
	}

	class MyThread extends Thread {
		// db.delete("daytable", null, null);
		final Handler mHandler = new Handler();

		// 쓰레드 ( 로딩바 )
		public synchronized void run() {
			Log.d("dd", "동기화");
			try {
				// 데이터 처리

				URL url = new URL(xmlUrl);

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				if (conn != null) {
					conn.setConnectTimeout(1000); // 1초 동안 인터넷 연결을 실패할경우 Fall 처리
					conn.setUseCaches(false);
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						Log.d("dd", "연결 성공");
						int deleteWebSum2 = cr.delete(Uri.parse(URL), null,
								null);

					}
					conn.disconnect();
				}

				XmlPullParserFactory parserCreator = XmlPullParserFactory
						.newInstance();
				XmlPullParser parser = parserCreator.newPullParser();

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
							row.put("newNote", 0);
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
							Uri insertRslt = cr.insert(Uri.parse(URL), row);
						}

						break;
					}
					parserEvent = parser.next();

				}

				mHandler.post(new Runnable() {
					public void run() {
						Cursor c3 = cr.query(Uri.parse(URL), null, "websum",
								null, null);
						c3.moveToFirst();
						webSum = c3.getInt(0);
						Log.d("dd", "웹" + webSum);
						Log.d("dd", "로컬" + sum);
						c3.close();
						// Toast.makeText(context, " " + sum + "  " + webSum,
						// Toast.LENGTH_SHORT).show();

						if (webSum > sum) {
							Log.d("dd", "알림" + sum + "  " + webSum);

							showNotification(context, R.drawable.ic_launcher,
									"새 알림이 도착했습니다.", "");

							PushWakeLock.acquireCpuWakeLock(context);
							TimerTask myTask = new TimerTask() {
								public void run() {
									PushWakeLock.releaseCpuLock();
								}
							};
							Timer timer = new Timer();
							timer.schedule(myTask, 5000);
							ContentValues row3 = new ContentValues();
							row3.put("daysum", webSum);
							cr.update(Uri.parse(URL), row3, "_id=1", null);

						}
					}

				});

			}

			catch (Exception e) {

				e.printStackTrace();

			}

		}
	}

}
