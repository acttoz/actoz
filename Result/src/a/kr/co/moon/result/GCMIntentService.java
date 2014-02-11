package a.kr.co.moon.result;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import android.R.integer;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	String gcm_msg = null;
	public ServerRequest serverRequest_insert = null;
	DayHelper mHelper;
	SQLiteDatabase db;
	ContentValues dailyRow;

	// GCM�� ���������� ��ϵǾ������ �߻��ϴ� �޼ҵ�

	@Override
	protected void onRegistered(Context arg0, String arg1) {

		// TODO Auto-generated method stub

		Log.d("test", "���ID:" + arg1);

		HashMap<Object, Object> param = new HashMap<Object, Object>();

		param.put("regid", arg1);
		serverRequest_insert = new ServerRequest(
				"http://clicknote.cafe24.com/gcm/insert.jsp?nick_name="
						+ SpeedAlimActivity.gcmSchoolId + "&reg_id=" + arg1,
				param, mResHandler, mHandler);

		serverRequest_insert.start();

	}

	/**
	 * 
	 * ��û�� �ڵ鷯������ ����Ʈ�� ����
	 */

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {

			serverRequest_insert.interrupt();

			String result = msg.getData().getString("result");

			if (result.equals("success")) {

				// Toast.makeText(MainActivity.mContext,

				// "�����ͺ��̽��� regid�� ��ϵǾ����ϴ�.", 3000).show();

			} else {

				// Toast.makeText(MainActivity.mContext,

				// "�����ͺ��̽��� regid ����� �����Ͽ����ϴ�.", 3000).show();

			}

		}

	};

	/**
	 * 
	 * ��û�� response�� ���� �Ľ�ó��
	 */

	private ResponseHandler<String> mResHandler = new ResponseHandler<String>() {

		public String handleResponse(HttpResponse response)

		throws org.apache.http.client.ClientProtocolException,

		java.io.IOException {

			HttpEntity entity = response.getEntity();

			Message message = mHandler.obtainMessage();

			Bundle bundle = new Bundle();

			String result = EntityUtils.toString(entity).trim();

			if (result.equals("success")) {

				bundle.putString("result", "success");

			} else {

				bundle.putString("result", "fail");

			}

			message.setData(bundle);

			mHandler.sendMessage(message);

			return null;

		}

	};

	// GCM�� �����Ͽ������ �߻��ϴ� �޼ҵ�

	@Override
	protected void onUnregistered(Context arg0, String arg1) {

		Toast.makeText(arg0, "����ID:" + arg1, 3000).show();
		Log.d("test", "����ID:" + arg1);

	}

	// GCM�� �޽����� ���������� �߻��ϴ� �޼ҵ�

	@Override
	protected void onMessage(Context arg0, Intent arg1) {

		// TODO Auto-generated method stub
		Log.d("test", "�޽����� �Խ��ϴ� : " + arg1.getExtras().getString("test"));

		gcm_msg = arg1.getExtras().getString("test");

		// �˸��� ID ��ġ ���� �˻�
		DayHelper mHelper = new DayHelper(arg0);
		SQLiteDatabase db = mHelper.getReadableDatabase();
		 db.execSQL("update GCMWORK set work='1' where _id like '1';");
		Cursor cursor = db.rawQuery("SELECT _id FROM dayId WHERE dayId LIKE '%"
				+ gcm_msg + "%';", null);
		cursor.moveToFirst();
		if (cursor != null && cursor.getCount() > 0) {
			// �ߺ� ����
			Log.d("gcm", "�ߺ�����");

		} else {
			// ���˸�
			if (Integer.parseInt(gcm_msg) > 200000000) {
				// ����
				Log.d("gcm", "�������˸�");
				db.execSQL("update NEWNOTE set newNote='2' where _id like '1';");
			} else {
				// ����
				Log.d("gcm", "���˸�");
				db.execSQL("update NEWNOTE set newNote='1' where _id like '1';");
			}
			db.execSQL("INSERT INTO dayID (dayId) VALUES ('" + gcm_msg + "');");
		}
		cursor.close();
		db.close();

	}

	// ������ �ڵ鸵�ϴ� �޼ҵ�

	@Override
	protected void onError(Context arg0, String arg1) {

		Log.d("test", "" + arg1);

	}

	// ���� ������

	public GCMIntentService() {

		super(SpeedAlimActivity.PROJECT_ID);

		Log.d("test", "GCM���� ������ ����");

	}

	public void showMessage(Context arg0) {

		Thread thread = new Thread(new Runnable() {

			public void run() {

				handler.sendEmptyMessage(0);

			}

		});

		thread.start();

	}

	private Handler handler = new Handler() {

		// public void handleMessage(Message msg) {
		// Toast.makeText(arg0, "���� �޽��� : " + gcm_msg, 3000).show();
		// Log.d("test", "���Ÿ޼���" + gcm_msg);
		//
		// }

	};

}
